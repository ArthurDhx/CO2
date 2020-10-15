package CO2;

public class Continent {

    private String name;
    private Subvention[] subventions = new Subvention[3];
    private int nbCep;
    //mettre un attribut des tuiles "agenda régionales" (à voir plus tard)

    public Continent(String name, int nbCep) {
        subventions[0] = new Subvention("argent");
        subventions[1] = new Subvention("Cubes ressources technologique");
        subventions[2] = new Subvention("recherche en collaboration");
        this.name = name;
        this.nbCep = nbCep;
    }
}
