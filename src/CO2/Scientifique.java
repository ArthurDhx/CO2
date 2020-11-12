package CO2;

public class Scientifique {
    /*
    Un scientifique est assigné à un continent et à une case subvention où un projet et mis en place
     */
    private Continent continent; // Si null, alors il est dans la base du joueur
    private Subvention subvention;
    private SommetTile sommetTile;

    public Scientifique(){
        this.continent = null;
        this.subvention = null;
        this.sommetTile = null;
    }

    public Continent getContinent(){
        return this.continent;
    }

    public Subvention getSubvention() {
        return subvention;
    }

    public SommetTile getSommetTile() {
        return sommetTile;
    }

    public void setContinent(Continent continent){
        this.continent = continent;
    }

    public void setSubvention(Subvention subvention){
        this.subvention = subvention;
    }

    public void setSommetTile(SommetTile sommetTile){
        this.sommetTile = sommetTile;
    }

    public String toString(){
        return this.subvention.toString();
    }
}
