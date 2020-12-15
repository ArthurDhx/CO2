package CO2;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class SommetUnitTest {

    SommetTile sommetTile;
    ArrayList<Subject> lst;

    @Before
    public void setup() {
        lst = new ArrayList<Subject>();
        for(int i=0; i<3;i++){
            Subject sub = new Subject();
            if(i == 0) sub.setEnergy(greenEnergyTypes.FUSION);
            if(i == 1) sub.setEnergy(greenEnergyTypes.RECYCLING);
            if(i == 2) sub.setEnergy(greenEnergyTypes.BIOMASS);
            lst.add(sub);
        }
        Image imgSommet = new Image(getClass().getResourceAsStream("images/Sommets/Paris.png"));
        ImageView imgSommetView = new ImageView(imgSommet);
        Image imgContinent = new Image(getClass().getResourceAsStream("images/Sommets/Paris.png"));
        Continent Europe = new Continent("Europe", 4, imgContinent,0);
        sommetTile = new SommetTile("Paris", Europe, lst.size(), lst, imgSommetView);
        sommetTile.setSubjects(lst);

    }

    @Test
    public void testHaveEnergy() {
        Assert.assertTrue(sommetTile.haveEnergyAndUnoccupied(greenEnergyTypes.FUSION));
        Assert.assertTrue(sommetTile.haveEnergyAndUnoccupied(greenEnergyTypes.RECYCLING));
        Assert.assertTrue(sommetTile.haveEnergyAndUnoccupied(greenEnergyTypes.BIOMASS));
    }

    @Test
    public void testHaveEnergy2() {
        Assert.assertFalse(sommetTile.haveEnergyAndUnoccupied(greenEnergyTypes.SOLAR));
    }

    @Test
    public void testIsFull() {
        Assert.assertFalse(sommetTile.isFull());
    }

    @Test
    public void testIsFull2() {
        Scientifique s = new Scientifique();
        for (int i = 0; i<lst.size();i++) lst.get(i).setScientifique(s);
        Assert.assertTrue(sommetTile.isFull());
    }

    @Test
    public void testGetIndexSubject(){
        Subject sub1 = new Subject();sub1.setEnergy(greenEnergyTypes.FUSION);
        Subject sub2 = new Subject();sub2.setEnergy(greenEnergyTypes.RECYCLING);
        Subject sub3 = new Subject();sub3.setEnergy(greenEnergyTypes.BIOMASS);
        Assert.assertEquals(0, sommetTile.getIndexSubject(sub1));
        Assert.assertEquals(1, sommetTile.getIndexSubject(sub2));
        Assert.assertEquals(2, sommetTile.getIndexSubject(sub3));
    }

/*
    @Before
    public void setup() {
        ArrayList<GreenEnergyTypes> lst = new ArrayList<>();
        lst.add(GreenEnergyTypes.FUSION);
        lst.add(GreenEnergyTypes.RECYCLING);
        lst.add(GreenEnergyTypes.BIOMASS);
        Image img = new Image(getClass().getResourceAsStream("images/Sommets/Paris.png"));
        sommetTile = new SommetTile("Paris", lst.size(),lst,img);
    }

    @Test
    public void testGetSubjectIndex(){
        Assert.assertEquals(GreenEnergyTypes.FUSION, sommetTile.getSubjectIndex(0));
        Assert.assertEquals(GreenEnergyTypes.RECYCLING, sommetTile.getSubjectIndex(1));
        Assert.assertEquals(GreenEnergyTypes.BIOMASS, sommetTile.getSubjectIndex(2));
    }

    @Test
    public void testGetSubjectInSommet(){
        Assert.assertTrue(sommetTile.getSubjectInSommet(GreenEnergyTypes.FUSION));
        Assert.assertTrue(sommetTile.getSubjectInSommet(GreenEnergyTypes.RECYCLING));
        Assert.assertTrue(sommetTile.getSubjectInSommet(GreenEnergyTypes.BIOMASS));
        Assert.assertFalse(sommetTile.getSubjectInSommet(GreenEnergyTypes.SOLAR));
    }

    @Test
    public void testSetAllStaffedAs1(){
        sommetTile.setAllStaffedAs(true);
        Assert.assertTrue(sommetTile.getStaffed().get(0));
        Assert.assertTrue(sommetTile.getStaffed().get(1));
        Assert.assertTrue(sommetTile.getStaffed().get(2));
    }

    @Test
    public void testSetAllStaffedAs2(){
        sommetTile.setAllStaffedAs(false);
        Assert.assertFalse(sommetTile.getStaffed().get(0));
        Assert.assertFalse(sommetTile.getStaffed().get(1));
        Assert.assertFalse(sommetTile.getStaffed().get(2));
    }

    @Test
    public void testSetStaffedAsOn(){
        sommetTile.setStaffedAsOn(0, true);
        sommetTile.setStaffedAsOn(1, false);
        sommetTile.setStaffedAsOn(2, false);
        Assert.assertTrue(sommetTile.getStaffed().get(0));
        Assert.assertFalse(sommetTile.getStaffed().get(1));
        Assert.assertFalse(sommetTile.getStaffed().get(2));
    }

    @Test
    public void testIsAllStaffed1(){
        sommetTile.setAllStaffedAs(true);
        Assert.assertTrue(sommetTile.isAllStaffed());
    }

    @Test
    public void testIsAllStaffed2(){
        sommetTile.setStaffedAsOn(0, true);
        sommetTile.setStaffedAsOn(1, false);
        sommetTile.setStaffedAsOn(2, true);
        Assert.assertFalse(sommetTile.isAllStaffed());
    }

    /*@Test
    public void testSetStaffedScientifiquesAt(){
        Scientifique scientifique = new Scientifique();


        sommetTile.setStaffedScientifiquesAt(scientifique, 0);
        Assert.assertEquals(scientifique, sommetTile.getScientifiques().get(0));
    }*/

}
