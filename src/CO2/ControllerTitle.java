package CO2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControllerTitle implements EventHandler<ActionEvent>{

    protected Model model;
    protected ViewTitle viewTitle;
    protected Controller control;

    public ControllerTitle(Model model, ViewTitle viewTitle, Controller control) {
        this.model = model;
        this.viewTitle = viewTitle;
        this.control = control;
        viewTitle.setButtonControler(this);
    }

    public void handle(ActionEvent event) {
        if (event.getSource() == viewTitle.btn) {
            if (model.state == Model.STATE_INIT) control.startGame();
        }
        /* Choix du joueur desactivé
        else if (event.getSource() == viewTitle.comboBox){
            model.nbJoueur = Integer.parseInt(viewTitle.comboBox.getSelectionModel().getSelectedItem());
        }*/
    }    
}
