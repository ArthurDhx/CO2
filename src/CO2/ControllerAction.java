package CO2;
import javafx.event.*;

public class ControllerAction implements EventHandler<ActionEvent> {

    protected Model model;
    protected ViewTitle viewTitle;
    protected Controller control;

    public ControllerAction(Model model, ViewTitle viewTitle, Controller control) {
        this.model = model;
        this.viewTitle = viewTitle;
        this.control = control;
        viewTitle.setButtonControler(this);
    }

    public void handle(ActionEvent event) {
        if (event.getSource() == viewTitle.btn) {
            if (model.state == Model.STATE_INIT) control.startGame();
        }
        else if (event.getSource() == viewTitle.comboBox){
            model.nbJoueur = Integer.parseInt(viewTitle.comboBox.getSelectionModel().getSelectedItem());
        }
    }    
}
