package CO2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ControllerAction implements EventHandler<ActionEvent>{
    Model model ;
    ViewGame viewGame ;

    public ControllerAction(Model model, ViewGame viewGame) {
        this.model = model ;
        this.viewGame = viewGame ;
        viewGame.hboxAction.setButtonActionControler(this);
    }

    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource() ;
        if (source == viewGame.hboxAction.btnActionPrincipale) {
            viewGame.hboxAction.displayActionPrincipale() ;
        } else if (source == viewGame.hboxAction.btnActionGratuite){
            viewGame.hboxAction.displayActionGratuite() ;
        } else if (source == viewGame.hboxAction.btnCancelAction) {
            viewGame.hboxAction.resetHbox();
        }else if (source == viewGame.hboxAction.btnFinTour) { // appuyer sur bouton fin de tour
            verifOnuCard();
            finTour();
        } else if (source == viewGame.hboxAction.btnTest) {
            model.curPlayer.setCEP(0);
            model.curPlayer.setArgent(0);
            model.curPlayer.getContinentsControlles().get(0).setNbCep(0);
            viewGame.reloadCEP();
            viewGame.reloadArgent();
        }
        viewGame.reloadArgent();
    }

    private void distributionRevenu() {
        Player p = model.getCurrentPLayer();
        // pour chaque piste d'expertise
        for (PisteExpertise exp : model.getExpertises()) {
            // type d'energie
            greenEnergyTypes type = exp.getType();
            // niveau d'expertise du joueur dans ce type d'expertise
            int playerExp = p.getExpertise(type);
            // nombre de revenu correspondant
            int revenu = 0;
            if (playerExp > 0) revenu = exp.getPiste().get(playerExp-1).getNumero();

            // si il y a un revenu a distribuer
            if (revenu>0) {
                // boite de dialogue
                viewGame.hboxAction.dialogChoisirRevenu(type, revenu);
                if (viewGame.hboxAction.dialogChoisirRevenu != null) {
                    Optional<String> result = viewGame.hboxAction.dialogChoisirRevenu.showAndWait();
                    result.ifPresent(repartition -> {
                        // donne le revenu au joueur dans le model
                        model.giveRevenu(p, repartition);
                        viewGame.reloadArgent();
                        viewGame.reloadPointVictoire();
                        return;
                    });
                }
            }
        }
    }

    public void choisirScientifiqueExpertise() {
        viewGame.hboxAction.displayFinTourScientifiqueChoiceDialog() ;
        if(viewGame.hboxAction.dialogChoisirScientifique != null){
            Optional<Scientifique> result = viewGame.hboxAction.dialogChoisirScientifique.showAndWait();
            result.ifPresent(scientifiqueChoisi -> {
                model.getCurrentPLayer().addExpertise(greenEnergyTypes.SOLAR, 1);
                viewGame.reloadPlayerExpertise(model.getCurrentPLayer());
                return;
            });
        }
    }

    public void finTour(){
        // si le scientifique n'est pas retourné dans la réserve
        // l'expertise lors du retour d'un scientifique à la réserve est géré par le ControllerActionGratuite
        if (model.getCurrentPLayer().getCurrentScientifique().getContinent() != null){
            choisirScientifiqueExpertise();
        }
        // vérification du nombre de tour et décénnie
        model.TourSuivant();
        // si on est au tour 1 de n'importe quelle decenie sauf la première
        if ((model.getTour() == 1) && (model.getDecade() != 1970)) {
            // ~= phase d'approvisionnement
            distributionRevenu();
            //approvisionnement en énergie
            ArrayList<Continent> continentsEnBesoin = new ArrayList<>();
            if(model.getDecade() == 1970){
                continentsEnBesoin = verifierNbCentral(1);
            }
            else if(model.getDecade() == 1980){
                continentsEnBesoin = verifierNbCentral(2);
            }
            else if(model.getDecade() == 1990){
                continentsEnBesoin = verifierNbCentral(3);
            }
            else if(model.getDecade() == 2000){
                continentsEnBesoin = verifierNbCentral(4);
            }
            else if(model.getDecade() == 2010){
                continentsEnBesoin = verifierNbCentral(5);
            }
            //Seulement pour le multi :
            else if(model.getDecade() == 2020){
                continentsEnBesoin = verifierNbCentral(6);
            }
            if(continentsEnBesoin.isEmpty()){ //Si aucun continent n'est en besoin de centrale
                //affichage d'un message
                viewGame.displayAlertWithoutHeaderText("Bien joué !", "Vous avez bien joué durant cette décennie. Aucun contient ne manque d'énergie !");
            }
            else{ //Si il manque une/des centrale(s)s à un/des continent(s)
                manqueCentral(continentsEnBesoin);
            }
        }
        model.endGame();
        // récompense sommets
        model.giveRewardsSommet();
        model.getCurrentPLayer().setActionPrincipaleDone(false);
        model.getCurrentPLayer().setDeplacerScientifiqueDone(false);
        model.getCurrentPLayer().setMarcheCEPDone(false);
        model.getCurrentPLayer().setActionMarche(0);
        viewGame.hboxAction.resetHbox();
        // actualisation du nombre de tour et de décénnie
        viewGame.reloadTour();
        viewGame.reloadDecade();
        viewGame.reloadresourcesTech();
        viewGame.reloadCEP();
        viewGame.reloadPointVictoire();
        viewGame.reloadCo2();
        // affichage sur la console le nombre de tour et décénnie
        System.out.println("Tour : " + model.getTour() + "/" + (model.NB_TOUR_PAR_DECENNIE-1));
        System.out.println("Décénnie : " + model.getDecade() + "/" + model.NB_DECENNIE);
        // model.tilesSolarProjects.get(0).setSubventionPossible(false); // temporaire

        // affiche message si fin de partie
        try {
            viewGame.isEndGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * permet de savoir si tout les contients on le bon nombre de central
     * @param nbCentral
     * @return la liste des continents dans le besoin
     */
    public ArrayList<Continent> verifierNbCentral(int nbCentral){
        Continent[] continents = model.getContinents();
        ArrayList<Continent> continentsEnBesoin = new ArrayList<>();
        for(int i = 0; i < continents.length; i++){
            //Récupération des case central du continent
            ArrayList<Central> caseCentrals = continents[i].getCentrales();
            //Variables pour stocker le nombre de central dans le continent
            int nbCentralInContinent = 0;
            //On regarde le nombre de case qui sont occupée ou non
            for (Central c: caseCentrals){
                //Si la case central est occupée, on ajout 1 au nombre de central dans le continent
                if(c.isOccupe()) nbCentralInContinent += 1;
            }
            //On regarde si le nombre de central dans le continent est suffisant ou non
            if(nbCentralInContinent < nbCentral && nbCentralInContinent < continents[i].getCentrales().size()){
                String title = "ATTENTION ! L'"+continents[i].getName()+" a besoin de central";
                String message = "Il manque "+(nbCentral-nbCentralInContinent)+" centrale en "+continents[i].getName();
                viewGame.displayAlertWithoutHeaderText(title, message);
                continentsEnBesoin.add(continents[i]);
            }
            else{
                String title = "Tout va bien !";
                String message = "Il ne manque aucunes centrales en "+continents[i].getName();
                viewGame.displayAlertWithoutHeaderText(title, message);
            }
        }
        return continentsEnBesoin;
    }

    /**
     * Permet d'effectuer les actions pour les contients n'ayant pas assez de central c'est-à-dire:
     * 1. Prenez la centrale à combustible fossile du haut de la pile et posez-la sur la case énergie libre la plus à gauche de la région.
     * 2. Augmentez le niveau global de pollution de CO2 du nombre indiqué par la centrale à combustible fossile. (Char-bon = 40 ppm, Pétrole = 30 ppm, Gaz = 20 ppm). TODO (Yassino)
     * 3. Si un joueur contrôle la région, il doit payer 1 CEP à labanque de sa réserve ou d’une région qu’il contrôle (pas nécessairement de la région concernée !).
     *    S’il ne possède pas de CEP (ni dans sa réserve, ni dans une des régions qu’il contrôle):
     *    il doit en acheter un au marché.
     *    S’il n’a pas assez d’argent pour acheter un CEP au marché au cours de cette phase d’approvisionnement (et seulement au cours de cette phase):
     *    il doit échanger des points de victoire (PV) pour 1 pièce chacun, jusqu’à (et seulement jusqu’à) ce qu’il ait assez d’argent pour payer le CEP ;
     *    il recule alors son disque d’une case sur la piste de PV pour chaque pièce perçue. Les points négatifs sont autorisés.
     * Si personne ne contrôle la région, le CEP est pris dans la pile de la région.
     *
     * @param continentsEnBesoin
     */
    public void manqueCentral(ArrayList<Continent> continentsEnBesoin){
        //pour chaque continent dans le besoin
        Random random = new Random();
        centralTypes[] typesCentralsFossile = {centralTypes.CHARBON, centralTypes.PETROLE, centralTypes.GAZNATUREL};
        for(Continent c: continentsEnBesoin){
            //Phase 1.
            //on cherche la première case de libre dans les centrales du continent
            for(Central caseCentral: c.getCentrales()){
                if(!caseCentral.isOccupe()){
                    //Une fois trouvée, on ajoute une central à cette case
                    int type = random.nextInt(3) ;
                    viewGame.addCentrale(typesCentralsFossile[type], c, caseCentral.getIndex());
                    //on met à jour le modèle
                    model.putFossileCentral(c, typesCentralsFossile[type],caseCentral.getIndex());
                    //et on sort de la boucle
                    break;
                }
            }
            //Phase 2.
            //On regarde si le joueur controle le continent (à changer pour généraliser à tout les joueurs si jeu en multi)
            List<Continent> continentsControlleParJoueur = model.getCurrentPLayer().getContinentsControlles();
            for (Continent continentController:continentsControlleParJoueur){
                if(continentController.equals(c)){
                    //affichage d'un message
                    viewGame.displayAlertWithoutHeaderText("Danger !", "Vous controllez un continent en manque d'énergie ! "+c.getName()+" Vous devez payer !!");
                    // TODO : Mettre les vrai condition
                    if (model.curPlayer.getCEP() >= 1) {
                        // La vrai condition = model.curPlayer.getCEP() > 1
                        //on retire CEP
                    } else if (continentController.getNbCep() >= 1 ) {
                        //si il y a des cep dans continent controler on prend
                    } else {
                        // Si on a pas de cep dans les contient controler et dans nos poche alors
                        if (model.currentPriceCEP > model.curPlayer.getArgent()) {
                            // La vrai condition = model.currentPriceCEP > model.curPlayer.getArgent()
                            // on echange nos pv contre de l'agrgent puis on achete un cep
                            viewGame.displayAlertWithoutHeaderText("Echange", "Vous n'avez pas assez de CEP\n" +
                                    "Le jeux va échanger vos point de victoire pour acheter un CEP");
                            model.tradePVtoCEP();
                            System.out.println("trade fait");
                        }
                    }
                }
            }
        }
    }

    /**
     * vérifie si un joueur à marquer une carte "Objectif de l'ONU"
     */
    public void verifOnuCard(){
        // TODO : donner la carte selectionnée dans le menu
        model.giveVictoryPointsOnuCards(model.getOnuCardsInGame().get(1)); // test sur la 2e carte
    }
}