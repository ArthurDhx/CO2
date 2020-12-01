package CO2;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class AgendaTile {

    // Type d'energie
    private List<GreenEnergyTypes> energies;
    private Image imageAgendaTile;

    public AgendaTile(GreenEnergyTypes energy1, GreenEnergyTypes energy2, GreenEnergyTypes energy3, Image imageAgendaTile) {
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
    public boolean isPossiblePlacement(GreenEnergyTypes energy) {
        if (energies.contains(energy)) return true;
        return false;
    }

    public List<GreenEnergyTypes> getEnergies() {
        return energies;
    }

    public void setEnergies(List<GreenEnergyTypes> energies) {
        this.energies = energies;
    }

    public Image getImageAgendaTile() {
        return imageAgendaTile;
    }

    public void setImageAgendaTile(Image imageAgendaTile) {
        this.imageAgendaTile = imageAgendaTile;
    }
}
