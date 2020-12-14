package CO2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class PlayerUnitTest {

    public Player p;

    @Before
    public void setup() {
        p = new Player();
    }

    @Test
    public void testExpertiseInit() {
        Assert.assertEquals(0, p.getExpertise(greenEnergyTypes.SOLAR));
        Assert.assertEquals(0, p.getExpertise(greenEnergyTypes.BIOMASS));
        Assert.assertEquals(0, p.getExpertise(greenEnergyTypes.RECYCLING));
        Assert.assertEquals(0, p.getExpertise(greenEnergyTypes.FUSION));
        Assert.assertEquals(0, p.getExpertise(greenEnergyTypes.REFORESTATION));
    }

    @Test
    public void testIncrementExpertiseSolaire() {
        p.addExpertise(greenEnergyTypes.SOLAR, 1);
        Assert.assertEquals(1, p.getExpertise(greenEnergyTypes.SOLAR));
    }

    @Test
    public void testIncrementExpretiseBiomass() {
        p.addExpertise(greenEnergyTypes.BIOMASS, 1);
        Assert.assertEquals(1, p.getExpertise(greenEnergyTypes.BIOMASS));
    }

    @Test
    public void testIncrementExpretiseRecycling() {
        p.addExpertise(greenEnergyTypes.RECYCLING, 1);
        Assert.assertEquals(1, p.getExpertise(greenEnergyTypes.RECYCLING));
    }

    @Test
    public void testIncrementExpretiseFusion() {
        p.addExpertise(greenEnergyTypes.FUSION, 1);
        Assert.assertEquals(1, p.getExpertise(greenEnergyTypes.FUSION));
    }

    @Test
    public void testIncrementExpretiseReforestation() {
        p.addExpertise(greenEnergyTypes.REFORESTATION, 1);
        Assert.assertEquals(1, p.getExpertise(greenEnergyTypes.REFORESTATION));
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
        p.rewardSetupProject(greenEnergyTypes.SOLAR);
        Assert.assertEquals(1,p.getCEP());
    }
    
    @Test
    public void testMettreEnPlaceSolar() {
        Assert.assertEquals(0, p.getResourcesTech());
        p.rewardSetupProject(greenEnergyTypes.SOLAR);
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

    @Test
    public void testAjoutRevenu() {
        Assert.assertEquals(0 , p.getPointVictoire());
        Assert.assertEquals(21 , p.getArgent());
        p.giveRevenu(new int[]{5, 5});
        Assert.assertEquals(5 , p.getPointVictoire());
        Assert.assertEquals(26 , p.getArgent());
    }
    
    @Test
    public void testInitLobbyCards() throws IOException {
        Assert.assertEquals(null, p.getLobbyCards());
        Model model = new Model();
        model.init();
        Assert.assertEquals(5, model.getCurrentPLayer().getLobbyCards().size());
    }

    @Test
    public void testPlayLobbyCardRetireLaCarteDeLaMain() throws IOException {
        Model model = new Model();
        model.init();
        p = model.getCurrentPLayer();
        Assert.assertEquals(5, model.getCurrentPLayer().getLobbyCards().size());
        p.playLobbyCard(p.getLobbyCards().get(0));
        Assert.assertEquals(4, model.getCurrentPLayer().getLobbyCards().size());
    }
}
