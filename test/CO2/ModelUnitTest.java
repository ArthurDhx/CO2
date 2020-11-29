package CO2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

public class ModelUnitTest {

    Model model;

    @Before
    public void setup() throws IOException {
        model = new Model();
        model.init();
    }

    @Test
    public void testInitJoueur() {
        Assert.assertEquals(21,model.getCurrentPLayer().getArgent());
    }

    @Test
    public void testIncrementExpertiseJoueurCourant() {
        model.incrementExpertise(GreenEnergyTypes.SOLAR);
        Assert.assertEquals(1, model.getCurrentPLayer().getExpertise(GreenEnergyTypes.SOLAR));
        model.incrementExpertise(GreenEnergyTypes.BIOMASS);
        Assert.assertEquals(1, model.getCurrentPLayer().getExpertise(GreenEnergyTypes.BIOMASS));
        model.incrementExpertise(GreenEnergyTypes.RECYCLING);
        Assert.assertEquals(1, model.getCurrentPLayer().getExpertise(GreenEnergyTypes.RECYCLING));
        model.incrementExpertise(GreenEnergyTypes.FUSION);
        Assert.assertEquals(1, model.getCurrentPLayer().getExpertise(GreenEnergyTypes.FUSION));
        model.incrementExpertise(GreenEnergyTypes.REFORESTATION);
        Assert.assertEquals(1, model.getCurrentPLayer().getExpertise(GreenEnergyTypes.REFORESTATION));
    }

    @Test
    public void testAddTilesSolarProjectToSubventionCase() {
        TilesSolarProject[] t1 = new TilesSolarProject[1];
        t1[0] = new TilesSolarProject();
        // a refaire modification de la methode addTiles...
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

    @Test
    public void testMettreEnPlaceProjet(){
        Player p = model.getCurrentPLayer();
        Continent c = model.getContinents()[0];
        Assert.assertEquals(2,p.getCEP());
        Assert.assertEquals(0,p.getResourcesTech());
        model.mettreEnPlaceProjet(c,c.getSubventions().get(0));
        Assert.assertEquals(1,p.getCEP());
        Assert.assertEquals(3,p.getResourcesTech());
    }

    @Test
    public void testStringToSubject(){
        Subject subject1 = new Subject(GreenEnergyTypes.SOLAR);
        Subject subject2 = new Subject(GreenEnergyTypes.RECYCLING);
        Subject subject3 = new Subject(GreenEnergyTypes.REFORESTATION);
        Subject subject4 = new Subject(GreenEnergyTypes.FUSION);
        Subject subject5 = new Subject(GreenEnergyTypes.BIOMASS);
        Assert.assertEquals(subject1.getEnergy(),model.stringToSubject("Solar").getEnergy());
        Assert.assertEquals(subject2.getEnergy(),model.stringToSubject("Recycling").getEnergy());
        Assert.assertEquals(subject3.getEnergy(),model.stringToSubject("Reforestation").getEnergy());
        Assert.assertEquals(subject4.getEnergy(),model.stringToSubject("Fusion").getEnergy());
        Assert.assertEquals(subject5.getEnergy(),model.stringToSubject("Biomass").getEnergy());
    }

    @Test
    public void testAjoutRevenu() {
        Player p = model.getCurrentPLayer();
        model.giveRevenu(p, "5 pts victoire - 5 argent");
        Assert.assertEquals(5 , p.getPointVictoire());
        Assert.assertEquals(26 , p.getArgent());
    }

    @Test
    public void testControlleContinent() {
        Player p = model.getCurrentPLayer();
        Continent continent = model.getContinents()[2];
        Assert.assertFalse(p.hasControl(continent));
        model.giveControl(continent);
        Assert.assertTrue(p.hasControl(continent));
    }
}
