package CO2;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static CO2.greenEnergyTypes.*;

public class Model {

	final static int STATE_INIT = 1; // Title
	final static int STATE_PLAY = 2; // Game
	final int NB_TOUR_PAR_DECENNIE = 7; // 6 pour jeu solo + 1 pour pouvoir changer décénnie
	final int NB_DECENNIE = 2010; // 2010 pour jeu solo, 2020 pour jeu multi

	// tour courant
	private int tour;
	// nombre de decenies
	private int nbDecade;
	// decenie courante
	private int decade;

	//prix courant des CEPs
	int currentPriceCEP;
	//nombre de CEP disponible au marché
	int nbCEPdispo;
	// Valeur du CO2 actuelle
	private int co2;

	// List des tuiles de projet
	ArrayList<Project> projects;

	// Liste des sommets
	List<SommetTile> allSommetTile;

	// Liste des pistes d'expertises
	List<PisteExpertise> pistesExpertise;

	// Liste des cartes Lobby
	List<LobbyCard> lobbyCards;

	List<OnuCard> onuCards;
	List<OnuCard> onuCardsInGame;
	OnuCard markedCard;

	// nombre de joueurs
	private int nbJoueur;
	// tableau contenant les joueurs
	private final Player[] players;
	// joueur courant
	int curPlayerId;
	Player curPlayer;

	private Continent[] continents;

