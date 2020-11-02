package CO2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Text;

import java.util.Optional;

public class ControllerActionPrincipale implements EventHandler<ActionEvent>{
    Model model ;
    ViewGame viewGame ;

    public ControllerActionPrincipale(Model model, ViewGame viewGame) {
        this.model = model ;
        this.viewGame = viewGame ;
        viewGame.setButtonActionPrincipaleControler(this);
    }

    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource() ;
        if (source == viewGame.btnProposerProjet) {
            viewGame.displayProposerProjetChoiceDialog() ;
            Optional<Continent> result = viewGame.dialogProposerProjet.showAndWait();
            result.ifPresent(continentChoisi -> {
                viewGame.displayChoisirSubventionChoiceDialog(continentChoisi);
                Optional<Subvention> resulltSubv = viewGame.dialogSubvention.showAndWait();
                resulltSubv.ifPresent(subvention -> {
                    if(model.addTilesSolarProjectToSubventionCase()) viewGame.addTuilesToSubvention(subvention.getIndex()+1, viewGame.imageViewTilesSolarProject, continentChoisi);
                    // TODO : [Yassine] a vérifier apres refactoring tab vers liste
                    viewGame.nbTilesSolarProject.setText("Il y a "+model.getNbSolarProject()+" projets solaires");
                    model.getCurentPLayer().setActionPrincipaleDone(true);
                    return;
                });
            });
            viewGame.resetHbox();
        } else if (source == viewGame.btnMettreProjet){
            // A implementer
        } else if (source == viewGame.btnConstruire) {
            // A implementer
        }
    }
}