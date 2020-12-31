package CO2;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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

	public final int PROPOSER_PROJET = 0;
	public final int METTRE_EN_PLACE_PROJET = 1;

	public Object btnTest;
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
	Text objectifCompagnie;

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
	Image biomassProjetRecto;
	Image biomassProjectVerso;
	Image fusionProjetRecto;
	Image fusionProjectVerso;
	Image reforestationProjetRecto;
	Image reforestationProjectVerso;
	Image recyclingProjetRecto;
	Image recyclingProjectVerso;
	Image solarProjetRecto;
	Image solarProjectVerso;

	//centrales
	Image imgCentralSolar;
	Image imgCentralBiomass;
	Image imgCentralNuclear;
	Image imgCentralRecycling;
	Image imgCentralReforestation;


	Image imgCentralCharbon;
	Image imgCentralPetrole;
	Image imgCentralGaz;

	ViewMenuActionHbox hboxAction;
	private Object AlertType;

	Button btnDefausser;

	//evenements
	Rectangle evenement;

	Scene scene;

	// CONSTANTES

	// coordonnée du texte en x
	private final int TEXT_X = 10;

	// largeur des images agendas et sommets
	private final int AGENDA_SOMMET_WIDTH = 60;
	// valeur d'ajout sur des coordonnées d'agenda
	private final int AJOUT_AGENDA = 85;
	// largeur des continents
	private final int CONTINENT_WIDTH = 220;

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

	// agendas
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
	// sommets
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

	// subventions (subvention x = continent x : voir initSubvention)
	private final int[] subventionY = {
			continentY[0]+50, // ligne de subvention de l'europe
			continentY[1]+60, // ligne de subvention de l'afrique
			continentY[2]+50, // ligne de subvention de l'amSud
			continentY[3],    // ligne de subvention de l'amNord
			continentY[4]+10, // ligne de subvention de l'océanie
			continentY[5]     // ligne de subvention de l'asie
	};

	// centrales val = 250, k = 100
	private final int[] centraleX = {
			continentX[0] - 100, // premiere case de centrale europe
			continentX[1] - 15,  // premiere case de centrale afrique
			continentX[2] - 60,  // premiere case de centrale amSud
			continentX[3] - 100, // premiere case de centrale amNord
			continentX[4] - 60,  // premiere case de centrale océanie
			continentX[5] - 180  // premiere case de centrale asie
	};
	private final int[] centraleY = {
			continentY[0] + 130, // ligne de centrale de l'europe
			continentY[1] + 140, // ligne de centrale de l'afrique
			continentY[2] + 130, // ligne de centrale de l'amSud
			continentY[3] + 80,  // ligne de centrale de l'amNord
			continentY[4] + 90, // ligne de centrale de l'océanie
			continentY[5] + 80  // ligne de centrale de l'asie
	};
	private ImageView[] imageViewSommetTiles;

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
		biomassProjetRecto = new Image(getClass().getResourceAsStream("images/Projets/BIOMASSProjectRecto.png"));
		biomassProjectVerso = new Image(getClass().getResourceAsStream("images/Projets/BIOMASSProjectVerso.png"));
		fusionProjetRecto = new Image(getClass().getResourceAsStream("images/Projets/FUSIONProjectRecto.png"));
		fusionProjectVerso = new Image(getClass().getResourceAsStream("images/Projets/FUSIONProjectVerso.png"));
		reforestationProjetRecto = new Image(getClass().getResourceAsStream("images/Projets/REFORESTATIONProjectRecto.png"));
		reforestationProjectVerso = new Image(getClass().getResourceAsStream("images/Projets/REFORESTATIONProjectVerso.png"));
		recyclingProjetRecto = new Image(getClass().getResourceAsStream("images/Projets/RECYCLINGProjectRecto.png"));
		recyclingProjectVerso = new Image(getClass().getResourceAsStream("images/Projets/RECYCLINGProjectVerso.png"));
		solarProjetRecto = new Image(getClass().getResourceAsStream("images/Projets/SOLARProjectRecto.png"));
		solarProjectVerso = new Image(getClass().getResourceAsStream("images/Projets/SOLARProjectVerso.png"));
		imgCentralSolar = new Image(getClass().getResourceAsStream("images/Centrales/Solar.png"));
		imgCentralBiomass = new Image(getClass().getResourceAsStream("images/Centrales/Biomass.png"));
		imgCentralNuclear = new Image(getClass().getResourceAsStream("images/Centrales/Nuclear.png"));
		imgCentralRecycling= new Image(getClass().getResourceAsStream("images/Centrales/Recycling.png"));
		imgCentralReforestation= new Image(getClass().getResourceAsStream("images/Centrales/Reforestation.png"));
		imgCentralCharbon = new Image(getClass().getResourceAsStream("images/Centrales/Coal.png"));
		imgCentralPetrole = new Image(getClass().getResourceAsStream("images/Centrales/Oil.png"));
		imgCentralGaz = new Image(getClass().getResourceAsStream("images/Centrales/Gas.png"));


		//On récupère l'image des scientifiques et on les ajoutes à l'écran
		for (int i = 0; i < model.getCurrentPLayer().getScientifiques().size(); i++) {
			addScientifiqueToPane(i);
		}


		initContinent();
		initExpertise(1320, 840, 50, 5);
		initSubvention();
		initCentral();
		initEvenements();

		reloadTour();
		reloadDecade();
		reloadArgent();
		reloadPointVictoire();
		reloadresourcesTech();
		reloadCEPRessTech();
		reloadCo2();
		reloadObjectifCompagnie();

		hboxAction = new ViewMenuActionHbox(model);
		hboxAction.init();
		pane.getChildren().add(hboxAction);
    }

	/**
	 * Affiche les barres d'expertise
	 * la premiere case de la premiere piste est en bas a gauche
	 * a partir de (xStart, yStart)
	 */
	private void initExpertise(int xStart, int yStart, int rectWidth, int space) {
		int x = xStart;
		int y = yStart;
		// nb pixels entre la gauche de la piste courrante
		// et la gauche de la piste la plus a gauche
		int offset = 0;

		// pour chaque barre d'expertise
		for (PisteExpertise pisteExpertise : model.getPistesExpertise()) {
			// rapel case
			int i = 0;
			// pour chaque case de la piste d'expertise
			for (CasePisteExpertise c : pisteExpertise.getPiste()) {
				// rectangle de la couleur de l'expertise
				Rectangle rect = new Rectangle(rectWidth, rectWidth, Color.WHITE);
				rect.setStroke(pisteExpertise.getColor());
				rect.setX(x + offset);
				rect.setY(y - ((rectWidth+space) * i));
				// nombre de points de la case
				Text nb = new Text((x+offset), (y - ((rectWidth+space) * i - 10)), String.valueOf(c.getNumero()));
				nb.setStroke(pisteExpertise.getColor());

				pane.getChildren().add(rect);
				// image bonus si besoin
				if (c.getImageBonus() != null) {
					ImageView img = new ImageView(c.getImageBonus());
					img.setFitWidth(rectWidth - 5);
					img.setFitHeight(rectWidth - 5);
					img.setX(x + offset + 2.5);
					img.setY(y - ((rectWidth + space) * i) + 2.5);
					Tooltip.install(img, new Tooltip(c.getBonus().description));
					pane.getChildren().add(img);
				}
				pane.getChildren().add(nb);
				i++;
			}
			// logo de l'expertise au dessus de la barre
			ImageView imgExpertise = new ImageView(new Image(getClass().getResourceAsStream("images/BonusExpertise/"+pisteExpertise.getType()+".png")));
			imgExpertise.setFitWidth(rectWidth - 5);
			imgExpertise.setFitHeight(rectWidth - 5);
			imgExpertise.setX(x + offset + 2.5);
			imgExpertise.setY(y - ((rectWidth + space) * 10) + 2.5);
			pane.getChildren().add(imgExpertise);

			// offset la prochaine piste
			offset += rectWidth + space;
		}

		reloadPlayerExpertise(model.getCurrentPLayer());
	}


	//A appeler lors d'une modification d'expertise
	public void reloadPlayerExpertise(Player p){
		// initialisation de la liste des cercle representant le joueur
		if (player1ExpertiseIndicator != null) pane.getChildren().removeAll(player1ExpertiseIndicator);
		player1ExpertiseIndicator = new ArrayList<>();

		// pour chaque piste
		int i = 0;
		for (PisteExpertise pisteExpertise : model.getPistesExpertise()) {
			// expertise du joueur pour cette energie
			int expertise = p.getExpertise(pisteExpertise.getType());
			// si le joueur a de l'expertise
			if (expertise > 0) {
				// si le joueur atteint un palier bonus et qu'il n'a pas deja pris la recompense
				BonusExpertise bonus = pisteExpertise.getSpecialBonus(expertise);
				if (bonus != null && !model.getCurrentPLayer().getBonusExpertise(pisteExpertise.getType()) ) {
					// declare le bonus comme pris
					model.getCurrentPLayer().setBonusExpertise(pisteExpertise.getType(), true);

					// si bonus a choix faire choisir
					if (bonus.equals(BonusExpertise.CEP) || bonus.equals(BonusExpertise.EXPERTISE)) {
						// choix continent
						if (bonus.equals(BonusExpertise.CEP)) {
							hboxAction.dialogChoisirContinentForExpBonus();
							if (hboxAction.dialogExpertiseBonusCEP != null) {
								Optional<Continent> result = hboxAction.dialogExpertiseBonusCEP.showAndWait();
								result.ifPresent(continent -> {
									// donne le bonus en fonction du continent choisi
									model.giveExpertiseBonus(p, bonus, continent);
									reloadCEPRessTech();
									return;
								});
							}
						}
						// choix expertise
						if (bonus.equals(BonusExpertise.EXPERTISE)) {
							hboxAction.dialogChoisirExpertiseForExpBonus();
							if (hboxAction.dialogExpertiseBonusExpertise != null) {
								Optional<greenEnergyTypes> result = hboxAction.dialogExpertiseBonusExpertise.showAndWait();
								result.ifPresent(expertiseChoice -> {
									// donne le bonus en fonction de l'expertise choisi
									model.giveExpertiseBonus(p, bonus, expertiseChoice);
									return;
								});
							}
						}
					} else {
						// donne le bonus
						model.giveExpertiseBonus(p, bonus, null);
						reloadresourcesTech();
					}
					// message information
					displayAlertWithoutHeaderText("Gain de bonus d'expertise",
							"Vous avez atteint "+expertise+ " d'expertise "+ pisteExpertise.getType() +", vous avez donc gagné "+ bonus.description);
				}

				// placer le cercle au bon endroit
				player1ExpertiseIndicator.add(placePlayerExpertise(expertise, i, p.getColor()));
			}
			i++;
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
		resourcesTechJoueur = new Text(TEXT_X, 95, "Vous avez " + model.getCurrentPLayer().getResourcesTech() + " cubes de ressources technologiques ");
		pane.getChildren().add(resourcesTechJoueur);
	}

	//A appeler lors d'une modification des CEP
	public void reloadCEPRessTech(){
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
		CEPEurope = new Text(continentX[0]+60, continentY[0]-70, "L'Europe à "+model.getContinents()[0].getNbCep()+"/5 CEP \net " + model.getContinents()[0].getNbRessTech() +" Ressource(s) technologique(s)");
		CEPAfrique = new Text(continentX[1]+60,continentY[1]-60, "L'Afrique à "+model.getContinents()[1].getNbCep()+"/3 CEP \net " + model.getContinents()[1].getNbRessTech() +" Ressource(s) technologique(s)");
		CEPAmSud = new Text(continentX[2]+45, continentY[2]-70, "L'Amérique du Sud à "+model.getContinents()[2].getNbCep()+"/4 CEP \net " + model.getContinents()[2].getNbRessTech() +" Ressource(s) technologique(s)");
		CEPAmNord = new Text(continentX[3]+45, continentY[3]-120, "L'Amérique du Nord à "+model.getContinents()[3].getNbCep()+"/5 CEP \net " + model.getContinents()[3].getNbRessTech() +" Ressource(s) technologique(s)");
		CEPOceanie = new Text(continentX[4]+60, continentY[4]-110, "L'Océanie à "+model.getContinents()[4].getNbCep()+"/4 CEP \net " + model.getContinents()[4].getNbRessTech() +" Ressource(s) technologique(s)");
		CEPAsie = new Text(continentX[5]+85, continentY[5]-120, "L'Asie à "+model.getContinents()[5].getNbCep()+"/6 CEP \net " + model.getContinents()[5].getNbRessTech() +" Ressource(s) technologique(s)");
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
		co2 = new Text(TEXT_X, 125, "Valeur du CO2 : " + model.getCo2() + "/500 \n" +
				"(si en 2000 le CO2 est à moins \n" +
				"de 350, la partie \n se termine)");
		pane.getChildren().add(co2);
	}

	public void reloadObjectifCompagnie() {
		pane.getChildren().remove(objectifCompagnie);
		if (model.getCurrentPLayer().getObjectifCompagnie().getId() != -1)
			objectifCompagnie = new Text(1320, 200, "Objectif de votre compagnie:\n"+model.getCurrentPLayer().getObjectifCompagnie().getLibelle());
		else objectifCompagnie = new Text(1320, 200, "Vous n'avez plus d'objectif de compagnie !\n");
		pane.getChildren().add(objectifCompagnie);
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
		int y = 0;

		switch (continentId) {
			case 0 : y = continentY[continentId] - 50;break;
			case 1 : y = continentY[continentId] - 30;break;
            case 2 : y = continentY[continentId] - 50;break;
            case 3 : y = continentY[continentId] - 100;break;
			case 4 : y = continentY[continentId] - 80;break;
            case 5 : y = continentY[continentId] - 100;break;
        }
		return new Circle(continentX[continentId]+75,y, 15, playerColor);
	}

	/**
	 * Initialisation des évènements
	 */
	public void initEvenements(){
		Text txtEvenement = new Text(1510,15,"Évènements");
		evenement = new Rectangle(1495, 20, 90, 140);
		evenement.setFill(Color.WHITE);

		updateEvent();

		pane.getChildren().add(txtEvenement);
		pane.getChildren().add(evenement);
	}

	/**
	 * Charge l'image de l'évenement courant
	 */
	public void updateEvent(){
		evenement.setFill(new ImagePattern(model.getImgCurEvent()));
		Tooltip.install(evenement,new Tooltip("Si le niveau global de pollution de CO2\nest supérieur ou égal à 350 ppm,\nune catastrophe aura lieu en "+ model.getContinents()[model.currentEvent]+"."));
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
		imageViewSommetTiles = new ImageView[6];
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
			if(i!=2) imageViewContinents[i].setFitWidth(CONTINENT_WIDTH);
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
	 * réaffiche un sommet (remplacement) lorsque un sommet est fini
	 */
	public void reloadSommet(SommetTile sommet){
    	int i = model.getIndexContinentSommet(sommet);
    	pane.getChildren().remove(imageViewSommetTiles[i]);
		imageViewSommetTiles[i] = model.getContinents()[i].getSommetTile().getImageSommetTile();
		imageViewSommetTiles[i].setX(sommetX[i]);
		imageViewSommetTiles[i].setY(sommetY[i]);
		imageViewSommetTiles[i].setFitWidth(AGENDA_SOMMET_WIDTH);
		imageViewSommetTiles[i].setPreserveRatio(true);
		pane.getChildren().add(imageViewSommetTiles[i]);

	}

	/**
	 * Initialisation des subventions
	 */
	public void initSubvention(){
		int espace = 0;
		for(int i=0;i<3;i++) {
			for(int j=0;j<model.getContinents().length;j++) {

				model.getContinents()[j].getTabRectangleSubvention()[i].setX(continentX[j]+espace);
				model.getContinents()[j].getTabRectangleSubvention()[i].setY(subventionY[j]);

				switch (i) {
					case 0 : {
						model.getContinents()[j].getTabRectangleSubvention()[i].setFill(new ImagePattern(imgArgent));
						Tooltip.install(model.getContinents()[j].getTabRectangleSubvention()[i],new Tooltip("Proposer un projet : autant de $ que de cep du continent"));
						break;
					}
					case 1 : {
						model.getContinents()[j].getTabRectangleSubvention()[i].setFill(new ImagePattern(imgRessource));
						Tooltip.install(model.getContinents()[j].getTabRectangleSubvention()[i],new Tooltip("Proposer un projet : donne 2 cubes de ressources"));
						break;
					}
					case 2 : {
						model.getContinents()[j].getTabRectangleSubvention()[i].setFill(new ImagePattern(imgRecherche));
						Tooltip.install(model.getContinents()[j].getTabRectangleSubvention()[i],new Tooltip("Proposer un projet : Déplace un scientifique ou +1 scientifique"));
						break;
					}
				}

				pane.getChildren().add(model.getContinents()[j].getTabRectangleSubvention()[i]);
			}
			espace += 80;
		}
	}

	/**
	 * Initialisation des centrales
	 */
	public void initCentral(){
		// boucle sur les continents
		for(int i=0;i<model.getContinents().length;i++) {
			// boucle sur le nombre de CEP pen fonction du continent
			int espace = 0;
			for(int j=0;j<model.getContinents()[i].getNbCep();j++) {
				// affichage des cases de centrales
				model.getContinents()[i].getTabRectangleCentral()[j].setX(centraleX[i]+espace);
				model.getContinents()[i].getTabRectangleCentral()[j].setY(centraleY[i]);
				// ajout au pane
				pane.getChildren().add(model.getContinents()[i].getTabRectangleCentral()[j]);
				espace += 90;
			}

		}
	}

	/**
	 * Propose ou mets en place un projet selon state (cf constante)
	 * @param projectChoice
	 * @param greenEnergyTypes
	 * @param state
	 * @param continent
	 */
	public void changeProjectState(int projectChoice, greenEnergyTypes greenEnergyTypes, int state, Continent continent){
		int argent = 0;
		int ressTech = 0;
		int cep = 0;
		int expRec = 0;
		int expGain = 0;
		int ptVictoire = 0;

		String side ="";
		if(state == PROPOSER_PROJET) {
			side = "Recto";
			// tooltip data
			switch (greenEnergyTypes) {
				case REFORESTATION:
					cep = 2;
					break;
				case SOLAR:
					ressTech = 3;
					break;
				case FUSION:
					ressTech = 1;
					argent =5;
					break;
				case BIOMASS:
					argent = 3;
					ressTech = 1;
					cep = 1;
					break;
				case RECYCLING:
					argent = 5;
					cep = 1;
					break;
			}
			Tooltip.install(continent.getTabRectangleSubvention()[projectChoice], new Tooltip("Mettre en place : \nCoût : \n- 1 CEP\n\nGains : \n- "+ argent + " €\n- "+ ressTech +" ressource(s) technologique(s)\n- "+ cep +" CEP"));
		}
		else if (state == METTRE_EN_PLACE_PROJET) {
			side = "Verso";
			//tooltip data
			switch (greenEnergyTypes) {
				case REFORESTATION:
					argent = centralTypes.REBOISEMENT.getCout()[0];
					ressTech = centralTypes.REBOISEMENT.getCout()[1];
					expRec = centralTypes.REBOISEMENT.getExpertise();
					expGain = 1 ;
					ptVictoire = centralTypes.REBOISEMENT.getPtsVictoire();
					break;
				case SOLAR:
					argent = centralTypes.SOLAIRE.getCout()[0];
					ressTech = centralTypes.SOLAIRE.getCout()[1];
					expRec = centralTypes.SOLAIRE.getExpertise();
					expGain = 1 ;
					ptVictoire = centralTypes.SOLAIRE.getPtsVictoire();
					break;
				case FUSION:
					argent = centralTypes.FUSIONFROIDE.getCout()[0];
					ressTech = centralTypes.FUSIONFROIDE.getCout()[1];
					expRec = centralTypes.FUSIONFROIDE.getExpertise();
					expGain = 1 ;
					ptVictoire = centralTypes.FUSIONFROIDE.getPtsVictoire();
					break;
				case BIOMASS:
					argent = centralTypes.BIOMASSE.getCout()[0];
					ressTech = centralTypes.BIOMASSE.getCout()[1];
					expRec = centralTypes.BIOMASSE.getExpertise();
					expGain = 1 ;
					ptVictoire = centralTypes.BIOMASSE.getPtsVictoire();
					break;
				case RECYCLING:
					argent = centralTypes.RECYCLAGE.getCout()[0];
					ressTech = centralTypes.RECYCLAGE.getCout()[1];
					expRec = centralTypes.RECYCLAGE.getExpertise();
					expGain = 1 ;
					ptVictoire = centralTypes.RECYCLAGE.getPtsVictoire();
					break;
			}
			Tooltip.install(continent.getTabRectangleSubvention()[projectChoice], new Tooltip(" Construire centrale : \nCoût :\n- "+argent+" $ \n- "+ressTech+" ressource(s) technologique(s)\n- min "+expRec+" d\'expertises \n\nGains : \n- "+expGain+" expertise \n- "+ ptVictoire +" points de victoire "));
		}

		Image imgProject = new Image(getClass().getResourceAsStream("images/Projets/"+greenEnergyTypes+"Project"+side+".png"));
		continent.getTabRectangleSubvention()[projectChoice].setFill(new ImagePattern(imgProject));
	}

	/**
	 * Reset d'une subvention
	 * @param continent
	 * @param idSubvention
	 */
	public void resetSubvention(Continent continent, int idSubvention){
		if (idSubvention == 0){
			continent.getTabRectangleSubvention()[idSubvention].setFill(new ImagePattern(imgArgent));
			Tooltip.install(continent.getTabRectangleSubvention()[idSubvention],new Tooltip("Proposer un projet : autant de $ que de cep du continent"));
		}
		if (idSubvention == 1){
			continent.getTabRectangleSubvention()[idSubvention].setFill(new ImagePattern(imgRessource));
			Tooltip.install(continent.getTabRectangleSubvention()[idSubvention],new Tooltip("Proposer un projet : donne 2 cubes de ressources"));
		}
		if (idSubvention == 2){
			continent.getTabRectangleSubvention()[idSubvention].setFill(new ImagePattern(imgRecherche));
			Tooltip.install(continent.getTabRectangleSubvention()[idSubvention],new Tooltip("Proposer un projet : Déplace un scientifique ou +1 scientifique"));
		}
	}

	/**
	 * Permet d'ajouter le scientifique passé en paramètre au panneaux
	 * @param idScientifique
	 */
	public void addScientifiqueToPane(int idScientifique){
		ImageView imageViewScientifique = model.getCurrentPLayer().getScientifiques().get(idScientifique).getImgScientifique();
		imageViewScientifique.setFitWidth(40);
		imageViewScientifique.setPreserveRatio(true);
		Tooltip.install(imageViewScientifique, new Tooltip("Scientifique n°" + (idScientifique+1)));
		deplacerScientifiqueReserve(imageViewScientifique,idScientifique);
		pane.getChildren().add(imageViewScientifique);
	}

	/**
	 * Ajouter un Scientifique sur un projet
	 * @param projectChoice
	 * @param imageViewScientifique
	 * @param continent
	 */
	public void addScientifiqueToProject(int projectChoice, ImageView imageViewScientifique, Continent continent){
		imageViewScientifique.setX(continent.getTabRectangleSubvention()[projectChoice].getX());
		imageViewScientifique.setY(continent.getTabRectangleSubvention()[projectChoice].getY());
		imageViewScientifique.toFront();
	}

	/**
	 * Ajouter un Scientifique sur un sommet
	 * @param imageViewScientifique
	 * @param scientifique
	 * @param sommetTile
	 */
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
			if (indexSubject == 0) {x = -8;y=-8;}
			else if (indexSubject == 1){x = 10;y=-18;}
			else if (indexSubject == 2){x = 28;y=-8;}
		} else if (nbSubject == 4){
			if (indexSubject == 0) {x = -5;y=-20;}
			else if (indexSubject == 1) {x = 25;y=-20;}
			else if (indexSubject == 2) {x = -5;y=-2;}
			else if (indexSubject == 3){x = 25;y=-2;}
		}
		imageViewScientifique.setX(continent.getSommetTile().getImageSommetTile().getX()+x);
		imageViewScientifique.setY(continent.getSommetTile().getImageSommetTile().getY()+y);
		imageViewScientifique.toFront();

		// le scientifique quitte le continent lorsqu'il va sur un sommet
		scientifique.setContinent(null);
	}

	/**
	 * Permet d'ajouter l'image du scientifique passé en paramètre à la réserve
	 * dans son emplacement grâce à l'idScientifique qui est l'index du scientifique dans la liste
	 * des scientifiques du joueurs
	 * @param imageViewScientifique
	 * @param idScientifique
	 */
	public void deplacerScientifiqueReserve(ImageView imageViewScientifique, int idScientifique){
		int y= 0;
		switch (idScientifique){
			case 0 : y = 0;
				break;
			case 1 : y = 50;
				break;
			case 2 : y = 100;
				break;
			case 3 : y = 150;
		}
		System.out.println(idScientifique);
		imageViewScientifique.setX(30);
		imageViewScientifique.setY(180+y);
	}

	/**
	 * Afficher un message
	 * @param title
	 * @param message
	 */
	public void displayAlertWithoutHeaderText(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}

	/**
	 * vérifie si c'est la fin du jeu
	 */
	public void isEndGame() throws IOException {
		// si le nombre de décénnie max est atteinte => renvoie true par model.EndGame
		if(model.endGame()){
			if(model.getCo2() >= 500 && !model.checkCentralVerte()){ //Fin malheureuse
				displayFinMalheureuse();
				AskReplay();
				return;
			}
			//Fin classique
			//Récupération des CEP
			displayRecuperationCEP();
			//Point objectif de compagnie, si pas défaussé
			if (model.getCurrentPLayer().getObjectifCompagnie().getId() != -1) displayPointObjectifCompagnie();
			//Vente des CEP
			displayVenteCEP();
			//Calcul score final
			displayScoreFinal();
			//demande si le joueur veux rejouer ou non
			AskReplay();
			return;
		}
	}

	public void displayFinMalheureuse(){
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setTitle("Quelle horreur !");
		alert.setContentText("Le taux de CO2 dépasse les 500 ppm. Vous devez trouver une nouvelle planète à habiter ! \n Points de victoires: "+model.getCurrentPLayer().getPointVictoire());
		Optional<ButtonType> result = alert.showAndWait();
	}

	public void displayScoreFinal(){
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setTitle("Score final");
		alert.setContentText("Vous marquez un total de "+model.getScoreFinal()+" point(s) pour cette partie.");
		Optional<ButtonType> result = alert.showAndWait();
	}

	public void displayPointObjectifCompagnie(){
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setTitle("Objectif de compagnie");
		alert.setContentText("Vous marquez "+model.getPointsObjectifCompagnie()+" point(s) de votre objectif de compagnie");
		Optional<ButtonType> result = alert.showAndWait();
	}

	public void displayVenteCEP(){
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setTitle("Vente des CEP");
		alert.setContentText("Vous avez un total de "+model.sellAllCEP()+" € (après la vente de tout vos CEP)");
		Optional<ButtonType> result = alert.showAndWait();
	}

	public void displayRecuperationCEP(){
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setTitle("Récupération des CEP");
		alert.setContentText("Vous avez un total de "+model.getAllCEP()+" CEP (continents controllé inclus)");
		Optional<ButtonType> result = alert.showAndWait();
	}

	public void AskReplay() throws IOException {
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
			System.out.println("coucou");
			// création de la nouvelle partie
			Main main = new Main();
			main.start(new Stage());
		}
	}


	/**
	 * Place la centrale sur le continent a la bonne position
	 * @param type Le type de la centrale
	 * @param continent Le continent ou deposer la centrale
	 * @param index l'index pour la position de la centrale sur le continent
	 */
	public void addCentrale(centralTypes type, Continent continent, int index) {
		Image central = imgCentralCharbon;

		switch (type){
			case CHARBON:
				central = imgCentralCharbon;
				Tooltip.install(continent.getTabRectangleCentral()[index], new Tooltip("Centrale au Charbon, Pollution : " + centralTypes.CHARBON.getCo2()));
				break;
			case PETROLE :
				central = imgCentralPetrole;
				Tooltip.install(continent.getTabRectangleCentral()[index], new Tooltip("Centrale au Petrole, Pollution : " + centralTypes.PETROLE.getCo2()));
				break;
			case GAZNATUREL :
				central = imgCentralGaz;
				Tooltip.install(continent.getTabRectangleCentral()[index], new Tooltip("Centrale a Gaz, Pollution : " + centralTypes.GAZNATUREL.getCo2()));
				break;
			case SOLAIRE:
				central = imgCentralSolar;
				Tooltip.install(continent.getTabRectangleCentral()[index], new Tooltip("Centrale Solaire, Polution :" + centralTypes.SOLAIRE.getCo2()));
				break;
			case REBOISEMENT:
				central = imgCentralReforestation;
				Tooltip.install(continent.getTabRectangleCentral()[index], new Tooltip("Centrale de Reboisement, Polution :" + centralTypes.REBOISEMENT.getCo2()));
				break;
			case RECYCLAGE:
				central = imgCentralRecycling;
				Tooltip.install(continent.getTabRectangleCentral()[index], new Tooltip("Centrale de Recyclage, Polution :" + centralTypes.RECYCLAGE.getCo2()));
				break;
			case FUSIONFROIDE:
				central = imgCentralNuclear;
				Tooltip.install(continent.getTabRectangleCentral()[index], new Tooltip("Centrale à fusion froide, Polution :" + centralTypes.FUSIONFROIDE.getCo2()));
				break;
			case BIOMASSE:
				central = imgCentralBiomass;
				Tooltip.install(continent.getTabRectangleCentral()[index], new Tooltip("Centrale à bio-masse, Polution :" + centralTypes.BIOMASSE.getCo2()));
				break;
		}
		continent.getTabRectangleCentral()[index].setFill(new ImagePattern(central));
	}

	/**
	 * Reset d'une centrale
	 */
	public void resetCentrale(Continent continent, int idCentrale){
		continent.getTabRectangleCentral()[idCentrale].setFill(null);
	}
}
