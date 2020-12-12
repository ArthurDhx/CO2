package CO2;

enum greenEnergyTypes {SOLAR, BIOMASS, RECYCLING, FUSION, REFORESTATION}

enum subventionTypes {ARGENT, RESSOURCE, RECHERCHE}

enum typeLobbyAction {PROPOSER, METTRE, CONSTRUIRE, SOMMET, MARCHE_ACHAT, MARCHE_VENTE}

enum centralTypes {
    //TODO :  a changer une fois le reste implementer
    REBOISEMENT(10, new int[]{10, 10},10,0),
    BIOMASSE(10, new int[]{10, 10},10,0),
    SOLAIRE(10, new int[]{8, 4},2,0),
    RECYCLAGE(10, new int[]{10, 10},10,0),
    FUSIONFROIDE(10, new int[]{10, 10},10,0),
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