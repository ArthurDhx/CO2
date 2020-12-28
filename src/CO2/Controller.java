package CO2;

import java.util.*;

public class Controller {

    protected Model model;
    protected ViewTitle viewTitle;
    protected ControllerTitle titleController;
    protected ControllerAction controllerAction;
    protected ControllerActionPrincipale controllerActionPrincipale;
    protected ControllerActionGratuite controllerActionGratuite;
    protected ViewGame viewGame ;
    private int tour;
    private greenEnergyTypes energyChoisi;
    
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
            initOnuCards();
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
                "5 projets vont être affectés aux régions aléatoirement,\n" +
                        "Vous allez devoir choisir sur qu'elle case de subvention ceux-ci seront disposés");

        // Pour chaque continent on propose de poser le projet
        tour = 5;
        while(tour != 0){
            energyChoisi = null;
            for (Continent continent: model.getContinents()) {
                if(tour == 0) break;
                // si il reste des projets dans la main alors on propose un projet
                    viewGame.hboxAction.displayChoisirSubventionChoiceDialog(continent);
                    // la subvention choisis par le joueur
                    Optional<Subvention> resulltSubv = viewGame.hboxAction.dialogSubvention.showAndWait();
                    resulltSubv.ifPresent(subvention -> {
                        // tire une energie
                        if (energyChoisi == null){
                            energyChoisi = greenEnergyTypes.getRandomGreenEnergyTypes();
                            viewGame.displayAlertWithoutHeaderText(
                                    "Energie tiré aléatoirement",
                                    "Vous avez tiré aléatoirement l'énergie " + energyChoisi);
                        }


                        // Si la tuile peut etre ajouter
                        if (model.verrifAddProjectTileToSubvention(continent, subvention, energyChoisi)) {

                            // Mets a jour le model : fait en sorte que le projet ne puisse pas etre réutilisé
                            model.addProjectTileToSubvention(continent, subvention, energyChoisi);
                            tour--;
                            energyChoisi = null;
                            // Affiche la tuile a l'écran
                            viewGame.changeProjectState(subvention.getIndex(), model.getCurEnergyChoice(), viewGame.PROPOSER_PROJET, continent);
                        }else{
                            viewGame.displayAlertWithoutHeaderText(
                                    "ENERGIE NON PLACABLE DANS LE CONTINENT",
                                    "L'energie "+ energyChoisi + " n'est pas placable dans le continent " + continent + "\n" +
                                            "On va essayer de la placer sur le prochain continent ");
                        }
                    });
            }
        }
    }

    /**
     * Permet de mettre aleatoirement des centrale fossile sur le premiere case pour le jeu en 1 joueur
     */
    public void initStartingFossileCentral(){
        // On recupere les continent
        Continent[] continents= model.getContinents();
        Random random = new Random();
        centralTypes[] typesCentralsFossile = {centralTypes.CHARBON, centralTypes.PETROLE, centralTypes.GAZNATUREL};
        for (Continent continent: continents ) {
            // On tire au sort le type entre 0 et 2
            int type = random.nextInt(3) ;
            // On mets a jour le model avec le bon type sur le bon continent
            model.putFossileCentral(continent, typesCentralsFossile[type],0);
            // On ajoute la centrale a la vue
            viewGame.addCentrale(continent.getCentrales().get(0).getType(), continent, 0);
        }
        // On met a jour le co2 de la vue
        viewGame.reloadCo2();
    }

    /**
     * Permet de choisir aléatoirement 10 cartes "objectifs de l'ONU" au début de la partie
     */
    public void initOnuCards(){
        System.out.println(model.initOnuCardsInGame()); // sélectionne les cartes pour la partie
    }
}
