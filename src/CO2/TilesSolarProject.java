package CO2;

/*
 * Cette classe s'appelle Tiles (tuiles) Project (Projet) Solar (Solaire)
 * Les autres tuiles projet auront comme schéma (TilesProjectType)
 */
public class TilesSolarProject {
    boolean subPossible;
    public TilesSolarProject(){
        this.subPossible = true;
    }

    public boolean addOnSubvention(){
        // a développer pour choisir d'ajouter la tuile ou non
        return true;
    }

    public void setSubventionPossible(boolean subPossible){
        this.subPossible = subPossible;
    }
}
