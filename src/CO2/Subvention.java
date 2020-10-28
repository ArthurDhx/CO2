package CO2;

public class Subvention {

    private String name;
    // l'effet produit par la subvention
    private String effect;
    // peut contenir une tuile pour un projet solaire
    private TilesSolarProject tilesSolarProject;
    private boolean empty = true;
    // si la case est vide = true
    // si la case contient une tuile = false;

    public Subvention() {}

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public void hasTilesSolarProject(TilesSolarProject tilesSolarProject){
        // ajout d'une tuile sur le projet sur la case permettant la subvention
        this.tilesSolarProject = tilesSolarProject;
        // la case permmettant la subvention se remplit par la tuile
        empty = false;
    }

    @Override
    public String toString() {
        return name ;
    }

    public boolean isEmpty() { return empty; }
}
