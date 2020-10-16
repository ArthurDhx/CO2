package CO2;

public class Subvention {

    private String name;
    //effet
    private String effet;
    private TilesSolarProject tilesSolarProject;


    public Subvention() {}

    public Subvention(String name, String effet) {
        this.name = name;
        this.effet = effet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
