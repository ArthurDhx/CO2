package CO2;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Model {

    final static int STATE_INIT = 1; // Title
	final static int STATE_PLAY = 2; // Game
	final int NB_TOUR_PAR_DECENNIE = 5;

	int state;
    int width;
    int height;
	int nbJoueur;
	int tour;

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
		ArrayList<String> nomContinents = new ArrayList<>(Arrays.asList("Europe", "Afrique", "Amérique du Sud", "Amérique du Nord", "Océanie", "Asie"));
		for(int i=0; i<6 ;i++){
			continents[i] = new Continent(nomContinents.get(i), 3, new Image(getClass().getResourceAsStream("images/Continents/" + nomContinents.get(i) +".jpg")));

			// TODO dans un prochain sprint, generer les agendaTiles et en prendre une aleatoire par continent
			AgendaTile agendaTile = new AgendaTile("Reforesting", "Solar", "Fusion", new Image(getClass().getResourceAsStream("images/TileAgenda_Reforestation_Solar_Fusion.png")));
			continents[i].setAgendaTile(agendaTile);
		}
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
		// si l'energie solaire ne peux pas etre placee sur le continent -> action impossible
		if(!continents[0].getAgendaTile().isPossiblePlacement("Solar")) return false;

    	// permet d'ajouter la tuile sur la case subvention
		if(tilesSolarProjects[0].addOnSubvention() && tilesSolarProjects[0].subPossible){
			continents[0].getSubventions().get(2).hasTilesSolarProject(tilesSolarProjects[0]);
			// TODO : [Yassine] a vérifier apres refactoring tab vers liste
			tilesSolarProjects[0].subPossible = false;
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

	public void TourSuivant() {
		if (tour != NB_TOUR_PAR_DECENNIE) {
			tour++;
		} else {
			//passe à la décennie suivante
		}
	}
}
