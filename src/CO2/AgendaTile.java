package CO2;

import javafx.scene.image.Image;

public class AgendaTile {

    // Type d'energie
    private String[] energies;
    private Image imageAgendaTile;

    public AgendaTile(String energy1, String energy2, String energy3, Image imageAgendaTile) {
        this.energies = new String[3];
        energies[0] = energy1;
        energies[1] = energy2;
        energies[2] = energy3;
        this.imageAgendaTile = imageAgendaTile;
    }

    /**
     * @param energy {"Solar","Biomass","Fusion","Recycling","Reforestation"}
     * @return true si energy est dans la liste des energies acceptables
     */
    public boolean isPossiblePlacement(String energy) {
        for (String s : energies) {
            if (s.equals(energy)) return true;
        }
        return false;
    }

    public String[] getEnergies() {
        return energies;
    }

    public void setEnergies(String[] energies) {
        this.energies = energies;
    }

    public Image getImageAgendaTile() {
        return imageAgendaTile;
    }

    public void setImageAgendaTile(Image imageAgendaTile) {
        this.imageAgendaTile = imageAgendaTile;
    }
}
