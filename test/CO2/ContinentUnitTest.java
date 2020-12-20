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
        c1.getSubventions().get(2).addTilesProject(t1);
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

    @Test
    public void testHasGreenCentralAllFossile(){
        Continent c1 = new Continent("Afrique", 3, new Image(getClass().getResourceAsStream("images/Continents/Afrique.jpg")),1);
        c1.getCentrales().get(0).setType(centralTypes.PETROLE); //centrale dans l'emplacement 1 = fossile
        c1.getCentrales().get(1).setType(centralTypes.GAZNATUREL); //centrale dans l'emplacement 2 = fossile
        c1.getCentrales().get(2).setType(centralTypes.CHARBON); //centrale dans l'emplacement 3 = fossile
        for (Central c: c1.getCentrales()) {
            c.setOccupe(true);
        }
        Assert.assertFalse(c1.hasGreenCentral());
    }

    @Test
    public void testHasGreenCentralEmpty(){
        Continent c1 = new Continent("Afrique", 3, new Image(getClass().getResourceAsStream("images/Continents/Afrique.jpg")),1);
        for (Central c: c1.getCentrales()) {
            c.setOccupe(false);
        }
        Assert.assertFalse(c1.hasGreenCentral());
    }

    @Test
    public void testHasGreenCentral(){
        Continent c1 = new Continent("Afrique", 3, new Image(getClass().getResourceAsStream("images/Continents/Afrique.jpg")),1);
        c1.getCentrales().get(0).setType(centralTypes.PETROLE); //centrale dans l'emplacement 1 = fossile
        c1.getCentrales().get(1).setType(centralTypes.BIOMASSE); //centrale dans l'emplacement 2 = verte
        c1.getCentrales().get(2).setType(centralTypes.CHARBON); //centrale dans l'emplacement 3 = fossile
        for (Central c: c1.getCentrales()) {
            c.setOccupe(true);
        }

        Assert.assertTrue(c1.hasGreenCentral());
    }

    @Test
    public void testHasGreenCentral2(){
        Continent c1 = new Continent("Afrique", 3, new Image(getClass().getResourceAsStream("images/Continents/Afrique.jpg")),1);
        c1.getCentrales().get(0).setType(centralTypes.PETROLE); //centrale dans l'emplacement 1 = fossile
        c1.getCentrales().get(1).setType(centralTypes.BIOMASSE); //centrale dans l'emplacement 2 = verte
        c1.getCentrales().get(2).setType(centralTypes.SOLAIRE); //centrale dans l'emplacement 3 = fossile
        for (Central c: c1.getCentrales()) {
            c.setOccupe(true);
        }

        Assert.assertTrue(c1.hasGreenCentral());
    }

}
