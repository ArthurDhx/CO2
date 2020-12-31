package CO2;

import javafx.scene.image.Image;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AgendaTileUnitTest {

    private AgendaTile agendaTile;

    @Before
    public void setup() {
        agendaTile = new AgendaTile(greenEnergyTypes.SOLAR,
                greenEnergyTypes.BIOMASS,
                greenEnergyTypes.REFORESTATION,
                new Image(getClass().getResourceAsStream("images/Agendas/AgendaTile_SOLAR_BIOMASS_REFORESTATION.png"))
        );
    }

    @Test
    public void testPlacementPossibleAgenda() {
        Assert.assertTrue(agendaTile.isPossiblePlacement(greenEnergyTypes.SOLAR));
        Assert.assertTrue(agendaTile.isPossiblePlacement(greenEnergyTypes.BIOMASS));
        Assert.assertTrue(agendaTile.isPossiblePlacement(greenEnergyTypes.REFORESTATION));
    }

    @Test
    public void testPlacementImpossibleAgenda() {
        Assert.assertFalse(agendaTile.isPossiblePlacement(greenEnergyTypes.FUSION));
        Assert.assertFalse(agendaTile.isPossiblePlacement(greenEnergyTypes.RECYCLING));
    }
}
