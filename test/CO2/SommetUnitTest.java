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
        lst.add("Solar");
        lst.add("Biomass");
        Image img = new Image(getClass().getResourceAsStream("images/scientifique.png"));
        sommetTile = new SommetTile("Paris", lst.size(),lst,img);
    }

    @Test
    public void testGetSubjectIndex(){
        Assert.assertEquals("Solar", sommetTile.getSubjectIndex(0));
        Assert.assertEquals("Biomass", sommetTile.getSubjectIndex(1));
    }

    @Test
    public void testSetAllStaffedAs1(){
        sommetTile.setAllStaffedAs(true);
        Assert.assertTrue(sommetTile.getStaffed().get(0));
        Assert.assertTrue(sommetTile.getStaffed().get(1));
    }

    @Test
    public void testSetAllStaffedAs2(){
        sommetTile.setAllStaffedAs(false);
        Assert.assertFalse(sommetTile.getStaffed().get(0));
        Assert.assertFalse(sommetTile.getStaffed().get(1));
    }

    @Test
    public void testSetStaffedAsOn(){
        sommetTile.setStaffedAsOn(0, true);
        sommetTile.setStaffedAsOn(1, false);
        Assert.assertTrue(sommetTile.getStaffed().get(0));
        Assert.assertFalse(sommetTile.getStaffed().get(1));
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
        Assert.assertFalse(sommetTile.isAllStaffed());
    }

}
