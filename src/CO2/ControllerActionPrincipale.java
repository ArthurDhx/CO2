package CO2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

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
        //TODO : Controller les actions "fesable" et desactiver les boutons non disponible
        Object source = event.getSource() ;
        if (source == viewGame.btnProposerProjet) {
            viewGame.displayProposerProjetChoiceDialog() ;
            Optional<Continent> result = viewGame.dialogProposerProjet.showAndWait();
            result.ifPresent(continentChoisi -> {
                viewGame.displayChoisirSubventionChoiceDialog(continentChoisi);
                Optional<Subvention> resulltSubv = viewGame.dialogSubvention.showAndWait();
                // TODO : Mettre a jour le modele
                resulltSubv.ifPresent(subvention -> {
                    if(model.addTilesSolarProjectToSubventionCase()) viewGame.addTuilesToSubvention(subvention.getIndex()+1, viewGame.imageViewTilesSolarProject, continentChoisi);
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