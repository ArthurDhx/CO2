package CO2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.IOException;
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
            int revenu = exp.getPiste().get(playerExp).getNumero();

            // si il y a un revenu a distribuer
            if (revenu>0) {
                // boite de dialogue
                viewGame.hboxAction.dialogChoisirRevenu(type, revenu);
                if (viewGame.hboxAction.dialogChoisirRevenu != null) {
                    Optional<String> result = viewGame.hboxAction.dialogChoisirRevenu.showAndWait();
                    result.ifPresent(repartition -> {
                        System.out.println(repartition);
                        // TODO donner revenu choisi
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
}