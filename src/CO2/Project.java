package CO2;

public class Project {
    private boolean subventionPossible;
    private boolean misEnPlace;
    private greenEnergyTypes energyType;

    public Project(greenEnergyTypes energyType){
        this.subventionPossible = true;
        this.misEnPlace = false;
        this.energyType = energyType;
    }


    /**
     * Donne le type de centrale correspondant au type de projet
     * @return typesCentral
     */
    public centralTypes getCentralType() {
        switch (energyType) {
            case SOLAR:
                return centralTypes.SOLAIRE;
            case BIOMASS:
                return centralTypes.BIOMASSE;
            case RECYCLING:
                return centralTypes.RECYCLAGE;
            case FUSION:
                return centralTypes.FUSIONFROIDE;
            case REFORESTATION:
                return centralTypes.REBOISEMENT;
        }
        return null;
    }

    public boolean isMisEnPlace() {
        return misEnPlace;
    }

    public void setMisEnPlace(boolean misEnPlace) {
        this.misEnPlace = misEnPlace;
    }

    public greenEnergyTypes getEnergyType() { return energyType; }

    public void setEnergyType(greenEnergyTypes energyType) { this.energyType = energyType; }

    @Override
    public String toString() {
        return "ProjectTile"+energyType;
    }

    public boolean isSubventionPossible() {
        return subventionPossible;
    }

    public void setSubventionPossible(boolean subventionPossible) {
        this.subventionPossible = subventionPossible;
    }
}