	int state;
	int width;
	int height;

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
		projects = new ArrayList<>();
		for(int i = 0; i< 30; i++){// 30 pour l'instant (représente tous les projet)
			projects.add(new Project(SOLAR));
		}
		// Initialisation des joueurs
		initPlayers();
		// Initialisation des tours
		initTour();
		// Initialisation des décénnies
		initDecade();
		// Initialisation des continents
		initContinents();
		// Initialisation des sommets
		initSommetTile();
		// Initialisation les barres d'expertise
		initExpertise();
		// Initialisation les cartes Lobby
		initLobbyCards();
		// Initialisation les cartes Objectifs de l'ONU
		initOnuCards();
	}

	/**
	 * Initialise les cartes objectif de l'ONU
	 */
	private void initOnuCards() {
		onuCards = new ArrayList<>();
		int id = 34;
		for(int i=0; i<13;i++){ // 12 cartes (comme le jeu)
			// TODO : changer 13 en fonction du nombre de cartes qu'on a
			onuCards.add(new OnuCard(id, 0)); // création carte ONU et ajout dans la liste totale
			// ajout du nombre de points de victoires en fonction du nombre de type centrales sur la carte
			if(onuCards.get(i).getTypesCentral().size() == 2){ // si 2 types
				int nbPoints = 6 - new Random().nextInt(3); // 4, 5 ou 6
				onuCards.get(i).setNbPointDeVictoire(nbPoints);
			}
			if(onuCards.get(i).getTypesCentral().size() == 3){ // si 3 types
				int nbPoints = 7 - new Random().nextInt(3); // 5, 6 ou 7
				onuCards.get(i).setNbPointDeVictoire(nbPoints);
			}
			if(onuCards.get(i).getTypesCentral().size() == 4){ // si 4 types
				int nbPoints = 8 - new Random().nextInt(3); // 6, 7 ou 8
				onuCards.get(i).setNbPointDeVictoire(nbPoints);
			}
			id++;// id de 34 à 46
		}
	}

	/**
	 * Initialise les cartes Lobby
	 * et en donne 5 au joueur
	 */
	private void initLobbyCards() {
		lobbyCards = new ArrayList<>();

		// cartes proposer un projet sur un continent
		for (Continent c : continents)
			lobbyCards.add(new LobbyCard<>(lobbyActionTypes.PROPOSER, c));

		// cartes proposer un projet sur une subvention
		for (subventionTypes sub : subventionTypes.values())
			lobbyCards.add(new LobbyCard<>(lobbyActionTypes.PROPOSER, sub));

		// cartes mettre en place un type de projet
		for (greenEnergyTypes type : greenEnergyTypes.values())
			lobbyCards.add(new LobbyCard<>(lobbyActionTypes.METTRE, type));

		// cartes construire une centrale
		for (centralTypes centralType : centralTypes.values())
			lobbyCards.add(new LobbyCard<>(lobbyActionTypes.CONSTRUIRE, centralType.name()));

		// cartes sommet d'un type d'energie
		for (greenEnergyTypes type : greenEnergyTypes.values())
			lobbyCards.add(new LobbyCard<>(lobbyActionTypes.SOMMET, type));

		// donner 5 cartes parmi ces cartes au joueur
		getCurrentPLayer().giveLobbyCards(lobbyCards, 5);
	}

	/**
	 * Initialise les barres d'expertise
	 */
	private void initExpertise() {
		pistesExpertise = new ArrayList<>();
		pistesExpertise.add(new PisteExpertise(SOLAR, 6, Color.GOLD));
		pistesExpertise.add(new PisteExpertise(BIOMASS, 7, Color.BURLYWOOD));
		pistesExpertise.add(new PisteExpertise(RECYCLING, 7, Color.DEEPSKYBLUE));
		pistesExpertise.add(new PisteExpertise(FUSION, 6, Color.DARKSLATEGRAY));
		pistesExpertise.add(new PisteExpertise(REFORESTATION, 5, Color.SEAGREEN));
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
			continents[i] = new Continent(nomContinents.get(i), nbCep, new Image(getClass().getResourceAsStream("images/Continents/" + nomContinents.get(i) +".jpg")),i);
			// TODO dans un prochain sprint, generer les agendaTiles et en prendre une aleatoire par continent
			AgendaTile agendaTile = new AgendaTile(REFORESTATION, SOLAR, FUSION, new Image(getClass().getResourceAsStream("images/Agendas/TileAgenda_Reforestation_Solar_Fusion.png")));
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

			lstAllSommet.add(new SommetTile(location,this.continents[continentNb], subjects.size(), subjects,new ImageView(new Image(getClass().getResourceAsStream("images/Sommets/"+location+".png")))));
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
		if (subject.equals("Solar")) subjectEnergy.setEnergy(SOLAR);
		if (subject.equals("Fusion")) subjectEnergy.setEnergy(FUSION);
		if (subject.equals("Reforestation")) subjectEnergy.setEnergy(REFORESTATION);
		if (subject.equals("Biomass")) subjectEnergy.setEnergy(BIOMASS);
		if (subject.equals("Recycling")) subjectEnergy.setEnergy(RECYCLING);
		return subjectEnergy;
	}

	/**
	 * @return le nombre de tuiles "Projet Solaire" restantes dans la pile
	 */
	public int getNbSolarProject(){
		return projects.size();
	}

	/**
	 * Ajoute 1 d'expertise au joueur courant pour un type d'energie verte
	 * @param energyType type d'energie concernee
	 */
	public void incrementExpertise(greenEnergyTypes energyType) {
		curPlayer = players[curPlayerId];
		curPlayer.addExpertise(energyType, 1);
	}

	/**
	 * permet d'ajouter la tuile sur la case subvention(recherche en collaboration)
	 * @return true si la tuile veut être ajoutée sinon retourne false
	 */
	public boolean addProjectTileToSubvention(Continent continent, int indexSub){
		// si l'energie ne peux pas etre placee sur le continent -> action impossible
		if(!continent.getAgendaTile().isPossiblePlacement(SOLAR)) return false;

		// permet d'ajouter la tuile sur la case subvention
		if(projects.get(0).addOnSubvention() && projects.get(0).isSubventionPossible()){
			continent.getSubventions().get(indexSub).addTilesSolarProject(projects.get(0));
			projects.get(0).setSubventionPossible(false);
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
			return sommetTile.haveEnergy(SOLAR) && subvention.getProject() != null;
		}
	}

	/**
	 * Permet de savoir si un joueur peut mettre en place un projet ET LE MET EN PLACE
	 * @return
	 */
	public boolean mettreEnPlaceProjetByPlayer(Continent continent, Subvention subvention){
		curPlayer = getCurrentPLayer();
		if(curPlayer.getCEP() >= 1){
			curPlayer.rewardSetupProject(SOLAR);
			curPlayer.removeCEP();
			subvention.getProject().setMisEnPlace(true);
			return true;
		}
		return false;
	}

	public boolean mettreEnPlaceProjetByContinent(Continent continent, Subvention subvention, Continent ProjectBuyContinent){
		curPlayer = getCurrentPLayer();
		if(ProjectBuyContinent.getNbCep() >= 1){
			curPlayer.rewardSetupProject(SOLAR);
			ProjectBuyContinent.removeCEP();
			subvention.getProject().setMisEnPlace(true);
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
							sPlayer.setSommetTile(null); // le scientifique n'est plus sur le sommet
							// si multi : redonner chaque scientifique à chaque joueur
							p.setScientifiques(sommet.getScientifiques()); // redonne les scientifiques sur le sommet au joueur
							sommet.setContinent(null); // supression du sommet sur le continent
							sommet.getContinent().setSommetTile(new SommetTile()); // création d'un nouveau sommet
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
	private void giveRewardsSommetToPlayer(greenEnergyTypes energy, Player p) {
		switch (energy){
			case SOLAR:
				p.addExpertise(SOLAR, 1);
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
	 * @return - <0 Si la centrale n'est pas créable
	 * 		   - l'index ou la centrale a été posé si cela est possible
	 */
    public int putCentral(Subvention projetMisEnPlaceChoisi) {
    	//TODO : Suis qui enleve le projet ne pas oublier de le faire aussi dans le modele
		// Si le joueur n'as pas assez d'expertise, d'argent ou de ressources technologiques
		if (!getCurrentPLayer().canConstruct(projetMisEnPlaceChoisi.getProject())) return -3;
		// Si pas des scientifique sur projetMisEnplaceChoisi
		if(projetMisEnPlaceChoisi.isStaffed()) return -2;
		ArrayList<Central> centrales = projetMisEnPlaceChoisi.getContinent().getCentrales();
		for (int i = 0; i < centrales.size(); i++) {
			// Si un espace est libre ou qu'il n'est pas libre et que c'est une centrale fossile
			if (!centrales.get(i).isOccupe() || (centrales.get(i).isOccupe() && centrales.get(i).isFossile() && projetMisEnPlaceChoisi.getContinent().allPlantsAreOccupied())) {
				// Alors on l'occupe
				getCurrentPLayer().setActionPrincipaleDone(true);
				centrales.get(i).setOccupe(true);
				// comme seul joueur, il prend le controlle du continent quand il met en place
				giveControl(projetMisEnPlaceChoisi.getContinent());
				// Affecation type
				centrales.get(i).setType(projetMisEnPlaceChoisi.getProject().getCentralType());
				// le joueur paye la centrale
				getCurrentPLayer().payCentral(projetMisEnPlaceChoisi.getProject().getCentralType().getCout());

				if (centrales.get(i).isOccupe() && centrales.get(i).isFossile() && projetMisEnPlaceChoisi.getContinent().allPlantsAreOccupied()) {
					// récompenses du remplacement
					// le joueur prends 1 CEP du marché et le place sur la la région si la limite régionale de CEP est respecté
					projetMisEnPlaceChoisi.getContinent().addCEP(1);
					//réduisez le niveau de CO2 globale en enlevant celui de la centrale fossile
					this.co2 -= centrales.get(i).getType().getCo2();
				}
				return centrales.get(i).getIndex();
			}
		}
		return -1 ;
    }

	/**
	 * Donne le controlle d'un continent au joueur courrant
	 * @param continent continent controller
	 */
	public void giveControl(Continent continent) {
    	// update dans player
		getCurrentPLayer().takeControl(continent);
		// update dans continent
		continent.setControlPlayer(getCurrentPLayer());
	}

	/**
	 * Extrait les valeurs de la chaine de characteres choisi par le joueur
	 * et les passes a une methode du joueur qui les ajoute a ces proprietes
	 * @param p le joueur concerne
	 * @param repartition la reponse choisie par le joueur
	 */
	public void giveRevenu(Player p, String repartition) {
		int[] nombres = new int[2];
		// extraire les chiffres de la chaine de characteres
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(repartition);
		for (int i = 0; i<nombres.length; i++) {
			matcher.find();
			System.out.println(matcher.group());
			nombres[i] = Integer.parseInt(matcher.group());
		}
		// donne les valeurs trouvees au joueur
		p.giveRevenu(nombres);
	}

	/**
	 * Selection de 10 cartes parmis toutes les cartes au début du jeu
	 * les autres ne seront pas utilisé pour le jeu
	 * @return List<OnuCard> retourne la liste des cartes sélectionnées
	 */
	public List<OnuCard> initOnuCardsInGame(){
		onuCardsInGame = new ArrayList<>(); // liste de carte qui seront selectionnée ppour la partie
		OnuCard card;
		// TODO : prochain sprint : remettre à jour
		//for (int i = 0;i<10;i++) { // 10 cartes choisi (7 pour 2 joueurs)
		for (int i = 0;i<2;i++) { // 10 cartes choisi (7 pour 2 joueurs)
			// selection d'une carte aléatoirement parmis la liste totale des cartes de l'ONU
			//do card = onuCards.get(new Random().nextInt(onuCards.size()));
			//while(onuCardsInGame.contains(card));
			card = onuCards.get(new Random().nextInt(onuCards.size()));
			onuCardsInGame.add(card);
		}
		System.out.println("les 10 cartes 'objectifs de l'ONU' selectionnées sont :");
		return onuCardsInGame;
	}


	/**
	 * Vérifier si une des cartes "objectif de l'ONU" du jeu est marquer par un joueur
	 * @return true si une carte est marquée (si un joueur à contruit tout les types de centrales présents sur une carte)
	 */
	public boolean markOnuCard(OnuCard card){
		ArrayList<String> centralsGreen = new ArrayList<>();
		String CentraleName;
		int nbSolaire = 0;
		// TODO : pour démo : int nbSolaire permet de vérifier que le nombre de solaire dans la carte soit égal au nombre de centrales solaires construites
		// TODO : prochain sprint : enlever quand on aura implémenter tous les types de projets
		for(int i=0; i<getContinents().length; i++) { // boucle sur continent
			ArrayList<Central> caseCentrals = continents[i].getCentrales();
			for (Central c : caseCentrals) { // pour toutes les cases de centrales
				if (c.isOccupe() && !c.isFossile()) { // si centrale occupé et pas fossible
					nbSolaire++;
					CentraleName = c.getType().name(); // récupération du nom
					centralsGreen.add(CentraleName); // ajouter a la liste des centrales vertes construites sur le jeu
					System.out.println("liste des centrales vertes :" + centralsGreen);
				}
			}
		}
		if (centralsGreen.containsAll(card.getTypesCentral())) { // si la liste des centrales vertes construites contient tous les types de centrales d'une carte ONU
			if(nbSolaire == card.getTypesCentral().size()) {
				return true;
			}
		}
		return false;
	}


	/**
	 * Vérifier si le joueur a marquer une carte de l'ONU et si il possède au moins un cube de ressource technologique
	 *	Donne les points de victoires au joueur et diminue ses ressources technologiques de -1
	 */
	public void giveVictoryPointsOnuCards(OnuCard card){
		if (markOnuCard(card) && getCurrentPLayer().getResourcesTech() >= 1){ // si une carte est marquée par le joueur et qur la ressource technologique
			getCurrentPLayer().setPointVictoire(getCurrentPLayer().getPointVictoire() + card.getNbPointDeVictoire()); // augmente points de victoires avec la carte
			getCurrentPLayer().setResourcesTech(getCurrentPLayer().getResourcesTech()-1); // diminue ressources technologieques du joueur
			onuCardsInGame.remove(card); // supprime la carte du jeu
			System.out.println("carte ONU n°"  + card.getId()+ " jouer !");
		}else{
			// TODO : mettre alerte manque ressource technologique
			System.out.println("pas assez de points de ressource technologique!");
		}
	}


	public List<OnuCard> getOnuCardsInGame() {
		return onuCardsInGame;
	}

	public List<PisteExpertise> getExpertises() {
		return pistesExpertise;
	}

	public void setExpertises(List<PisteExpertise> pistesExpertise) {
		this.pistesExpertise = pistesExpertise;
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

	/**
	 * Permet de placer uen centrale fossile sur le modele est de mettre a jour son niveau de co2
	 * @param continent Le continent ou placé la centrale
	 * @param type Le type de la centrale
	 *             0 = centrale a charbon
	 *             1 = centrale a petrole
	 *             2 = centrale a gaz
	 */
	public void putFossileCentral(Continent continent, centralTypes type, int index) {
		Central central = continent.getCentrales().get(index) ;
		central.setOccupe(true);
		switch (type){
			case CHARBON :
				central.setType(centralTypes.CHARBON);
				break;
			case PETROLE :
				central.setType(centralTypes.PETROLE);
				break;
			case GAZNATUREL :
				central.setType(centralTypes.GAZNATUREL);
				break;
		}
		this.co2 += central.getType().getCo2();
	}

	public int getCo2() {
		return this.co2 ;
	}

	public List<SommetTile> getAllSommetTile() {
		return allSommetTile;
	}

	public void tradePVtoCEP() {
		while (currentPriceCEP > curPlayer.getArgent()) {
			curPlayer.setPointVictoire(curPlayer.getPointVictoire()-1);
			curPlayer.gainArgent(1);
		}
		curPlayer.retirerArgent(currentPriceCEP);
		curPlayer.addCEP();
		achatCEP();
		curPlayer.removeCEP();
	}
}