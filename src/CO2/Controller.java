package CO2;

public class Controller {

    protected Model model;
    protected ViewTitle viewTitle;
    protected ControllerAction actions;
    
    public Controller(Model model, ViewTitle viewTitle, ViewGame viewGame) {
		this.model = model;
		this.viewTitle = viewTitle;
		actions = new ControllerAction(model, viewTitle, this);
    }

    public void startGame() {
		model.startGame();
		viewTitle.startGame();
		viewTitle.root.setFocusTraversable(true);
		viewTitle.root.requestFocus();
    }
}
