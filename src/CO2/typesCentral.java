package CO2;

enum typesCentral {
    //TODO :  a changer une fois le reste implementer
    REBOISEMENT(10,10,10),
    BIOMASSE(10,10,10),
    SOLAIRE(10,8,4),
    RECYCLAGE(10,10,10),
    FUSIONFROIDE(10,10,10),
    CHARBON(10,10,10),
    PETROLE(10,10,10),
    GAZNATUREL(10,10,10);

    private int ptsVictoire ;
    private int cout ;
    private int expertise ;

    typesCentral(int ptsVictoire, int cout, int expertise) {
        this.ptsVictoire = ptsVictoire ;
        this.cout = cout ;
        this.expertise = expertise ;
    }

    public int getPtsVictoire() {
        return ptsVictoire;
    }

    public int getCout() {
        return cout;
    }

    public int getExpertise() {
        return expertise;
    }
}
