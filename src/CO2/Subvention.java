package CO2;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

enum typesSubvention {ARGENT, RESSOURCE, RECHERCHE}

public class Subvention {

    // contient un projet
    private TilesSolarProject tilesSolarProject;
    /*
        false si la subvention a un projet
     */
    private boolean empty = true;
    // si la case est vide = true
    // si la case contient une tuile = false;
    private int index ;
    // L'index ou est placer la subvention sur le continent
    private Continent continent;
    // false si il n'y a pas de scientifique
    // true si il y a un scientifique
    private boolean staffed;

    //Le continent où se situe la subvention
    private typesSubvention type;

    public Subvention(int index, Continent continent, Rectangle[] tabRectangleSubvention){
        this.index = index;
        setType(index);
        this.continent = continent;
        tabRectangleSubvention[index] = new Rectangle(65, 65, Color.WHITE);
        tabRectangleSubvention[index].setStroke(Color.BLACK);
        this.staffed = false;
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

    public int getIndex() {
        return index;
    }

    public Continent getContinent(){ return this.continent;}


    public void setContinent(Continent continent){ this.continent = continent; }

    // ajoute à cette subvention
    public void addTilesSolarProject(TilesSolarProject tilesSolarProject){
        this.tilesSolarProject = tilesSolarProject;
        // la subvention n'est plus vide
        empty = false;
    }

    @Override
    public String toString() {
        if (tilesSolarProject != null && tilesSolarProject.isMisEnPlace())
            return this.continent.getName() +": " + typesCentral.SOLAIRE.toString(); // TODO : Switch avec les autres quand implementer
        return this.continent.getName() +": "+ this.type ;
    }

    public boolean isEmpty() { return empty; }

    public TilesSolarProject getTilesSolarProject() {
        return tilesSolarProject;
    }
    public typesSubvention getType() {
        return type;
    }

    /**
     * Applique l'effet de la subvention en fonction de son type
     * @param currentPLayer le joueur courant
     */
    public typesSubvention effect(Player currentPLayer) {
        switch (type) {
            case ARGENT:
                return typesSubvention.ARGENT;
            case RESSOURCE:
                // Ajoute 2 cubes de ressources technologiques au joueur courant
                currentPLayer.addResourcesTech(2);
                return typesSubvention.RESSOURCE;
            case RECHERCHE:
                return typesSubvention.RECHERCHE;
        }
        return null;
    }

    public boolean isStaffed() {
        return staffed;
    }

    public void setStaffed(boolean staffed) {
        this.staffed = staffed;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
