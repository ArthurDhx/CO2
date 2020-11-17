package CO2;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Map;

public class SommetTile {

    private String location;
    private int nbSubjects;
    // Liste des sujets d'un sommet
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


    /**
     * @param energyTypes Une energie verte
     * @return un booléen
     * vérifie si le sommet contient cette énergie
     */
    public boolean haveEnergy(GreenEnergyTypes energyTypes){
        for (Subject s: this.subjects) {
            // si le sujet a comme energie l'energie envoyé alors true
            if(s.getEnergy().equals(energyTypes)) return true;
        }
        return false;
    }


    /**
     * @return la liste de scientifiques du sommet
     */
    public ArrayList<Scientifique> getScientifiques(){
        ArrayList<Scientifique> scientifiques = new ArrayList<Scientifique>();
        for(Subject s: subjects){
            scientifiques.add(s.getScientifique());
        }
        return scientifiques;
    }

    /**
     * @param energyTypes energie verte
     * @return le sujet ou see trouve l'energie
     */
    public Subject getSubjectInSommet(GreenEnergyTypes energyTypes){
        for (Subject s: this.subjects) {
            // si le sujet a comme energie l'energie envoyé alors true
            if(s.getEnergy().equals(energyTypes)) return s;
        }
        return null;
    }
    public Image getImageSommetTile() {
        return imageSommetTile;
    }

    /**
     * @param scientifique Scientifique a jouté
     * @param type l'energie ou on veut placer le scientifique
     *
     * Ajoute un scientifique a une énergie donnée
     */
    public void addScientifiqueToEnergy(Scientifique scientifique, GreenEnergyTypes type) {
        scientifique.setSommetTile(this);
        // récupère le sujet avec comme type le type donnée et le remplie d'un scientifique
        getSubjectInSommet(type).setScientifique(scientifique);
    }

    /**
     * @return booléen
     * vérifie si le sommet est remplie de scientifique / fini
     */
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
