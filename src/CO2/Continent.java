package CO2;

import javafx.scene.image.Image;

import java.util.Arrays;

public class Continent {

    private String name;
    private final Subvention[] subventions = new Subvention[3];
    private int nbCep;
    private Image imgContinent;
    // mettre un attribut des tuiles "agenda régionales" (à voir plus tard)

    public Continent(String name, int nbCep, Image imgContinent) {
        // initalisation des 3 cases permettant de recevoir des subventions
        for(int i = 0;i<3;i++) {
            subventions[i] = new Subvention();
        }
        // initalisation du nom de la subvention
        subventions[0].setName("argent");
        subventions[1].setName("ressources \ntechnologiques");
        subventions[2].setName("recherche en\ncollaboration");
        this.name = name;
        this.nbCep = nbCep;
        this.imgContinent = imgContinent;
    }

    public Subvention[] getSubventions() {
        return subventions;
    }

    public Image getImgContinent() {return imgContinent;}

    public boolean isContainsTilesSolarProject(){
        // permet de savoir si le continent contient des projets de tuiles solaires
        if(!subventions[2].isEmpty()){
            System.out.println("Tuile de projet solaire sur case subvention : " +
                    subventions[2].getName() + ", dans le continent " + name);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Continent{" +
                "name='" + name + '\'' +
                ", subventions=" + Arrays.toString(subventions) +
                ", nbCep=" + nbCep +
                ", imgContinent=" + imgContinent +
                '}';
    }
}
