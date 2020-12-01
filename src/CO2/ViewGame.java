package CO2;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
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

	Text nbTour;
	Text nbDecade;
	Text argentJoueur;
	Text pointVictoireJoueur;
	Text resourcesTechJoueur;
	Text co2 ;
	Text CEPJoueur;
	Text CEPMarche;

	//text pour afficher le nombre de CEP de chaque continent
	Text CEPAsie;
	Text CEPAfrique;
	Text CEPOceanie;
	Text CEPEurope;
	Text CEPAmNord;
	Text CEPAmSud;

	// listes des cercles représentant le joueur sur le plateau
	// sur les pistes d'expertise
	List<Circle> player1ExpertiseIndicator;
	// sur les continents controlles
	List<Circle> player1ControlIndicator;
	Image imgArgent;
	Image imgRessource;
	Image imgRecherche;


	//projets
	Image imgTilesSolarProject;
	Image imgTilesSolarProjectBack;

	//centrales
	Image imgCentralSolar;
	Image imgCentralCharbon;
	Image imgCentralPetrole;
	Image imgCentralGaz;

	ImageView imageViewScientifiqueN1;
	ImageView imageViewScientifiqueN2;

	ViewMenuActionHbox hboxAction;
	private Object AlertType;


	Scene scene;

	// CONSTANTES

	// coordonnée du texte en x
	private final int TEXT_X = 10;

	// largeur des images agendas et sommets
	private final int AGENDA_SOMMET_WIDTH = 60;
	// valeur d'ajout sur des coordonnées d'agenda
	private final int AJOUT_AGENDA = 85;

	// coordonnées des continents

	private final int[] continentX = {
			200, // EuropeX
			600, // AfriqueX
			950, // AmSudX
			950, // AmNordX
			600, // OcéanieX
			200  // AsieX
	};
	private final int[] continentY = {
			200, // EuropeY
			70,  // AfriqueY
			200, // AmSudY
			550, // AmNordY
			700, // OcéanieY
			550  // AsieY
	};
	private final int[] agendaX = {
			continentX[0] + 85, // EuropeAgendaX
			continentX[1] + 85, // AfriqueAgendaX
			continentX[2] + 85, // AmSudAgendaX
			continentX[3] + 85, // AmNordAgendaX
			continentX[4] + 85, // OcéanieAgendaX
			continentX[5] + 85  // AsieAgendaX
	};
	private final int[] agendaY = {
			continentY[0] - 50, // EuropeAgendaY
			continentY[1] - 40, // AfriqueAgendaY
			continentY[2] - 50, // AmSudAgendaY
			continentY[3] - 100,// AmNordAgendaY
			continentY[4] - 90, // OcéanieAgendaY
			continentY[5] - 100 // AsieAgendaY
	};

	private final int[] sommetX = {
			continentX[0], // EuropeSommetX
			continentX[1], // AfriqueSommetX
			continentX[2], // AmSudSommetX
			continentX[3], // AmNordSommetX
			continentX[4], // OcéanieSommetX
			continentX[5]  // AsieSommetX
	};
	private final int[] sommetY = {
			continentY[0] - 10, // EuropeSommetY
			continentY[1],      // AfriqueSommetY
			continentY[2] - 10, // AmSudSommetY
			continentY[3] - 60, // AmNordSommetY
			continentY[4] - 50, // OcéanieSommetY
			continentY[5] - 60  // AsieSommetY
	};

	private final int[][] subventionX = {
			{continentX[0],continentX[0] + 80, continentX[0] + 160},
			{continentX[1],continentX[1] + 80, continentX[1] + 160},
			{continentX[2],continentX[2] + 80, continentX[2] + 160},
			{continentX[3],continentX[3] + 80, continentX[3] + 160},
			{continentX[4],continentX[4] + 80, continentX[4] + 160},
			{continentX[5],continentX[5] + 80, continentX[5] + 160}
	};
	private final int[] subventionY = {
			continentY[0]+50,
			continentY[1]+60,
			continentY[2]+50,
			continentY[3],
			continentY[4]+10,
			continentY[5]
	};
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
    	// On lance l'initialisation du model qui générera toute les pièces, les joueurs et les valeurs (points,...)
    	this.model.init();

    	imgArgent = new Image(getClass().getResourceAsStream("images/argent.png"));
		imgRessource = new Image(getClass().getResourceAsStream("images/ressource.png"));
		imgRecherche = new Image(getClass().getResourceAsStream("images/recherche.png"));


    	// On récupère l'image de la tuile et on l'ajoute à l'écran
		imgTilesSolarProject = new Image(getClass().getResourceAsStream("images/Projets/TilesSolarProjectRecto.png"));
		imgTilesSolarProjectBack = new Image(getClass().getResourceAsStream("images/Projets/TilesSolarProjectVerso.png"));
		imgCentralSolar = new Image(getClass().getResourceAsStream("images/Centrales/Solar.png"));
		imgCentralCharbon = new Image(getClass().getResourceAsStream("images/Centrales/Coal.png"));
		imgCentralPetrole = new Image(getClass().getResourceAsStream("images/Centrales/Oil.png"));
		imgCentralGaz = new Image(getClass().getResourceAsStream("images/Centrales/Gas.png"));


		//On récupère l'image d'un scientifique et on l'ajoute à l'écran
		// img scientifique n°1
		imageViewScientifiqueN1 = model.getCurrentPLayer().getCurrentScientifique().getImgScientifique();
		imageViewScientifiqueN1.setFitWidth(40);
		imageViewScientifiqueN1.setPreserveRatio(true);
		deplacerScientifiqueReserve(imageViewScientifiqueN1);
		pane.getChildren().add(imageViewScientifiqueN1);

		reloadTour();
		reloadDecade();
		reloadArgent();
		reloadPointVictoire();
		reloadresourcesTech();
		reloadCEP();
		reloadCo2();

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


	//A appeler lors d'une modification d'expertise
	public void reloadPlayerExpertise(Player p){
		if (player1ExpertiseIndicator != null) pane.getChildren().removeAll(player1ExpertiseIndicator);
		player1ExpertiseIndicator = new ArrayList<>();
		int i = 0;
		for (GreenEnergyTypes energy : GreenEnergyTypes.values()) {
			int expertise = p.getExpertise(energy);
			if (expertise > 0) {
				player1ExpertiseIndicator.add(placePlayerExpertise(expertise, i, p.getColor()));
				i++;
			}
		}
		pane.getChildren().addAll(player1ExpertiseIndicator);
	}
	//A appeler lors d'une modification du controle de continent
	public void reloadContinentControl(Player p){
		if (player1ControlIndicator != null) pane.getChildren().removeAll(player1ControlIndicator);
		player1ControlIndicator = new ArrayList<>();
		Continent[] continents = model.getContinents();
		for (int i = 0; i < continents.length; i++) {
			// si le joueur controlle ce continent
			if (p.getContinentsControlles().contains(continents[i]))
				// creation de son point
				player1ControlIndicator.add(placePlayerControl(i, p.getColor()));
		}
		pane.getChildren().addAll(player1ControlIndicator);
	}

	//A appeler lors d'une modification du tour
	public void reloadTour(){
		pane.getChildren().remove(nbTour);
		nbTour = new Text(10, 110,"Tour : "+model.getTour()+"/" + (model.NB_TOUR_PAR_DECENNIE-1));
		pane.getChildren().add(nbTour);
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
		//reload CEP Joueur
		pane.getChildren().remove(CEPJoueur);
		CEPJoueur = new Text(TEXT_X,80,"Vous avez "+model.getCurrentPLayer().getCEP()+" CEP");
		pane.getChildren().add(CEPJoueur);
		//reload CEP marche
		pane.getChildren().remove(CEPMarche);
		CEPMarche = new Text(model.width/2-140,model.height/2,
				"Il y a "+model.nbCEPdispo+" CEP dans le marché\n" +
						"Le prix actuel est de "+model.currentPriceCEP+" €"
		);
		pane.getChildren().add(CEPMarche);
		//reload CEP continent
		pane.getChildren().remove(CEPAsie);
		pane.getChildren().remove(CEPAmNord);
		pane.getChildren().remove(CEPAmSud);
		pane.getChildren().remove(CEPEurope);
		pane.getChildren().remove(CEPOceanie);
		pane.getChildren().remove(CEPAfrique);
		CEPAsie = new Text(CONTINENT_05_X_02_Y+85, CONTINENT_35_Y-120, "L'Asie à "+model.getContinents()[5].getNbCep()+" CEP");
		CEPAmNord = new Text(CONTINENT_4_Y+300, (model.height/2)-20, "L'Amérique du nord à "+model.getContinents()[3].getNbCep()+" CEP");
		CEPAmSud = new Text(CONTINENT_4_Y+300, CONTINENT_05_X_02_Y-70, "L'Amérique du sud à "+model.getContinents()[2].getNbCep()+" CEP");
		CEPEurope = new Text(CONTINENT_05_X_02_Y+70, CONTINENT_05_X_02_Y-70, "L'Europe à "+model.getContinents()[0].getNbCep()+" CEP");
		CEPAfrique = new Text(CONTINENT_4_Y-25,19, "L'Afrique à "+model.getContinents()[1].getNbCep()+" CEP");
		CEPOceanie = new Text(CONTINENT_4_Y-25, CONTINENT_35_Y+30, "L'Océanie à "+model.getContinents()[4].getNbCep()+" CEP");
		pane.getChildren().add(CEPAsie);
		pane.getChildren().add(CEPAmNord);
		pane.getChildren().add(CEPAmSud);
		pane.getChildren().add(CEPEurope);
		pane.getChildren().add(CEPAfrique);
		pane.getChildren().add(CEPOceanie);
	}

	/**
	 * Mets a jour le texte indiquant le niveaux de CO2 grace au model
	 */
	public void reloadCo2() {
		pane.getChildren().remove(co2);
		co2 = new Text(TEXT_X, 125, "Valeur du CO2 : " + model.getCo2() + ". ");
		pane.getChildren().add(co2);
	}

	/**
	 * Creer un cercle représentant le joueur sur la piste d'expertise
	 * @param expertise expertise du joueur joueur
	 * @param expertiseId id du type d'energie
	 * @param playerColor couleur du joueur
	 * @return cercle
	 */
	private Circle placePlayerExpertise(int expertise, int expertiseId, Color playerColor) {
		// cf valeurs de initExpertise();
		int xPistes = 1320;
		int yPistes = 840;
		int rectWidth = 50;
		int space = 5;

		int radius = 15;
		int x = xPistes + expertiseId*(rectWidth+space) + rectWidth/2;
		int y = yPistes - (expertise-1)*(rectWidth+space) + rectWidth/2;
		Circle circle = new Circle(x, y, radius, playerColor);

		return circle;
	}


	/**
	 * Creer un cercle représentant le joueur sur le continent qu'il controlle
	 * @param continentId
	 * @param playerColor
	 * @return
	 */
	private Circle placePlayerControl(int continentId, Color playerColor) {
		int radius = 15;
		int x = 0;
		int y = 0;
		int xOffset = 75;
		int yOffset = 75;

		if(continentId==0 || continentId==5) x = CONTINENT_05_X_02_Y+AJOUT_AGENDA + xOffset;
		if(continentId==0 || continentId==2) y = CONTINENT_05_X_02_Y-60 + yOffset;
		if(continentId==3 || continentId==5) y = CONTINENT_35_Y-110 + yOffset;
		if(continentId==1 || continentId==4) x = CONTINENT_14_X+AJOUT_AGENDA + xOffset;
		if(continentId==2 || continentId==3) x = CONTINENT_23_X+AJOUT_AGENDA + xOffset;
		if(continentId==4) y = CONTINENT_4_Y-100 + yOffset;
		if(continentId==1) y = CONTINENT_1_Y-50 + yOffset;

		Circle circle = new Circle(x, y, radius, playerColor);

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
			imageViewSommetTiles[i] = model.getContinents()[i].getSommetTile().getImageSommetTile();
			// Position des continents
			imageViewContinents[i].setX(continentX[i]);
			imageViewContinents[i].setY(continentY[i]);
			// Position des Agendas
			imageViewAgendaTiles[i].setX(agendaX[i]);
			imageViewAgendaTiles[i].setY(agendaY[i]);
			// Position des sommets
			imageViewSommetTiles[i].setX(sommetX[i]);
			imageViewSommetTiles[i].setY(sommetY[i]);

			// Redimention
			if(i!=2) imageViewContinents[i].setFitWidth(220);
			imageViewContinents[i].setPreserveRatio(true);
			imageViewAgendaTiles[i].setFitWidth(AGENDA_SOMMET_WIDTH);
			imageViewAgendaTiles[i].setPreserveRatio(true);
			imageViewSommetTiles[i].setFitWidth(AGENDA_SOMMET_WIDTH);
			imageViewSommetTiles[i].setPreserveRatio(true);
		}



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
		for(int i=0;i<3;i++) {
			for(int j=0;j<model.getContinents().length;j++) {

				model.getContinents()[j].getTabRectangleSubvention()[i].setX(subventionX[j][i]);
				model.getContinents()[j].getTabRectangleSubvention()[i].setY(subventionY[j]);

				switch (i) {
					case 0 -> model.getContinents()[j].getTabRectangleSubvention()[i].setFill(new ImagePattern(imgArgent));
					case 1 -> model.getContinents()[j].getTabRectangleSubvention()[i].setFill(new ImagePattern(imgRessource));
					case 2 -> model.getContinents()[j].getTabRectangleSubvention()[i].setFill(new ImagePattern(imgRecherche));
				}

				pane.getChildren().add(model.getContinents()[j].getTabRectangleSubvention()[i]);
			}
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
	 * @param imageProject
	 */
	public void addTuilesToSubvention(int subventionChoice, Image imageProject, Continent continent){
		//TODO : Switch pour tooltip une fois toute les tuiles implementés
		Tooltip.install(continent.getTabRectangleSubvention()[subventionChoice], new Tooltip("Mettre en place : + 3 Ressources technologiques"));
		continent.getTabRectangleSubvention()[subventionChoice].setFill(new ImagePattern(imageProject));
	}

	/**
	 * Mettre en place une subvention
	 */
	public void mettreEnPlaceProjet(int projectChoice, Image imageSolarProjectBack, Continent continent){
		continent.getTabRectangleSubvention()[projectChoice].setFill(new ImagePattern(imageSolarProjectBack));
	}

	/**
	 * Reset d'une subvention
	 */
	public void resetSubvention(Continent continent, int idSubvention){
		if (idSubvention == 0) continent.getTabRectangleSubvention()[idSubvention].setFill(new ImagePattern(imgArgent));
		if (idSubvention == 1) continent.getTabRectangleSubvention()[idSubvention].setFill(new ImagePattern(imgRessource));
		if (idSubvention == 2) continent.getTabRectangleSubvention()[idSubvention].setFill(new ImagePattern(imgRecherche));
	}

	/** Ajouter un Scientifique sur un projet */
	public void addScientifiqueToProject(int projectChoice, ImageView imageViewScientifique, Continent continent){
		imageViewScientifique.setX(continent.getTabRectangleSubvention()[projectChoice].getX());
		imageViewScientifique.setY(continent.getTabRectangleSubvention()[projectChoice].getY());
		imageViewScientifique.toFront();
	}

	/** Ajouter un Scientifique sur un sommet */
	public void addScientifiqueToSommet(ImageView imageViewScientifique, Scientifique scientifique, SommetTile sommetTile){
		int nbSubject = sommetTile.getNbSubjects();
		int indexSubject = sommetTile.getIndexSubject(scientifique.getSubject());
		Continent continent = sommetTile.getContinent();
		int x = 0;
		int y = 0;

		if (nbSubject == 2){
			if (indexSubject == 0) {x = -5;y=-10;}
			else if (indexSubject == 1){x = 25;y=-10;}
		} else if (nbSubject == 3){
			//TODO Coordonnées pour sommet à 3 sujets
		} else if (nbSubject == 4){
			//TODO Coordonnées pour sommet à 4 sujets
		}
		imageViewScientifique.setX(continent.getSommetTile().getImageSommetTile().getX()+x);
		imageViewScientifique.setY(continent.getSommetTile().getImageSommetTile().getY()+y);
		imageViewScientifique.toFront();

		// le scientifique quitte le continent lorsqu'il va sur un sommet
		scientifique.setContinent(null);
	}

	public void deplacerScientifiqueReserve(ImageView imageViewScientifique){
		imageViewScientifique.setX(30);
		imageViewScientifique.setY(180);
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


	/**
	 * Place la centrale sur le continent a la bonne position
	 * @param type Le type de la centrale
	 * @param continent Le continent ou deposer la centrale
	 * @param index l'index pour la position de la centrale sur le continent
	 */
	public void addCentrale(typesCentral type, Continent continent,int index) {
		Image central = imgCentralCharbon;

		switch (type){
			case CHARBON:
				central = imgCentralCharbon;
				Tooltip.install(continent.getTabRectangleCentral()[index], new Tooltip("Centrale au Charbon, Pollution : " + typesCentral.CHARBON.getCo2()));
				break;
			case PETROLE :
				central = imgCentralPetrole;
				Tooltip.install(continent.getTabRectangleCentral()[index], new Tooltip("Centrale au Petrole, Pollution : " + typesCentral.PETROLE.getCo2()));
				break;
			case GAZNATUREL :
				central = imgCentralGaz;
				Tooltip.install(continent.getTabRectangleCentral()[index], new Tooltip("Centrale a Gaz, Pollution : " + typesCentral.GAZNATUREL.getCo2()));
				break;
			case SOLAIRE:
				central = imgCentralSolar;
				Tooltip.install(continent.getTabRectangleCentral()[index], new Tooltip("Centrale Solaire, Polution :" + typesCentral.SOLAIRE.getCo2()));				break;
				//TODO reboissement, recylage, fusionfroide, biomass
		}
		continent.getTabRectangleCentral()[index].setFill(new ImagePattern(central));
	}

	/**
	 * Reset d'une centrale
	 */
	public void resetCentrale(Continent continent, int idCentrale){
		continent.getTabRectangleCentral()[idCentrale].setFill(null);
	}

	/**
	 * Permet d'ajouter un scientifique à la réserve du joueur
	 */
	public void addScientifiqueToReserve(){
		//On récupère l'image du deuxième scientifique du joueur
		imageViewScientifiqueN2 = model.getCurrentPLayer().getScientifiques().get(1).getImgScientifique();
		imageViewScientifiqueN2.setFitWidth(40);
		imageViewScientifiqueN2.setPreserveRatio(true);
		deplacerScientifiqueReserve(imageViewScientifiqueN2);
		pane.getChildren().add(imageViewScientifiqueN2);
	}
}
