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
    private Central centralFusion;
    private Central centralBiomasse;
    private Central centralRecyclage;
    private Central centralSolaire;

    @Before
    public void setup() throws IOException {
        Continent continent = new Continent("Europe", 3, new Image(getClass().getResourceAsStream("images/Continents/Europe.jpg")),1);
        Rectangle[] tab = new Rectangle[1];

        centralePetrole = new Central(0,continent,tab);
        centralePetrole.setType(centralTypes.PETROLE);
        centralCharbon = new Central(0,continent,tab);
        centralCharbon.setType(centralTypes.CHARBON);
        centralGaz = new Central(0,continent,tab);
        centralGaz.setType(centralTypes.GAZNATUREL);

        centralReboisement = new Central(0,continent,tab);
        centralReboisement.setType(centralTypes.REBOISEMENT);
        centralFusion = new Central(0,continent,tab);
        centralFusion.setType(centralTypes.FUSIONFROIDE);
        centralBiomasse = new Central(0,continent,tab);
        centralBiomasse.setType(centralTypes.BIOMASSE);
        centralRecyclage = new Central(0,continent,tab);
        centralRecyclage.setType(centralTypes.RECYCLAGE);
        centralSolaire = new Central(0,continent,tab);
        centralSolaire.setType(centralTypes.SOLAIRE);
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
        Assert.assertTrue(centralBiomasse.energyEquals(greenEnergyTypes.BIOMASS));
        Assert.assertTrue(centralFusion.energyEquals(greenEnergyTypes.FUSION));
        Assert.assertTrue(centralReboisement.energyEquals(greenEnergyTypes.REFORESTATION));
        Assert.assertTrue(centralRecyclage.energyEquals(greenEnergyTypes.RECYCLING));
        Assert.assertTrue(centralSolaire.energyEquals(greenEnergyTypes.SOLAR));
    }
}
