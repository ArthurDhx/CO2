package CO2;

import javafx.scene.control.Alert;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.awt.image.ImagingOpException;
import java.io.IOException;

public class ViewGame {

	Model model;
    Pane pane;

	Text nbTilesSolarProject ;
	Text nbTour;
	Text nbDecade;
	Text argentJoueur;
	Text resourcesTechJoueur;
	Text CEPJoueur;
	Text CEPMarche;

	ImageView imageViewTilesSolarProject;
	ImageView imageViewTilesSolarProjectBack;
	ImageView imageViewScientifique;

	ViewMenuActionHbox hboxAction;
	private Object AlertType;
	public Image imgTilesSolarProject;


	public ViewGame(Model model, Pane pane) throws IOException {
		this.model = model;
		this.pane = pane;
		pane.getChildren().clear();
		init();
    }

	public void init() throws IOException {
    	System.out.println("Le jeu commence avec " + model.getNbJoueur() + " joueur(s)");
    	// On lance l'initialisation du model qui générera toute les pièces, les joueurs et les valeurs (points,...)
    	this.model.init();

    	// On récupère l'image de la tuile et on l'ajoute à l'écran
		// Image imgTilesSolarProject = new Image("CO2/images/TilesSolarProject.jpg");
		imgTilesSolarProject = new Image(getClass().getResourceAsStream("images/Projets/TilesSolarProjectRecto.png"));
		imageViewTilesSolarProject = new ImageView(imgTilesSolarProject);
		imageViewTilesSolarProject.setPreserveRatio(true);
		imageViewTilesSolarProject.setFitWidth(75);
		imageViewTilesSolarProject.setX(1460);
		imageViewTilesSolarProject.setY(50);
		imageViewTilesSolarProject.setPreserveRatio(true);
		ImageView imageViewTilesSolarProjectInDeck = new ImageView(imgTilesSolarProject);
		imageViewTilesSolarProjectInDeck.setPreserveRatio(true);
		imageViewTilesSolarProjectInDeck.setFitWidth(75);
		imageViewTilesSolarProjectInDeck.setX(1460);
		imageViewTilesSolarProjectInDeck.setY(50);
		pane.getChildren().add(imageViewTilesSolarProject);
		pane.getChildren().add(imageViewTilesSolarProjectInDeck);

		//On récupère l'image d'un scientifique et on l'ajoute à l'écran
		Image imgScientifique = new Image(getClass().getResourceAsStream("images/scientifique.png"));
		imageViewScientifique = new ImageView(imgScientifique);
		imageViewScientifique.setX(30);
		imageViewScientifique.setY(100);
		imageViewScientifique.setFitWidth(50);
		imageViewScientifique.setPreserveRatio(true);
		pane.getChildren().add(imageViewScientifique);

		// On indique combien il y'a de tuile dans le paquet
		nbTilesSolarProject = new Text(1430, 150,"Il y a "+model.getNbSolarProject()+" projets solaires");
		pane.getChildren().add(nbTilesSolarProject);

		reloadTour();
		reloadDecade();
		reloadArgent();
		reloadresourcesTech();
		reloadCEP();

		initContinent();
		initExpertise(50, 5);
		initSubvention(250, 100);

		hboxAction = new ViewMenuActionHbox(model) ;
		hboxAction.init();
		pane.getChildren().add(hboxAction);
    }

	/**
	 * Affiche les barres d'expertise
	 */
	private void initExpertise(int rectWidth, int space) {
		int offset = 0;
		for (Expertise expertise : model.expertises) {
			offset += rectWidth + space;
			for (int i = 0; i < expertise.getMax(); i++) {
				Rectangle rect = new Rectangle(rectWidth, rectWidth, Color.WHITE);
				rect.setStroke(expertise.getColor());
				rect.setX(1200+offset);
				rect.setY(800 - ((rectWidth+space) * i));
				Text nb = new Text((1200+offset), (800 - ((rectWidth+space) * i - 10)), String.valueOf(i+1));
				nb.setStroke(expertise.getColor());
				pane.getChildren().add(rect);
				pane.getChildren().add(nb);
			}
		}
	}

