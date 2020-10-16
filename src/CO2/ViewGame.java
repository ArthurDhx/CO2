package CO2;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class ViewGame {

    Model model;
    Pane pane;

	// TODO : [Theo] ajouter un bouton+indicateur pour demonstration de l'augmentation du niveau d'expertise
	// temporaire sprint 1
	Button btnAddSolarExpToCurPlayer;

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
//		Image imgTilesSolarProject = new Image("CO2/images/TilesSolarProject.jpg");
		Image imgTilesSolarProject = new Image(getClass().getResourceAsStream("images/TilesSolarProject.JPG"));
		ImageView imageViewTilesSolarProject = new ImageView(imgTilesSolarProject);
		imageViewTilesSolarProject.setX(900);
		imageViewTilesSolarProject.setY(100);
		imageViewTilesSolarProject.setPreserveRatio(true);
		pane.getChildren().add(imageViewTilesSolarProject);

		//On indique combien il y'a de tuile dans le paquet
		Text nbTilesSolarProject = new Text(880, 190,"Il y a "+model.getNbSolarProject()+" projets solaires");
		pane.getChildren().add(nbTilesSolarProject);

		Image imgEurope = new Image(getClass().getResourceAsStream("images/Europe.jpg"));
		ImageView imageViewEurope = new ImageView(imgEurope);
		imageViewEurope.setX(500);
		imageViewEurope.setY(300);
		pane.getChildren().add(imageViewEurope);

		Rectangle[] tabRectangleSubvention = new Rectangle[3];
		int k = 100;
		for(int i = 0;i<3;i++) {
			tabRectangleSubvention[i] = new Rectangle(80,80, Color.WHITE);
			tabRectangleSubvention[i].setStroke(Color.BLACK);
			tabRectangleSubvention[i].setX(k+350);
			tabRectangleSubvention[i].setY(350);
			k=k+100;
			pane.getChildren().add(tabRectangleSubvention[i]);
		}
		addTuilesToSubvention(3, imageViewTilesSolarProject);
    }

	public void addTuilesToSubvention(int subventionchoice, ImageView imageViewTilesSolarProject){
		switch (subventionchoice){
			case 1 :
				imageViewTilesSolarProject.setX(450);
				break;
			case 2 :
				imageViewTilesSolarProject.setX(550);
				break;
			case 3 :
				imageViewTilesSolarProject.setX(650);

		}
		imageViewTilesSolarProject.setY(350);
		imageViewTilesSolarProject.toFront();
	}
}
