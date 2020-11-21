package CO2;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

enum typesCentral {REBOISEMENT, BIOMASSE, SOLAIRE, RECYCLAGE, FUSIONFROIDE, CHARBON, PETROLE, GAZNATUREL}

public class Central {

    // L'index ou est placer la central sur le continent
    private int index ;
    private Continent continent;

    public Central(int index, Continent continent, Rectangle[] tabRectangleCentral){
        this.index = index;
        //setType(type);
        this.continent = continent;
        tabRectangleCentral[index] = new Rectangle(75, 75, Color.WHITE);
        tabRectangleCentral[index].setStroke(Color.BLACK);
    }


    public int getIndex() { return index; }

    public void setIndex(int index) { this.index = index; }

    public Continent getContinent() { return continent; }

}
