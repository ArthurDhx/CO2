package CO2;

public class Continent {

    private String name;
    private Subvention[] subventions = new Subvention[3];
    private int nbCep;
    //mettre un attribut des tuiles "agenda régionales" (à voir plus tard)

    public Continent(String name, int nbCep) {
        for(int i = 0;i<3;i++) {
            subventions[i] = new Subvention();
        }
        subventions[0].setName("argent");
        subventions[1].setName("ressources technologiques");
        subventions[2].setName("recherche collaboration");
        this.name = name;
        this.nbCep = nbCep;
    }

    public Subvention[] getSubventions() {
        return subventions;
    }
}
