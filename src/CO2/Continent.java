package CO2;

import javafx.scene.image.Image;

public class Continent {

    private String name;
    private Subvention[] subventions = new Subvention[3];
    private int nbCep;
    private Image imgContinent;
    //mettre un attribut des tuiles "agenda régionales" (à voir plus tard)

    public Continent(String name, int nbCep, Image imgContinent) {
        for(int i = 0;i<3;i++) {
            subventions[i] = new Subvention();
        }
        subventions[0].setName("argent");
        subventions[1].setName("ressources \ntechnologiques");
        subventions[2].setName("recherche \ncollaboration");
        this.name = name;
        this.nbCep = nbCep;
        this.imgContinent = imgContinent;
    }

    public Subvention[] getSubventions() {
        return subventions;
    }

    public Image getImgContinent() { return imgContinent; }
}
