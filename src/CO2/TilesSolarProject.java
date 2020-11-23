package CO2;

/*
 * Cette classe s'appelle Tiles (tuiles) Project (Projet) Solar (Solaire)
 * Les autres tuiles projet auront comme schéma (TilesProjectType)
 */
public class TilesSolarProject {
    boolean subPossible;
    boolean misEnPlace;

    public TilesSolarProject(){
        this.subPossible = true;
        this.misEnPlace = false;
    }

    public boolean addOnSubvention(){
        // a développer pour choisir d'ajouter la tuile ou non
        return true;
    }

    public boolean isSubPossible() {
        return subPossible;
    }

    public void setSubPossible(boolean subPossible) {
        this.subPossible = subPossible;
    }

    public boolean isMisEnPlace() {
        return misEnPlace;
    }

    public void setMisEnPlace(boolean misEnPlace) {
        this.misEnPlace = misEnPlace;
    }

    @Override
    public String toString() {
        return "TilesSolarProject";
    }

    public void setSubventionPossible(boolean subPossible){
        this.subPossible = subPossible;
    }
}
