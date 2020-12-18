package CO2;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Random;

public class OnuCardUnitTest {

    @Test
    public void testRandom(){
        OnuCard card = new OnuCard(10, 4);
        Random random = Mockito.mock(Random.class);
        Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(1,3);
        Assert.assertEquals(3, card.random(random));
    }

    @Test
    public void testSetTypesCentral(){
        // test de la ligne : centralTypes.values()[random.nextInt(centralTypes.values().length - 3)].name();
        Random random = Mockito.mock(Random.class);
        Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(1, 10);
        Assert.assertEquals(centralTypes.values()[1].name(), centralTypes.values()[random.nextInt(centralTypes.values().length-3)].name());

    }
}
