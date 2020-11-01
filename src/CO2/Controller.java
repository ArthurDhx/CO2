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
    }
}
