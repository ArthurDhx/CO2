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

    //le joueur à des CEP
    int CEP;

    private boolean actionPrincipaleDone ;
    private boolean[] actionGratuiteDone ;

    private int argent;
    /*
    * true si une action a été faite
    * [0] deplacer scientifique Projet
    * [1] deplacer scientifique Sommet
    * [2] visite au marche
    * [3] jouer une carte
    */



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

    public Player(boolean actionPrincipaleDone, boolean[] actionGratuiteDone) {
        this.actionPrincipaleDone = actionPrincipaleDone ;
        this.actionGratuiteDone = actionGratuiteDone;
        this.scientifiques = new ArrayList<>();
        this.scientifiques.add(new Scientifique());
        this.CEP = 2;
        setArgent(21);
    }

    /**
     * Increment le niveau d'expertise dans un source d'energie verte donnee
     * @param eneryType la source d'energie verte concernee
     */
    public void addExpertise(Integer eneryType) {
        expertise[eneryType]++;
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

    public void setActionPrincipaleDone(boolean actionPrincipaleDone) {
        this.actionPrincipaleDone = actionPrincipaleDone;
    }

    public boolean[] getActionGratuiteDone() {
        return actionGratuiteDone;
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

    public int getArgent() {
        return argent;
    }
    public void setArgent(int argent) {
        this.argent = argent;
    }

    public Scientifique getCurrentScientifique(){
        return scientifiques.get(0);// a terme il y aura plus de scientifique
    }

    public int getCEP() { return CEP; }
    public void addCEP(){ CEP += 1; }
    public void removeCEP(){ CEP -= 1; }
    public void mettreEnPlaceProjet(){ CEP -= 1; }

    public void gainArgent(int argent) {
        this.argent += Math.abs(argent);
    }

    public boolean retirerArgent(int argent) {
        if ((this.argent-argent) >= 0){
            this.argent -= Math.abs(argent);
            return true;
        }
        return false;
    }
}
