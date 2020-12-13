package CO2;

import javafx.scene.image.Image;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ContinentUnitTest {

    @Test
    public void testIsContainsTilesSolarProject(){
        Continent c1 = new Continent("Europe", 3, new Image(getClass().getResourceAsStream("images/Continents/Europe.jpg")),1);
        Project t1 = new Project(greenEnergyTypes.SOLAR);
        //c1.getSubventions().get(2).addTilesSolarProject(t1);
        Assert.assertTrue(c1.isContainsTilesSolarProject());
    }

    @Test
    public void testAllPlantsAreOccupied1(){
        Continent c1 = new Continent("Afrique", 3, new Image(getClass().getResourceAsStream("images/Continents/Afrique.jpg")),1);
        List<Central> lstCentralC1 = c1.getCentrales();
        for (Central c: lstCentralC1) {
            c.setOccupe(true);
        }
        Assert.assertTrue(c1.allPlantsAreOccupied());
    }

    @Test
    public void testAllPlantsAreOccupied2(){
        Continent c1 = new Continent("Afrique", 3, new Image(getClass().getResourceAsStream("images/Continents/Afrique.jpg")),1);
        List<Central> lstCentralC1 = c1.getCentrales();
        for (Central c: lstCentralC1) {
            c.setOccupe(true);
        }
        lstCentralC1.get(2).setOccupe(false);
        Assert.assertFalse(c1.allPlantsAreOccupied());
    }

}
