package CO2;

public class Subvention {

    private String nom;
    //effet
    private String effet;
    private TilesSolarProject tilesSolarProject;

    public Subvention(String nom) {
        this.nom = nom;
    }

    public Subvention(String nom, String effet) {
        this.nom = nom;
        this.effet = effet;
    }
}
