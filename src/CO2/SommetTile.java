package CO2;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.List;

public class SommetTile {

    private String location;
    private int nbSubjects;
    // Liste des sujets d'un sommet
    private ArrayList<Subject> subjects;
    private ArrayList<Boolean> staffed;
    private ImageView imageSommetTile;
    private Continent continent;

    public SommetTile(){}

    public SommetTile(String location, Continent continent, int nbSubjects, List<Subject> subjects, ImageView imageSommetTile){
        this.location = location;
        this.nbSubjects = nbSubjects;
        this.imageSommetTile = imageSommetTile;
        this.subjects = new ArrayList<Subject>();
        this.staffed = new ArrayList<Boolean>();
        this.subjects.addAll(subjects);
        for (int i = 0; i < nbSubjects; i++) staffed.add(false);
        this.continent = continent;
    }

    public String getLocation() {
        return location;
    }

    public int getNbSubjects() {
        return nbSubjects;
    }

    public Continent getContinent() {
        return continent;
    }

    /**
     * @param energyTypes Une energie verte
     * @return un booléen
     * vérifie si le sommet contient cette énergie
     */
    public boolean haveEnergy(greenEnergyTypes energyTypes){
        for (Subject s: this.subjects) {
            // si le sujet a comme energie l'energie envoyé et qu'il n'y a pas de scientifique sur cette source alors renvoie true
            if(s.getEnergy().equals(energyTypes) && s.getScientifique() == null) return true;
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
     * retourn l'index du sujet
     * @param subject
     * @return
     */
    public int getIndexSubject(Subject subject) {
        for (int i = 0; i < this.subjects.size(); i++) {
            if (this.subjects.get(i).getEnergy() == subject.getEnergy()) return i;

        }
        return -1;
    }

    public ImageView getImageSommetTile() {
        return imageSommetTile;
    }

    /**
     * un scientifique est présent sur un sujet donnée en paramètre
     * @param subject
     */
    public void setStaffedOnEnergy(Subject subject) {
        staffed.set(getIndexSubject(subject),true);
    }

    /**
     * retourne true si le sujet est occupé par un scientifique
     * @param subject
     * @return
     */
    public boolean isStaffed(Subject subject) {
        return staffed.get(getIndexSubject(subject));
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
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

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < subjects.size(); i++) {
            s.append(subjects.get(i)).append(" ");
        }
        return "Sommet de "+location + " : " + s;
    }

    public void print() {
        String subjectsLst ="";
        String subjectsStaffed ="";
        System.out.println(location + " " + nbSubjects + " " + subjectsLst + subjectsStaffed);
    }
}