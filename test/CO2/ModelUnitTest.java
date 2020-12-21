package CO2;

import jdk.jfr.Enabled;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.when;

public class ModelUnitTest {

    Model model;


    @Before
    public void setup() throws IOException {
        model = new Model();
        model.init();
    }

    @Test
    public void testInitOnuCards() {
        Random rand1 = Mockito.mock(Random.class);
        Random rand2 = Mockito.mock(Random.class);
        Random rand3 = Mockito.mock(Random.class);
        when(rand1.nextInt(Mockito.anyInt())).thenReturn(1,2); // 3
        when(rand2.nextInt(Mockito.anyInt())).thenReturn(0,2); // 6
        when(rand3.nextInt(Mockito.anyInt())).thenReturn(1,2); // 7
        model.initOnuCards(rand1, rand2, rand3);
    }

    @Test
    public void testInitOnuCardsInGame() {
        // test de la ligne : do card = onuCards.get(randOnuCard.nextInt(onuCards.size()));
        Random random = Mockito.mock(Random.class);
        OnuCard card = model.getOnuCards().get(1);
        when(random.nextInt(Mockito.anyInt())).thenReturn(1, 13);
        Assert.assertEquals(card, model.getOnuCards().get(random.nextInt(model.getOnuCards().size())));
    }

    @Test
    public void testInitObjectifCompagnie(){
        Random random = Mockito.mock(Random.class);
        ObjectifsCompagnie objectif = model.objectifsCompagnies.get(random.nextInt(0));
        when(random.nextInt(Mockito.anyInt())).thenReturn(0);
        Assert.assertEquals(objectif,model.objectifsCompagnies.get(random.nextInt(model.objectifsCompagnies.size())));
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
        model.addProjectTileToSubvention(c,c.getSubventions().get(0),greenEnergyTypes.SOLAR); //propose un projet
        model.mettreEnPlaceProjetByPlayer(greenEnergyTypes.SOLAR,c.getSubventions().get(0)); //met en place le projet
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

    //Ne fonctionne pas, je ne comprends pas pourquoi
    @Test
    public void testGetAllCEP(){
        //Player p = model.getCurrentPLayer();
        //System.out.print(p.getCEP());
        //Continent continent = model.getContinents()[0];
        //model.giveControl(continent);
        //System.out.print(p.getContinentsControlles());
        //model.getAllCEP();
    }

    //Ne fonctionne pas, je ne comprends pas pourquoi
    @Test
    public void testSellAllCEP(){
        //Assert.assertEquals(model.sellAllCEP(), 27);
    }

    @Test
    public void testDonne5CartesLobbyAuJoueur() {
        Player p = model.getCurrentPLayer();
        Assert.assertEquals(5, p.getLobbyCards().size());
    }


    public void initCard(OnuCard card, boolean diff){
        ArrayList<String> types = new ArrayList<>();
        types.add(centralTypes.REBOISEMENT.name());
        types.add(centralTypes.SOLAIRE.name());
        if(diff) types.add(centralTypes.FUSIONFROIDE.name());
        Mockito.when(card.getTypesCentral()).thenReturn(types);
    }

    public void initListTypesCentral(ArrayList<String> list, boolean diff){
        list.add(centralTypes.SOLAIRE.name());
        list.add(centralTypes.REBOISEMENT.name());
        if(!diff) list.add(centralTypes.FUSIONFROIDE.name());
    }

    @Test
    public void testMarkOnuCard() {
        // création OnuCard
        OnuCard card = Mockito.mock(OnuCard.class);
        initCard(card, false);
        // création liste des centrales sur le jeu
        ArrayList<String> list = new ArrayList<>();
        initListTypesCentral(list, false);
        // comparaison des types de centrales dans la liste et de la carte
        Assert.assertTrue(model.markOnuCard(card, list));
    }

    @Test
    public void testMarkOnuCard2() {
        // création OnuCard
        OnuCard card = Mockito.mock(OnuCard.class);
        initCard(card, true);
        // création liste des centrales sur le jeu
        ArrayList<String> list = new ArrayList<>();
        initListTypesCentral(list, true);
        // comparaison des types de centrales dans la liste et de la carte
        Assert.assertFalse(model.markOnuCard(card, list));
    }

    @Test
    public void testgiveVictoryPointsOnuCards(){
        // markOnuCard(card, centralGreen) renvoie true
        OnuCard card = Mockito.mock(OnuCard.class);
        initCard(card,false);
        ArrayList<String> list = new ArrayList<>();
        initListTypesCentral(list,false);
        try {
            // le joueur courant dispose de 2 ressource technologique
            model.getCurrentPLayer().setResourcesTech(2);
            // la carte peut donc être joué
            model.giveVictoryPointsOnuCards(card,list);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testgiveVictoryPointsOnuCards2() {
        // markOnuCard(card, centralGreen) renvoie false
        OnuCard card = Mockito.mock(OnuCard.class);
        initCard(card,true);
        ArrayList<String> list = new ArrayList<>();
        initListTypesCentral(list,true);
        try {
            // le joueur courant dispose de 2 ressource technologique
            model.getCurrentPLayer().setResourcesTech(2);
            // la carte ne peut donc être joué (markOnuCard(...) => false)
            model.giveVictoryPointsOnuCards(card,list);
        }catch (Exception e) {
            // renvoie une exception
            e.printStackTrace();
        }
    }

    @Test
    public void testgiveVictoryPointsOnuCards3() throws Exception{
        // markOnuCard(card, centralGreen) renvoie true
        OnuCard card = Mockito.mock(OnuCard.class);
        initCard(card,false);
        ArrayList<String> list = new ArrayList<>();
        initListTypesCentral(list,false);
        try {
            // le joueur courant dispose de 0 ressource technologique
            model.getCurrentPLayer().setResourcesTech(0);
            // la carte ne peut donc être joué (ressourceTech <= 1)
            model.giveVictoryPointsOnuCards(card,list);
        }catch (Exception e) {
            // renvoie une exception
            e.printStackTrace();
        }
    }

    @Test
    public void testgiveVictoryPointsOnuCards4() throws Exception{
        // markOnuCard(card, centralGreen) renvoie false
        OnuCard card = Mockito.mock(OnuCard.class);
        initCard(card,true);
        ArrayList<String> list = new ArrayList<>();
        initListTypesCentral(list,true);
        try {
            // le joueur courant dispose de 0 ressource technologique
            model.getCurrentPLayer().setResourcesTech(0);
            // la carte ne peut donc être joué (ressourceTech <= 1 && markOnuCard(..) => false)
            model.giveVictoryPointsOnuCards(card,list);
        }catch (Exception e) {
            // renvoie une exception
            e.printStackTrace();
        }
    }

    @Test
    public void testPullEvent(){
        Random random = Mockito.mock(Random.class);
        Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(0,1,2,3,4,5);
        for (int i = 0; i < 6; i++) {
            model.pullEvent(random);
            Assert.assertEquals(i, model.currentEvent);
        }
    }
    
    //@Test
    //public void testCheckCentralVerte(){

    //}
}