	public void reloadTour(){
		pane.getChildren().remove(nbTour);
		nbTour = new Text(10, 95,"Tour : "+model.getTour()+"/" + (model.NB_TOUR_PAR_DECENNIE-1));
		pane.getChildren().add(nbTour);
	}
	public void reloadDecade(){
		pane.getChildren().remove(nbDecade);
		nbDecade = new Text(80, 95,"Décénnie : "+model.getDecade()+"/" + model.NB_DECENNIE);
		pane.getChildren().add(nbDecade);
	}
	//A appeler lors d'une modification de l'argent du joueur
	public void reloadArgent(){
		pane.getChildren().remove(argentJoueur);
		argentJoueur = new Text(10, 50, "Vous avez "+ model.getCurrentPLayer().getArgent() + " € ");
		pane.getChildren().add(argentJoueur);
	}
	//A appeler lors d'une modification de l'argent du joueur
	public void reloadresourcesTech() {
		pane.getChildren().remove(resourcesTechJoueur);
		resourcesTechJoueur = new Text(10, 80, "Vous avez " + model.getCurrentPLayer().getResourcesTech() + " cubes de ressources technologiques. ");
		pane.getChildren().add(resourcesTechJoueur);
	}
	//A appeler lors d'une modification des CEP
	public void reloadCEP(){
		pane.getChildren().remove(CEPJoueur);
		CEPJoueur = new Text(10,65,"Vous avez "+model.getCurrentPLayer().getCEP()+" CEP");
		pane.getChildren().add(CEPJoueur);
		pane.getChildren().remove(CEPMarche);
		CEPMarche = new Text(model.width/2-80,model.height/2,
				"Il y a "+model.nbCEPdispo+" CEP dans le marché\n" +
						"Le prix actuel est de "+model.currentPriceCEP+" €"
		);
		pane.getChildren().add(CEPMarche);
	}

	/**
	 * Initialisation des continents
	 */
    public void initContinent(){
		// Tableau des images continents
		ImageView[] imageViewContinents = new ImageView[6];
		// Tableau des images agenda
		ImageView[] imageViewAgendaTiles = new ImageView[6];
		// Tableau des images sommets
		ImageView[] imageViewSommetTiles = new ImageView[6];
		for(int i = 0; i<imageViewContinents.length;i++) {
			// initialisation des Image View pour chaque tableau
			imageViewContinents[i] = new ImageView(model.getContinents()[i].getImgContinent());
			imageViewAgendaTiles[i] = new ImageView(model.getContinents()[i].getAgendaTile().getImageAgendaTile());
			imageViewSommetTiles[i] = new ImageView(model.getContinents()[i].getSommetTile().getImageSommetTile());
			// Position des continents
			if(i==0 || i==5) imageViewContinents[i].setX(400);
			if(i==0 || i==2) imageViewContinents[i].setY(200);
			if(i==3 || i==5) imageViewContinents[i].setY(550);
			if(i==1 || i==4) imageViewContinents[i].setX(700);
			if(i==2 || i==3) imageViewContinents[i].setX(1000);
			// Position des Agendas
			if(i==0 || i==5) imageViewAgendaTiles[i].setX(400+50);
			if(i==0 || i==2) imageViewAgendaTiles[i].setY(200-100);
			if(i==3 || i==5) imageViewAgendaTiles[i].setY(550-100);
			if(i==1 || i==4) imageViewAgendaTiles[i].setX(700+50);
			if(i==2 || i==3) imageViewAgendaTiles[i].setX(1000+50);
			// Position des sommets
			if(i==0 || i==5) imageViewSommetTiles[i].setX(400-50);
			if(i==0 || i==2) imageViewSommetTiles[i].setY(200-20);
			if(i==3 || i==5) imageViewSommetTiles[i].setY(550-20);
			if(i==1 || i==4) imageViewSommetTiles[i].setX(700-50);
			if(i==2 || i==3) imageViewSommetTiles[i].setX(1000-50);

			// Redimention
			imageViewAgendaTiles[i].setFitWidth(75);
			imageViewAgendaTiles[i].setPreserveRatio(true);
			imageViewSommetTiles[i].setFitWidth(75);
			imageViewSommetTiles[i].setPreserveRatio(true);
		}
		imageViewContinents[4].setY(700);
		imageViewContinents[1].setY(70);

		imageViewAgendaTiles[4].setY(700-125);
		imageViewAgendaTiles[1].setY(70+125);

		imageViewSommetTiles[4].setY(700-20);
		imageViewSommetTiles[1].setY(70+115);

		// Ajout au pane
		for(int i = 0; i < 6; i++) {
			pane.getChildren().add(imageViewContinents[i]);
			pane.getChildren().add(imageViewAgendaTiles[i]);
			pane.getChildren().add(imageViewSommetTiles[i]);
		}
	}

