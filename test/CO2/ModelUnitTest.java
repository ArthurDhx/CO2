package CO2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        model.incrementExpertise(greenEnergyTypes.SOLAR);
        Assert.assertEquals(1, model.getCurrentPLayer().getExpertise(greenEnergyTypes.SOLAR));
        model.incrementExpertise(greenEnergyTypes.BIOMASS);
        Assert.assertEquals(1, model.getCurrentPLayer().getExpertise(greenEnergyTypes.BIOMASS));
        model.incrementExpertise(greenEnergyTypes.RECYCLING);
        Assert.assertEquals(1, model.getCurrentPLayer().getExpertise(greenEnergyTypes.RECYCLING));
        model.incrementExpertise(greenEnergyTypes.FUSION);
        Assert.assertEquals(1, model.getCurrentPLayer().getExpertise(greenEnergyTypes.FUSION));
        model.incrementExpertise(greenEnergyTypes.REFORESTATION);
        Assert.assertEquals(1, model.getCurrentPLayer().getExpertise(greenEnergyTypes.REFORESTATION));
    }

    @Test
    public void testAddTilesSolarProjectToSubventionCase() {
        Project[] t1 = new Project[1];
        t1[0] = new Project(greenEnergyTypes.SOLAR);
        // a refaire modification de la methode addTiles...
        //Assert.assertEquals(model.addTilesSolarProjectToSubventionCase(), t1[0].addOnSubvention());
    }

    @Test
    public void testInitContinentAgendaTile() {
        // test si les continents ont bien l'unique agendaTiles ["Reforesting", "Solar", "Fusion"]
        List<greenEnergyTypes> listEnergies = new ArrayList<>();
        listEnergies.add(greenEnergyTypes.REFORESTATION);
        listEnergies.add(greenEnergyTypes.SOLAR);
        listEnergies.add(greenEnergyTypes.FUSION);
        Assert.assertEquals(listEnergies, model.getContinents()[0].getAgendaTile().getEnergies());
    }

    @Test
    public void testPlacementPossibleTuileSolaireSurContinent() {
        // tout les continents on l'agendaTiles ["Reforesting", "Solar", "Fusion"] dans ce sprint
        Assert.assertTrue(model.getContinents()[0].getAgendaTile().isPossiblePlacement(greenEnergyTypes.SOLAR));
    }

    @Test
    public void testPlacementImpossibleTuileRecyclageSurContinent() {
        // tout les continents on l'agendaTiles ["Reforesting", "Solar", "Fusion"] dans ce sprint
        Assert.assertFalse(model.getContinents()[0].getAgendaTile().isPossiblePlacement(greenEnergyTypes.RECYCLING));
    }

    @Test
    public void testMettreEnPlaceProjet(){
        Player p = model.getCurrentPLayer();
        Continent c = model.getContinents()[0];
        Assert.assertEquals(2,p.getCEP());
        Assert.assertEquals(0,p.getResourcesTech());
        model.mettreEnPlaceProjetByPlayer(greenEnergyTypes.SOLAR,c.getSubventions().get(0));
        Assert.assertEquals(1,p.getCEP());
        Assert.assertEquals(3,p.getResourcesTech());
    }

    @Test
    public void testStringToSubject(){
        Subject subject1 = new Subject(greenEnergyTypes.SOLAR);
        Subject subject2 = new Subject(greenEnergyTypes.RECYCLING);
        Subject subject3 = new Subject(greenEnergyTypes.REFORESTATION);
        Subject subject4 = new Subject(greenEnergyTypes.FUSION);
        Subject subject5 = new Subject(greenEnergyTypes.BIOMASS);
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

    @Test
    public void testDonne5CartesLobbyAuJoueur() {
        Player p = model.getCurrentPLayer();
        Assert.assertEquals(5, p.getLobbyCards().size());
    }
}
