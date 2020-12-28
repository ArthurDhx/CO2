package CO2;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class CentralUnitTest {


    private Central centralePetrole;
    private Central centralCharbon;
    private Central centralGaz;
    private Central centralReboisement;

    @Before
    public void setup() throws IOException {
        Continent continent = new Continent("Europe", 3, new Image(getClass().getResourceAsStream("images/Continents/Europe.jpg")),1);
        Rectangle[] tab = new Rectangle[1];
        centralePetrole = new Central(0,continent,tab);
        centralCharbon = new Central(0,continent,tab);
        centralGaz = new Central(0,continent,tab);
        centralReboisement = new Central(0,continent,tab);
        centralePetrole.setType(centralTypes.PETROLE);
        centralCharbon.setType(centralTypes.CHARBON);
        centralGaz.setType(centralTypes.GAZNATUREL);
        centralReboisement.setType(centralTypes.REBOISEMENT);
    }

    @Test
    public void testIsFossile(){
        Assert.assertTrue(centralePetrole.isFossile());
        Assert.assertTrue(centralCharbon.isFossile());
        Assert.assertTrue(centralGaz.isFossile());
        Assert.assertFalse(centralReboisement.isFossile());
    }

    @Test
    public void testEnergieEgale() {
        Assert.assertTrue(centralReboisement.energyEquals(greenEnergyTypes.REFORESTATION));
        Assert.assertFalse(centralReboisement.energyEquals(greenEnergyTypes.SOLAR));
    }
}
