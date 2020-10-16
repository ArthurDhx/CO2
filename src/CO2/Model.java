package CO2;

import javafx.scene.image.Image;

public class Model {

    final static int STATE_INIT = 1; // Title
	final static int STATE_PLAY = 2; // Game

	int state;
    int width;
    int height;
	int nbJoueur;

	//Tableau contenant les 6 tuiles de projet solaire
	TilesSolarProject[] tilesSolarProjects;

	// tableau contenant les joueurs
	private Player[] players;
	// joueur courant
	int curPlayerId;
	Player curPlayer;

	private Continent[] continents;

	public Model() {
		state = STATE_INIT;
		width = 1000;
		height = 600;
		nbJoueur = 1;

		players = new Player[nbJoueur];
    	curPlayerId = 0;
	}

	/**
	 * Initialisation des attributs
	 */
	public void init(){
    	// Initialisation du tableau contenant les 6 tuiles de projet solaire
		tilesSolarProjects = new TilesSolarProject[6];
		for(int i = 0; i<tilesSolarProjects.length; i++){
			tilesSolarProjects[i] = new TilesSolarProject();
		}

		// Initialisation des joueurs
		initPlayers();
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
	 * Initialise les continents
	 */
	private void initContinents(){
		continents = new Continent[6];
		continents[0] = new Continent("Europe", 3, new Image(getClass().getResourceAsStream("images/Europe.jpg")));
		// Initialisation autres continents plus tard
	}

	/**
	 * @return le nombre de tuiles "Projet Solaire" restantes dans la pile
	 */
	public int getNbSolarProject(){
    	int res = 0;
    	for(int i = 0; i<tilesSolarProjects.length; i++){
    		if(tilesSolarProjects[i] != null){
    			res += 1;
			}
		}
    	return res;
	}

    public void startGame() { state = STATE_PLAY; }

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
	public boolean addTilesSolarProjectToSubventionCase(){
    	// permet d'ajouter la tuile sur la case subvention
		if(tilesSolarProjects[0].addOnSubvention()){
			continents[0].getSubventions()[2].hasTilesSolarProject(tilesSolarProjects[0]);
			return true;
		}
		return false;
	}

	public boolean tilesSolarProjectOnWhichContinent(){
    	// à développer pour savoir quel continent contient les tuiles de projet solaire
		return continents[0].isContainsTilesSolarProject();
	}
	public Player getCurentPLayer() { return players[curPlayerId]; }

	public Continent[] getContinents() { return continents; }
}
