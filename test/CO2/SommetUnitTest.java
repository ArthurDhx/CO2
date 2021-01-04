package CO2;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class SommetUnitTest {

    SommetTile sommetTile;
    ArrayList<Subject> lst;

    @Before
    public void setup() {
        lst = new ArrayList<Subject>();
        for(int i=0; i<3;i++){
            Subject sub = new Subject();
            if(i == 0) sub.setEnergy(greenEnergyTypes.FUSION);
            if(i == 1) sub.setEnergy(greenEnergyTypes.RECYCLING);
            if(i == 2) sub.setEnergy(greenEnergyTypes.BIOMASS);
            lst.add(sub);
        }
        Image imgSommet = new Image(getClass().getResourceAsStream("images/Sommets/Paris.png"));
        ImageView imgSommetView = new ImageView(imgSommet);
        Image imgContinent = new Image(getClass().getResourceAsStream("images/Sommets/Paris.png"));
        Continent Europe = new Continent("Europe", 4, imgContinent,0);
        sommetTile = new SommetTile("Paris", Europe, lst.size(), lst, imgSommetView);
        sommetTile.setSubjects(lst);

    }

    @Test
    public void testHaveEnergy() {
        Assert.assertTrue(sommetTile.haveEnergyAndUnoccupied(greenEnergyTypes.FUSION));
        Assert.assertTrue(sommetTile.haveEnergyAndUnoccupied(greenEnergyTypes.RECYCLING));
        Assert.assertTrue(sommetTile.haveEnergyAndUnoccupied(greenEnergyTypes.BIOMASS));
    }

    @Test
    public void testHaveEnergy2() {
        Assert.assertFalse(sommetTile.haveEnergyAndUnoccupied(greenEnergyTypes.SOLAR));
    }

    @Test
    public void testIsFull() {
        Assert.assertFalse(sommetTile.isFull());
    }

    @Test
    public void testIsFull2() {
        Scientifique s = new Scientifique();
        for (int i = 0; i<lst.size();i++) lst.get(i).setScientifique(s);
        Assert.assertTrue(sommetTile.isFull());
    }

    @Test
    public void testGetIndexSubject(){
        Subject sub1 = new Subject();sub1.setEnergy(greenEnergyTypes.FUSION);
        Subject sub2 = new Subject();sub2.setEnergy(greenEnergyTypes.RECYCLING);
        Subject sub3 = new Subject();sub3.setEnergy(greenEnergyTypes.BIOMASS);
        Assert.assertEquals(0, sommetTile.getIndexSubject(sub1));
        Assert.assertEquals(1, sommetTile.getIndexSubject(sub2));
        Assert.assertEquals(2, sommetTile.getIndexSubject(sub3));
    }

    @Test
    public void testGetScientifiques(){
        ArrayList<Scientifique> scientifiques = new ArrayList<>();
        ArrayList<Subject> subjects = new ArrayList<>();
        Subject subject = new Subject(greenEnergyTypes.FUSION);
        Subject subject1 = new Subject(greenEnergyTypes.BIOMASS);
        subjects.add(subject); subjects.add(subject1);
        sommetTile.setSubjects(subjects);
        Scientifique scientifique = new Scientifique();
        subject.setScientifique(scientifique);
        for(Subject s : subjects){
            scientifiques.add(s.getScientifique());
        }
        Assert.assertEquals(scientifiques, sommetTile.getScientifiques());
    }

    @Test
    public void testSetStaffedOnEnergy(){
        Subject subject = new Subject(greenEnergyTypes.FUSION);
        sommetTile.setStaffedOnEnergy(subject);
    }

    @Test
    public void testIsStaffed(){
        Subject subject = new Subject(greenEnergyTypes.FUSION);
        sommetTile.setStaffedOnEnergy(subject);
        Assert.assertTrue(sommetTile.isStaffed(subject));
    }

    @Test
    public void testIsStaffed2(){
        Subject subject = new Subject(greenEnergyTypes.FUSION);
        Assert.assertFalse(sommetTile.isStaffed(subject));
    }

}
