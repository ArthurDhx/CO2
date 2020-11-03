package CO2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ModelUnitTest {

    Model model;

    @Before
    public void setup() {
        model = new Model();
        model.init();
    }

    @Test
    public void testIncrementExpertiseJoueurCourant() {
        model.incrementExpertise(Player.expertiseId.get("Solar"));
        Assert.assertEquals(1, model.getCurentPLayer().getSolarExpertise());
        model.incrementExpertise(Player.expertiseId.get("Biomass"));
        Assert.assertEquals(1, model.getCurentPLayer().getBiomassExpertise());
        model.incrementExpertise(Player.expertiseId.get("Recycling"));
        Assert.assertEquals(1, model.getCurentPLayer().getRecyclingExpertise());
        model.incrementExpertise(Player.expertiseId.get("Fusion"));
        Assert.assertEquals(1, model.getCurentPLayer().getFusionExpertise());
        model.incrementExpertise(Player.expertiseId.get("Reforestation"));
        Assert.assertEquals(1, model.getCurentPLayer().getReforestationExpertise());
    }

    @Test
    public void testAddTilesSolarProjectToSubventionCase() {
        TilesSolarProject[] t1 = new TilesSolarProject[1];
        t1[0] = new TilesSolarProject();
        //Assert.assertEquals(model.addTilesSolarProjectToSubventionCase(), t1[0].addOnSubvention());
    }

    @Test
    public void testInitContinentAgendaTile() {
        // test si les continents ont bien l'unique agendaTiles ["Reforesting", "Solar", "Fusion"]
        String[] tabEnergies = {"Reforesting", "Solar", "Fusion"};
        Assert.assertEquals(tabEnergies, model.getContinents()[0].getAgendaTile().getEnergies());
    }

    @Test
    public void testPlacementPossibleTuileSolaireSurContinent() {
        // tout les continents on l'agendaTiles ["Reforesting", "Solar", "Fusion"] dans ce sprint
        Assert.assertTrue(model.getContinents()[0].getAgendaTile().isPossiblePlacement("Solar"));
    }

    @Test
    public void testPlacementImpossibleTuileRecyclageSurContinent() {
        // tout les continents on l'agendaTiles ["Reforesting", "Solar", "Fusion"] dans ce sprint
        Assert.assertFalse(model.getContinents()[0].getAgendaTile().isPossiblePlacement("Recycling"));
    }

}
