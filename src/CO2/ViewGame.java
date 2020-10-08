package CO2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.control.*;

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

    	//On lance l'initialisation du model qui générera toute les pièces, les joueurs et les valeurs (points,...)
    	this.model.init();

    	//On récupère l'image de la tuile et on l'ajoute à l'écran
		Image imgTilesSolarProject = new Image("co2/images/TilesSolarProject.jpg");
		ImageView imageViewTilesSolarProject = new ImageView(imgTilesSolarProject);
		imageViewTilesSolarProject.setX(900);
		imageViewTilesSolarProject.setY(100);
		imageViewTilesSolarProject.setPreserveRatio(true);
		pane.getChildren().add(imageViewTilesSolarProject);

		//On indique combien il y'a de tuile dans le paquet
		Text nbTilesSolarProject = new Text(880, 190,"Il y a "+model.getNbSolarProject()+" projets solaires");
		pane.getChildren().add(nbTilesSolarProject);

	}
}
