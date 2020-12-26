package CO2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class OnuCard {
    private int id;
    private int nbPointDeVictoire;
    private ArrayList<String> typesCentral;

    OnuCard(int id, int nbPointDeVictoire) {
        this.id = id;
        this.nbPointDeVictoire = nbPointDeVictoire;
        typesCentral = new ArrayList<>();
        setTypesCentral(typesCentral, new Random());
    }

    /**
     * Sélectionne aléatoirement des types de centrales parmis tous les types de centrales vertes
     * la liste doit être composée de types différents
     * la liste est variable de 2 à 4 types
     */
    public void setTypesCentral(ArrayList<String> typesCentral, Random random) {
        for(int i = 0; i< random(random); i++) {
            String type;
            // selection d'un type de centrale verte
            do type = centralTypes.values()[new Random().nextInt(centralTypes.values().length - 3)].name();
            // test si ce type n'est pas déjà dans la liste
            while(typesCentral.contains(type));
            // ajout du type à la liste
            typesCentral.add(type);
        }
    }

    /**
     * Sélectionne un nombre aléatoire de 2 à 4
     * ce nombre définira le nombre de type de centrales de la carte
     * @return int nombre aléatoire
     */
    public int random(Random random) {
        return 4-random.nextInt(3);
    }


    public ArrayList<String> getTypesCentral() {
        return typesCentral;
    }


    public void setNbPointDeVictoire(int nbPointDeVictoire) {
        this.nbPointDeVictoire = nbPointDeVictoire;
    }

    public int getNbPointDeVictoire() {
        return nbPointDeVictoire;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Créer des centrales " + typesCentral + "\n Pour gagner " + nbPointDeVictoire + " Points de victoire" ;
    }
}
