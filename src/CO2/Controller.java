package CO2;

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
		    viewGame.displayAlertWithoutHeaderText(
                    "Mode 1 Joueur",
                    "Des projets vont etre affécté aux regions aléatoirement,\n" +
                            "Vous allez devoir choisir sur qu'elle case de subvention ceux-ci seront disposés");
            for (int i = 0; i < 5; i++) {
                // TODO : [YASSINE] Créer les 5 types de tuiles, mettre a jour le modele + la vue en proposant les choix a l'utilisateur
            }
        }
    }
}
