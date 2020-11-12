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

    /**
     * Récupere la source de l'evenemenet et le traite selon la source
     * @param event L'evenement
     */
    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource();
        if (source == viewGame.hboxAction.btnDeplacerScientifiqToProject) {
            // Affiche le ChoiceDialog qui permet de deplacer un scientifque
            viewGame.hboxAction.displayDeplacerScientifiqueChoiceDialog() ;
            if(viewGame.hboxAction.dialogDeplacerScientifiqueProjet == null) return;
            Optional<Subvention> result = viewGame.hboxAction.dialogDeplacerScientifiqueProjet.showAndWait();
            result.ifPresent(projetChoisi -> {
                // Si un projet a ete choisi
                if(model.moveScientificOnProject(projetChoisi.getContinent(), projetChoisi))
                    viewGame.addScientifiqueToProject(projetChoisi.getIndex()+1, viewGame.imageViewScientifique, projetChoisi.getContinent());
                model.getCurentPLayer().setDeplacerScientifiqDone(true);
                return;
            });
            // Sinon reset la hbox
            viewGame.hboxAction.resetHbox();
        } else if (source == viewGame.hboxAction.btnDeplacerScientifiqToSommet) {
            if(model.moveScientificOnSommet(model.getCurentPLayer().getCurrentScientifique().getSubvention())){
                viewGame.addScientifiqueToSommet(viewGame.imageViewScientifique, model.getCurentPLayer().getCurrentScientifique());
            } else {
                viewGame.sommetInfo();
            }
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
