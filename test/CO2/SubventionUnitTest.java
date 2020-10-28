package CO2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SubventionUnitTest {

    @Test
    public void testHasTilesSolarProject(){
        TilesSolarProject t1 = new TilesSolarProject();
        Subvention s1 = new Subvention(0);
        s1.hasTilesSolarProject(t1);
    }
}
