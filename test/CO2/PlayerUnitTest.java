package CO2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerUnitTest {

    public Player p;

    @Before
    public void setup() {
        p = Player.createPlayer();
    }

    @Test
    public void testExpertiseSolaireInit() {
        Assert.assertEquals(0, p.getSolarExpertise());
    }

    @Test
    public void testIncrementExpertiseSolaire() {
        p.addExpertise(Player.expertiseId.get("Solar"));
        Assert.assertEquals(1, p.getSolarExpertise());
    }

}
