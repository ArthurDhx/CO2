package CO2;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
enum typesSubvention {ARGENT, RESSOURCE, RECHERCHE}

public class Subvention {

    private String name;
    // l'effet produit par la subvention
    private String effect;
    // peut contenir une tuile pour un projet solaire
    private TilesSolarProject tilesSolarProject;
    private boolean empty = true;
    // si la case est vide = true
    // si la case contient une tuile = false;
    private int index ;
    // L'index ou est placer la subvention sur le continent
    private Continent continent;
    //Le continent où se situe la subvention
    private typesSubvention type;

    public Subvention(int index, Continent continent, Rectangle[] tabRectangleSubvention){
        setType(index);
        this.continent = continent;
        tabRectangleSubvention[index] = new Rectangle(75, 75, Color.WHITE);
        tabRectangleSubvention[index].setStroke(Color.BLACK);
    }

    private void setType(int index){
        switch(index){
            case 0:
                type = typesSubvention.ARGENT;
                break;
            case 1:
                type = typesSubvention.RESSOURCE;
                break;
            case 2:
                type = typesSubvention.RECHERCHE;
                break;
        }
    }

    public Subvention(int index) {
        this.index = index ;
    }

    public String getName() { return name; }

    public int getIndex() {
        return index;
    }

    public Continent getContinent(){ return this.continent;}

    public void setName(String name) {
        this.name = name;
    }

    public void setContinent(Continent continent){ this.continent = continent; }

    // ajoute à cette subvention
    public void addTilesSolarProject(TilesSolarProject tilesSolarProject){
        this.tilesSolarProject = tilesSolarProject;
        // la subvention n'est plus vide
        empty = false;
    }

    @Override
    public String toString() {
        return this.continent.getName() +": "+ this.name ;
    }

    public boolean isEmpty() { return empty; }

    public TilesSolarProject getTilesSolarProject() {
        return tilesSolarProject;
    }
}
