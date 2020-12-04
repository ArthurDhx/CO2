package CO2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

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
            finTour();
        }
        viewGame.reloadArgent();
    }

    private void distributionRevenu() {
        Player p = model.getCurrentPLayer();
        // pour chaque piste d'expertise
        for (Expertise exp : model.getExpertises()) {
            // type d'energie
            GreenEnergyTypes type = exp.getType();
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
                model.getCurrentPLayer().addExpertise(GreenEnergyTypes.SOLAR, 1);
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
            else{
                viewGame.displayAlertWithoutHeaderText("Problème !", "Il manque des centrales !");
            }
        }
        model.endGame();
        // récompense sommets
        model.giveRewardsSommet();
        model.getCurrentPLayer().setActionPrincipaleDone(false);
        model.getCurrentPLayer().setDeplacerScientifiqueDone(false);
        model.getCurrentPLayer().setMarcheCEPDone(false);
        viewGame.hboxAction.resetHbox();
        // actualisation du nombre de tour et de décénnie
        viewGame.reloadTour();
        viewGame.reloadDecade();
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
                String message = "Il manque "+(nbCentral-nbCentralInContinent)+" centrale(s) en "+continents[i].getName();
                viewGame.displayAlertWithoutHeaderText(title, message);
            }
            else{
                String title = "Tout va bien !";
                String message = "Il ne manque aucunes centrales en "+continents[i].getName();
                viewGame.displayAlertWithoutHeaderText(title, message);
                continentsEnBesoin.add(continents[i]);
            }
        }
        return continentsEnBesoin;
    }
}