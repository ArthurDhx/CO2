package CO2;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Central {

    // L'index ou est placer la central sur le continent
    private int index ;
    private Continent continent;
    private boolean occupe ;
    private centralTypes type ;

    public Central(int index, Continent continent, Rectangle[] tabRectangleCentral){
        this.occupe = false ;
        this.index = index;
        this.continent = continent;
        tabRectangleCentral[index] = new Rectangle(75, 75, Color.WHITE);
        tabRectangleCentral[index].setStroke(Color.BLACK);
    }

    public void setType(centralTypes type) {
        this.type = type;
    }

    public boolean isOccupe() {
        return occupe;
    }

    public void setOccupe(boolean occupe) {
        this.occupe = occupe;
    }

    public int getIndex() { return index; }

    public void setIndex(int index) { this.index = index; }

    public Continent getContinent() { return continent; }

    public centralTypes getType() {
        return type;
    }

    public boolean isFossile(){
        if (type == centralTypes.GAZNATUREL || type == centralTypes.CHARBON || type == centralTypes.PETROLE){
            return true;
        } else {
            return false;
        }
    }
}
