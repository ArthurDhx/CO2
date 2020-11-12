package CO2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerUnitTest {

    public Player p;

    @Before
    public void setup() {
        p = Player.createPlayer();
    }

    @Test
    public void testExpertiseInit() {
        Assert.assertEquals(0, p.getSolarExpertise());
        Assert.assertEquals(0, p.getBiomassExpertise());
        Assert.assertEquals(0, p.getRecyclingExpertise());
        Assert.assertEquals(0, p.getFusionExpertise());
        Assert.assertEquals(0, p.getReforestationExpertise());
    }

    @Test
    public void testIncrementExpertiseSolaire() {
        p.addExpertise(Player.expertiseId.get("Solar"));
        Assert.assertEquals(1, p.getSolarExpertise());
    }

    @Test
    public void testIncrementExpretiseBiomass() {
        p.addExpertise(Player.expertiseId.get("Biomass"));
        Assert.assertEquals(1, p.getBiomassExpertise());
    }

    @Test
    public void testIncrementExpretiseRecycling() {
        p.addExpertise(Player.expertiseId.get("Recycling"));
        Assert.assertEquals(1, p.getRecyclingExpertise());
    }

    @Test
    public void testIncrementExpretiseFusion() {
        p.addExpertise(Player.expertiseId.get("Fusion"));
        Assert.assertEquals(1, p.getFusionExpertise());
    }

    @Test
    public void testIncrementExpretiseReforestation() {
        p.addExpertise(Player.expertiseId.get("Reforestation"));
        Assert.assertEquals(1, p.getReforestationExpertise());
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
        p.mettreEnPlaceProjet();
        Assert.assertEquals(1,p.getCEP());
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
