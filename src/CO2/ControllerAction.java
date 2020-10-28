package CO2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControllerAction implements EventHandler<ActionEvent>{
    Model model ;
    ViewGame viewGame ;

    public ControllerAction(Model model, ViewGame viewGame) {
        this.model = model ;
        this.viewGame = viewGame ;
        viewGame.setButtonActionControler(this);
    }

    @Override
    public void handle(ActionEvent event) {
        //TODO : Controller les actions "fesable" et desactiver les boutons non disponible
        Object source = event.getSource() ;
        if (source == viewGame.btnActionPrincipale) {
            viewGame.displayActionPrincipale() ;
        } else if (source == viewGame.btnActionGratuite){
            viewGame.displayActionGratuite() ;
        } else if (source == viewGame.btnCancelAction) {
            viewGame.resetHbox();
        }
    }
}