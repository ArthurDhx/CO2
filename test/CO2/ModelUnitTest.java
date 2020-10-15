package CO2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ModelUnitTest {

    Model model;

    @Before
    public void setup() {
        model = new Model();
        model.init();
    }

    @Test
    public void testIncrementExpertiseJoueurCourant() {
        model.incrementExpertise(Player.expertiseId.get("Solar"));
        Assert.assertEquals(1, model.getCurentPLayer().getSolarExpertise());
        // TODO : ajouter les tests pour les autres energies vertes
    }


}
