package CO2;

import javafx.scene.image.Image;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static org.mockito.Matchers.anyInt;

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
    public void testMettreEnPlaceReforestaion() {
        //Reforestaion = 2cep
        Assert.assertEquals(2, p.rewardSetupProject(greenEnergyTypes.REFORESTATION));
    }

    @Test
    public void testMettreEnPlaceSolar() {
        // Solar = 3 ressTech
        Assert.assertEquals(0, p.getResourcesTech());
        p.rewardSetupProject(greenEnergyTypes.SOLAR);
        Assert.assertEquals(3, p.getResourcesTech());
    }

    @Test
    public void testMettreEnPlaceFusion() {
        //Fusion = 1 resstech et 5$
        p.setArgent(0);
        Assert.assertEquals(0, p.getResourcesTech());
        Assert.assertEquals(0, p.getArgent());
        p.rewardSetupProject(greenEnergyTypes.FUSION);
        Assert.assertEquals(1, p.getResourcesTech());
        Assert.assertEquals(5, p.getArgent());
    }

    @Test
    public void testMettreEnPlaceBiomass() {
        //Biomass = 1 ressTech et 3$
        p.setArgent(0);
        Assert.assertEquals(0, p.getResourcesTech());
        Assert.assertEquals(0, p.getArgent());
        p.rewardSetupProject(greenEnergyTypes.BIOMASS);
        Assert.assertEquals(1, p.getResourcesTech());
        Assert.assertEquals(3, p.getArgent());
    }
    @Test
    public void testMettreEnPlaceRecycling() {
        //Recycling 5$ et 1 cep
        p.setArgent(0);
        Assert.assertEquals(0, p.getArgent());
        p.rewardSetupProject(greenEnergyTypes.RECYCLING);
        Assert.assertEquals(5, p.getArgent());
        Assert.assertEquals(1, p.rewardSetupProject(greenEnergyTypes.RECYCLING));
    }

    @Test
    public void testConstructionCentralSolaireImpossible() {
        Project project = new Project(greenEnergyTypes.SOLAR);
        Assert.assertFalse(p.canConstruct(project));
    }

    @Test
    public void testConstructionCentralSolairePossible() {
        Project project = new Project(greenEnergyTypes.SOLAR);

        // necessite : 8€, 2 cubes de ressources technologiques et 2 d'expertise solaire
        p.setArgent(8);
        p.setResourcesTech(2);
        p.addExpertise(greenEnergyTypes.SOLAR, 2);

        Assert.assertTrue(p.canConstruct(project));
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

    @Test
    public void testSetAndGetObjectifCompagnie(){
        ObjectifsCompagnie objectif = new ObjectifsCompagnie(0,"test");
        Assert.assertEquals(p.getObjectifCompagnie(), null);
        p.setObjectifCompagnie(objectif);
        Assert.assertEquals(p.getObjectifCompagnie(), objectif);
    }

    @Test
    public void testGiveLobbyCard() {
        // liste de lobby cards parmis lesquels choisir
        ArrayList<LobbyCard> lobbyCardsList = new ArrayList<>();
        for (greenEnergyTypes type : greenEnergyTypes.values()) {
            lobbyCardsList.add(new LobbyCard<>(lobbyActionTypes.METTRE, type, lobbyMineurTypes.SCIENTIFIQUE));
            lobbyCardsList.add(new LobbyCard<>(lobbyActionTypes.CONSTRUIRE, type, lobbyMineurTypes.ARGENT));
            lobbyCardsList.add(new LobbyCard<>(lobbyActionTypes.SOMMET, type, lobbyMineurTypes.ARGENT));
        }
        ArrayList<LobbyCard> verifList;
        verifList = new ArrayList<>(lobbyCardsList.subList(0,5));

        // choisir les 5 premières cartes lobby de la liste
        // id 0 car les cartes sont retires lorsqu'elles sont tirees
        Random random = Mockito.mock(Random.class);
        Mockito.when(random.nextInt(anyInt())).thenReturn(0,0,0,0,0);
        p.giveLobbyCards(lobbyCardsList, 5, random);

        Assert.assertEquals(verifList, p.getLobbyCards());
    }
}
