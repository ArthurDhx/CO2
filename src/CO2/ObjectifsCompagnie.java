package CO2;

import java.util.HashMap;

public class ObjectifsCompagnie {
    //Permet d'utiliser l'id au lieu du string pour identifier l'objet
    int id;
    //Ce qui va être affiché à l'écran
    String libelle;
    //Permet de calculer au fur et a mesure le nombre de point remporter
    //à voir si on garde ce systeme ou non...
    int nbPointRemporte;

    /**
     * Constructeur pour créer les objectifs dans le model (fonction initObjectifCompagnie)
     * @param id
     * @param libelle
     */
    public ObjectifsCompagnie(int id, String libelle){
        this.id = id;
        this.libelle = libelle;
        nbPointRemporte = 0;
    }

    /**
     * getter et setter
     */
    public int getId(){ return id; }
    public void setId(int i){ id = i; }
    public String getLibelle(){ return libelle; }
    public void setLibelle(String s){ libelle = s; }
}
