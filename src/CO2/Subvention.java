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
    private int index ;
    // L'index ou est placer la subvention sur le continent
    Continent continent;
    //Le continent parent de la subvention

    public Subvention(int index) {
        this.index = index ;
    }

    public String getName() { return name; }

    public int getIndex() {
        return index;
    }

    public Continent getContinent(){ return this.continent;}

    public void setName(String name) {
        this.name = name;
    }

    public void setContinent(Continent continent){ this.continent = continent; }

    // ajoute à cette subvention
    public void addTilesSolarProject(TilesSolarProject tilesSolarProject){
        this.tilesSolarProject = tilesSolarProject;
        // la subvention n'est plus vide
        empty = false;
    }

    @Override
    public String toString() {
        return this.continent.getName() +": "+ this.name ;
    }

    public boolean isEmpty() { return empty; }

    public TilesSolarProject getTilesSolarProject() {
        return tilesSolarProject;
    }
}
