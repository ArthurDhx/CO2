package CO2;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import org.junit.Assert;
import org.junit.Test;

public class CentralUnitTest {

    @Test
    public void testIsFossile(){
        Continent continent = new Continent("Europe", 3, new Image(getClass().getResourceAsStream("images/Continents/Europe.jpg")),1);
        Rectangle[] tab = new Rectangle[1];
        Central central0 = new Central(0,continent,tab);
        Central central1 = new Central(0,continent,tab);
        Central central2 = new Central(0,continent,tab);
        Central central3 = new Central(0,continent,tab);
        central0.setType(centralTypes.PETROLE);
        central1.setType(centralTypes.CHARBON);
        central2.setType(centralTypes.GAZNATUREL);
        central3.setType(centralTypes.REBOISEMENT);

        Assert.assertTrue(central0.isFossile());
        Assert.assertTrue(central1.isFossile());
        Assert.assertTrue(central2.isFossile());
        Assert.assertFalse(central3.isFossile());
    }
}