	/**
	 * Initialisation des subventions
	 */
	public void initSubvention(int val, int k){
		// tableau de texte des subvention
		Text[] subventionName = new Text[6];
		for(int i=0;i<3;i++) {
			for(int j=0;j<model.getContinents().length;j++) {
				// initialisation du texte
				subventionName[j] = new Text(model.getContinents()[j].getSubventions().get(i).getType().toString());
				// style du texte
				subventionName[j].setStyle("-fx-font: 9 arial;");
				// position du texte (les 3 noms de subvention) pour chaque continent
				if(j==0 || j==5) {model.getContinents()[j].getTabRectangleSubvention()[i].setX(k+val);subventionName[j].setX(k+val+5);}
				if(j==0 || j==2) {model.getContinents()[j].getTabRectangleSubvention()[i].setY(val);subventionName[j].setY(val+40);}
				if(j==1 || j==4) {model.getContinents()[j].getTabRectangleSubvention()[i].setX(k+val+300);subventionName[j].setX(k+val+305);}
				if(j==2 || j==3) {model.getContinents()[j].getTabRectangleSubvention()[i].setX(k+val+600);subventionName[j].setX(k+val+605);}
				if(j==3 || j==5) {model.getContinents()[j].getTabRectangleSubvention()[i].setY(val+350);subventionName[j].setY(val+390);}
				if(j==1) {model.getContinents()[j].getTabRectangleSubvention()[i].setY(val-150);subventionName[1].setY(val-110);}
				if(j==4) {model.getContinents()[4].getTabRectangleSubvention()[i].setY(val+500);subventionName[4].setY(val+540);}
				// ajout au pane
				pane.getChildren().add(model.getContinents()[j].getTabRectangleSubvention()[i]);
				pane.getChildren().add(subventionName[j]);
			}
			k = k + 100;
		}
	}

