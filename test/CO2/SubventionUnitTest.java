package CO2;

import javafx.scene.image.Image;
import org.junit.Assert;
import org.junit.Test;

public class SubventionUnitTest {

    /* C'est cassé
    @Test
    public void testHasTilesSolarProject(){
        TilesSolarProject t1 = new TilesSolarProject();
        Subvention s1 = new Subvention(0);
        s1.addTilesSolarProject(t1);
    }
     */

    @Test
    public void testEffetRessources() {
        Continent continent = new Continent("Europe", 2, new Image(getClass().getResourceAsStream("images/Continents/Europe.jpg")),1);
        Player p = new Player();

        Assert.assertEquals(0, p.getResourcesTech());
        Subvention sub = continent.getSubventions().get(1);
        sub.effect(p);
        Assert.assertEquals(2, p.getResourcesTech());
    }
}
