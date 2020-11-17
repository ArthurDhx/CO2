package CO2;

public class Subject {
    // le sujet peut contenir un scientifique
    private Scientifique scientifique = null;
    // le sujet à un type d'énergie
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
