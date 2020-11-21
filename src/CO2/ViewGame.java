package CO2;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ViewGame {

	Model model;
    Pane pane;

	Text nbTilesSolarProject ;
	Text nbTour;
	Text nbDecade;
	Text argentJoueur;
	Text pointVictoireJoueur;
	Text resourcesTechJoueur;
	Text CEPJoueur;
	Text CEPMarche;

	List<Circle> player1ExpertiseIndicator;

	ImageView imageViewTilesSolarProject;
	ImageView imageViewTilesSolarProjectBack;
	ImageView imageViewScientifique;

	ViewMenuActionHbox hboxAction;
	private Object AlertType;
	public Image imgTilesSolarProject;

	Scene scene;

	// CONSTANTES

	// coordonnée du texte en x
	private final int TEXT_X = 10;

	// largeur des images agendas et sommets
	private final int AGENDA_SOMMET_WIDTH = 60;
	// valeur d'ajout sur des coordonnées d'agenda
	private final int AJOUT_AGENDA = 85;

	// coordonnées des continents
	private final int CONTINENT_05_X_02_Y = 200;
	private final int CONTINENT_35_Y = 550;
	private final int CONTINENT_14_X = 600;
	private final int CONTINENT_23_X = 950;
	private final int CONTINENT_1_Y = 70;
	private final int CONTINENT_4_Y = 700;

	private final int CONTINENT_02_Y = CONTINENT_05_X_02_Y+50;
	private final int CONTINENT_1_Y_SUB = 130;
	private final int CONTINENT_4_Y_SUB = CONTINENT_4_Y+10;

	// coordonnées des sommets
	private final int SOMMET_02_Y = CONTINENT_05_X_02_Y-10;
	private final int SOMMET_35_Y = CONTINENT_35_Y-60;

	// coordonnées des subventions 2 et 3 (Ressources et recherches)
	private final int SUB_2 = 80;
	private final int SUB_3 = SUB_2*2;

	// coordonnées des continents des subeventions 2 et 3
	private final int CONTINENT_23_X_SUB_2 = CONTINENT_23_X+SUB_2;
	private final int CONTINENT_23_X_SUB_3 = CONTINENT_23_X+SUB_3;

	private final int CONTINENT_05_X_02_Y_SUB_2 = CONTINENT_05_X_02_Y+SUB_2;
	private final int CONTINENT_05_X_02_Y_SUB_3 = CONTINENT_05_X_02_Y+SUB_3;

	private final int CONTINENT_14_X_SUB_2 = CONTINENT_14_X+SUB_2;
	private final int CONTINENT_14_X_SUB_3 = CONTINENT_14_X+SUB_3;

	public ViewGame(Scene scene, Model model, Pane pane) throws IOException {
		this.model = model;
		this.pane = pane;
		this.scene = scene;
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
		/*ImageView imageViewTilesSolarProjectInDeck = new ImageView(imgTilesSolarProject);
		imageViewTilesSolarProjectInDeck.setPreserveRatio(true);
		imageViewTilesSolarProjectInDeck.setFitWidth(75);
		imageViewTilesSolarProjectInDeck.setX(1460);
		imageViewTilesSolarProjectInDeck.setY(50);*/
		// pane.getChildren().add(imageViewTilesSolarProject); // ne plus afficher le nb de tuiles de projets solaire et images
		//pane.getChildren().add(imageViewTilesSolarProjectInDeck);

		//On récupère l'image d'un scientifique et on l'ajoute à l'écran
		Image imgScientifique = new Image(getClass().getResourceAsStream("images/scientifique.png"));
		imageViewScientifique = new ImageView(imgScientifique);
		imageViewScientifique.setX(30);
		imageViewScientifique.setY(180);
		imageViewScientifique.setFitWidth(40);
		imageViewScientifique.setPreserveRatio(true);
		pane.getChildren().add(imageViewScientifique);

		// On indique combien il y'a de tuile dans le paquet
		nbTilesSolarProject = new Text(1430, 150,"Il y a "+model.getNbSolarProject()+" projets solaires");
		//pane.getChildren().add(nbTilesSolarProject); / ne plus afficher le nb de tuiles de projets solaire et images

		reloadTour();
		reloadDecade();
		reloadArgent();
		reloadPointVictoire();
		reloadresourcesTech();
		reloadCEP();

		initContinent();
		initExpertise(50, 5);
		initSubvention(250, 100);
		initCentral(250, 100);

		hboxAction = new ViewMenuActionHbox(model);
		hboxAction.init();
		pane.getChildren().add(hboxAction);
    }

	/**
	 * Affiche les barres d'expertise
	 * la premiere case de la premiere piste est en bas a gauche
	 * a partir de (1300, 800)
	 */
	private void initExpertise(int rectWidth, int space) {
		int x = 1320;
		int y = 840;
		// nb pixels entre la gauche de la piste courrante
		// et la gauche de la piste la plus a gauche
		int offset = 0;

		// pour chaque barre d'expertise
		for (Expertise expertise : model.expertises) {
			// rapel case
			int i = 0;
			// pour chaque case de la piste d'expertise
			for (CasePisteExpertise c : expertise.getPiste()) {
				// rectangle de la couleur de l'expertise
				Rectangle rect = new Rectangle(rectWidth, rectWidth, Color.WHITE);
				rect.setStroke(expertise.getColor());
				rect.setX(x + offset);
				rect.setY(y - ((rectWidth+space) * i));
				// nombre de points de la case
				Text nb = new Text((x+offset), (y - ((rectWidth+space) * i - 10)), String.valueOf(c.getNumero()));
				nb.setStroke(expertise.getColor());

				pane.getChildren().add(rect);
				// image bonus si besoin
				if (c.getImageBonus() != null) {
					ImageView img = new ImageView(c.getImageBonus());
					img.setFitWidth(rectWidth - 5);
					img.setFitHeight(rectWidth - 5);
					img.setX(x + offset + 2.5);
					img.setY(y - ((rectWidth + space) * i) + 2.5);
					pane.getChildren().add(img);
				}
				pane.getChildren().add(nb);
				i++;
			}
			offset += rectWidth + space;
		}

		reloadPlayerExpertise(model.getCurrentPLayer());
	}

	//A appeler lors d'une modification du tour
	public void reloadTour(){
		pane.getChildren().remove(nbTour);
		nbTour = new Text(10, 110,"Tour : "+model.getTour()+"/" + (model.NB_TOUR_PAR_DECENNIE-1));
		pane.getChildren().add(nbTour);
	}

	public void reloadPlayerExpertise(Player p){
		if (player1ExpertiseIndicator != null) pane.getChildren().removeAll(player1ExpertiseIndicator);
		player1ExpertiseIndicator = new ArrayList<>();
		int i = 0;
		for (GreenEnergyTypes energy : GreenEnergyTypes.values()) {
			int expertise = p.getExpertise(energy);
			if (expertise > 0) {
				player1ExpertiseIndicator.add(placePlayerExpertise(expertise, i));
				i++;
			}
		}
		pane.getChildren().addAll(player1ExpertiseIndicator);
	}

	//A appeler lors d'une modification de la décénnie
	public void reloadDecade(){
		pane.getChildren().remove(nbDecade);
		nbDecade = new Text(80, 110,"Décénnie : "+model.getDecade()+"/" + model.NB_DECENNIE);
		pane.getChildren().add(nbDecade);
	}

	//A appeler lors d'une modification de l'argent du joueur
	public void reloadArgent(){
		pane.getChildren().remove(argentJoueur);
		argentJoueur = new Text(TEXT_X, 50, "Vous avez "+ model.getCurrentPLayer().getArgent() + " € ");
		pane.getChildren().add(argentJoueur);
	}
	//A appeler lors d'une modification de l'argent du joueur
	public void reloadPointVictoire(){
		pane.getChildren().remove(pointVictoireJoueur);
		pointVictoireJoueur = new Text(TEXT_X, 65, "Vous avez "+ model.getCurrentPLayer().getPointVictoire() + " points de victoire ");
		pane.getChildren().add(pointVictoireJoueur);
	}
	//A appeler lors d'une modification de l'argent du joueur
	public void reloadresourcesTech() {
		pane.getChildren().remove(resourcesTechJoueur);
		resourcesTechJoueur = new Text(TEXT_X, 95, "Vous avez " + model.getCurrentPLayer().getResourcesTech() + " cubes de ressources technologiques. ");
		pane.getChildren().add(resourcesTechJoueur);
	}
	//A appeler lors d'une modification des CEP
	public void reloadCEP(){
		pane.getChildren().remove(CEPJoueur);
		CEPJoueur = new Text(TEXT_X,80,"Vous avez "+model.getCurrentPLayer().getCEP()+" CEP");
		pane.getChildren().add(CEPJoueur);
		pane.getChildren().remove(CEPMarche);
		CEPMarche = new Text(model.width/2-140,model.height/2,
				"Il y a "+model.nbCEPdispo+" CEP dans le marché\n" +
						"Le prix actuel est de "+model.currentPriceCEP+" €"
		);
		pane.getChildren().add(CEPMarche);
	}

	/**
	 * Creer un cercle représentant le jour sur la piste d'expertise
	 * @param expertise expertise du joueur joueur
	 * @param expertiseId id du type d'energie
	 * @return cercle
	 */
	private Circle placePlayerExpertise(int expertise, int expertiseId) {
		// cf valeurs de initExpertise();
		int xPistes = 1320;
		int yPistes = 840;
		int rectWidth = 50;
		int space = 5;

		int radius = 15;
		int x = xPistes + expertiseId*(rectWidth+space) + rectWidth/2;
		int y = yPistes - (expertise-1)*(rectWidth+space) + rectWidth/2;
		Circle circle = new Circle(x, y, radius, Color.INDIANRED);

		return circle;
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
			if(i==0 || i==5) imageViewContinents[i].setX(CONTINENT_05_X_02_Y);
			if(i==0 || i==2) imageViewContinents[i].setY(CONTINENT_05_X_02_Y);
			if(i==3 || i==5) imageViewContinents[i].setY(CONTINENT_35_Y);
			if(i==1 || i==4) imageViewContinents[i].setX(CONTINENT_14_X);
			if(i==2 || i==3) imageViewContinents[i].setX(CONTINENT_23_X);
			// Position des Agendas
			if(i==0 || i==5) imageViewAgendaTiles[i].setX(CONTINENT_05_X_02_Y+AJOUT_AGENDA);
			if(i==0 || i==2) imageViewAgendaTiles[i].setY(CONTINENT_05_X_02_Y-60);
			if(i==3 || i==5) imageViewAgendaTiles[i].setY(CONTINENT_35_Y-110);
			if(i==1 || i==4) imageViewAgendaTiles[i].setX(CONTINENT_14_X+AJOUT_AGENDA);
			if(i==2 || i==3) imageViewAgendaTiles[i].setX(CONTINENT_23_X+AJOUT_AGENDA);
			// Position des sommets
			if(i==0 || i==5) imageViewSommetTiles[i].setX(CONTINENT_05_X_02_Y);
			if(i==0 || i==2) imageViewSommetTiles[i].setY(SOMMET_02_Y);
			if(i==3 || i==5) imageViewSommetTiles[i].setY(SOMMET_35_Y);
			if(i==1 || i==4) imageViewSommetTiles[i].setX(CONTINENT_14_X);
			if(i==2 || i==3) imageViewSommetTiles[i].setX(CONTINENT_23_X);

			// Redimention
			if(i!=2) imageViewContinents[i].setFitWidth(CONTINENT_05_X_02_Y+20);
			imageViewContinents[i].setPreserveRatio(true);
			imageViewAgendaTiles[i].setFitWidth(AGENDA_SOMMET_WIDTH);
			imageViewAgendaTiles[i].setPreserveRatio(true);
			imageViewSommetTiles[i].setFitWidth(AGENDA_SOMMET_WIDTH);
			imageViewSommetTiles[i].setPreserveRatio(true);
		}
		// Redimension de l'Afrique car image trop grande
		imageViewContinents[2].setFitWidth(170);
		imageViewContinents[2].setPreserveRatio(true);

		imageViewContinents[4].setY(CONTINENT_4_Y);
		imageViewContinents[1].setY(CONTINENT_1_Y);

		imageViewAgendaTiles[4].setY(CONTINENT_4_Y-100);
		imageViewAgendaTiles[1].setY(CONTINENT_1_Y-50);

		imageViewSommetTiles[4].setY(CONTINENT_4_Y-50);
		imageViewSommetTiles[1].setY(CONTINENT_1_Y);

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
				subventionName[j].setStyle("-fx-font: 8 arial;");
				// position du texte (les 3 noms de subvention) pour chaque continent
				if(j==0 || j==5) {model.getContinents()[j].getTabRectangleSubvention()[i].setX(k+val-150);subventionName[j].setX(k+val-145);}
				if(j==0 || j==2) {model.getContinents()[j].getTabRectangleSubvention()[i].setY(val);subventionName[j].setY(val+35);}
				if(j==1 || j==4) {model.getContinents()[j].getTabRectangleSubvention()[i].setX(k+val+250);subventionName[j].setX(k+val+255);}
				if(j==2 || j==3) {model.getContinents()[j].getTabRectangleSubvention()[i].setX(k+val+600);subventionName[j].setX(k+val+605);}
				if(j==3 || j==5) {model.getContinents()[j].getTabRectangleSubvention()[i].setY(val+300);subventionName[j].setY(val+335);}
				if(j==1) {model.getContinents()[j].getTabRectangleSubvention()[i].setY(val-120);subventionName[1].setY(val-85);}
				if(j==4) {model.getContinents()[4].getTabRectangleSubvention()[i].setY(val+460);subventionName[4].setY(val+495);}

				// ajout au pane
				pane.getChildren().add(model.getContinents()[j].getTabRectangleSubvention()[i]);
				pane.getChildren().add(subventionName[j]);
			}
			k = k + 80;
		}
	}

	/**
	 * Initialisation des centrales
	 */
	public void initCentral(int val, int k){
		// boucle sur les continents
		for(int i=0;i<model.getContinents().length;i++) {
			// boucle sur le nombre de CEP pen fonction du continent
			for(int j=0;j<model.getContinents()[i].getNbCep();j++) {
				// affichage des cases de centrales
				if(i==0 || i==2) model.getContinents()[i].getTabRectangleCentral()[j].setY(val+80);
				if(i==3 || i==5) model.getContinents()[i].getTabRectangleCentral()[j].setY(val+380);
				if(i==0) model.getContinents()[i].getTabRectangleCentral()[j].setX(k+val-250);
				if(i==2) model.getContinents()[i].getTabRectangleCentral()[j].setX(k+val-180);
				if(i==3) model.getContinents()[i].getTabRectangleCentral()[j].setX(k+val-580);
				if(i==5) model.getContinents()[i].getTabRectangleCentral()[j].setX(k+val-2220);
				if(i==1) {
					model.getContinents()[i].getTabRectangleCentral()[j].setX(k+val-215);
					model.getContinents()[i].getTabRectangleCentral()[j].setY(val-40);
				}
				if(i==4) {
					model.getContinents()[i].getTabRectangleCentral()[j].setX(k+val-1340);
					model.getContinents()[i].getTabRectangleCentral()[j].setY(val+540);
				}
				// ajout au pane
				pane.getChildren().add(model.getContinents()[i].getTabRectangleCentral()[j]);
				k = k + 90;
			}

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
						imageViewTilesSolarProject.setX(CONTINENT_05_X_02_Y);
						break;
					case 2:
						imageViewTilesSolarProject.setX(CONTINENT_05_X_02_Y_SUB_2);
						break;
					case 3:
						imageViewTilesSolarProject.setX(CONTINENT_05_X_02_Y_SUB_3);
						break;
				}
				imageViewTilesSolarProject.setY(CONTINENT_02_Y);
				// mettre l'image en premier plan
				imageViewTilesSolarProject.toFront();
				break;
			case "Afrique" :
				switch (subventionChoice) {
					case 1:
						imageViewTilesSolarProject.setX(CONTINENT_14_X);
						break;
					case 2:
						imageViewTilesSolarProject.setX(CONTINENT_14_X_SUB_2);
						break;
					case 3:
						imageViewTilesSolarProject.setX(CONTINENT_14_X_SUB_3);
						break;
				}
				imageViewTilesSolarProject.setY(CONTINENT_1_Y_SUB);
				imageViewTilesSolarProject.toFront();
				break;
			case "Amérique du Sud" :
				switch (subventionChoice) {
					case 1:
						imageViewTilesSolarProject.setX(CONTINENT_23_X);
						break;
					case 2:
						imageViewTilesSolarProject.setX(CONTINENT_23_X_SUB_2);
						break;
					case 3:
						imageViewTilesSolarProject.setX(CONTINENT_23_X_SUB_3);
						break;
				}
				imageViewTilesSolarProject.setY(CONTINENT_02_Y);
				imageViewTilesSolarProject.toFront();
				break;
			case "Amérique du Nord" :
				switch (subventionChoice) {
					case 1:
						imageViewTilesSolarProject.setX(CONTINENT_23_X);
						break;
					case 2:
						imageViewTilesSolarProject.setX(CONTINENT_23_X_SUB_2);
						break;
					case 3:
						imageViewTilesSolarProject.setX(CONTINENT_23_X_SUB_3);
						break;
				}
				imageViewTilesSolarProject.setY(CONTINENT_35_Y);
				imageViewTilesSolarProject.toFront();
				break;
			case "Océanie" :
				switch (subventionChoice) {
					case 1:
						imageViewTilesSolarProject.setX(CONTINENT_14_X);
						break;
					case 2:
						imageViewTilesSolarProject.setX(CONTINENT_14_X_SUB_2);
						break;
					case 3:
						imageViewTilesSolarProject.setX(CONTINENT_14_X_SUB_3);
						break;
				}
				imageViewTilesSolarProject.setY(CONTINENT_4_Y_SUB);
				imageViewTilesSolarProject.toFront();
				break;
			case "Asie" :
				switch (subventionChoice) {
					case 1:
						imageViewTilesSolarProject.setX(CONTINENT_05_X_02_Y);
						break;
					case 2:
						imageViewTilesSolarProject.setX(CONTINENT_05_X_02_Y_SUB_2);
						break;
					case 3:
						imageViewTilesSolarProject.setX(CONTINENT_05_X_02_Y_SUB_3);
						break;
				}
				imageViewTilesSolarProject.setY(CONTINENT_35_Y);
				imageViewTilesSolarProject.toFront();
				break;
		}
	}

	public void addScientifiqueToProject(int projectChoice, ImageView imageViewScientifique, Continent continent){
		switch(continent.getName()) {
			case "Europe" :
				switch (projectChoice) {
					case 1:
						imageViewScientifique.setX(CONTINENT_05_X_02_Y);
						break;
					case 2:
						imageViewScientifique.setX(CONTINENT_05_X_02_Y_SUB_2);
						break;
					case 3:
						imageViewScientifique.setX(CONTINENT_05_X_02_Y_SUB_3);
						break;
				}
				imageViewScientifique.setY(CONTINENT_02_Y);
				imageViewScientifique.toFront();
				break;
			case "Afrique" :
				switch (projectChoice) {
					case 1:
						imageViewScientifique.setX(CONTINENT_14_X);
						break;
					case 2:
						imageViewScientifique.setX(CONTINENT_14_X_SUB_2);
						break;
					case 3:
						imageViewScientifique.setX(CONTINENT_14_X_SUB_3);
						break;
				}
				imageViewScientifique.setY(CONTINENT_1_Y_SUB);
				imageViewScientifique.toFront();
				break;
			case "Amérique du Sud" :
				switch (projectChoice) {
					case 1:
						imageViewScientifique.setX(CONTINENT_23_X);
						break;
					case 2:
						imageViewScientifique.setX(CONTINENT_23_X_SUB_2);
						break;
					case 3:
						imageViewScientifique.setX(CONTINENT_23_X_SUB_3);
						break;
				}
				imageViewScientifique.setY(CONTINENT_02_Y);
				imageViewScientifique.toFront();
				break;
			case "Amérique du Nord" :
				switch (projectChoice) {
					case 1:
						imageViewScientifique.setX(CONTINENT_23_X);
						break;
					case 2:
						imageViewScientifique.setX(CONTINENT_23_X_SUB_2);
						break;
					case 3:
						imageViewScientifique.setX(CONTINENT_23_X_SUB_3);
						break;
				}
				imageViewScientifique.setY(CONTINENT_35_Y);
				imageViewScientifique.toFront();
				break;
			case "Océanie" :
				switch (projectChoice) {
					case 1:
						imageViewScientifique.setX(CONTINENT_14_X);
						break;
					case 2:
						imageViewScientifique.setX(CONTINENT_14_X_SUB_2);
						break;
					case 3:
						imageViewScientifique.setX(CONTINENT_14_X_SUB_3);
						break;
				}
				imageViewScientifique.setY(CONTINENT_4_Y_SUB);
				imageViewScientifique.toFront();
				break;
			case "Asie" :
				switch (projectChoice) {
					case 1:
						imageViewScientifique.setX(CONTINENT_05_X_02_Y);
						break;
					case 2:
						imageViewScientifique.setX( CONTINENT_05_X_02_Y_SUB_2);
						break;
					case 3:
						imageViewScientifique.setX( CONTINENT_05_X_02_Y_SUB_3);
						break;
				}
				imageViewScientifique.setY(CONTINENT_35_Y);
				imageViewScientifique.toFront();
				break;
		}
	}

	public void addScientifiqueToSommet(ImageView imageViewScientifique, Scientifique scientifique, SommetTile sommetTile){
		Continent continent = sommetTile.getContinent();
		switch(continent.getName()) {
			case "Europe" :
				imageViewScientifique.setX(CONTINENT_05_X_02_Y);
				imageViewScientifique.setY(SOMMET_02_Y);
				imageViewScientifique.toFront();
				break;
			case "Afrique" :
				imageViewScientifique.setX(CONTINENT_14_X);
				imageViewScientifique.setY(CONTINENT_1_Y);
				imageViewScientifique.toFront();
				break;
			case "Amérique du Sud" :
				imageViewScientifique.setX(CONTINENT_23_X);
				imageViewScientifique.setY(SOMMET_02_Y);
				imageViewScientifique.toFront();
				break;
			case "Amérique du Nord" :
				imageViewScientifique.setX(CONTINENT_23_X);
				imageViewScientifique.setY(SOMMET_35_Y);
				imageViewScientifique.toFront();
				break;
			case "Océanie" :
				imageViewScientifique.setX(CONTINENT_14_X);
				imageViewScientifique.setY(CONTINENT_4_Y-50);
				imageViewScientifique.toFront();
				break;
			case "Asie" :
				imageViewScientifique.setX(CONTINENT_05_X_02_Y);
				imageViewScientifique.setY(SOMMET_35_Y);
				imageViewScientifique.toFront();
				break;
		}
		// le scientifique quitte le continent lorsqu'il va sur un sommet
		scientifique.setContinent(null);
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
						imageViewTilesSolarProjectBack.setX(CONTINENT_05_X_02_Y);
						break;
					case 2:
						imageViewTilesSolarProjectBack.setX(CONTINENT_05_X_02_Y_SUB_2);
						break;
					case 3:
						imageViewTilesSolarProjectBack.setX(CONTINENT_05_X_02_Y_SUB_3);
						break;
				}
				imageViewTilesSolarProjectBack.setY(CONTINENT_02_Y);
				imageViewTilesSolarProjectBack.toFront();
				break;
			case "Afrique" :
				switch (projectChoice) {
					case 1:
						imageViewTilesSolarProjectBack.setX(CONTINENT_14_X);
						break;
					case 2:
						imageViewTilesSolarProjectBack.setX(CONTINENT_14_X_SUB_2);
						break;
					case 3:
						imageViewTilesSolarProjectBack.setX(CONTINENT_14_X_SUB_3);
						break;
				}
				imageViewTilesSolarProjectBack.setY(CONTINENT_1_Y_SUB);
				imageViewTilesSolarProjectBack.toFront();
				break;
			case "Amérique du Sud" :
				switch (projectChoice) {
					case 1:
						imageViewTilesSolarProjectBack.setX(CONTINENT_23_X);
						break;
					case 2:
						imageViewTilesSolarProjectBack.setX(CONTINENT_23_X_SUB_2);
						break;
					case 3:
						imageViewTilesSolarProjectBack.setX(CONTINENT_23_X_SUB_3);
						break;
				}
				imageViewTilesSolarProjectBack.setY(CONTINENT_02_Y);
				imageViewTilesSolarProjectBack.toFront();
				break;
			case "Amérique du Nord" :
				switch (projectChoice) {
					case 1:
						imageViewTilesSolarProjectBack.setX(CONTINENT_23_X);
						break;
					case 2:
						imageViewTilesSolarProjectBack.setX(CONTINENT_23_X_SUB_2);
						break;
					case 3:
						imageViewTilesSolarProjectBack.setX(CONTINENT_23_X_SUB_3);
						break;
				}
				imageViewTilesSolarProjectBack.setY(CONTINENT_35_Y);
				imageViewTilesSolarProjectBack.toFront();
				break;
			case "Océanie" :
				switch (projectChoice) {
					case 1:
						imageViewTilesSolarProjectBack.setX(CONTINENT_14_X);
						break;
					case 2:
						imageViewTilesSolarProjectBack.setX(CONTINENT_14_X_SUB_2);
						break;
					case 3:
						imageViewTilesSolarProjectBack.setX(CONTINENT_14_X_SUB_3);
						break;
				}
				imageViewTilesSolarProjectBack.setY(CONTINENT_4_Y_SUB);
				imageViewTilesSolarProjectBack.toFront();
				break;
			case "Asie" :
				switch (projectChoice) {
					case 1:
						imageViewTilesSolarProjectBack.setX(CONTINENT_05_X_02_Y);
						break;
					case 2:
						imageViewTilesSolarProjectBack.setX(CONTINENT_05_X_02_Y_SUB_2);
						break;
					case 3:
						imageViewTilesSolarProjectBack.setX(CONTINENT_05_X_02_Y_SUB_3);
						break;
				}
				imageViewTilesSolarProjectBack.setY(CONTINENT_35_Y);
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
		imageView.setFitWidth(65);
		//TODO : Switch pour tooltip une fois toute les tuiles implementés
		Tooltip.install(imageView, new Tooltip("Mettre en place : + 3 Ressources technologiques"));
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
		alert.setContentText("Il n'y a pas votre source d'énergie dans ce sommet");
		alert.showAndWait();
	}

	/**
	 * vérifie si c'est la fin du jeu
	 */
	public void isEndGame() throws IOException {
		// si le nombre de décénnie max est atteinte => renvoie true par model.EndGame
		if(model.endGame()){
			// message d'alerte (confirmation)
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setTitle("Partie terminé !");
			alert.setContentText("la partie est terminée, Voulez-vous rejouer?");
			ButtonType btnRestart = new ButtonType("Oui");
			alert.getButtonTypes().setAll(btnRestart);
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == btnRestart) {
				// récupération de la fenetre du jeu
				Stage stage = (Stage) scene.getWindow();
				// fermeture de la fenetre du jeu actuel
				stage.close();
				// création de la nouvelle partie
				Main main = new Main();
				main.start(new Stage());
			}
		}
	}
}
