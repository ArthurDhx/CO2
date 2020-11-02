package CO2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.Optional;

public class ControllerActionGratuite implements EventHandler<ActionEvent>{
    Model model ;
    ViewGame viewGame ;

    public ControllerActionGratuite(Model model, ViewGame viewGame) {
        this.model = model ;
        this.viewGame = viewGame ;
        viewGame.hboxAction.setButtonActionGratuiteControler(this);
    }

    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource();
        if (source == viewGame.hboxAction.btnDeplacerScientifiq) {
            viewGame.hboxAction.displayDeplacerScientifiqueChoiceDialog() ;
            Optional<Subvention> result = viewGame.hboxAction.dialogDeplacerScientifique.showAndWait();
            result.ifPresent(projetChoisi -> {
                if(model.moveScientificOnProject()) viewGame.addScientifiqueToProject(projetChoisi.getIndex()+1, viewGame.imageViewScientifique, projetChoisi.getContinent());
                model.getCurentPLayer().setDeplacerScientifiqDone(true);
                return;
            });
            viewGame.hboxAction.resetHbox();
        }
        else if (source == viewGame.hboxAction.btnMarche) {
            System.out.println("bouton marche");
        }
        else if (source == viewGame.hboxAction.btnJouerCarte) {
            System.out.println("bouton jouer carte");
        }
    }
}
