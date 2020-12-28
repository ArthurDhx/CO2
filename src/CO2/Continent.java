package CO2;


import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Continent {

    private final String name;
    private final ArrayList<Subvention> subventions;
    private final Rectangle[] tabRectangleSubvention;
    private int nbCep;
    private int nbRessTech;
    private final ArrayList<Central> centrales;
    private final Rectangle[] tabRectangleCentral;
    private final Image imgContinent;
    // tuile agenda régionale
    private AgendaTile agendaTile;
    // Tuile sommet
    private SommetTile sommetTile;
    int index;
    // joueur qui controlle le continent
    Player controlPlayer;


    public Continent(String name, int nbCep, Image imgContinent, int index) {
        this.name = name;
        this.nbCep = nbCep;
        this.nbRessTech = 0;
        this.imgContinent = imgContinent;
        this.subventions = new ArrayList<>(3);
        this.centrales = new ArrayList<>();
        this.tabRectangleCentral = new Rectangle[nbCep];
        this.tabRectangleSubvention = new Rectangle[3];
        for(int i = 0;i<3;i++) {
                subventions.add(new Subvention(i,this,tabRectangleSubvention));
        }
        for(int i = 0;i<nbCep;i++) {
            centrales.add(new Central(i,this,tabRectangleCentral));
        }
        this.index = index;
        this.controlPlayer = null;
    }

    /**
     * Permet d'ajouter un nombre i de CEP
     * @param i
     * @return true ou false suivant si l'opération à réussi ou non
     */
    public boolean addCEP(int i){
        //Dans chaque if, on teste le continent et si il peut recevoir les CEP
        if(getName().equals("Europe") && getNbCep()+i <= 5) {
            nbCep += i; //Si c'est le cas, on ajoute les cep
            return true; //On renvoit que la manip à fonctionné
        }
        else if(getName().equals("Afrique") && getNbCep()+i <= 3) {
            nbCep += i;
            return false;
        }
        else if(getName().equals("Amérique du Sud") && getNbCep()+i <= 4) {
            nbCep += i;
            return true;
        }
        else if(getName().equals("Amérique du Nord") && getNbCep()+i <= 5) {
            nbCep += i;
            return true;
        }
        else if(getName().equals("Océanie") && getNbCep()+i <= 4) {
            nbCep += i;
            return true;
        }
        else if(getName().equals("Asie") && getNbCep()+i <= 6) {
            nbCep += i;
            return true;
        }
        return false; //Sinon on renvoit que la manip n'a pas fonctionné
    }

    public boolean isContainsTilesSolarProject(){
        // permet de savoir si le continent contient des projets de tuiles solaires
        if(!subventions.get(2).isEmpty()){
            System.out.println("Tuile de projet solaire sur case subvention : " +
                    subventions.get(2).getType() + ", dans le continent " + name);
            return true;
        }
        return false;
    }

    public ArrayList<Subvention> getEmptySubventions() {
        ArrayList<Subvention> freeSubvention = new ArrayList<Subvention>();
        for (Subvention subvention : subventions) {
            if (subvention.isEmpty()) freeSubvention.add(subvention);
        }
        return freeSubvention ;
    }

    public void print() {
        System.out.println("Continent{" +
                "name=" + name + ", nbCep=" + nbCep +
                ", subventions=" + subventions.get(0).getProject() + '}');
    }

    public void removeCEP(){
        nbCep --;
    }

    public void removeRessTech(){
        nbRessTech--;
    }

    public Player getControlPlayer() {
        return controlPlayer;
    }

    public void setControlPlayer(Player controlPlayer) {
        this.controlPlayer = controlPlayer;
    }

    public Rectangle[] getTabRectangleSubvention() { return tabRectangleSubvention; }

    public Rectangle[] getTabRectangleCentral() { return tabRectangleCentral; }

    public int getNbCep() { return nbCep; }

    public int getNbRessTech() {
        return nbRessTech;
    }

    public ArrayList<Subvention> getSubventions() { return subventions; }

    public Image getImgContinent() {return imgContinent;}

    public ArrayList<Central> getCentrales() {
        return centrales;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public AgendaTile getAgendaTile() {
        return agendaTile;
    }

    public void setAgendaTile(AgendaTile agendaTile) {
        this.agendaTile = agendaTile;
    }

    public SommetTile getSommetTile() {
        return sommetTile;
    }

    public void setSommetTile(SommetTile sommetTile) {
        this.sommetTile = sommetTile;
    }

    public int getIndex() {
        return index;
    }

    public boolean allPlantsAreOccupied(){
        int nbcentralOccuped = 0;
        for (Central c: centrales) {
            if(c.isOccupe()) nbcentralOccuped++;
        }
        if (nbcentralOccuped == centrales.size()) return true;
        else return false;
    }

    public void setNbCep(int nbCep) {
        this.nbCep = nbCep;
    }

    public void setNbRessTech(int nbRessTech) {
        this.nbRessTech = nbRessTech;
    }

    public boolean hasGreenCentral(){
        // si multi vérifier l'appartenance de la centrale
        for (Central c : centrales) {
            if (c.isOccupe() && !c.isFossile()) return true;
        }
        return false;
    }
}

