package CO2;

import javafx.scene.image.Image;
import org.junit.Assert;
import org.junit.Test;

public class ContinentUnitTest {

    @Test
    public void testIsContainsTilesSolarProject(){
        Continent c1 = new Continent("Europe", 3, new Image(getClass().getResourceAsStream("images/Continents/Europe.jpg")),1);
        TilesSolarProject t1 = new TilesSolarProject();
        c1.getSubventions().get(2).addTilesSolarProject(t1);
        Assert.assertTrue(c1.isContainsTilesSolarProject());
    }

}
