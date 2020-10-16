package CO2;

import javafx.scene.image.Image;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ContinentUnitTest {

    @Test
    public void testIsContainsTilesSolarProject(){
        Continent c1 = new Continent("Europe", 3, new Image(getClass().getResourceAsStream("images/Europe.jpg")));
        TilesSolarProject t1 = new TilesSolarProject();
        c1.getSubventions()[2].hasTilesSolarProject(t1);
        Assert.assertTrue(c1.isContainsTilesSolarProject());
    }

}
