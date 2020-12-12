package CO2;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class AgendaTile {

    // Type d'energie
    private List<greenEnergyTypes> energies;
    private Image imageAgendaTile;

    public AgendaTile(greenEnergyTypes energy1, greenEnergyTypes energy2, greenEnergyTypes energy3, Image imageAgendaTile) {
        this.energies = new ArrayList<>();
        energies.add(energy1);
        energies.add(energy2);
        energies.add(energy3);
        this.imageAgendaTile = imageAgendaTile;
    }

    /**
     * @param energy {"Solar","Biomass","Fusion","Recycling","Reforestation"}
     * @return true si energy est dans la liste des energies acceptables
     */
    public boolean isPossiblePlacement(greenEnergyTypes energy) {
        if (energies.contains(energy)) return true;
        return false;
    }

    public List<greenEnergyTypes> getEnergies() {
        return energies;
    }

    public void setEnergies(List<greenEnergyTypes> energies) {
        this.energies = energies;
    }

    public Image getImageAgendaTile() {
        return imageAgendaTile;
    }

    public void setImageAgendaTile(Image imageAgendaTile) {
        this.imageAgendaTile = imageAgendaTile;
    }
}
