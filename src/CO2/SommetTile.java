package CO2;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Map;

public class SommetTile {

    private String location;
    private int nbSubjects;
    private ArrayList<Subject> subjects;
    private Image imageSommetTile;

    public SommetTile(){}

    public SommetTile(String location, int nbSubjects, ArrayList<Subject> subjects, Image imageSommetTile){
        this.location = location;
        this.nbSubjects = nbSubjects;
        this.imageSommetTile = imageSommetTile;
        this.subjects = subjects;
    }

    public String getLocation() {
        return location;
    }

    public int getNbSubjects() {
        return nbSubjects;
    }


    public boolean haveEnergy(GreenEnergyTypes energyTypes){
        for (Subject s: this.subjects) {
            if(s.getEnergy().equals(energyTypes)) return true;
        }
        return false;
    }

    public Subject getSubjectInSommet(GreenEnergyTypes energyTypes){
        for (Subject s: this.subjects) {
            if(s.getEnergy().equals(energyTypes)) return s;
        }
        return null;
    }
    public Image getImageSommetTile() {
        return imageSommetTile;
    }

    public void addScientifiqueToEnergy(Scientifique scientifique, GreenEnergyTypes type) {
        scientifique.setSommetTile(this);
        getSubjectInSommet(type).setScientifique(scientifique);
    }

    public boolean isFull(){
        for(Subject s: subjects){
            if(s.getScientifique() == null){
                return false;
            }
        }
        return true;
    }

    public void print() {
        String subjectsLst ="";
        String subjectsStaffed ="";
        System.out.println(location + " " + nbSubjects + " " + subjectsLst + subjectsStaffed);
    }
}
