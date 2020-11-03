package CO2;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class ViewGame {

	Model model;
    Pane pane;

	Text nbTilesSolarProject ;
	Text nbTour;

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
		imageViewTilesSolarProject.setX(1460);
		imageViewTilesSolarProject.setY(50);
		imageViewTilesSolarProjectInDeck.setX(1460);
		imageViewTilesSolarProjectInDeck.setY(50);
		imageViewTilesSolarProject.setPreserveRatio(true);
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

		nbTour = new Text(10, 80,"Tour : "+model.tour+"/" + model.NB_TOUR_PAR_DECENNIE);
		pane.getChildren().add(nbTour);


		initContinent();
		initSubvention(250, 100);

		hboxAction = new ViewMenuActionHbox(model) ;
		hboxAction.init();
		pane.getChildren().add(hboxAction);
    }

	public void reloadTour(){
		pane.getChildren().remove(nbTour);
		nbTour = new Text(10, 80,"Tour : "+model.tour+"/" + model.NB_TOUR_PAR_DECENNIE);
		pane.getChildren().add(nbTour);
	}


    public void initContinent(){
		// Tableau des continents
		ImageView[] imageViewContinents = new ImageView[6];
		ImageView[] imageViewAgendaTiles = new ImageView[6];
		for(int i = 0; i<imageViewContinents.length;i++) {
			imageViewContinents[i] = new ImageView(model.getContinents()[i].getImgContinent());
			imageViewAgendaTiles[i] = new ImageView(model.getContinents()[i].getAgendaTile().getImageAgendaTile());
			if(i==0 || i==5) imageViewContinents[i].setX(400);
			if(i==0 || i==2) imageViewContinents[i].setY(200);
			if(i==3 || i==5) imageViewContinents[i].setY(550);
			if(i==1 || i==4) imageViewContinents[i].setX(700);
			if(i==2 || i==3) imageViewContinents[i].setX(1000);

			if(i==0 || i==5) imageViewAgendaTiles[i].setX(400+50);
			if(i==0 || i==2) imageViewAgendaTiles[i].setY(200-100);
			if(i==3 || i==5) imageViewAgendaTiles[i].setY(550-100);
			if(i==1 || i==4) imageViewAgendaTiles[i].setX(700+50);
			if(i==2 || i==3) imageViewAgendaTiles[i].setX(1000+50);
		}
		imageViewContinents[4].setY(700);
		imageViewContinents[1].setY(70);

		imageViewAgendaTiles[4].setY(700-125);
		imageViewAgendaTiles[1].setY(70+125);
		for(int i = 0; i<6;i++) {
			pane.getChildren().add(imageViewContinents[i]);
			pane.getChildren().add(imageViewAgendaTiles[i]);
		}
	}

	public void initSubvention(int val, int k){
		Text[] subventionName = new Text[6];
		for(int i=0;i<3;i++) {
			for(int j=0;j<model.getContinents().length;j++) {
				subventionName[j] = new Text(model.getContinents()[j].getSubventions().get(i).getName());
				subventionName[j].setStyle("-fx-font: 9 arial;");
				if(j==0 || j==5) {model.getContinents()[j].getTabRectangleSubvention()[i].setX(k+val);subventionName[j].setX(k+val+5);}
				if(j==0 || j==2) {model.getContinents()[j].getTabRectangleSubvention()[i].setY(val);subventionName[j].setY(val+40);}
				if(j==1 || j==4) {model.getContinents()[j].getTabRectangleSubvention()[i].setX(k+val+300);subventionName[j].setX(k+val+305);}
				if(j==2 || j==3) {model.getContinents()[j].getTabRectangleSubvention()[i].setX(k+val+600);subventionName[j].setX(k+val+605);}
				if(j==3 || j==5) {model.getContinents()[j].getTabRectangleSubvention()[i].setY(val+350);subventionName[j].setY(val+390);}
				if(j==1) {model.getContinents()[j].getTabRectangleSubvention()[i].setY(val-150);subventionName[1].setY(val-110);}
				if(j==4) {model.getContinents()[4].getTabRectangleSubvention()[i].setY(val+500);subventionName[4].setY(val+540);}
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
