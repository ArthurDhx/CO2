package CO2;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LobbyCardUnitTest {

    private Model model;
    private Continent continent;
    private greenEnergyTypes energyType;

    @Before
    public void setup() throws IOException {
        model = new Model();
        model.init();
        continent = model.getContinents()[0];
        energyType = greenEnergyTypes.SOLAR;
    }

    @Test
    public void testCarteLobbyVerfifProposerSurContinent() {
        LobbyCard<Continent> card = new LobbyCard(lobbyActionTypes.PROPOSER, continent, lobbyMineurTypes.ARGENT);

        // ne peut pas jouer la carte
        Assert.assertFalse(model.playLobbyCard(card, true));
        // met les bonnes conditions
        continent.getSubventions().get(0).setProject(new Project(greenEnergyTypes.SOLAR));
        // peut jouer la carte
        Assert.assertTrue(model.playLobbyCard(card, true));
    }

    @Test
    public void testCarteLobbyVerfifProposerSurSubvention() {
        subventionTypes subvention = subventionTypes.ARGENT;
        LobbyCard<Continent> card = new LobbyCard(lobbyActionTypes.PROPOSER, subvention, lobbyMineurTypes.ARGENT);

        // ne peut pas jouer la carte
        Assert.assertFalse(model.playLobbyCard(card, true));
        // met les bonnes conditions
        // sub[0] du continent = ARGENT
        continent.getSubventions().get(0).setProject(new Project(greenEnergyTypes.SOLAR));
        // peut jouer la carte
        Assert.assertTrue(model.playLobbyCard(card, true));
    }

    @Test
    public void testCarteLobbyVerfifMettreSolaire() {
        LobbyCard<Continent> card = new LobbyCard(lobbyActionTypes.METTRE, energyType, lobbyMineurTypes.ARGENT);

        // ne peut pas jouer la carte
        Assert.assertFalse(model.playLobbyCard(card, true));
        // met les bonnes conditions
        continent.getSubventions().get(0).setProject(new Project(greenEnergyTypes.SOLAR));
        continent.getSubventions().get(0).getProject().setMisEnPlace(true);
        // peut jouer la carte
        Assert.assertTrue(model.playLobbyCard(card, true));
    }

    @Test
    public void testCarteLobbyVerfifConstruireSolaire() {
        LobbyCard<Continent> card = new LobbyCard(lobbyActionTypes.METTRE, energyType, lobbyMineurTypes.ARGENT);

        // ne peut pas jouer la carte
        Assert.assertFalse(model.playLobbyCard(card, true));
        // met les bonnes conditions
        continent.getSubventions().get(0).setProject(new Project(greenEnergyTypes.SOLAR));
        continent.getSubventions().get(0).getProject().setMisEnPlace(true);
        model.putCentral(continent.getSubventions().get(0));
        // peut jouer la carte
        Assert.assertTrue(model.playLobbyCard(card, true));
    }

    @Test
    public void testCarteLobbyVerfifSommetSolaire() {
        LobbyCard<Continent> card = new LobbyCard(lobbyActionTypes.SOMMET, energyType, lobbyMineurTypes.ARGENT);

        // ne peut pas jouer la carte
        Assert.assertFalse(model.playLobbyCard(card, true));

        // met les bonnes conditions
        ArrayList<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject(energyType));
        subjects.add(new Subject(greenEnergyTypes.RECYCLING));
        subjects.add(new Subject(greenEnergyTypes.BIOMASS));
        SommetTile sommet = new SommetTile("Sommet",
                continent,
                subjects.size(),
                subjects,
                new ImageView(new Image(getClass().getResourceAsStream("images/Sommets/Paris.png"))));
        sommet.setStaffedOnEnergy(subjects.get(0));
        model.setAllSommetTile(Collections.singletonList(sommet));

        // peut jouer la carte
        Assert.assertTrue(model.playLobbyCard(card, true));
    }
}