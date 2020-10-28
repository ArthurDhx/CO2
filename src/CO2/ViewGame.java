package CO2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

	// TEST
	Button btnActionPrincipale;
	Button btnProposerProjet;
	Button btnMettreProjet;
	Button btnConstruire;

	Button btnActionGratuite;
	Button btnDeplacerScientifiq;
	Button btnMarche;
	Button btnJouerCarte;

	HBox hboxAction;
	Button btnCancelAction;

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
		ImageView imageViewTilesSolarProject = new ImageView(imgTilesSolarProject);
		ImageView imageViewTilesSolarProjectInDeck = new ImageView(imgTilesSolarProject);
		imageViewTilesSolarProjectInDeck.setX(1460);
		imageViewTilesSolarProjectInDeck.setY(50);
		imageViewTilesSolarProjectInDeck.setPreserveRatio(true);
		pane.getChildren().add(imageViewTilesSolarProject);
		pane.getChildren().add(imageViewTilesSolarProjectInDeck);

		// On indique combien il y'a de tuile dans le paquet
		Text nbTilesSolarProject = new Text(1430, 150,"Il y a "+model.getNbSolarProject()+" projets solaires");
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
		Rectangle[] tabRectangleSubvention = new Rectangle[3];
		int k = 100;
		for(int i = 0;i<3;i++) {
			tabRectangleSubvention[i] = new Rectangle(75,75, Color.WHITE);
			tabRectangleSubvention[i].setStroke(Color.BLACK);
			tabRectangleSubvention[i].setX(k+300);
			tabRectangleSubvention[i].setY(250);
			pane.getChildren().add(tabRectangleSubvention[i]);
			Text subventionName = new Text(model.getContinents()[0].getSubventions()[i].getName());
			subventionName.setX(k+305);
			subventionName.setY(290);
			subventionName.setStyle("-fx-font: 9 arial;");
			k=k+100;
			pane.getChildren().add(subventionName);
		}

		if(model.addTilesSolarProjectToSubventionCase()) addTuilesToSubvention(3, imageViewTilesSolarProject);

		btnActionPrincipale = new Button("Action Principale");
		btnConstruire = new Button("Construire une centrale");
		btnProposerProjet = new Button("Proposer un projet");
		btnMettreProjet = new Button("Mettre en place un projet");

		btnActionGratuite = new Button("Action Gratuite");
		btnDeplacerScientifiq = new Button("Déplacer un scientifque");
		btnMarche = new Button("Marché au CEP");
		btnJouerCarte = new Button("Jouer une carte");

		btnCancelAction = new Button("Annuler");
		hboxAction = new HBox(10);
		hboxAction.getChildren().addAll(btnActionPrincipale, btnActionGratuite);

		pane.getChildren().add(hboxAction);
    }

	/** Ajoute la tuile sur la case souhaitée correspondant à une subvention
	 * @param subventionChoice
	 * @param imageViewTilesSolarProject
	 */
	public void addTuilesToSubvention(int subventionChoice, ImageView imageViewTilesSolarProject){
		switch (subventionChoice){
			case 1 :
				imageViewTilesSolarProject.setX(400);
				break;
			case 2 :
				imageViewTilesSolarProject.setX(500);
				break;
			case 3 :
				imageViewTilesSolarProject.setX(600);

		}
		imageViewTilesSolarProject.setY(250);
		imageViewTilesSolarProject.toFront();
	}

	public void setButtonActionControler(EventHandler<ActionEvent> handler) {
		btnActionGratuite.setOnAction(handler);
		btnActionPrincipale.setOnAction(handler);
		btnCancelAction.setOnAction(handler);
	}

	public void displayActionPrincipale() {
		hboxAction.getChildren().removeAll(btnActionGratuite,btnActionPrincipale);
		hboxAction.getChildren().addAll(btnProposerProjet,btnMettreProjet,btnConstruire,btnCancelAction);
	}

	public void displayActionGratuite() {
		hboxAction.getChildren().removeAll(btnActionGratuite,btnActionPrincipale);
		hboxAction.getChildren().addAll(btnDeplacerScientifiq,btnMarche,btnJouerCarte,btnCancelAction);
	}

	public void resetHbox() {
		hboxAction.getChildren().removeAll(hboxAction.getChildren());
		hboxAction.getChildren().addAll(btnActionPrincipale,btnActionGratuite);
	}
}
