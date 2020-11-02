package CO2;

import java.util.HashMap;
import java.util.Map;

public class Player {

    // Identifiant dans le tableau de chaque energie
    public static Map<String, Integer> expertiseId;
    // valeur d'expertise pour chaque energy
    private int[] expertise = {0};

    private boolean actionPrincipaleDone ;
    private boolean[] actionGratuiteDone ;
    /*
    * true si une action a été faite
    * [0] deplacer scientifique
    * [1] visite au marche
    * [2] jouer une carte
    */



    public static Player createPlayer() {
        final int NBACTIONGRATUITE = 3;
        boolean[] actionGratuiteDone = new boolean[NBACTIONGRATUITE];
        Player p = new Player(false , actionGratuiteDone);
        /*
         Initialise la map qui attribut un String qui definit
         le type d'energie verte (ex: "Solar")
         a son id dans le tableau des valeurs [expertise]
        */
        expertiseId = new HashMap<>();
        expertiseId.put("Solar", 0);

        return p;
    }

    public Player(boolean actionPrincipaleDone, boolean[] actionGratuiteDone) {
        this.actionPrincipaleDone = actionPrincipaleDone ;
        this.actionGratuiteDone = actionGratuiteDone;
    }


    public int getSolarExpertise() {
        return expertise[expertiseId.get("Solar")];
    }

    public void addExpertise(Integer eneryType) {
        expertise[eneryType]++;
    }

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
}
