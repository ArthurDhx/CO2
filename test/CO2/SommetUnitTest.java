package CO2;

import javafx.scene.image.Image;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class SommetUnitTest {

    SommetTile sommetTile;

    @Before
    public void setup() {
        ArrayList<String> lst = new ArrayList<>();
        lst.add("Fusion");
        lst.add("Recycling");
        lst.add("Biomass");
        Image img = new Image(getClass().getResourceAsStream("images/Sommets/Paris.png"));
        sommetTile = new SommetTile("Paris", lst.size(),lst,img);
    }

    @Test
    public void testGetSubjectIndex(){
        Assert.assertEquals("Fusion", sommetTile.getSubjectIndex(0));
        Assert.assertEquals("Recycling", sommetTile.getSubjectIndex(1));
        Assert.assertEquals("Biomass", sommetTile.getSubjectIndex(2));
    }

    @Test
    public void testGetSubjectInSommet(){
        Assert.assertTrue(sommetTile.getSubjectInSommet("Fusion"));
        Assert.assertTrue(sommetTile.getSubjectInSommet("Recycling"));
        Assert.assertTrue(sommetTile.getSubjectInSommet("Biomass"));
        Assert.assertFalse(sommetTile.getSubjectInSommet("Solar"));
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
