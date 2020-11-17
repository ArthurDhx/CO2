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
            viewGame.hboxAction.displayFinTourScientifiqueChoiceDialog() ;
            if(viewGame.hboxAction.dialogChoisirScientifique != null){
                Optional<Scientifique> result = viewGame.hboxAction.dialogChoisirScientifique.showAndWait();
                result.ifPresent(scientifiqueChoisi -> {
                    //model.getCurentPLayer().addExpertise();
                    return;
                });
            }
            // vérification du nombre de tour et décénnie
            model.TourSuivant();
            model.endGame();
            // récompense sommets
            model.giveRewardsSommet();
            model.getCurrentPLayer().setActionPrincipaleDone(false);
            model.getCurrentPLayer().setDeplacerScientifiqDone(false);
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
}