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
                "Vos projets vont être affectés aux régions aléatoirement,\n" +
                        "Vous allez devoir choisir sur qu'elle case de subvention ceux-ci seront disposés");

        // Prenez 1 tuile de chacun des 5 projets en main
        final int NB_PROJET = 5;
        List<Integer> projetsJoueur = new ArrayList<>();
        for (int i = 0; i < NB_PROJET; i++) projetsJoueur.add(i);

        // Mélange les projets
        Collections.shuffle(projetsJoueur);

        // Pour chaque continent on propose de poser le projet
        for (Continent continent: model.getContinents()) {
            // si il reste des projets dans la main alors on propose un projet
            if (!projetsJoueur.isEmpty()){
                viewGame.hboxAction.displayChoisirSubventionChoiceDialog(continent);
                // la subvention choisis par le joueur
                Optional<Subvention> resulltSubv = viewGame.hboxAction.dialogSubvention.showAndWait();
                resulltSubv.ifPresent(subvention -> {
                    // tire une energie
                    greenEnergyTypes energyChoisi = greenEnergyTypes.values()[projetsJoueur.get(0)];
                    projetsJoueur.remove(0);

                    // Si la tuile peut etre ajouter
                    if(model.verrifAddProjectTileToSubvention(continent, subvention)) {
                        // Mets a jour le model : fait en sorte que le projet ne puisse pas etre réutilisé
                        model.addProjectTileToSubvention(continent, subvention, energyChoisi);

                        // Affiche la tuile a l'écran
                        viewGame.changeProjectState(subvention.getIndex(), model.getCurEnergyChoice(), viewGame.PROPOSER_PROJET, continent);

                        // Le joueur en cours a effectuer son action principale
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
        System.out.println(model.initOnuCardsInGame(new Random())); // sélectionne les cartes pour la partie
    }
}
