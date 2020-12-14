package CO2;

import java.util.ArrayList;
import java.util.Random;

public class OnuCard {
    private int id;
    private int nbPointDeVictoire;
    private ArrayList<String> typesCentral;

    OnuCard(int id, int nbPointDeVictoire) {
        this.id = id;
        this.nbPointDeVictoire = nbPointDeVictoire;
        typesCentral = new ArrayList<>();
        setTypesCentral(typesCentral);
    }

    /**
     * Sélectionne aléatoirement des types de centrales parmis tous les types de centrales vertes
     * la liste doit être composée de types différents
     * la liste est variable de 2 à 4 types
     */
    public void setTypesCentral(ArrayList<String> typesCentral) {
        //final Random random = new Random();
        // TODO : prochain sprint : remettre à jour
        //for(int i=0;i< randomNbType();i++) {
        for(int i=0;i< randomNbType();i++) {
            String type;
            // selection d'un type de centrale verte
            //do type = centralTypes.values()[random.nextInt(centralTypes.values().length - 3)].name();
            //do type = centralTypes.values()[2].name();
            type = centralTypes.values()[2].name();
            // test si ce type n'est pas déjà dans la liste
            //while(typesCentral.contains(type));
            // ajout du type à la liste
            typesCentral.add(type);
        }
    }

    /**
     * Sélectionne un nombre aléatoire de 2 à 4
     * ce nombre définira le nombre de type de centrales de la carte
     * @return int nombre aléatoire
     */
    public int randomNbType() {
        final Random random = new Random();
        return 4-random.nextInt(3);
    }

    public ArrayList<String> getTypesCentral() {
        return typesCentral;
    }


    public void setNbPointDeVictoire(int nbPointDeVictoire) {
        this.nbPointDeVictoire = nbPointDeVictoire;
    }

    @Override
    public String toString() {
        return "OnuCard{" +
                "id=" + id +
                ", nbPointDeVictoire=" + nbPointDeVictoire +
                ", typesCentral=" + typesCentral +
                "} \n";
    }
}
