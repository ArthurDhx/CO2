package CO2;


import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;

public class Continent {

    private final String name;
    private final ArrayList<Subvention> subventions = new ArrayList<>(3);
    private final Rectangle[] tabRectangleSubvention = new Rectangle[3];
    private int nbCep;
    private final Image imgContinent;
    // tuile agenda régionale
    private AgendaTile agendaTile;
    // Tuile sommet
    private SommetTile sommetTile;

    public Continent(String name, int nbCep, Image imgContinent) {
        /* initalisation des 3 cases permettant de recevoir des subventions
            subventions [0] = argent
            subventions [1] = ressources
            subventions [2] = recherche
        */
        for(int i = 0;i<3;i++) {
                subventions.add(new Subvention(i,this,tabRectangleSubvention));
        }
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
                    subventions.get(2).getType() + ", dans le continent " + name);
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

    public SommetTile getSommetTile() {
        return sommetTile;
    }

    public void setSommetTile(SommetTile sommetTile) {
        this.sommetTile = sommetTile;
    }
}
