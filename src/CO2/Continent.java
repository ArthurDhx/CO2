package CO2;


import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;

public class Continent {

    private String name;
    private final ArrayList<Subvention> subventions = new ArrayList<>(3);
    private Rectangle[] tabRectangleSubvention = new Rectangle[3];
    private int nbCep;
    private Image imgContinent;
    // tuile agenda régionale
    private AgendaTile agendaTile;

    public Continent(String name, int nbCep, Image imgContinent) {
        // initalisation des 3 cases permettant de recevoir des subventions
        for(int i = 0;i<3;i++) {
                subventions.add(new Subvention(i));
                tabRectangleSubvention[i] = new Rectangle(75, 75, Color.WHITE);
                tabRectangleSubvention[i].setStroke(Color.BLACK);
        }
        // initalisation du nom de la subvention
        subventions.get(0).setName("argent");
        subventions.get(1).setName("ressources \ntechnologiques");
        subventions.get(2).setName("recherche en\ncollaboration");
        // initialisation du continent de la subvention
        subventions.get(0).setContinent(this);
        subventions.get(1).setContinent(this);
        subventions.get(2).setContinent(this);
        this.name = name;
        this.nbCep = nbCep;
        this.imgContinent = imgContinent;
    }

    public ArrayList<Subvention> getSubventions() { return subventions; }

    public Rectangle[] getTabRectangleSubvention() { return tabRectangleSubvention; }

    public Image getImgContinent() {return imgContinent;}

    public boolean isContainsTilesSolarProject(){
        // permet de savoir si le continent contient des projets de tuiles solaires
        if(!subventions.get(2).isEmpty()){
            System.out.println("Tuile de projet solaire sur case subvention : " +
                    subventions.get(2).getName() + ", dans le continent " + name);
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void print() {
        System.out.println("Continent{" +
                "name='" + name + '\'' +
                ", subventions=" + subventions.get(0).getTilesSolarProject() + '}');
    }

    public AgendaTile getAgendaTile() {
        return agendaTile;
    }

    public void setAgendaTile(AgendaTile agendaTile) {
        this.agendaTile = agendaTile;
    }
}
