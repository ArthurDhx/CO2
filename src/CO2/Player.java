package CO2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    final int NBACTIONGRATUITE = 4;

    // valeur d'expertise pour chaque energy
    private Map<GreenEnergyTypes, Integer> expertise;
    //le joueur à une liste de scientifique, elle peut être vide ou rempli jusqu'a 4 scientifiques maximum
    private List<Scientifique> scientifiques;
    // Nombre de CEP du joueur
    private int CEP;
    // Nombre de ressources technologiques du joueur
    private int resourcesTech;
    // argent du joueur
    private int argent;
    // Point de victoire du joueur
    private int pointVictoire;

    /*
     * true si une action a été faite
     * [0] deplacer scientifique Projet
     * [1] deplacer scientifique Sommet
     * [2] visite au marche
     * [3] jouer une carte
     */
    private boolean[] actionGratuiteDone ;
    private boolean actionPrincipaleDone ;

    public Player() {
        initExpertise();
        initScientifiques();
        actionPrincipaleDone = false;
        actionGratuiteDone = new boolean[NBACTIONGRATUITE];
        CEP = 2;
        resourcesTech = 0;
        argent = 21;
        pointVictoire = 0;
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
        expertise.put(GreenEnergyTypes.SOLAR, 0);
        expertise.put(GreenEnergyTypes.BIOMASS, 0);
        expertise.put(GreenEnergyTypes.RECYCLING, 0);
        expertise.put(GreenEnergyTypes.FUSION, 0);
        expertise.put(GreenEnergyTypes.REFORESTATION, 0);
    }

    /**
     * Increment le niveau d'expertise dans un source d'energie verte donnee
     * @param type la source d'energie verte concernee
     * @param quantity
     */
    public void addExpertise(GreenEnergyTypes type, int quantity) {
        int cur = expertise.get(type);
        expertise.replace(type, cur+quantity);
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
     *  - paye le CEP
     *  - donne la recompense du projet selon le type d'energie
     * @param type type energie du projet
     */
    public void rewardSetupProject(GreenEnergyTypes type){
        CEP -= 1;
        actionPrincipaleDone = true;

        // TEMPORAIRE DEMO SPRINT 3
        // TODO retirer temporaire
        addExpertise(GreenEnergyTypes.SOLAR,1);
        addExpertise(GreenEnergyTypes.BIOMASS,1);
        addExpertise(GreenEnergyTypes.RECYCLING,1);
        addExpertise(GreenEnergyTypes.FUSION,1);
        addExpertise(GreenEnergyTypes.REFORESTATION,1);
        // FIN TEMPORAIRE

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
    public int getExpertise(GreenEnergyTypes type) {
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
     * Permet de dire que l'action "aller au marché" est faite ou non pendant ce tour
     * @param done
     */
    public void setMarcheCEPDone(boolean done){
        this.actionGratuiteDone[2] = done;
    }

    /**
     * A commenter
     * @return
     */
    public Scientifique getCurrentScientifique(){
        return scientifiques.get(0);// a terme il y aura plus de scientifique
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
}