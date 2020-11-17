package CO2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {

    // Identifiant dans le tableau de chaque energie
    public static Map<String, Integer> expertiseId;
    // valeur d'expertise pour chaque energy
    private int[] expertise = {0, 0, 0, 0, 0};
    //le joueur à une liste de scientifique, elle peut être vide ou rempli jusqu'a 4 scientifiques maximum
    private List<Scientifique> scientifiques;

    // Nombre de CEP du joueur
    private int CEP;
    // Nombre de ressources technologiques du joueur
    private int resourcesTech;


    private int argent;

    /*
    * true si une action a été faite
    * [0] deplacer scientifique Projet
    * [1] deplacer scientifique Sommet
    * [2] visite au marche
    * [3] jouer une carte
    */
    private boolean[] actionGratuiteDone ;
    private boolean actionPrincipaleDone ;



    public static Player createPlayer() {
        final int NBACTIONGRATUITE = 4;
        boolean[] actionGratuiteDone = new boolean[NBACTIONGRATUITE];
        Player p = new Player(false , actionGratuiteDone);

        /*
         Initialise la map qui attribut un String qui definit
         le type d'energie verte (ex: "Solar")
         a son id dans le tableau des valeurs [expertise]
        */
        expertiseId = new HashMap<>();
        expertiseId.put("Solar", 0);
        expertiseId.put("Biomass", 1);
        expertiseId.put("Recycling", 2);
        expertiseId.put("Fusion", 3);
        expertiseId.put("Reforestation", 4);

        return p;
    }

    private Player(boolean actionPrincipaleDone, boolean[] actionGratuiteDone) {
        this.actionPrincipaleDone = actionPrincipaleDone ;
        this.actionGratuiteDone = actionGratuiteDone;
        this.scientifiques = new ArrayList<>();
        this.scientifiques.add(new Scientifique());
        this.CEP = 2;
        this.resourcesTech = 0;
        setArgent(21);
    }

    /**
     * Increment le niveau d'expertise dans un source d'energie verte donnee
     * @param eneryType la source d'energie verte concernee
     */
    public void addExpertise(Integer eneryType) {
        expertise[eneryType]++;
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
     *
     * @param argent
     */
    public void gainArgent(int argent) { this.argent += Math.abs(argent); }

    /**
     * @param argent
     * @return
     */
    public boolean retirerArgent(int argent) {
        if ((this.argent-argent) >= 0){
            this.argent -= Math.abs(argent);
            return true;
        }
        return false;
    }

    public void mettreEnPlaceProjet(GreenEnergyTypes type){
        CEP -= 1;
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

    public int getSolarExpertise() {
        return expertise[expertiseId.get("Solar")];
    }
    public int getBiomassExpertise() { return expertise[expertiseId.get("Biomass")]; }
    public int getRecyclingExpertise() { return expertise[expertiseId.get("Recycling")]; }

    public int getFusionExpertise() { return expertise[expertiseId.get("Fusion")]; }
    public int getReforestationExpertise() { return expertise[expertiseId.get("Reforestation")]; }

    public boolean isActionPrincipaleDone() {
        return actionPrincipaleDone;
    }
    public boolean isAllActionGratuiteDone() {
        for (int i = 0; i < actionGratuiteDone.length ; i++) {
            if (!actionGratuiteDone[i]) return false ;
        }
        return true ;
    }
    public int getResourcesTech() { return resourcesTech; }

    public boolean[] getActionGratuiteDone() {
        return actionGratuiteDone;
    }
    public int getArgent() {
        return argent;
    }
    public void setActionPrincipaleDone(boolean actionPrincipaleDone) {
        this.actionPrincipaleDone = actionPrincipaleDone;
    }

    public void setActionGratuiteDone(boolean[] actionGratuiteDone) {
        this.actionGratuiteDone = actionGratuiteDone;
    }

    public void setDeplacerScientifiqDone(boolean done){
        this.actionGratuiteDone[0] = done ;
    }
    public void setMarcheCEPDone(boolean done){
        this.actionGratuiteDone[2] = done;
    }
    public List<Scientifique> getScientifiques(){
        return this.scientifiques;
    }

    public void setArgent(int argent) {
        this.argent = argent;
    }

    public void setResourcesTech(int resourcesTech) { this.resourcesTech = resourcesTech; }
    public Scientifique getCurrentScientifique(){
        return scientifiques.get(0);// a terme il y aura plus de scientifique
    }
    public int getCEP() { return CEP; }
    public void addCEP(){ CEP += 1; }

    public void removeCEP(){ CEP -= 1; }

}
