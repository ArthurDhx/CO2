package CO2;

public class Subject {
    private Scientifique scientifique = null;
    private GreenEnergyTypes energy;

    public Subject(GreenEnergyTypes energy) {
        this.energy = energy;
    }

    public Scientifique getScientifique() {
        return scientifique;
    }

    public void setScientifique(Scientifique scientifique) {
        this.scientifique = scientifique;
        scientifique.setSubject(this);
    }

    public GreenEnergyTypes getEnergy() {
        return energy;
    }

    public void setEnergy(GreenEnergyTypes energy) {
        this.energy = energy;
    }
}
