package CO2;

import java.util.HashMap;
import java.util.Map;

public class Player {

    // Identifiant dans le tableau de chaque energie
    public static Map<String, Integer> expertiseId;
    // valeur d'expertise pour chaque energy
    private int[] expertise = {0};

    public static Player createPlayer() {
        Player p = new Player();

        /*
         Initialise la map qui attribut un String qui definit
         le type d'energie verte (ex: "Solar")
         a son id dans le tableau des valeurs [expertise]
        */
        expertiseId = new HashMap<>();
        expertiseId.put("Solar", 0);

        return p;
    }

    public int getSolarExpertise() {
        return expertise[expertiseId.get("Solar")];
    }

    public void addExpertise(Integer eneryType) {
        expertise[eneryType]++;
    }
}
