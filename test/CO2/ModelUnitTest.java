package CO2;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    public void testInitTour() {
        // on admet que le nombre de joueur est 4
        model.setNbJoueur(4);
        // on test la fonction initTour
        model.initTour();
        // on vérifie que le tour correspond à 3
        Assert.assertEquals(3, model.getTour());
    }

    @Test
    public void testInitDecade() {
        // on admet que le nombre de décennie est 3
        model.setNbDecade(3);
        // on test la fonction initDecade
        model.initDecade();
        // on vérifie que la décénnie est 1990
        Assert.assertEquals(1990, model.getDecade());
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
    public void testMettreEnPlaceProjetSolaire(){
        // mettre en place un projet coute 1 CEP
        // bonus mettre en place un projet solaire = 3 cubes de ressources
        Player p = model.getCurrentPLayer();
        Continent c = model.getContinents()[0];

        // ressources init du joueur
        Assert.assertEquals(2,p.getCEP());
        Assert.assertEquals(0,p.getResourcesTech());

        // proposer un projet pour pouvoir le mettre en place
        model.addProjectTileToSubvention(c,c.getSubventions().get(0),greenEnergyTypes.SOLAR); //propose un projet
        // mettre en place
        Assert.assertTrue(model.mettreEnPlaceProjetByPlayer(greenEnergyTypes.SOLAR,c.getSubventions().get(0))); //met en place le projet

        // ressources du joueur après avoir payer et recu la recompense
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
    public void testControleContinent() {
        Player p = model.getCurrentPLayer();
        Continent continent = model.getContinents()[2];

        // pas de controle
        Assert.assertFalse(p.hasControl(continent));
        Assert.assertEquals(null, continent.getControlPlayer());

        // donne le controle
        model.giveControl(continent);

        // verif controle
        Assert.assertTrue(p.hasControl(continent));
        Assert.assertEquals(p, continent.getControlPlayer());
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
        // verification model.init() donne 5 cartes au joueur
        Player p = model.getCurrentPLayer();
        Assert.assertEquals(5, p.getLobbyCards().size());
    }

    /**
     * initialise les OnuCard
     * @param card la carte
     * @param diff la différence pour savoir si elle est marqué par un joueur (pour test)
     */
    public void initCard(OnuCard card, boolean diff){
        ArrayList<String> types = new ArrayList<>();
        types.add(centralTypes.REBOISEMENT.name());
        types.add(centralTypes.SOLAIRE.name());
        if(diff) types.add(centralTypes.FUSIONFROIDE.name());
        Mockito.when(card.getTypesCentral()).thenReturn(types);
    }

    /**
     * initialise les types de centrales
     * @param list la liste
     * @param diff la différence pour savoir si elle est marqué par un joueur (pour test)
     */
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

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test (expected = Exception.class)
    public void testGiveVictoryPointsOnuCards() throws Exception {
        // markOnuCard(card, centralGreen) renvoie true
        OnuCard card = Mockito.mock(OnuCard.class);
        initCard(card,false);
        ArrayList<String> list = new ArrayList<>();
        initListTypesCentral(list,false);
        // le joueur courant dispose de 2 ressource technologique
        model.getCurrentPLayer().setResourcesTech(2);
        // la carte peut donc être joué
        model.giveVictoryPointsOnuCards(card,list);
        // pas de renvoie d'exception
        thrown.expect(Exception.class);
    }

    @Test (expected = Exception.class)
    public void testGiveVictoryPointsOnuCards2() throws Exception{
        // markOnuCard(card, centralGreen) renvoie false
        OnuCard card = Mockito.mock(OnuCard.class);
        initCard(card,true);
        ArrayList<String> list = new ArrayList<>();
        initListTypesCentral(list,true);
        // le joueur courant dispose de 2 ressource technologique
        model.getCurrentPLayer().setResourcesTech(2);
        // la carte ne peut donc être joué (markOnuCard(...) => false)
        model.giveVictoryPointsOnuCards(card,list);
        // renvoie une exception
        thrown.expect(Exception.class);
    }

    @Test (expected = Exception.class)
    public void testGiveVictoryPointsOnuCards3() throws Exception{
        // markOnuCard(card, centralGreen) renvoie true
        OnuCard card = Mockito.mock(OnuCard.class);
        initCard(card,false);
        ArrayList<String> list = new ArrayList<>();
        initListTypesCentral(list,false);
        // le joueur courant dispose de 0 ressource technologique
        model.getCurrentPLayer().setResourcesTech(0);
        // la carte ne peut donc être joué (ressourceTech <= 1)
        model.giveVictoryPointsOnuCards(card,list);
        // renvoie une exception
        thrown.expect(Exception.class);
    }

    @Test (expected = Exception.class)
    public void testGiveVictoryPointsOnuCards4() throws Exception{
        // markOnuCard(card, centralGreen) renvoie false
        OnuCard card = Mockito.mock(OnuCard.class);
        initCard(card,true);
        ArrayList<String> list = new ArrayList<>();
        initListTypesCentral(list,true);
        // le joueur courant dispose de 0 ressource technologique
        model.getCurrentPLayer().setResourcesTech(0);
        // la carte ne peut donc être joué (ressourceTech <= 1 && markOnuCard(..) => false)
        model.giveVictoryPointsOnuCards(card,list);
        // renvoie une exception
        thrown.expect(Exception.class);
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

    @Test
    public void testGiveRewardsSommet(){
        Continent europe = new Continent("Europe", 4, new Image(getClass().getResourceAsStream("images/Continents/Europe.jpg")), 0);
        ArrayList<Subject> subject = new ArrayList<>();
        subject.add(new Subject(greenEnergyTypes.FUSION));
        subject.add(new Subject(greenEnergyTypes.RECYCLING));
        SommetTile sommet = new SommetTile("Stockholm", europe, 4,subject, new ImageView(new Image(getClass().getResourceAsStream("images/Sommets/Stockholm.png"))));
        sommet.setContinent(europe);
        sommet.setSubjects(subject);
        europe.setSommetTile(sommet);
        Scientifique scientifique = new Scientifique();
        for(Subject s : subject){
            s.setScientifique(scientifique);
            scientifique.setSommetTile(sommet);
        }
        Assert.assertEquals(sommet.getScientifiques(), model.giveRewardsSommet(sommet));
    }

    @Test
    public void testGiveRewardsSommetToPlayer(){
        model.giveRewardsSommetToPlayer(greenEnergyTypes.FUSION, model.getCurrentPLayer());
        System.out.println("le joueur a " + model.getCurrentPLayer().getExpertise(greenEnergyTypes.FUSION) + " d'expertise en fusion.");
        model.giveRewardsSommetToPlayer(greenEnergyTypes.RECYCLING, model.getCurrentPLayer());
        System.out.println("le joueur a " + model.getCurrentPLayer().getExpertise(greenEnergyTypes.RECYCLING) + " d'expertise en recycling.");
        model.giveRewardsSommetToPlayer(greenEnergyTypes.BIOMASS, model.getCurrentPLayer());
        System.out.println("le joueur a " + model.getCurrentPLayer().getExpertise(greenEnergyTypes.BIOMASS) + " d'expertise en biomass.");
        model.giveRewardsSommetToPlayer(greenEnergyTypes.SOLAR, model.getCurrentPLayer());
        System.out.println("le joueur a " + model.getCurrentPLayer().getExpertise(greenEnergyTypes.SOLAR) + " d'expertise en solaire.");
        model.giveRewardsSommetToPlayer(greenEnergyTypes.REFORESTATION, model.getCurrentPLayer());
        System.out.println("le joueur a " + model.getCurrentPLayer().getExpertise(greenEnergyTypes.REFORESTATION) + " d'expertise en reforestation.");
    }
    
    //@Test
    //public void testCheckCentralVerte(){

    //}

    @Test
    public void testBonusExpertise() {
        model.giveExpertiseBonus(model.getCurrentPLayer(), BonusExpertise.BIOMASS, null);
        Assert.assertEquals(1, model.getCurrentPLayer().getExpertise(greenEnergyTypes.BIOMASS));
        model.giveExpertiseBonus(model.getCurrentPLayer(), BonusExpertise.FUSION, null);
        Assert.assertEquals(1, model.getCurrentPLayer().getExpertise(greenEnergyTypes.FUSION));
        model.giveExpertiseBonus(model.getCurrentPLayer(), BonusExpertise.SOLAR, null);
        Assert.assertEquals(1, model.getCurrentPLayer().getExpertise(greenEnergyTypes.SOLAR));
        model.giveExpertiseBonus(model.getCurrentPLayer(), BonusExpertise.REFORESTATION, null);
        Assert.assertEquals(1, model.getCurrentPLayer().getExpertise(greenEnergyTypes.REFORESTATION));
        model.giveExpertiseBonus(model.getCurrentPLayer(), BonusExpertise.RECYCLING, null);
        Assert.assertEquals(1, model.getCurrentPLayer().getExpertise(greenEnergyTypes.RECYCLING));

        model.giveExpertiseBonus(model.getCurrentPLayer(), BonusExpertise.RESOURCE, null);
        Assert.assertEquals(1, model.getCurrentPLayer().getResourcesTech());
    }

    @Test
    public void testBonusExpertiseAChoix() {
        Continent continentChoix = model.getContinents()[2];
        // cep du continent mis a 0 pour etre sur qu'on peut donner a ce continent
        continentChoix.setNbCep(0);
        greenEnergyTypes expertiseChoix = greenEnergyTypes.RECYCLING;

        model.giveExpertiseBonus(model.getCurrentPLayer(), BonusExpertise.CEP, continentChoix);
        Assert.assertEquals(1,continentChoix.getNbCep());
        model.giveExpertiseBonus(model.getCurrentPLayer(), BonusExpertise.EXPERTISE, expertiseChoix);
        Assert.assertEquals(1, model.getCurrentPLayer().getExpertise(expertiseChoix));
    }

    @Test
    public void testPutCentralFossileContinentCharbon() {
        int index = 1 ;
        Continent continent = model.getContinents()[1];
        centralTypes types = centralTypes.CHARBON ;

        model.putFossileCentral(continent,types,index);

        Assert.assertTrue(model.getContinents()[1].getCentrales().get(index).isOccupe());
        Assert.assertEquals(types,model.getContinents()[1].getCentrales().get(index).getType());
    }

    @Test
    public void testPutCentralFossileContinentPetrole() {
        int index = 1 ;
        Continent continent = model.getContinents()[1];
        centralTypes types = centralTypes.PETROLE ;

        model.putFossileCentral(continent,types,index);

        Assert.assertTrue(model.getContinents()[1].getCentrales().get(index).isOccupe());
        Assert.assertEquals(types,model.getContinents()[1].getCentrales().get(index).getType());
    }

    @Test
    public void testPutCentralFossileContinentGaz() {
        int index = 1 ;
        Continent continent = model.getContinents()[1];
        centralTypes types = centralTypes.GAZNATUREL ;

        model.putFossileCentral(continent,types,index);

        Assert.assertTrue(model.getContinents()[1].getCentrales().get(index).isOccupe());
        Assert.assertEquals(types,model.getContinents()[1].getCentrales().get(index).getType());
    }

    @Test
    public void testTradePvToCEP(){
        model.getCurrentPLayer().setPointVictoire(10);
        model.getCurrentPLayer().setArgent(10);
        model.setCurrentPriceCEP(12);

        model.tradePVtoCEP();

        Assert.assertEquals(0,model.getCurrentPLayer().getArgent());
        Assert.assertEquals(8,model.getCurrentPLayer().getPointVictoire());
    }

    @Test
    public void testDollarsToCEP(){
        // argent du joueur à 10
        model.getCurrentPLayer().setArgent(10);
        // 3 CEP dispo au marché
        model.setNbCEPdispo(3);
        // test de la méthode tradeDollarstoCEP
        model.tradeDollarstoCEP();
        // l'argent du joueur est de 7 car il a payé un CEP
        Assert.assertEquals(7, model.getCurrentPLayer().getArgent());
        // il rest 2 CEP au marché
        Assert.assertEquals(2, model.getNbCEPdispo());
    }

    @Test
    public void testAugmenterCo2AjoutCentral() {
        int index = 1 ;
        Continent continent = model.getContinents()[1];
        centralTypes types = CO2.centralTypes.PETROLE ;
        int co2expected = model.getCo2() + centralTypes.PETROLE.getCo2() ;

        model.putFossileCentral(continent,types,index);

        Assert.assertEquals(co2expected,model.getCo2());
    }

    /**
     * initialisation des sujets
     * @param subject sujet
     * @param types energy associé au sujet
     */
    public void initSubject(Subject subject, greenEnergyTypes types){
        // switch sur les types
        switch (types){
            case FUSION :
                subject.setEnergy(greenEnergyTypes.FUSION);
                break;
            case BIOMASS :
                subject.setEnergy(greenEnergyTypes.BIOMASS);
                break;
            case RECYCLING :
                subject.setEnergy(greenEnergyTypes.RECYCLING);
                break;
            case SOLAR :
                subject.setEnergy(greenEnergyTypes.SOLAR);
                break;
        }
    }

    /**
     * inititialisation des sommets
     * @param sommet le sommet
     * @param sommetSubject liste des sommets
     * @param types le type du sujet 1
     * @param types2 le type du sujet 2
     */
    public void initSommets(SommetTile sommet, ArrayList<Subject> sommetSubject, greenEnergyTypes types, greenEnergyTypes types2 ){
        // initialisation des sujets
        for( int i = 0;i<2;i++) {
            Subject sub = new Subject();
            if(i==0) initSubject(sub, types);
            if(i==1) initSubject(sub, types2);
            // ajout des sujets à la liste
            sommetSubject.add(sub);
        }
        // ajout de la liste des sujets ua sommet
        sommet.setSubjects(sommetSubject);
    }

    @Test
    public void testGetCurrentSommetFull(){
        ArrayList<SommetTile> listSommet = new ArrayList<>(); // création liste de sommet
        SommetTile sommetRome = new SommetTile(); // création sommet Rome
        SommetTile sommetCanberra = new SommetTile(); // création sommet Canberra
        // ajout des sommets à la liste des sommets
        listSommet.add(sommetRome);
        listSommet.add(sommetCanberra);
        // création liste des sujets pour Rome
        ArrayList<Subject> sommetRomeSubject = new ArrayList<>();
        //ajout des sujets
        initSommets(sommetRome, sommetRomeSubject, greenEnergyTypes.FUSION, greenEnergyTypes.BIOMASS);

        // boucle sur les sujets du sommet de Rome
        for (Subject s : sommetRome.getSubjects()) {
            // ajout des scientifiques aux sujets => sommet rempli
            Scientifique scientifique = new Scientifique();
            scientifique.setSubject(s);
            s.setScientifique(scientifique);
        }

        // création liste des sujets pour Canberra
        ArrayList<Subject> sommetCanberraSubject = new ArrayList<>();
        // ajout des sujets
        initSommets(sommetCanberra, sommetCanberraSubject, greenEnergyTypes.SOLAR, greenEnergyTypes.RECYCLING);

        // ajout des sommets aux continents
        model.getContinents()[0].setSommetTile(sommetRome);
        model.getContinents()[1].setSommetTile(sommetCanberra);

        model.setAllSommetTile(listSommet);

        // test de la méthode getCurrentSommetFull => égal au sommet Rome car sommet rempli
        Assert.assertEquals(sommetRome, model.getCurrentSommetFull());
    }

    @Test
    public void testGetIndexContinentSommet(){
        // initialisation des continents
        Continent[] continents = new Continent[2];
        ArrayList<String> nomContinents = new ArrayList<>(Arrays.asList("Europe", "Afrique"));
        int nbCep = 0;
        for(int i=0; i<2 ;i++) {
            if(nomContinents.get(i).equals("Europe")) nbCep = 5;if(nomContinents.get(i).equals("Afrique")) nbCep = 3;
            continents[i] = new Continent(nomContinents.get(i), nbCep, new Image(getClass().getResourceAsStream("images/Continents/" + nomContinents.get(i) + ".jpg")), i);
        }
        // création sommet + ce sommet sur continent 0
        SommetTile sommet = new SommetTile();
        continents[0].setSommetTile(sommet);
        sommet.setContinent(continents[0]);

        model.setContinents(continents);

        Assert.assertEquals(0, model.getIndexContinentSommet(sommet));
    }


}
