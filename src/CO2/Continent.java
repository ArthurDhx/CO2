package CO2;


import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;

public class Continent {

    private final String name;
    private final ArrayList<Subvention> subventions;
    private final Rectangle[] tabRectangleSubvention;
    private int nbCep;
    private final ArrayList<Central> centrales;
    private final Rectangle[] tabRectangleCentral;
    private final Image imgContinent;
    // tuile agenda régionale
    private AgendaTile agendaTile;
    // Tuile sommet
    private SommetTile sommetTile;


    public Continent(String name, int nbCep, Image imgContinent) {
        this.name = name;
        this.nbCep = nbCep;
        this.imgContinent = imgContinent;
        this.subventions = new ArrayList<>(3);
        this.centrales = new ArrayList<>();
        this.tabRectangleCentral = new Rectangle[nbCep];
        this.tabRectangleSubvention = new Rectangle[3];
        for(int i = 0;i<3;i++) {
                subventions.add(new Subvention(i,this,tabRectangleSubvention));
        }
        for(int i = 0;i<nbCep;i++) {
            centrales.add(new Central(i,this,tabRectangleCentral));
        }
    }

    public int getNbCep() { return nbCep; }

    public ArrayList<Subvention> getSubventions() { return subventions; }

    public ArrayList<Subvention> getEmptySubventions() {
        ArrayList<Subvention> freeSubvention = new ArrayList<Subvention>();
        for (Subvention subvention : subventions) {
            if (subvention.isEmpty()) freeSubvention.add(subvention);
        }
        return freeSubvention ;
    }

    public Rectangle[] getTabRectangleSubvention() { return tabRectangleSubvention; }

    public Rectangle[] getTabRectangleCentral() { return tabRectangleCentral; }

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

    public ArrayList<Central> getCentrales() {
        return centrales;
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
                "name=" + name + ", nbCep=" + nbCep +
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
