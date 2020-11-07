package CO2;

import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Model {

    final static int STATE_INIT = 1; // Title
	final static int STATE_PLAY = 2; // Game
	final int NB_TOUR_PAR_DECENNIE = 6; // 6 pour jeu solo

	private int nbJoueur;
	private int tour;

	int state;
	int width;
	int height;

	//Tableau contenant les 6 tuiles de projet solaire
	//TilesSolarProject[] tilesSolarProjects;
	ArrayList<TilesSolarProject> tilesSolarProjects;

	// Liste des sommets
	List<SommetTile> allSommetTile;

	// tableau contenant les joueurs
	private Player[] players;
	// joueur courant
	int curPlayerId;
	Player curPlayer;

	private Continent[] continents;

	public Model() {
		state = STATE_INIT;
		width = 1600;
		height = 900;
		nbJoueur = 1;
		tour = 1;

		players = new Player[nbJoueur];
    	curPlayerId = 0;
	}

	/**
	 * Initialisation des attributs
	 */
	public void init() throws IOException {
    	// Initialisation du tableau contenant les 6 tuiles de projet solaire
		tilesSolarProjects = new ArrayList<TilesSolarProject>();
		for(int i = 0; i< 6; i++){
			tilesSolarProjects.add(new TilesSolarProject());
		}
		// Initialisation des joueurs
		initPlayers();
		initTour();
		// Initialisation des sommets
		initSommetTile();
		// Initialisation des continents
		initContinents();
	}

	/**
	 * Initialise les joueurs
	 */
	private void initPlayers() {
		for (int i = 0; i < nbJoueur; i++) {
			players[i] = Player.createPlayer();
		}
	}
	/**
	 * Initialise le nombre de tour
	 */
	private void initTour(){
		if (nbJoueur == 2) setTour(5);
		if (nbJoueur == 3) setTour(4);
		if (nbJoueur == 4) setTour(3);
		if (nbJoueur == 5) setTour(2);
	}

	/**
	 * Initialise les continents
	 */
	private void initContinents(){
		continents = new Continent[6];
		ArrayList<String> nomContinents = new ArrayList<>(Arrays.asList("Europe", "Afrique", "Amérique du Sud", "Amérique du Nord", "Océanie", "Asie"));
		for(int i=0; i<6 ;i++){
			continents[i] = new Continent(nomContinents.get(i), 3, new Image(getClass().getResourceAsStream("images/Continents/" + nomContinents.get(i) +".jpg")));

			// TODO dans un prochain sprint, generer les agendaTiles et en prendre une aleatoire par continent
			AgendaTile agendaTile = new AgendaTile("Reforesting", "Solar", "Fusion", new Image(getClass().getResourceAsStream("images/Agendas/TileAgenda_Reforestation_Solar_Fusion.png")));
			continents[i].setAgendaTile(agendaTile);

			SommetTile sommetTile = allSommetTile.get(i);
			continents[i].setSommetTile(sommetTile);
		}
	}

	/**
	 * Initialise les sommets
	 */
	public void initSommetTile() throws IOException {
		ArrayList<SommetTile> lstAllSommet = new ArrayList<SommetTile>();
		ArrayList<String> lstSubject = new ArrayList<String>();

		File fichier = new File("src/CO2/sommetTile.txt");
		BufferedReader bufferedReader = new BufferedReader(new FileReader(fichier));
		String st;

		while((st = bufferedReader.readLine()) != null) {
			String location = st.split(" ")[0];
			String subject1 = st.split(" ")[1];
			String subject2 = st.split(" ")[2];
			String subject3 = st.split(" ")[3];
			String subject4 = st.split(" ")[4];

			lstSubject.add(subject1);
			lstSubject.add(subject2);
			if(!subject3.equals("none")) lstSubject.add(subject3);
			if(!subject4.equals("none")) lstSubject.add(subject4);

			lstAllSommet.add(new SommetTile(location, lstSubject.size(), lstSubject,new Image(getClass().getResourceAsStream("images/Sommets/"+location+".png"))));
			lstSubject.clear();
		}
		bufferedReader.close();
		this.allSommetTile = lstAllSommet;
	}

	/**
	 * @return le nombre de tuiles "Projet Solaire" restantes dans la pile
	 */
	public int getNbSolarProject(){
    	return tilesSolarProjects.size();
	}

	/**
	 * Ajoute 1 d'expertise au joueur courant pour un type d'energie verte
	 * @param energyType type d'energie a modifier
	 */
    public void incrementExpertise(int energyType) {
		curPlayer = players[curPlayerId];
		curPlayer.addExpertise(energyType);
	}

	/**
	 * permet d'ajouter la tuile sur la case subvention(recherche en collaboration)
	 * @return true si la tuile veut être ajoutée sinon retourne false
	 */
	public boolean addTilesSolarProjectToSubventionCase(Continent continent, int indexSub){
		// si l'energie solaire ne peux pas etre placee sur le continent -> action impossible
		if(!continent.getAgendaTile().isPossiblePlacement("Solar")) return false;

    	// permet d'ajouter la tuile sur la case subvention
		if(tilesSolarProjects.get(0).addOnSubvention() && tilesSolarProjects.get(0).subPossible){
			continent.getSubventions().get(indexSub).addTilesSolarProject(tilesSolarProjects.get(0));
			// TODO : [Yassine] a vérifier apres refactoring tab vers liste
			tilesSolarProjects.get(0).subPossible = false;
			return true;
		}
		return false;
	}

    /**
	 * permet de savoir si le joueur peut déplacer un scientifique, et met à jour la "localisation" du scientifique
	 * @return true si il peut, sinon false
	 */
	public boolean moveScientificOnProject(Continent continent, Subvention subvention){
		// à compléter
		List<Scientifique> scientifiques = this.getCurentPLayer().getScientifiques();
		for (Scientifique sc: scientifiques){
			if(sc.getContinent() == null){
				sc.setContinent(continent);
				sc.setSubvention(subvention);
				return true;
			}
		}
		return false;
	}

	public boolean tilesSolarProjectOnWhichContinent(){
    	// à développer pour savoir quel continent contient les tuiles de projet solaire
		return continents[0].isContainsTilesSolarProject();
	}
	public Player getCurentPLayer() { return players[curPlayerId]; }

	public Continent[] getContinents() { return continents; }

	public int getTour() {
		return tour;
	}

	public void setTour(int tour) {
		this.tour = tour;
	}

	public void TourSuivant() {
		if (tour != NB_TOUR_PAR_DECENNIE) {
			tour++;
		} else {
			//passe à la décennie suivante
		}
	}

	public void setNbJoueur(int nbJoueur) { this.nbJoueur = nbJoueur; }

	public int getNbJoueur() { return players.length; }

	public void startGame() { state = STATE_PLAY; }
}
