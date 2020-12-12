package CO2;

public class Subject {
    // le sujet peut contenir un scientifique
    private Scientifique scientifique = null;
    // le sujet à un type d'énergie
    private greenEnergyTypes energy;

    public Subject(){}

    public Subject(greenEnergyTypes energy) {
        this.energy = energy;
    }

    public Scientifique getScientifique() {
        return scientifique;
    }

    public void setScientifique(Scientifique scientifique) {
        this.scientifique = scientifique;
        scientifique.setSubject(this);
    }

    public greenEnergyTypes getEnergy() {
        return energy;
    }

    public void setEnergy(greenEnergyTypes energy) {
        this.energy = energy;
    }

    @Override
    public String toString() {
        return energy.name();
    }
}
