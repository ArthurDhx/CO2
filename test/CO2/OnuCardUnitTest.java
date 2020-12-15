package CO2;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Random;

public class OnuCardUnitTest {

    @Test
    public void testRandomNbType(){
        OnuCard card = new OnuCard(10, 4);
        Random random = Mockito.mock(Random.class);
        Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(1,3);
        Assert.assertEquals(3, card.random(random));
    }

    @Test
    public void testSetTypesCentral(){
        OnuCard card = new OnuCard(10,4);
        ArrayList<String> typesCentral = new ArrayList<>();
        typesCentral.add(centralTypes.values()[2].name());
        typesCentral.add(centralTypes.values()[3].name());
        Random random = Mockito.mock(Random.class);
        Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(1,3);
        // TODO : prochain sprint : ajouter en paramètre de la méthode un ramdom pour les types de centrales
        card.setTypesCentral(typesCentral, random);
    }
}
