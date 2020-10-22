package CO2;
mais
public class Controller {

    // TODO : Poubelle
    protected ViewGame viewGame;
    // Fin poubelle

    protected Model model;
    protected ViewTitle viewTitle;
    protected ControllerAction actions;
    
    public Controller(Model model, ViewTitle viewTitle, ViewGame viewGame) {
		this.model = model;
		this.viewTitle = viewTitle;
		actions = new ControllerAction(model, viewTitle, this);

        // TODO : Poubelle
        this.viewGame = viewGame ;
        ControlBoutonChooseTile controlBoutonChooseTile = new ControlBoutonChooseTile(model, viewGame);
        // Fin poubelle
    }

    public void startGame() {
		model.startGame();
		viewTitle.startGame();
		viewTitle.root.setFocusTraversable(true);
		viewTitle.root.requestFocus();
    }
}
