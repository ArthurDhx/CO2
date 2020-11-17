package CO2;

import javafx.scene.image.Image;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class SommetUnitTest {

    SommetTile sommetTile;
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
