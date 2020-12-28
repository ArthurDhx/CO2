package CO2;

import java.util.Random;

enum greenEnergyTypes {
    SOLAR, BIOMASS, RECYCLING, FUSION, REFORESTATION;
    /**
     * Pick a random value of the BaseColor enum.
     * @return a random BaseColor.
     */
    public static greenEnergyTypes getRandomGreenEnergyTypes() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}

enum subventionTypes {ARGENT, RESSOURCE, RECHERCHE}

enum lobbyActionTypes {PROPOSER, METTRE, CONSTRUIRE, SOMMET, MARCHE_ACHAT, MARCHE_VENTE}
enum lobbyMineurTypes {
    RESOURCES("1 ressource technologique"),
    ARGENT("2€"),
    CEP("1 CEP"),
    SCIENTIFIQUE("1 deplacement de scientifique");

    String description;

    lobbyMineurTypes(String description) {
        this.description = description;
    }
}


enum centralTypes {
    REBOISEMENT(120, new int[]{10, 3},3,0),
    BIOMASSE(75, new int[]{6, 1},1,0),
    SOLAIRE(105, new int[]{8, 2},2,0),
    RECYCLAGE(75, new int[]{6, 1},1,0),
    FUSIONFROIDE(105, new int[]{8, 2},2,0),
    CHARBON(10, new int[]{10, 10},10,40),
    PETROLE(10, new int[]{10, 10},10,30),
    GAZNATUREL(10, new int[]{10, 10},10,20);

    // cout[0] = argent cout[1] = ressource technologique
    private int ptsVictoire ;
    private int[] cout ;
    private int expertise ;
    private int co2 ;

    centralTypes(int ptsVictoire, int[] cout, int expertise, int co2) {
        this.ptsVictoire = ptsVictoire ;
        this.cout = cout ;
        this.expertise = expertise ;
        this.co2 = co2 ;
    }

    public int getPtsVictoire() {
        return ptsVictoire;
    }

    public int[] getCout() {
        return cout;
    }

    public int getExpertise() {
        return expertise;
    }

    public int getCo2() {
        return co2;
    }

    @Override
    public String toString() {
        return "Points de Victoire : " + ptsVictoire + ", Cout : " + cout[0] + " argent et " + cout[1] + " ressources techniques, Expertise nécessaire :" + expertise ;
    }
}