package CO2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class ViewGame {

	Model model;
    Pane pane;

	// TODO : [Theo] ajouter un bouton+indicateur pour demonstration de l'augmentation du niveau d'expertise
	// temporaire sprint 1
	Button btnAddSolarExpToCurPlayer;

	Text nbTilesSolarProject ;

	ImageView imageViewTilesSolarProject;
	ImageView imageViewScientifique;

	ViewMenuActionHbox hboxAction;

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
		imageViewTilesSolarProject = new ImageView(imgTilesSolarProject);
		ImageView imageViewTilesSolarProjectInDeck = new ImageView(imgTilesSolarProject);
		imageViewTilesSolarProjectInDeck.setX(1460);
		imageViewTilesSolarProjectInDeck.setY(50);
		imageViewTilesSolarProjectInDeck.setPreserveRatio(true);
		pane.getChildren().add(imageViewTilesSolarProject);
		pane.getChildren().add(imageViewTilesSolarProjectInDeck);

		//On récupère l'image d'un scientifique et on l'ajoute à l'écran
		Image imgScientifique = new Image(getClass().getResourceAsStream("images/scientifique.JPG"));
		imageViewScientifique = new ImageView(imgScientifique);
		imageViewScientifique.setX(30);
		imageViewScientifique.setY(100);
		pane.getChildren().add(imageViewScientifique);

		// On indique combien il y'a de tuile dans le paquet
		nbTilesSolarProject = new Text(1430, 150,"Il y a "+model.getNbSolarProject()+" projets solaires");
		pane.getChildren().add(nbTilesSolarProject);

		// Tableau des continents
		ImageView[] imageViewContinents = new ImageView[6];
		for(int i = 0; i<6;i++)
			imageViewContinents[i] = new ImageView(model.getContinents()[i].getImgContinent());
		imageViewContinents[0].setX(400);imageViewContinents[0].setY(200);
		imageViewContinents[1].setX(700);imageViewContinents[1].setY(70);
		imageViewContinents[2].setX(1000);imageViewContinents[2].setY(200);
		imageViewContinents[3].setX(1000);imageViewContinents[3].setY(550);
		imageViewContinents[4].setX(700);imageViewContinents[4].setY(700);
		imageViewContinents[5].setX(400);imageViewContinents[5].setY(550);
		for(int i = 0; i<6;i++)
			pane.getChildren().add(imageViewContinents[i]);

		// Tableau des cases subventions => "proposer un projet"
		Rectangle[] tabRectangleSubventionEurope = new Rectangle[3];
		Rectangle[] tabRectangleSubventionAfrique = new Rectangle[3];
		Rectangle[] tabRectangleSubventionAsie = new Rectangle[3];
		Rectangle[] tabRectangleSubventionAmeriqueSud = new Rectangle[3];
		Rectangle[] tabRectangleSubventionAmeriqueNord = new Rectangle[3];
		Rectangle[] tabRectangleSubventionOceanie = new Rectangle[3];

		Text[] subventionName = new Text[6];
		int k = 100;
		for(int i = 0;i<3;i++) {
			tabRectangleSubventionEurope[i] = new Rectangle(75, 75, Color.WHITE);
			tabRectangleSubventionAfrique[i] = new Rectangle(75, 75, Color.WHITE);
			tabRectangleSubventionAsie[i] = new Rectangle(75, 75, Color.WHITE);
			tabRectangleSubventionAmeriqueSud[i] = new Rectangle(75, 75, Color.WHITE);
			tabRectangleSubventionAmeriqueNord[i] = new Rectangle(75, 75, Color.WHITE);
			tabRectangleSubventionOceanie[i] = new Rectangle(75, 75, Color.WHITE);

			tabRectangleSubventionEurope[i].setStroke(Color.BLACK);
			tabRectangleSubventionAfrique[i].setStroke(Color.BLACK);
			tabRectangleSubventionAsie[i].setStroke(Color.BLACK);
			tabRectangleSubventionAmeriqueSud[i].setStroke(Color.BLACK);
			tabRectangleSubventionAmeriqueNord[i].setStroke(Color.BLACK);
			tabRectangleSubventionOceanie[i].setStroke(Color.BLACK);

			tabRectangleSubventionEurope[i].setX(k+250);tabRectangleSubventionEurope[i].setY(250);
			tabRectangleSubventionAfrique[i].setX(k+550);tabRectangleSubventionAfrique[i].setY(100);
			tabRectangleSubventionAmeriqueSud[i].setX(k+850);tabRectangleSubventionAmeriqueSud[i].setY(250);
			tabRectangleSubventionAmeriqueNord[i].setX(k+850);tabRectangleSubventionAmeriqueNord[i].setY(600);
			tabRectangleSubventionOceanie[i].setX(k+550);tabRectangleSubventionOceanie[i].setY(750);
			tabRectangleSubventionAsie[i].setX(k+250);tabRectangleSubventionAsie[i].setY(600);

			pane.getChildren().add(tabRectangleSubventionAfrique[i]);
			pane.getChildren().add(tabRectangleSubventionEurope[i]);
			pane.getChildren().add(tabRectangleSubventionAmeriqueNord[i]);
			pane.getChildren().add(tabRectangleSubventionAmeriqueSud[i]);
			pane.getChildren().add(tabRectangleSubventionOceanie[i]);
			pane.getChildren().add(tabRectangleSubventionAsie[i]);

			subventionName[0] = new Text(model.getContinents()[0].getSubventions()[i].getName());
			subventionName[1] = new Text(model.getContinents()[1].getSubventions()[i].getName());
			subventionName[2] = new Text(model.getContinents()[2].getSubventions()[i].getName());
			subventionName[3] = new Text(model.getContinents()[3].getSubventions()[i].getName());
			subventionName[4] = new Text(model.getContinents()[4].getSubventions()[i].getName());
			subventionName[5] = new Text(model.getContinents()[5].getSubventions()[i].getName());
			subventionName[0].setStyle("-fx-font: 9 arial;");subventionName[1].setStyle("-fx-font: 9 arial;");
			subventionName[2].setStyle("-fx-font: 9 arial;");subventionName[3].setStyle("-fx-font: 9 arial;");
			subventionName[4].setStyle("-fx-font: 9 arial;");subventionName[5].setStyle("-fx-font: 9 arial;");
			subventionName[0].setX(k + 255);subventionName[0].setY(290);
			subventionName[1].setX(k + 555);subventionName[1].setY(140);
			subventionName[2].setX(k + 855);subventionName[2].setY(290);
			subventionName[3].setX(k + 855);subventionName[3].setY(640);
			subventionName[4].setX(k + 555);subventionName[4].setY(790);
			subventionName[5].setX(k + 255);subventionName[5].setY(640);
			pane.getChildren().add(subventionName[0]);pane.getChildren().add(subventionName[1]);
			pane.getChildren().add(subventionName[2]);pane.getChildren().add(subventionName[3]);
			pane.getChildren().add(subventionName[4]);pane.getChildren().add(subventionName[5]);
			k = k + 100;
		}

		hboxAction = new ViewMenuActionHbox(model) ;
		hboxAction.init();
		pane.getChildren().add(hboxAction);
    }

	/** Ajoute la tuile sur la case souhaitée correspondant à une subvention
	 * @param subventionChoice
	 * @param imageViewTilesSolarProject
	 */
	public void addTuilesToSubvention(int subventionChoice, ImageView imageViewTilesSolarProject, Continent continent){
		switch(continent.getName()) {
			case "Europe" :
				switch (subventionChoice) {
					case 1:
						imageViewTilesSolarProject.setX(350);
						break;
					case 2:
						imageViewTilesSolarProject.setX(450);
						break;
					case 3:
						imageViewTilesSolarProject.setX(550);
						break;
				}
				imageViewTilesSolarProject.setY(250);
				imageViewTilesSolarProject.toFront();
				break;
			case "Afrique" :
				switch (subventionChoice) {
					case 1:
						imageViewTilesSolarProject.setX(650);
						break;
					case 2:
						imageViewTilesSolarProject.setX(750);
						break;
					case 3:
						imageViewTilesSolarProject.setX(850);
						break;
				}
				imageViewTilesSolarProject.setY(100);
				imageViewTilesSolarProject.toFront();
				break;
			case "Amérique du Sud" :
				switch (subventionChoice) {
					case 1:
						imageViewTilesSolarProject.setX(950);
						break;
					case 2:
						imageViewTilesSolarProject.setX(1050);
						break;
					case 3:
						imageViewTilesSolarProject.setX(1150);
						break;
				}
				imageViewTilesSolarProject.setY(250);
				imageViewTilesSolarProject.toFront();
				break;
			case "Amérique du Nord" :
				switch (subventionChoice) {
					case 1:
						imageViewTilesSolarProject.setX(950);
						break;
					case 2:
						imageViewTilesSolarProject.setX(1050);
						break;
					case 3:
						imageViewTilesSolarProject.setX(1150);
						break;
				}
				imageViewTilesSolarProject.setY(600);
				imageViewTilesSolarProject.toFront();
				break;
			case "Océanie" :
				switch (subventionChoice) {
					case 1:
						imageViewTilesSolarProject.setX(650);
						break;
					case 2:
						imageViewTilesSolarProject.setX(750);
						break;
					case 3:
						imageViewTilesSolarProject.setX(850);
						break;
				}
				imageViewTilesSolarProject.setY(750);
				imageViewTilesSolarProject.toFront();
				break;
			case "Asie" :
				switch (subventionChoice) {
					case 1:
						imageViewTilesSolarProject.setX(350);
						break;
					case 2:
						imageViewTilesSolarProject.setX(450);
						break;
					case 3:
						imageViewTilesSolarProject.setX(550);
						break;
				}
				imageViewTilesSolarProject.setY(600);
				imageViewTilesSolarProject.toFront();
				break;
		}
	}

	public void addScientifiqueToProject(int projectChoice, ImageView imageViewScientifique, Continent continent){
		switch(continent.getName()) {
			case "Europe" :
				switch (projectChoice) {
					case 1:
						imageViewScientifique.setX(350);
						break;
					case 2:
						imageViewScientifique.setX(450);
						break;
					case 3:
						imageViewScientifique.setX(550);
						break;
				}
				imageViewScientifique.setY(250);
				imageViewScientifique.toFront();
				break;
			case "Afrique" :
				switch (projectChoice) {
					case 1:
						imageViewScientifique.setX(650);
						break;
					case 2:
						imageViewScientifique.setX(750);
						break;
					case 3:
						imageViewScientifique.setX(850);
						break;
				}
				imageViewScientifique.setY(100);
				imageViewScientifique.toFront();
				break;
			case "Amérique du Sud" :
				switch (projectChoice) {
					case 1:
						imageViewScientifique.setX(950);
						break;
					case 2:
						imageViewScientifique.setX(1050);
						break;
					case 3:
						imageViewScientifique.setX(1150);
						break;
				}
				imageViewScientifique.setY(250);
				imageViewScientifique.toFront();
				break;
			case "Amérique du Nord" :
				switch (projectChoice) {
					case 1:
						imageViewScientifique.setX(950);
						break;
					case 2:
						imageViewScientifique.setX(1050);
						break;
					case 3:
						imageViewScientifique.setX(1150);
						break;
				}
				imageViewScientifique.setY(600);
				imageViewScientifique.toFront();
				break;
			case "Océanie" :
				switch (projectChoice) {
					case 1:
						imageViewScientifique.setX(650);
						break;
					case 2:
						imageViewScientifique.setX(750);
						break;
					case 3:
						imageViewScientifique.setX(850);
						break;
				}
				imageViewScientifique.setY(750);
				imageViewScientifique.toFront();
				break;
			case "Asie" :
				switch (projectChoice) {
					case 1:
						imageViewScientifique.setX(350);
						break;
					case 2:
						imageViewScientifique.setX(450);
						break;
					case 3:
						imageViewScientifique.setX(550);
						break;
				}
				imageViewScientifique.setY(600);
				imageViewScientifique.toFront();
				break;
		}
	}

}
