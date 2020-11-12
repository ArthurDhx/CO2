package CO2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

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
        //TODO : Controller les actions "fesable" et desactiver les boutons non disponible
        Object source = event.getSource() ;
        if (source == viewGame.hboxAction.btnActionPrincipale) {
            viewGame.hboxAction.displayActionPrincipale() ;
        } else if (source == viewGame.hboxAction.btnActionGratuite){
            viewGame.hboxAction.displayActionGratuite() ;
        } else if (source == viewGame.hboxAction.btnCancelAction) {
            viewGame.hboxAction.resetHbox();
        }else if (source == viewGame.hboxAction.btnFinTour) {
            viewGame.hboxAction.displayFinTourScientifiqueChoiceDialog() ;
            if(viewGame.hboxAction.dialogChoisirScientifique != null){
                Optional<Scientifique> result = viewGame.hboxAction.dialogChoisirScientifique.showAndWait();
                result.ifPresent(scientifiqueChoisi -> {
                    //model.getCurentPLayer().addExpertise();
                    return;
                });
            }
            model.TourSuivant();
            model.getCurentPLayer().setActionPrincipaleDone(false);
            model.getCurentPLayer().setDeplacerScientifiqDone(false);
            model.getCurentPLayer().setMarcheCEPDone(false);
            viewGame.hboxAction.resetHbox();
            viewGame.reloadTour();
            System.out.println("Tour : " + model.getTour() + "/" + model.NB_TOUR_PAR_DECENNIE);
            model.tilesSolarProjects.get(0).setSubventionPossible(false); // temporaire
            //Vérifier si 6/6 passer à la décennie suivante
        }
        viewGame.reloadArgent();
    }
}