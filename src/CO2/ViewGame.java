package CO2;

import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class ViewGame {

    Model model;
    Pane pane;
    
    public ViewGame(Model model, Pane pane) {
		this.model = model;
		this.pane = pane;
		pane.getChildren().clear();

		Text t = new Text(10, 50, "Le jeu commence ici avec " + model.nbJoueur + " joueur(s)");

		pane.getChildren().add(t);
		init();
    }

	public void init(){
		System.out.println("Le jeu commence ici avec " + model.nbJoueur + " joueur(s)");
	}
}
