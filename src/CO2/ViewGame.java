package CO2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
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
	// TODO : poubelle
	ImageView imageViewTilesSolarProject;
	// fin poubelle
	HBox hboxChooseTile ;
	Button[] btnChooseTile ;

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

    	// On lance l'initialisation du model qui générera toute les pièces, les joueurs et les valeurs (points,...)
    	this.model.init();

    	// On récupère l'image de la tuile et on l'ajoute à l'écran
		// Image imgTilesSolarProject = new Image("CO2/images/TilesSolarProject.jpg");
		Image imgTilesSolarProject = new Image(getClass().getResourceAsStream("images/TilesSolarProject.JPG"));
		// TODO : yass
		imageViewTilesSolarProject = new ImageView(imgTilesSolarProject);
		imageViewTilesSolarProject.setX(900);
		imageViewTilesSolarProject.setY(100);
		imageViewTilesSolarProject.setPreserveRatio(true);
		pane.getChildren().add(imageViewTilesSolarProject);

		// On indique combien il y'a de tuile dans le paquet
		Text nbTilesSolarProject = new Text(880, 190,"Il y a "+model.getNbSolarProject()+" projets solaires");
		pane.getChildren().add(nbTilesSolarProject);
		// Image du continent Europe
		ImageView imageViewEurope = new ImageView(model.getContinents()[0].getImgContinent());
		imageViewEurope.setX(500);
		imageViewEurope.setY(300);
		pane.getChildren().add(imageViewEurope);

		// Tableau des cases subventions => "proposer un projet"
		Rectangle[] tabRectangleSubvention = new Rectangle[3];
		int k = 100;
		for(int i = 0;i<3;i++) {
			tabRectangleSubvention[i] = new Rectangle(75,75, Color.WHITE);
			tabRectangleSubvention[i].setStroke(Color.BLACK);
			tabRectangleSubvention[i].setX(k+350);
			tabRectangleSubvention[i].setY(350);
			pane.getChildren().add(tabRectangleSubvention[i]);
			Text subventionName = new Text(model.getContinents()[0].getSubventions()[i].getName());
			subventionName.setX(k+355);
			subventionName.setY(390);
			subventionName.setStyle("-fx-font: 9 arial;");
			k=k+100;
			pane.getChildren().add(subventionName);
		}

		// TODO : a remettre
		//if(model.addTilesSolarProjectToSubventionCase()) addTuilesToSubvention(3, imageViewTilesSolarProject);

		// TODO : Poubelle
		hboxChooseTile = new HBox();
		hboxChooseTile.setPadding(new Insets(15, 12, 15, 12));
		hboxChooseTile.setSpacing(10);

		btnChooseTile = new Button[6];
		btnChooseTile[0] = new Button("Choisir une tuile");
		btnChooseTile[1] = new Button("Projet Solaire");
		btnChooseTile[2] = new Button("Projet Hydraulique");
		btnChooseTile[3] = new Button("Projet Recyclage");
		btnChooseTile[4] = new Button("Projet Eolien");
		btnChooseTile[5] = new Button("Projet Forestier");

		hboxChooseTile.getChildren().add(btnChooseTile[0]);
		pane.getChildren().add(hboxChooseTile);
		// Fin poubelle
    }

	/** Ajoute la tuile sur la case souhaitée correspondant à une subvention
	 * @param subventionChoice
	 * @param imageViewTilesSolarProject
	 */
	public void addTuilesToSubvention(int subventionChoice, ImageView imageViewTilesSolarProject){
		switch (subventionChoice){
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
	// TODO : Poubelle


	public void setButtonController(EventHandler<ActionEvent> handler) {
		btnChooseTile[0].setOnAction(handler);
		btnChooseTile[1].setOnAction(handler);
	}

	public void displayChoiceProjectTile() {
		hboxChooseTile.getChildren().remove(btnChooseTile[0]);
		for (int i = 1; i < btnChooseTile.length; i++) {
			hboxChooseTile.getChildren().add(btnChooseTile[i]);
		}
	}

	// Fin poubelle
}
