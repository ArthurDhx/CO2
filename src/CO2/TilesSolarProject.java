package CO2;

import java.util.ArrayList;

import static CO2.GreenEnergyTypes.SOLAR;

/*
 * Cette classe s'appelle Tiles (tuiles) Project (Projet) Solar (Solaire)
 * Les autres tuiles projet auront comme schéma (TilesProjectType)
 */
public class TilesSolarProject {
    boolean subPossible;
    boolean misEnPlace;
    private GreenEnergyTypes type;

    public TilesSolarProject(){
        this.subPossible = true;
        this.misEnPlace = false;
        // TODO mettre en paramètre le type quand on aura tout les types (refactor)
        this.type = SOLAR;
    }

    public boolean addOnSubvention(){
        // a développer pour choisir d'ajouter la tuile ou non
        return true;
    }

    public typesCentral getTypeToCentral() {
        switch (type) {
            case SOLAR:
                return typesCentral.SOLAIRE;
            case BIOMASS:
                return typesCentral.BIOMASSE;
            case RECYCLING:
                return typesCentral.RECYCLAGE;
            case FUSION:
                return typesCentral.FUSIONFROIDE;
            case REFORESTATION:
                return typesCentral.REBOISEMENT;
        }
        return null;
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

    public GreenEnergyTypes getType() { return type; }

    public void setType(GreenEnergyTypes type) { this.type = type; }

    @Override
    public String toString() {
        return "TilesSolarProject";
    }

    public void setSubventionPossible(boolean subPossible){
        this.subPossible = subPossible;
    }
}
