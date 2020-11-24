package CO2;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
		for(int i = 0; i< 30; i++){// 30 pour l'instant (représente tous les projet)
			tilesSolarProjects.add(new TilesSolarProject());
		}
		// Initialisation des joueurs
		initPlayers();
		initTour();
		// Initialisation des décénnies
		initDecade();
		// Initialisation des continents
		initContinents();
		// Initialisation des sommets
		initSommetTile();
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
			players[i] = new Player();
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
		int nbCep = 0;
		for(int i=0; i<6 ;i++){
			// Initialisation du nombre de CEP en fonction du continent
			if(nomContinents.get(i).equals("Europe")) nbCep = 5;if(nomContinents.get(i).equals("Afrique")) nbCep = 3;
			if(nomContinents.get(i).equals("Amérique du Sud")) nbCep = 4;if(nomContinents.get(i).equals("Amérique du Nord")) nbCep = 5;
			if(nomContinents.get(i).equals("Océanie")) nbCep = 4;if(nomContinents.get(i).equals("Asie")) nbCep = 6;
			continents[i] = new Continent(nomContinents.get(i), nbCep, new Image(getClass().getResourceAsStream("images/Continents/" + nomContinents.get(i) +".jpg")));
			// TODO dans un prochain sprint, generer les agendaTiles et en prendre une aleatoire par continent
			AgendaTile agendaTile = new AgendaTile("Reforesting", "Solar", "Fusion", new Image(getClass().getResourceAsStream("images/Agendas/TileAgenda_Reforestation_Solar_Fusion.png")));
			continents[i].setAgendaTile(agendaTile);

			/*SommetTile sommetTile = allSommetTile.get(i);
			continents[i].setSommetTile(sommetTile);*/
		}
	}

	/**
	 * Initialise les sommets
	 */
	public void initSommetTile() throws IOException {
		ArrayList<SommetTile> lstAllSommet = new ArrayList<SommetTile>();
		ArrayList<Subject> subjects= new ArrayList<Subject>();

		File fichier = new File("src/CO2/sommetTile.txt");
		BufferedReader bufferedReader = new BufferedReader(new FileReader(fichier));
		String st;

		int continentNb = 0;
		while((st = bufferedReader.readLine()) != null) {
			String location = st.split(" ")[0];
			String subject1 = st.split(" ")[1];
			String subject2 = st.split(" ")[2];
			String subject3 = st.split(" ")[3];
			String subject4 = st.split(" ")[4];

			subjects.add(stringToSubject(subject1));
			subjects.add(stringToSubject(subject2));
			if(!subject3.equals("none")) subjects.add(stringToSubject(subject3));
			if(!subject4.equals("none")) subjects.add(stringToSubject(subject4));

			lstAllSommet.add(new SommetTile(location,this.continents[continentNb], subjects.size(), subjects,new Image(getClass().getResourceAsStream("images/Sommets/"+location+".png"))));
			subjects.clear();
			if (continentNb < 5){
				continentNb++;
			} else {
				continentNb = 0;
			}
		}
		bufferedReader.close();
		this.allSommetTile = lstAllSommet;
		for(int i=0; i<6 ;i++){
			SommetTile sommetTile = allSommetTile.get(i);
			continents[i].setSommetTile(sommetTile);
		}
	}

	public Subject stringToSubject(String subject){
		Subject subjectEnergy = new Subject();
		if (subject.equals("Solar")) subjectEnergy.setEnergy(GreenEnergyTypes.SOLAR);
		if (subject.equals("Fusion")) subjectEnergy.setEnergy(GreenEnergyTypes.FUSION);
		if (subject.equals("Reforestation")) subjectEnergy.setEnergy(GreenEnergyTypes.REFORESTATION);
		if (subject.equals("Biomass")) subjectEnergy.setEnergy(GreenEnergyTypes.BIOMASS);
		if (subject.equals("Recycling")) subjectEnergy.setEnergy(GreenEnergyTypes.RECYCLING);
		return subjectEnergy;
	}

	/**
	 * @return le nombre de tuiles "Projet Solaire" restantes dans la pile
	 */
	public int getNbSolarProject(){
		return tilesSolarProjects.size();
	}

	/**
	 * Ajoute 1 d'expertise au joueur courant pour un type d'energie verte
	 * @param energyType type d'energie concernee
	 */
	public void incrementExpertise(GreenEnergyTypes energyType) {
		curPlayer = players[curPlayerId];
		curPlayer.addExpertise(energyType, 1);
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
		Scientifique sc= this.getCurrentPLayer().getCurrentScientifique();
		// si la subvention n'est pas occupé
		if(!subvention.isStaffed()){
			sc.setContinent(continent);
			sc.setSubvention(subvention);
			return true;
		}
		return false;
	}

	/**
	 * permet de savoir si le joueur peut déplacer un scientifique d'un projet à un sommet
	 * @return true si il peut, sinon false
	 */
	public boolean moveScientificOnSommet(Subvention subvention, SommetTile sommetTile){
		Scientifique scientifique = this.getCurrentPLayer().getCurrentScientifique();
		if (scientifique.getContinent() == null){
			return false;
		} else {
			// À faire pour toutes les énergies
			// vérifie si le sommet ainsi que la subvention on tous deux l'énergie solaire.
			if (sommetTile.haveEnergy(GreenEnergyTypes.SOLAR) && subvention.getTilesSolarProject() != null){
				return true;
			}
			return false;
		}
	}

	/**
	 * Permet de savoir si un joueur peut mettre en place un projet ET LE MET EN PLACE
	 * @return
	 */
	public boolean mettreEnPlaceProjet(Continent continent, Subvention subvention){
		curPlayer = getCurrentPLayer();
		System.out.println();
		if(curPlayer.getCEP() >= 1){
			curPlayer.rewardSetupProject(GreenEnergyTypes.SOLAR);
			subvention.getTilesSolarProject().setMisEnPlace(true);
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

	/**
	 * donne les récompenses de tous les scientifiques
	 * si ils sont complet
	 */
	public void giveRewardsSommet() {
		// boucle sur tous les sommets du jeu
		for(SommetTile sommet: this.getAllSommetTile()){
			if(sommet.isFull()){ // si le sommet est rempli de scientifique
				ArrayList<Scientifique> scientifiques =  sommet.getScientifiques(); // récupère les scientifiques d'un sommet
				for(Player p: players){
					for(Scientifique sPlayer: p.getScientifiques()){ // récupère tous les scientifiques d'un joueur
						if(scientifiques.contains(sPlayer)){ // si le sommet contient le scientifique d'un joueur
							// on donne le bonus du joueur en fonction du sujet étudié par le scientifique
							giveRewardsSommetToPlayer(sPlayer.getSubject().getEnergy(), p);
							sPlayer.setSubject(null); // le scientifique n'a plus de sujet
							// TODO redéplacer le scientifique au joueur graphiquement
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
				p.addExpertise(GreenEnergyTypes.SOLAR, 1);
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

	/**
	 * @param projetMisEnPlaceChoisi La case de subvention ou le projet choisis se situe
	 * @return -1 Si la centrale n'est pas créable
	 * 			l'index ou la centrale a été posé si cela est possible
	 */
    public int putCentral(Subvention projetMisEnPlaceChoisi) {
    	//TODO : Suis qui enleve le projet ne pas oublier de le faire aussi dans le modele
		// Les conditions pour payer ect
		ArrayList<Central> centrales = projetMisEnPlaceChoisi.getContinent().getCentrales();
		for (int i = 0; i < centrales.size(); i++) {
			// Si un espace est libre
			if (!centrales.get(i).isOccupe()) {
				// Alors on l'occupe
				curPlayer.setActionPrincipaleDone(true);
				centrales.get(i).setOccupe(true);
				// Affecation type
				centrales.get(i).setType(projetMisEnPlaceChoisi.getTilesSolarProject().getTypeToCentral());
				return centrales.get(i).getIndex();
			}
		}
		return -1 ;
    }
}