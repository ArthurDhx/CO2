package CO2;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

enum GreenEnergyTypes {SOLAR, BIOMASS, RECYCLING, FUSION, REFORESTATION}

public class Model {

    final static int STATE_INIT = 1; // Title
	final static int STATE_PLAY = 2; // Game
	final int NB_TOUR_PAR_DECENNIE = 7; // 6 pour jeu solo + 1 pour pouvoir changer décénnie
	final int NB_DECENNIE = 2010; // 2010 pour jeu solo, 2020 pour jeu multi

	private int nbJoueur;
	private int tour;

	private int nbDecade;
	private int decade;

	int state;
	int width;
	int height;

	//prix courant des CEPs
	int currentPriceCEP;
	//nombre de CEP disponible au marché
	int nbCEPdispo;

	//Tableau contenant les 6 tuiles de projet solaire
	//TilesSolarProject[] tilesSolarProjects;
	ArrayList<TilesSolarProject> tilesSolarProjects;

	// Liste des sommets
	List<SommetTile> allSommetTile;

	public List<SommetTile> getAllSommetTile() {
		return allSommetTile;
	}

	// Liste des expertises
	List<Expertise> expertises;

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
		nbDecade = 1;
		decade = 1970;
		players = new Player[nbJoueur];
    	curPlayerId = 0;
    	//On initialise le prix des CEPs à 3
    	currentPriceCEP = 3;
    	//On place 2 CEP dans le marché
		nbCEPdispo = 2;
	}

	/**
	 * Initialisation des attributs
	 */
	public void init() throws IOException {
    	// Initialisation du tableau contenant les 6 tuiles de projet solaire
		//TODO : Rabaisser a 6 une fois toutes les tuiles projet mis en place car sinon il n'y a pas assez de tuiles pour la demo
		tilesSolarProjects = new ArrayList<TilesSolarProject>();
		for(int i = 0; i< 7; i++){
			tilesSolarProjects.add(new TilesSolarProject());
		}
		// Initialisation des joueurs
		initPlayers();
		initTour();
		//initialisation des décénnies
		initDecade();
		// Initialisation des sommets
		initSommetTile();
		// Initialisation des continents
		initContinents();
		// Initialisation les barres d'expertise
		initExpertise();
	}

	/**
	 * Initialise les barres d'expertise
	 */
	private void initExpertise() {
		expertises = new ArrayList<>();
		expertises.add(new Expertise(GreenEnergyTypes.SOLAR, 6, Color.GOLD));
		expertises.add(new Expertise(GreenEnergyTypes.BIOMASS, 7, Color.BURLYWOOD));
		expertises.add(new Expertise(GreenEnergyTypes.RECYCLING, 7, Color.DEEPSKYBLUE));
		expertises.add(new Expertise(GreenEnergyTypes.FUSION, 6, Color.DARKSLATEGRAY));
		expertises.add(new Expertise(GreenEnergyTypes.REFORESTATION, 5, Color.SEAGREEN));
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
	 * Initialise le nombre de décennie
	 */
	private void initDecade(){
		if (nbDecade == 2) setDecade(1980);
		if (nbDecade == 3) setDecade(1990);
		if (nbDecade == 4) setDecade(2000);
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
		ArrayList<GreenEnergyTypes> lstSubject = new ArrayList<GreenEnergyTypes>();

		File fichier = new File("src/CO2/sommetTile.txt");
		BufferedReader bufferedReader = new BufferedReader(new FileReader(fichier));
		String st;


		while((st = bufferedReader.readLine()) != null) {
			String location = st.split(" ")[0];
			String subject1 = st.split(" ")[1];
			String subject2 = st.split(" ")[2];
			String subject3 = st.split(" ")[3];
			String subject4 = st.split(" ")[4];

			// liste de sujets avec comme sujet Soleil et Fusion
			ArrayList<Subject> subjects= new ArrayList<Subject>(Arrays.asList(new Subject(GreenEnergyTypes.SOLAR),new Subject(GreenEnergyTypes.FUSION)));

			lstSubject.add(GreenEnergyTypes.SOLAR);
			lstSubject.add(GreenEnergyTypes.FUSION);
			if(!subject3.equals("none")) lstSubject.add(GreenEnergyTypes.BIOMASS);
			if(!subject4.equals("none")) lstSubject.add(GreenEnergyTypes.RECYCLING);

			lstAllSommet.add(new SommetTile(location, lstSubject.size(), subjects,new Image(getClass().getResourceAsStream("images/Sommets/"+location+".png"))));
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
		List<Scientifique> scientifiques = this.getCurrentPLayer().getScientifiques();
		for (Scientifique sc: scientifiques){
			if(sc.getContinent() == null){
				sc.setContinent(continent);
				sc.setSubvention(subvention);
				return true;
			}
		}
		return false;
	}

	/**
	 * permet de savoir si le joueur peut déplacer un scientifique d'un projet à un sommet
	 * @return true si il peut, sinon false
	 */
	public boolean moveScientificOnSommet(Subvention subvention){
		List<Scientifique> scientifiques = this.getCurrentPLayer().getScientifiques();
		Scientifique scientifique = new Scientifique();

		for (Scientifique sc : scientifiques) {
			if (sc.getSubvention()!= null && sc.getSubvention().equals(subvention)) scientifique = sc;
		}
		SommetTile sommetTile = scientifique.getContinent().getSommetTile();

		// A terme vérifié si le type du projet = nécessite  héritage tuile => solaire
		if (sommetTile.haveEnergy(GreenEnergyTypes.SOLAR)){
			for (Scientifique sc : scientifiques) {
				if (sc.getSubvention().equals(subvention)) {
					sommetTile.addScientifiqueToEnergy(sc,GreenEnergyTypes.SOLAR);
				}
				return true;
			}
		}
		return false;
	}


	/**
	 * Permet de savoir si un joueur peut mettre en place un projet
	 * @return
	 */
	public boolean mettreEnPlaceProjet(Continent continent, Subvention subvention){
		curPlayer = getCurrentPLayer();
		System.out.println();
		if(curPlayer.getCEP() >= 1){
			curPlayer.mettreEnPlaceProjet(GreenEnergyTypes.SOLAR);
			return true;
		}
		return false;
	}

	/**
	 * vérifier le nombre de tour pour augmenter ou non la décénnie
	 */
	public void TourSuivant() {
		// vérifie si le nombre de tour par décénnie est atteinte
		if (tour != NB_TOUR_PAR_DECENNIE-1) {
			// incrémentation de la décénnie
			tour++;
		} else {
			// réinitialise le tour à 1
			tour = 1;
			// passe à la décennie suivante
			decade+=10;
		}
	}

	/**
	 * vérifie le nombre de décénnie
	 * @return true si le nombre de décénnie max est atteinte, sinon, false
	 */
	public boolean endGame() {
		// si le nombre de décénnie a atteint son maximum
		if (decade == NB_DECENNIE) {
			// alors c'est la fin du jeu
			System.out.println("FIN DU JEU");
			// return true
			Model model = new Model();
			return true;
		}
		return false;
	}

	public boolean tilesSolarProjectOnWhichContinent(){
    	// à développer pour savoir quel continent contient les tuiles de projet solaire
		return continents[0].isContainsTilesSolarProject();
	}
	public Player getCurrentPLayer() { return players[curPlayerId]; }

	public Continent[] getContinents() { return continents; }

	public int getTour() {
		return tour;
	}

	public void setTour(int tour) {
		this.tour = tour;
	}

	public void setNbJoueur(int nbJoueur) { this.nbJoueur = nbJoueur; }

	public int getNbJoueur() { return players.length; }

	public int getNbDecade() { return nbDecade; }

	public void setNbDecade(int nbDecade) { this.nbDecade = nbDecade; }

	public int getDecade() { return decade; }

	public void setDecade(int decade) { this.decade = decade; }

	public void startGame() { state = STATE_PLAY; }

	public void achatCEP(){
		this.nbCEPdispo -= 1;
		if(nbCEPdispo == 0){
			nbCEPdispo += 2;
			if(currentPriceCEP < 8) currentPriceCEP += 1;
		}
	}

	public void venteCEP(){
		this.nbCEPdispo += 1;
		if(currentPriceCEP > 1) currentPriceCEP -= 1;
	}

	public void giveRewardsSommet() {
		for(SommetTile sommet: this.getAllSommetTile()){
			if(sommet.isFull()){
				ArrayList<Scientifique> scientifiques =  sommet.getScientifiques();
				for(Player p: players){
					for(Scientifique sPlayer: p.getScientifiques()){
						if(scientifiques.contains(sPlayer)){
							giveRewardsSommetToPlayer(sPlayer.getSubject().getEnergy(), p);
						}
					}
				}
			}
		}
	}

	/**
	 * @param energy Energy verte ou était le scientifique
	 * @param p Le joueur a donnné les bonus
	 */
	private void giveRewardsSommetToPlayer(GreenEnergyTypes energy,Player p) {
		switch (energy){
			case SOLAR:
				p.addExpertise(Player.expertiseId.get("Solar"));
				break;
			/*
			case FUSION ->
					break;
			case BIOMASS ->
					break;
			case RECYCLING ->
					break;
			case REFORESTATION ->
					break;
			 */

		}
	}
}
