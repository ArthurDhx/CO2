package CO2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerUnitTest {

    public Player p;

    @Before
    public void setup() {
        p = new Player();
    }

    @Test
    public void testExpertiseInit() {
        Assert.assertEquals(0, p.getExpertise(GreenEnergyTypes.SOLAR));
        Assert.assertEquals(0, p.getExpertise(GreenEnergyTypes.BIOMASS));
        Assert.assertEquals(0, p.getExpertise(GreenEnergyTypes.RECYCLING));
        Assert.assertEquals(0, p.getExpertise(GreenEnergyTypes.FUSION));
        Assert.assertEquals(0, p.getExpertise(GreenEnergyTypes.REFORESTATION));
    }

    @Test
    public void testIncrementExpertiseSolaire() {
        p.addExpertise(GreenEnergyTypes.SOLAR, 1);
        Assert.assertEquals(1, p.getExpertise(GreenEnergyTypes.SOLAR));
    }

    @Test
    public void testIncrementExpretiseBiomass() {
        p.addExpertise(GreenEnergyTypes.BIOMASS, 1);
        Assert.assertEquals(1, p.getExpertise(GreenEnergyTypes.BIOMASS));
    }

    @Test
    public void testIncrementExpretiseRecycling() {
        p.addExpertise(GreenEnergyTypes.RECYCLING, 1);
        Assert.assertEquals(1, p.getExpertise(GreenEnergyTypes.RECYCLING));
    }

    @Test
    public void testIncrementExpretiseFusion() {
        p.addExpertise(GreenEnergyTypes.FUSION, 1);
        Assert.assertEquals(1, p.getExpertise(GreenEnergyTypes.FUSION));
    }

    @Test
    public void testIncrementExpretiseReforestation() {
        p.addExpertise(GreenEnergyTypes.REFORESTATION, 1);
        Assert.assertEquals(1, p.getExpertise(GreenEnergyTypes.REFORESTATION));
    }

    @Test
    public void testGainArgent() {
        p.setArgent(100);
        p.gainArgent(50);
        Assert.assertEquals(150, p.getArgent());
    }

    @Test
    public void testRetraitArgent1() {
        p.setArgent(100);
        Assert.assertTrue(p.retirerArgent(50));
        Assert.assertEquals(50, p.getArgent());
    }

    @Test
    public void testRetraitArgent2() {
        p.setArgent(100);
        Assert.assertFalse(p.retirerArgent(120));
        Assert.assertEquals(100, p.getArgent());
    }

    @Test
    public void testMettreEnPlaceProjet(){
        Assert.assertEquals(2,p.getCEP());
        p.rewardSetupProject(GreenEnergyTypes.SOLAR);
        Assert.assertEquals(1,p.getCEP());
    }
    
    @Test
    public void testMettreEnPlaceSolar() {
        Assert.assertEquals(0, p.getResourcesTech());
        p.rewardSetupProject(GreenEnergyTypes.SOLAR);
        Assert.assertEquals(3, p.getResourcesTech());
    }

    @Test
    public void testIncrementRessourcesTech() {
        Assert.assertEquals(0, p.getResourcesTech());
        p.addResourcesTech(5);
        Assert.assertEquals(5, p.getResourcesTech());
    }

    @Test
    public void testDecrementRessourcesTech() {
        Assert.assertEquals(0, p.getResourcesTech());
        p.addResourcesTech(5);
        p.removeResourcesTech(3);
        Assert.assertEquals(2, p.getResourcesTech());
    }

    @Test
    public void testDecrementImpossible() {
        p.addResourcesTech(5);
        boolean ret = p.removeResourcesTech(10);
        Assert.assertFalse(ret);
        Assert.assertEquals(5, p.getResourcesTech());
    }
}
