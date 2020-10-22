package CO2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControlBoutonChooseTile implements EventHandler<ActionEvent>  {

    protected Model model;
    protected ViewGame viewGame;

    public ControlBoutonChooseTile(Model model, ViewGame viewGame) {
        this.model = model;
        this.viewGame = viewGame;
        viewGame.setButtonController(this);
    }

    @Override
    public void handle(ActionEvent event) {
        viewGame.displayChoiceProjectTile();
    }
}
