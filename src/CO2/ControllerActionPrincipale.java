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
                // un continent => continentChoisi )
                // choix de la subvention
                viewGame.hboxAction.displayChoisirSubventionChoiceDialog(continentChoisi);

                // la subvention choisis par le joueur
                Optional<Subvention> resulltSubv = viewGame.hboxAction.dialogSubvention.showAndWait();
                resulltSubv.ifPresent(subvention -> {
                    // meme principe que au dessus
                    if(model.addTilesSolarProjectToSubventionCase(continentChoisi,subvention.getIndex())){
                        // Si la tuile peut etre ajouter
                        // Affiche la tuile a l'ecran
                        viewGame.addTuilesToSubvention(subvention.getIndex()+1, viewGame.createTileProject(), continentChoisi);
                        // Mets a jour le model
                        model.tilesSolarProjects.remove(0);
                        // Récupération du type et application de l'effet de la subvention choisie
                        typesSubvention type = subvention.effect(model.getCurrentPLayer());
                        if(type == typesSubvention.RECHERCHE){ //Si il s'agit d'une subvention recheche
                            //On affiche la boite de dialogue pour choisir l'action à réaliser
                            viewGame.hboxAction.displayChoisirRechecheChoiceDialog();
                            Optional<String> resultAction = viewGame.hboxAction.dialogChoisirRecherche.showAndWait();
                            //On recupère le résultat
                            resultAction.ifPresent(action -> {
                                if(action == "Déplacer un scientifique"){ //S'il choisi de déplacer un scientifique
                                    //On affiche la boite de dialogue pour savoir comment il veut déplacer un scientifique
                                    viewGame.hboxAction.displayActionScientifiqueAfterRecherche();
                                    Optional<String> resultDeplacerScientifique = viewGame.hboxAction.dialogActionScientifiqueAfterRecherche.showAndWait();
                                    //On récupère le resultat
                                    resultDeplacerScientifique.ifPresent(deplacement -> {
                                            if(deplacement == "") return;
                                            else if(deplacement == "Déplacer sur un projet"){
                                                // Affiche le ChoiceDialog qui permet de deplacer un scientifque
                                                viewGame.hboxAction.displayDeplacerScientifiqueChoiceDialog();
                                                if (viewGame.hboxAction.dialogDeplacerScientifiqueProjet == null) return;
                                                if (model.getCurrentPLayer().getCurrentScientifique().getSubvention() != null){
                                                    model.getCurrentPLayer().getCurrentScientifique().getSubvention().setStaffed(false);
                                                }
                                                Optional<Subvention> resultProjet = viewGame.hboxAction.dialogDeplacerScientifiqueProjet.showAndWait();
                                                resultProjet.ifPresent(projetChoisi -> {
                                                    // Si un projet a ete choisi
                                                    if (model.moveScientificOnProject(projetChoisi.getContinent(), projetChoisi)) {
                                                        viewGame.addScientifiqueToProject(projetChoisi.getIndex() + 1, viewGame.imageViewScientifiqueN1, projetChoisi.getContinent());
                                                        model.getCurrentPLayer().getCurrentScientifique().setSubvention(projetChoisi);
                                                        projetChoisi.setStaffed(true);
                                                        if (model.getCurrentPLayer().getCurrentScientifique().getSubvention().getTilesSolarProject() != null) {
                                                            // set la valeur solaire si le scientifique joué est sur un projet solaire
                                                            model.getCurrentPLayer().getCurrentScientifique().setSubject(new Subject(GreenEnergyTypes.SOLAR));
                                                        }
                                                    }
                                                });
                                            }
                                            else if(deplacement == "Déplacer sur un sommet"){
                                                // Affiche le ChoiceDialog qui permet de deplacer un scientifque
                                                viewGame.hboxAction.displayDeplacerScientifiqueSommetChoiceDialog();
                                                if (viewGame.hboxAction.dialogDeplacerScientifiqueSommet == null) return;
                                                Optional<SommetTile> resultSommet = viewGame.hboxAction.dialogDeplacerScientifiqueSommet.showAndWait();
                                                resultSommet.ifPresent(sommetChoisi -> {
                                                    if (model.moveScientificOnSommet(model.getCurrentPLayer().getCurrentScientifique().getSubvention(), sommetChoisi)) {
                                                        model.getCurrentPLayer().getCurrentScientifique().getSubvention().setStaffed(false);
                                                        viewGame.addScientifiqueToSommet(viewGame.imageViewScientifiqueN1, model.getCurrentPLayer().getCurrentScientifique(), sommetChoisi);
                                                        model.getCurrentPLayer().getCurrentScientifique().setSommetTile(sommetChoisi);
                                                        model.getCurrentPLayer().getCurrentScientifique().setSubvention(null);
                                                        model.getCurrentPLayer().setDeplacerScientifiqueDone(true);
                                                    } else viewGame.sommetInfo();
                                                });
                                            }
                                            else{
                                                //remettre le scientifique dans la réserve et gagner 1 d’expertise dans le type d’énergie du projet
                                                // gagner 1 d’expertise dans le type d’énergie du projet
                                                model.getCurrentPLayer().getCurrentScientifique().getSubvention().setStaffed(false);
                                                model.getCurrentPLayer().addExpertise(model.getCurrentPLayer().getCurrentScientifique().getSubject().getEnergy(), 1);
                                                viewGame.displayAlertWithoutHeaderText("Gain d'expertise", "En remettant votre scientifique dans votre réserve, vous gagné 1 d’expertise dans le type d’énergie " + model.getCurrentPLayer().getCurrentScientifique().getSubject().getEnergy() + " !");
                                                //remettre le scientifique dans la réserve
                                                viewGame.deplacerScientifiqueReserve(model.getCurrentPLayer().getCurrentScientifique().getImgScientifique());
                                                viewGame.hboxAction.resetHbox();
                                                model.getCurrentPLayer().getCurrentScientifique().moveToReserve();
                                            }
                                    });

                                }
                                else{ //S'il il choisir d'avoir un nouveau scientifique
                                    if(model.getCurrentPLayer().addScientifique()){ //Si le joueur à pu avoir un nouveau scientifique
                                        //on met à jour la vue
                                        viewGame.addScientifiqueToReserve();
                                    }
                                    else{ //Si le joueur à deja 4 scientifique, on affiche un message d'erreur et on quitte ici
                                        viewGame.displayAlertWithoutHeaderText("Problème lors de l'action", "Vous avez déjà 4 scientifiques.");
                                        return;
                                    }
                                }
                            });
                        }
                        viewGame.reloadresourcesTech();
                        // Mets a jour la vue
                        viewGame.nbTilesSolarProject.setText("Il y a "+ model.getNbSolarProject()+" projets solaires");
                        // Le joueur en cours a effectuer son action principale
                        model.getCurrentPLayer().setActionPrincipaleDone(true);
                    }
                    return;
                });
            });
            // Si un dialog a été quitté reset la hbox
            viewGame.hboxAction.resetHbox();
        } else if (source == viewGame.hboxAction.btnMettreProjet){
            // On affiche le ChoiceDialog qui permet de mettre en place un projet déjà proposé
            viewGame.hboxAction.displayMettreEnPlaceProjetChoiceDialog() ;
            if(viewGame.hboxAction.dialogMettreEnPlaceProjet == null) return;
            // Le resultat du ChoiceDialog
            Optional<Subvention> result = viewGame.hboxAction.dialogMettreEnPlaceProjet.showAndWait();
            result.ifPresent(projetChoisi -> {
                // Si un projet a ete choisi
                if(model.mettreEnPlaceProjet(projetChoisi.getContinent(), projetChoisi)) {
                    viewGame.mettreEnPlaceProjet(projetChoisi.getIndex() + 1, viewGame.imageViewTilesSolarProjectBack, projetChoisi.getContinent());
                    viewGame.reloadresourcesTech();
                }
                return;
            });
            // Sinon reset la hbox
            viewGame.hboxAction.resetHbox();

        } else if (source == viewGame.hboxAction.btnConstruire) {
            viewGame.hboxAction.displayConstruireCentraleChoiceDialog();
            Optional<Subvention> result = viewGame.hboxAction.dialogConstruireCentrale.showAndWait();
            if(viewGame.hboxAction.dialogConstruireCentrale == null) return;
            result.ifPresent(projetMisEnPlaceChoisi -> {
                // METRE A JOUR LE MODELE CEUX QUI VONT RAJOUTER LE COUT JE VOUS VOIS
                // if ( VERIFIE SI POSSIBLE ET PAYE )
                int index = model.putCentral(projetMisEnPlaceChoisi);
                if( index != -1) {
                    viewGame.addCentrale( viewGame.createCentrale(), projetMisEnPlaceChoisi.getContinent(), index);
                    viewGame.resetSubvention(250,100, projetMisEnPlaceChoisi.getContinent().getIndex(),projetMisEnPlaceChoisi.getIndex());
                    projetMisEnPlaceChoisi.getTilesSolarProject().setMisEnPlace(false);
                    projetMisEnPlaceChoisi.setEmpty(true);
                    viewGame.reloadContinentControl(model.getCurrentPLayer());
                } else {
                    viewGame.displayAlertWithoutHeaderText("Erreur", "Impossible de placer la centrale");
                }
                viewGame.hboxAction.resetHbox();
                return;
            });
        }
    }
}