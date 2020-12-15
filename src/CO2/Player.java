package CO2;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    final int NBACTIONGRATUITE = 4;

    // valeur d'expertise pour chaque energy
    private Map<greenEnergyTypes, Integer> expertise;
    //le joueur à une liste de scientifique, elle peut être vide ou rempli jusqu'a 4 scientifiques maximum
    private List<Scientifique> scientifiques;
    private int curentScientifiqueId;
    // Nombre de CEP du joueur
    private int CEP;
    // Nombre de ressources technologiques du joueur
    private int resourcesTech;
    // argent du joueur
    private int argent;
    // Point de victoire du joueur
    private int pointVictoire;
    // liste des continents controlles
    private List<Continent> continentsControlles;
    // couleur du joueur
    private Color color;
    private boolean allScientifiqueIsOnSommet;
    // liste des cartes Lobby dans la main du joueur
    private List<LobbyCard> lobbyCards;

    /*
     * true si une action a été faite
     * [0] deplacer scientifique Projet
     * [1] deplacer scientifique Sommet
     * [2] visite au marche
     * [3] jouer une carte
     */
    private boolean[] actionGratuiteDone ;
    private boolean actionPrincipaleDone ;

    // action marche du tour
    // 0 = aucune; 1 = achat; 2 = vente
    private int actionMarche = 0;

    public Player() {
        initExpertise();
        initScientifiques();
        actionPrincipaleDone = false;
        actionGratuiteDone = new boolean[NBACTIONGRATUITE];
        CEP = 2;
        resourcesTech = 0;
        argent = 21;
        pointVictoire = 0;
        continentsControlles = new ArrayList<>();
        color = Color.INDIANRED;
        curentScientifiqueId = 0;
        allScientifiqueIsOnSommet = false;
    }

    /**
     * Initialise la liste de scientifiques du joueur
     */
    private void initScientifiques() {
        this.scientifiques = new ArrayList<>();
        this.scientifiques.add(new Scientifique());
    }

    /**
     * Initialise l'expertise du joueur pour chaque energie verte
     */
    private void initExpertise() {
        expertise = new HashMap<>();
        expertise.put(greenEnergyTypes.SOLAR, 0);
        expertise.put(greenEnergyTypes.BIOMASS, 0);
        expertise.put(greenEnergyTypes.RECYCLING, 0);
        expertise.put(greenEnergyTypes.FUSION, 0);
        expertise.put(greenEnergyTypes.REFORESTATION, 0);
    }

    /**
     * Initialise la liste de cartes lobby du joueur
     */
    public void giveLobbyCards(List<LobbyCard> cards, int nb) {
        lobbyCards = new ArrayList<>();
        // temporaire demo
        // TODO : supprimer tempo et faire vrai distribution dans la boucle
        lobbyCards.add(cards.remove(2));
        lobbyCards.add(cards.remove(5));
        lobbyCards.add(cards.remove(7));
        lobbyCards.add(cards.remove(13));
        lobbyCards.add(cards.remove(18));
        // FIN temporaire demo
        for (int i = 0; i<nb; i++) {
            System.out.println(lobbyCards.get(i));
            // prendre une carte de cards au hasard
            // la retirer de cards et ajouter a lobbyCards
        }
    }

    /**
     * Joue la carte choisie par le joueur
     * @param card la carte choisie
     */
    public void playLobbyCard(LobbyCard card) {
        // donner les recompenses
        Object complement = card.getComplement();
        switch (card.getLobbyActionType()) {
            case PROPOSER:
                if (complement instanceof Continent)
                    CEP += 3;
                if (complement instanceof subventionTypes) {
                    if (subventionTypes.ARGENT.equals(complement)) argent +=3;
                    else if (subventionTypes.RESSOURCE.equals(complement)) resourcesTech+=1;
                    else if (subventionTypes.RECHERCHE.equals(complement)) {
                        // repasse un mouvement scientifique a false pour autiriser un nouveau
                        if (actionGratuiteDone[0] && !actionGratuiteDone[1]) actionGratuiteDone[0] = false;
                        if (!actionGratuiteDone[0] && actionGratuiteDone[1]) actionGratuiteDone[1] = false;
                    }
                }
                break;
            case METTRE:
                // TODO: trouver une recompense
                break;
            case CONSTRUIRE:
                // si cette fonction est faite, le joueur a deja payer la centrale, on rembourse
                argent += 3;
                break;
            case SOMMET:
                addExpertise((greenEnergyTypes) complement, 1);
                break;
            case MARCHE_ACHAT:
                pointVictoire += 2;
                break;
            case MARCHE_VENTE:
                argent +=3;
                break;
        }
        actionGratuiteDone[3] = true;

        // retirer la carte du jeu
        lobbyCards.remove(card);
    }

    /**
     * Joue le lobby mineur de la carte choisie par le joueur
     * @param card la carte choisie
     */
    public void playMinorLobbyCard(LobbyCard card) {
        actionGratuiteDone[3] = true;
        // donner les recompenses
        switch (card.getLobbyMineurType()) {
            case ARGENT:
                argent+=2;
                break;
            case CEP:
                CEP+=1;
                break;
            case RESOURCES:
                resourcesTech+=1;
                break;
            case SCIENTIFIQUE:
                // repasse un mouvement scientifique a false pour autiriser un nouveau
                if (actionGratuiteDone[0] && !actionGratuiteDone[1]) actionGratuiteDone[0] = false;
                if (!actionGratuiteDone[0] && actionGratuiteDone[1]) actionGratuiteDone[1] = false;
                break;
        }
        // retirer la carte du jeu
        lobbyCards.remove(card);
    }

    /**
     * Increment le niveau d'expertise dans un source d'energie verte donnee
     * @param type la source d'energie verte concernee
     * @param quantity
     */
    public void addExpertise(greenEnergyTypes type, int quantity) {
        int max = 10;
        int cur = expertise.get(type);
        if (cur+quantity <= max) expertise.replace(type, cur+quantity);
    }

    /**
     * Increment le nombre de ressources technologiques d'un montant donne
     * @param amount
     */
    public void addResourcesTech(int amount) { resourcesTech += Math.abs(amount); }

    /**
     * Retire un nombre de ressources technologiques si le joueur a assez de cubes
     * @param amount
     * @return
     */
    public boolean removeResourcesTech(int amount) {
        int montant = Math.abs(amount);
        if ((this.resourcesTech-montant) >= 0){
            this.resourcesTech -= montant;
            return true;
        }
        return false;
    }

    /**
     * Ajoute un montant d'argent au joueur
     * @param argent
     */
    public void gainArgent(int argent) { this.argent += Math.abs(argent); }

    /**
     * Retire un montant d'argent au joueur
     * @param argent
     * @return true si la transaction est effectuer
     */
    public boolean retirerArgent(int argent) {
        if ((this.argent-argent) >= 0){
            this.argent -= Math.abs(argent);
            return true;
        }
        return false;
    }

    /**
     * Applique les effets de mise en place d'un projet au joueur :
     *  - donne la recompense du projet selon le type d'energie
     * @param type type energie du projet
     */
    public void rewardSetupProject(greenEnergyTypes type){
        actionPrincipaleDone = true;

        switch (type) {
            case REFORESTATION:
                // prendre 2 CEP du marcher
                break;
            case SOLAR:
                resourcesTech += 3;
                break;
            case FUSION:
                resourcesTech += 1;
                argent +=5;
                break;
            case BIOMASS:
                argent += 3;
                resourcesTech += 1;
                // prendre 1 CEP du marché
                break;
            case RECYCLING:
                argent += 5;
                // prendre 1 CEP du marché
                break;
        }
    }

    /**
     * Getter d'expertise pour un type d'energie donnee
     * @param type type d'energie concernee
     * @return
     */
    public int getExpertise(greenEnergyTypes type) {
        return expertise.get(type);
    }

    /**
     * Indique si toute les actions gratuites on ete faites
     * @return true si toutes les actions sont faites
     */
    public boolean isAllActionGratuiteDone() {
        for (int i = 0; i < actionGratuiteDone.length ; i++) {
            if (!actionGratuiteDone[i]) return false ;
        }
        return true ;
    }

    /**
     * Permet de dire que l'action "déplacer scientifique" est faite ou non pendant ce tour
     * @param done
     */
    public void setDeplacerScientifiqueDone(boolean done){
        this.actionGratuiteDone[0] = done ;
    }

    /**
     * Permet de dire que l'action gratuite jouer carte est faite ou non pendant ce tour
     * @param done
     */
    public void setCardDone(boolean done){
        this.actionGratuiteDone[3] = done ;
    }


    /**
     * Permet de dire que l'action "aller au marché" est faite ou non pendant ce tour
     * @param done
     */
    public void setMarcheCEPDone(boolean done){
        this.actionGratuiteDone[2] = done;
    }

    /**
     * @return Scientifique, retourne le scientifique courant
     */
    public Scientifique getCurrentScientifique(){
        return scientifiques.get(curentScientifiqueId);
    }

    /**
     * @return Scientifique, retourne le dernier scientifique ajouté
     */
    public Scientifique getLastAddScientifique(){
        return scientifiques.get(scientifiques.size()-1);
    }

    /**
     * Selectionne le scientifique courant
     */
    public void setCurrentScientifique(int curentScientifiqueId){
        this.curentScientifiqueId = curentScientifiqueId;
    }

    public int getCurentScientifiqueId() {
        return curentScientifiqueId;
    }

    /**
     * Incremente le nombre de CEP de l'utiliseur
     */
    public void addCEP(){ CEP += 1; }

    /**
     * Incremente le nombre de CEP de l'utilisateur
     */
    public void removeCEP(){ CEP -= 1; }

    /**
     * Ajoute le revenu au points de victoire et a l'argent du joueur
     * @param nombres [ptVictoire, argent]
     */
    public void giveRevenu(int[] nombres) {
        pointVictoire+=nombres[0];
        argent+=nombres[1];
    }

    /**
     * Permet de donner un CEP à un continent
     * @param continent
     * @return true ou false suivant si l'opération à réussi ou non
     */
    public boolean giveCEP(Continent continent){
        if(getCEP() >= 1){ //Le joueur doit avoir au moins un CEP
            if(continent.addCEP(1)){ //On essaye de donner un cep au continent
                CEP -= 1; //si ça à fonctionné, on retire une CEP au joueur
                return true; //On reevoit que la manip à fonctionné
            }
        }
        return false; //Sinon on revoit que la manip n'a pas fonctionné
    }

    /**
     * Permet d'ajouter un scientifique dans la réserve
     * @return true ou false suivant si l'opération à réussi ou non
     */
    public boolean addScientifique(){
        if(scientifiques.size() == 4) return false; //Si le joueur à deja 4 scientifiques, il ne peut pas en avoir plus
        scientifiques.add(new Scientifique()); //Sinon on en ajoute un dans sa liste
        return true;
    }

    /**
     * Prend le controlle d'un continent
     * @param continent continent a controller
     */
    public void takeControl(Continent continent) {
        // si le continent n'est pas deja controller on l'ajoute
        if (!continentsControlles.contains(continent))
            continentsControlles.add(continent);
    }

    /**
     * Determine si le continent est controller par le joueur
     * @param continent continent a verifier
     * @return true si le joueur controlle le continent
     */
    public boolean hasControl(Continent continent) {
        return continentsControlles.contains(continent);
    }

    /**
     * Determine si le joueur peut construire la centrale
     * - assez d'expertise
     * - assez d'argent
     * - assez de ressources technologiques
     * @param project projet a construire
     * @return
     */
    public boolean canConstruct(Project project) {
        centralTypes typeCentral = project.getCentralType();
        int[] cout = typeCentral.getCout();
        if (cout[0] > argent) return false;
        if (cout[1] > resourcesTech) return false;
        if (typeCentral.getExpertise() > getExpertise(project.getEnergyType())) return false;
        return true;
    }

    /**
     * Paye la construction
     * @param cost cout du projet a construire
     */
    public void payCentral(int[] cost) {
        argent -= cost[0];
        resourcesTech -= cost[1];
    }

    public int getNBACTIONGRATUITE() {
        return NBACTIONGRATUITE;
    }

    public List<Scientifique> getScientifiques() {
        return scientifiques;
    }

    public void setScientifiques(List<Scientifique> scientifiques) {
        this.scientifiques = scientifiques;
    }

    public int getCEP() {
        return CEP;
    }

    public void setCEP(int CEP) {
        this.CEP = CEP;
    }

    public int getResourcesTech() {
        return resourcesTech;
    }

    public void setResourcesTech(int resourcesTech) {
        this.resourcesTech = resourcesTech;
    }

    public int getArgent() {
        return argent;
    }

    public void setArgent(int argent) {
        this.argent = argent;
    }

    public boolean[] getActionGratuiteDone() {
        return actionGratuiteDone;
    }

    public void setActionGratuiteDone(boolean[] actionGratuiteDone) {
        this.actionGratuiteDone = actionGratuiteDone;
    }

    public boolean isActionPrincipaleDone() {
        return actionPrincipaleDone;
    }

    public void setActionPrincipaleDone(boolean actionPrincipaleDone) {
        this.actionPrincipaleDone = actionPrincipaleDone;
    }

    public int getPointVictoire() {
        return pointVictoire;
    }

    public void setPointVictoire(int pointVictoire) {
        this.pointVictoire = pointVictoire;
    }

    public void addPointVictoire(int pointVictoire) {
        this.pointVictoire += Math.abs(pointVictoire);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List<Continent> getContinentsControlles() {
        return continentsControlles;
    }

    public void setContinentsControlles(List<Continent> continentsControlles) {
        this.continentsControlles = continentsControlles;
    }

    public void setAllScientifiqueIsOnSommet(boolean allScientifiqueIsOnSommet) {
        this.allScientifiqueIsOnSommet = allScientifiqueIsOnSommet;
    }

    public boolean isAllScientifiqueIsOnSommet() {
        int all = 0;
        for (Scientifique s: scientifiques) {
            if (s.getSommetTile() != null) all++;
        }
        if (all == scientifiques.size()) return true;
        else return false;
    }

    public int getActionMarche() {
        return actionMarche;
    }

    public void setActionMarche(int actionMarche) {
        this.actionMarche = actionMarche;
    }

    public List<LobbyCard> getLobbyCards() {
        return lobbyCards;
    }
}