package CO2;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class SommetTile {

    private String location;
    private int nbSubjects;
    private ArrayList<String> subjects;
    private ArrayList<Boolean> staffed;
    private Image imageSommetTile;

    public SommetTile(){}

    public SommetTile(String location, int nbSubjects, ArrayList<String> subjects, Image imageSommetTile){
        this.location = location;
        this.nbSubjects = nbSubjects;
        this.subjects = new ArrayList<String>();
        this.subjects.addAll(subjects);
        this.staffed = new ArrayList<Boolean>();
        setAllStaffedAs(false);
        this.imageSommetTile = imageSommetTile;
    }

    public String getLocation() {
        return location;
    }

    public int getNbSubjects() {
        return nbSubjects;
    }

    public ArrayList<String> getSubjects() {
        return subjects;
    }

    public ArrayList<Boolean> getStaffed() {
        return staffed;
    }

    public String getSubjectIndex(int index) {
        return subjects.get(index);
    }
    public Image getImageSommetTile() {
        return imageSommetTile;
    }
    
    /**
     * Retourne vrai ou faux selon si un sujet est occupé
     * @return boolean
     */
    public boolean getStaffedIndex(int index) {
        return staffed.get(index);
    }


    public void setLocation(String location) {
        this.location = location;
    }

    public void setNbSubjects(int nbSubjects) {
        this.nbSubjects = nbSubjects;
    }

    public void setSubjects(ArrayList<String> subjects) {
        this.subjects = subjects;
    }

    public void setImageSommetTile(Image imageSommetTile) {
        this.imageSommetTile = imageSommetTile;
    }

    /**
     * Donne une valeur à tous les sujets (peut être utilisé pour reset)
     */
    public void setAllStaffedAs(boolean value) {
        staffed.clear();
        for (int i = 0; i < nbSubjects; i++) {
            staffed.add(value);
        }
    }

    /**
     * Donne une valeur à un sujet
     */
    public void setStaffedAsOn(int index, boolean value) {
        staffed.set(index,value);
    }


    /**
     * Vérifie si tous les sujets sont occupés par un scientifique
     * @return boolean
     */

    public boolean isAllStaffed(){
        int isTrue = 0;
        for (int i = 0; i < staffed.size(); i++) {
            if (staffed.get(i)) isTrue++;
        }
        if (isTrue == staffed.size()) return true;
        return false;
    }

    public void print() {
        String subjectsLst ="";
        String subjectsStaffed ="";
        for (int i = 0; i < subjects.size(); i++) {
            subjectsLst += subjects.get(i) + " ";
        }
        for (int i = 0; i < staffed.size(); i++) {
            subjectsStaffed += staffed.get(i) + " ";
        }
        System.out.println(location + " " + nbSubjects + " " + subjectsLst + subjectsStaffed);
    }
}
