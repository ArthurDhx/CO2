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
        viewGame.hboxAction.setButtonActionPrincipaleControler(this);
    }

    /**
     * Récupere la source de l'evenemenet et le traite selon la source
     * @param event L'evenement
     */
    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource() ;
        if (source == viewGame.hboxAction.btnProposerProjet) {
            // On affiche le ChoiceDialog qui permet de proposer un projet
            viewGame.hboxAction.displayProposerProjetChoiceDialog() ;

            // Le resultat du ChoiceDialog
            Optional<Continent> result = viewGame.hboxAction.dialogProposerProjet.showAndWait();
            result.ifPresent(continentChoisi -> {
                // Si le résultat est present ( l'utilisateur n'a pas quitter le ChoiceDialog et a donc choisi
                // un continent => continentChois )
                viewGame.hboxAction.displayChoisirSubventionChoiceDialog(continentChoisi);

                // la subvention choisis par le joueur
                Optional<Subvention> resulltSubv = viewGame.hboxAction.dialogSubvention.showAndWait();
                resulltSubv.ifPresent(subvention -> {
                    // meme principe que au dessus
                    if(model.addTilesSolarProjectToSubventionCase(continentChoisi,subvention.getIndex())){
                        // Si la tuile peut etre ajouter
                        // TODO : [Yassine] a vérifier apres refactoring tab vers liste
                        // Affiche la tuile a l'ecran
                        viewGame.addTuilesToSubvention(subvention.getIndex()+1, viewGame.imageViewTilesSolarProject, continentChoisi);
                        // Mets a jour le model
                        model.tilesSolarProjects.remove(0);
                        // Mets a jour la vue
                        viewGame.nbTilesSolarProject.setText("Il y a "+ model.getNbSolarProject()+" projets solaires");
                        // Le joueur en cours a effectuer son action principale
                        model.getCurentPLayer().setActionPrincipaleDone(true);
                    }
                    return;
                });
            });
            // Si un dialog a été quitté reset la hbox
            viewGame.hboxAction.resetHbox();
        } else if (source == viewGame.hboxAction.btnMettreProjet){
            // A implementer
        } else if (source == viewGame.hboxAction.btnConstruire) {
            // A implementer
        }
    }
}