	/** Ajoute la tuile sur la case souhaitée correspondant à une subvention
	 * @param subventionChoice
	 * @param imageViewTilesSolarProject
	 */
	public void addTuilesToSubvention(int subventionChoice, ImageView imageViewTilesSolarProject, Continent continent){
		switch(continent.getName()) {
			case "Europe" :
				// suivant la subvention choisie
				switch (subventionChoice) {
					// posiitionne les tuiles de projet à l'emplacement correspondant
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
				// mettre l'image en premier plan
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

	public void addScientifiqueToSommet(ImageView imageViewScientifique, Scientifique scientifique){
		Continent continent = scientifique.getContinent();
		switch(continent.getName()) {
			case "Europe" :
				imageViewScientifique.setX(400-50);
				imageViewScientifique.setY(200-20);
				imageViewScientifique.toFront();
				break;
			case "Afrique" :
				imageViewScientifique.setX(700-50);
				imageViewScientifique.setY(70+115);
				imageViewScientifique.toFront();
				break;
			case "Amérique du Sud" :
				imageViewScientifique.setX(1000-50);
				imageViewScientifique.setY(200-20);
				imageViewScientifique.toFront();
				break;
			case "Amérique du Nord" :
				imageViewScientifique.setX(1000-50);
				imageViewScientifique.setY(550-20);
				imageViewScientifique.toFront();
				break;
			case "Océanie" :
				imageViewScientifique.setX(700-50);
				imageViewScientifique.setY(700-20);
				imageViewScientifique.toFront();
				break;
			case "Asie" :
				imageViewScientifique.setX(400-50);
				imageViewScientifique.setY(550-20);
				imageViewScientifique.toFront();
				break;
		}
	}

	public void mettreEnPlaceProjet(int projectChoice, ImageView imageViewTilesSolarProjectBack, Continent continent){
		Image imgTilesSolarProjectBack = new Image(getClass().getResourceAsStream("images/Projets/TilesSolarProjectVerso.png"));
		imageViewTilesSolarProjectBack = new ImageView(imgTilesSolarProjectBack);
		imageViewTilesSolarProjectBack.setPreserveRatio(true);
		imageViewTilesSolarProjectBack.setFitWidth(75);
		imageViewTilesSolarProjectBack.setPreserveRatio(true);
		switch(continent.getName()) {
			case "Europe" :
				switch (projectChoice) {
					case 1:
						imageViewTilesSolarProjectBack.setX(350);
						break;
					case 2:
						imageViewTilesSolarProjectBack.setX(450);
						break;
					case 3:
						imageViewTilesSolarProjectBack.setX(550);
						break;
				}
				imageViewTilesSolarProjectBack.setY(250);
				imageViewTilesSolarProjectBack.toFront();
				break;
			case "Afrique" :
				switch (projectChoice) {
					case 1:
						imageViewTilesSolarProjectBack.setX(650);
						break;
					case 2:
						imageViewTilesSolarProjectBack.setX(750);
						break;
					case 3:
						imageViewTilesSolarProjectBack.setX(850);
						break;
				}
				imageViewTilesSolarProjectBack.setY(100);
				imageViewTilesSolarProjectBack.toFront();
				break;
			case "Amérique du Sud" :
				switch (projectChoice) {
					case 1:
						imageViewTilesSolarProjectBack.setX(950);
						break;
					case 2:
						imageViewTilesSolarProjectBack.setX(1050);
						break;
					case 3:
						imageViewTilesSolarProjectBack.setX(1150);
						break;
				}
				imageViewTilesSolarProjectBack.setY(250);
				imageViewTilesSolarProjectBack.toFront();
				break;
			case "Amérique du Nord" :
				switch (projectChoice) {
					case 1:
						imageViewTilesSolarProjectBack.setX(950);
						break;
					case 2:
						imageViewTilesSolarProjectBack.setX(1050);
						break;
					case 3:
						imageViewTilesSolarProjectBack.setX(1150);
						break;
				}
				imageViewTilesSolarProjectBack.setY(600);
				imageViewTilesSolarProjectBack.toFront();
				break;
			case "Océanie" :
				switch (projectChoice) {
					case 1:
						imageViewTilesSolarProjectBack.setX(650);
						break;
					case 2:
						imageViewTilesSolarProjectBack.setX(750);
						break;
					case 3:
						imageViewTilesSolarProjectBack.setX(850);
						break;
				}
				imageViewTilesSolarProjectBack.setY(750);
				imageViewTilesSolarProjectBack.toFront();
				break;
			case "Asie" :
				switch (projectChoice) {
					case 1:
						imageViewTilesSolarProjectBack.setX(350);
						break;
					case 2:
						imageViewTilesSolarProjectBack.setX(450);
						break;
					case 3:
						imageViewTilesSolarProjectBack.setX(550);
						break;
				}
				imageViewTilesSolarProjectBack.setY(600);
				imageViewTilesSolarProjectBack.toFront();
				break;
		}
		pane.getChildren().add(imageViewTilesSolarProjectBack);
		this.imageViewScientifique.toFront();
	}

	/**
	 * Permet de creer une tuiles project et de l'initialiser et l'ajoute au panneau
	 * @return La tuile prête a être placé a l'ecran
	 */
	public ImageView createTileProject() {
		ImageView imageView = new ImageView(imgTilesSolarProject);
		imageView.setPreserveRatio(true);
		imageView.setFitWidth(75);
		//TODO : Switch pour tooltip une fois toute les tuiles implementés
		Tooltip.install(imageView, new Tooltip("+ 3 Ressources technologiques"));
		pane.getChildren().add(imageView);
		return imageView ;
	}

	public void displayAlertWithoutHeaderText(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public void sommetInfo() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Information sur le sommet");
		alert.setHeaderText(null);
		alert.setContentText("Il n'y a pas la votre source d'énergie dans le sommet");
		alert.showAndWait();
	}

	/**
	 * vérifie si c'est la fin du jeu
	 */
	public void isEndGame(){
		// si le nombre de décénnie max est atteinte => renvoie true par model.EndGame
		if(model.endGame()){
			// message d'alerte
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setTitle("partie terminé !");
			alert.setContentText("la partie est terminé, Voulez-vous rejouer?");
			alert.showAndWait();
		}
	}
}
