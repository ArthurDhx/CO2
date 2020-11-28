package CO2;

import javafx.scene.image.ImageView;
import java.util.Optional;
import java.util.Random;

public class Controller {

    protected Model model;
    protected ViewTitle viewTitle;
    protected ControllerTitle titleController;
    protected ControllerAction controllerAction;
    protected ControllerActionPrincipale controllerActionPrincipale;
    protected ControllerActionGratuite controllerActionGratuite;
    protected ViewGame viewGame ;
    
    public Controller(Model model, ViewTitle viewTitle, ViewGame viewGame) {
		this.model = model;
		this.viewTitle = viewTitle;
		this.viewGame = viewGame;
        titleController = new ControllerTitle(model, viewTitle, this);
        controllerAction = new ControllerAction(model, viewGame);
        controllerActionPrincipale = new ControllerActionPrincipale(model, viewGame);
        controllerActionGratuite = new ControllerActionGratuite(model, viewGame);
    }

    public void startGame() {
		model.startGame();
		viewTitle.startGame();
		viewTitle.root.setFocusTraversable(true);
		viewTitle.root.requestFocus();
		if (model.getNbJoueur() == 1 ) {
		    initStartingProject();
		    initStartingFossileCentral();
        }
    }

    /**
     * Met en place des projets sur chaque continent en demandant au joueur qu'elle subvention celui si va occuper
     */
    public void initStartingProject(){
        viewGame.displayAlertWithoutHeaderText(
                "Mode 1 Joueur",
                "Des projets vont etre affécté aux regions aléatoirement,\n" +
                        "Vous allez devoir choisir sur qu'elle case de subvention ceux-ci seront disposés");
        //TODO: [Yassine] a retravailler en while une fois toutes les tuiles implementer
        for (Continent continent: model.getContinents()) {
            viewGame.hboxAction.displayChoisirSubventionChoiceDialog(continent);
            // la subvention choisis par le joueur
            Optional<Subvention> resulltSubv = viewGame.hboxAction.dialogSubvention.showAndWait();
            resulltSubv.ifPresent(subvention -> {
                // meme principe que au dessus
                if(model.addTilesSolarProjectToSubventionCase(continent,subvention.getIndex())){
                    // Si la tuile peut etre ajouter
                    // Affiche la tuile a l'ecran
                    viewGame.addTuilesToSubvention(subvention.getIndex()+1, viewGame.createTileProject(), continent);
                    // Mets a jour le model
                    model.tilesSolarProjects.remove(0);
                    // Mets a jour la vue
                    viewGame.nbTilesSolarProject.setText("Il y a "+ model.getNbSolarProject()+" projets solaires");
                    // Le joueur en cours a effectuer son action principale
                }
            });
        }
    }

    /**
     * Permet de mettre aleatoirement des centrale fossile sur le premiere case pour le jeu en 1 joueur
     */
    public void initStartingFossileCentral(){
        // On recupere les continent
        Continent[] continents= model.getContinents();
        Random random = new Random();
        for (Continent continent: continents ) {
            // On tire au sort le type entre 0 et 2
            int type = random.nextInt(3) ;
            // On mets a jour le model avec le bon type sur le bon continent
            model.putFossileCentral(continent, type);
            // On ajoute la centrale a la vue
            viewGame.addCentrale( viewGame.createFossileCentrale(type), continent, 0);
        }
        // On met a jour le co2 de la vue
        viewGame.reloadCo2();
    }
}
