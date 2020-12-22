package CO2;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class PisteExpertise {
    private greenEnergyTypes type;
    private Color color;
    private int max;
    private List<CasePisteExpertise> piste;

    public PisteExpertise(greenEnergyTypes type, int max, Color color) {
        this.type = type;
        this.max = max;
        this.color = color;
        initPiste();
    }

    /**
     * Initialise la piste d'expertise
     * avec les bon bonus
     * cf. plateau de jeu
     */
    private void initPiste() {
        piste = new ArrayList<>();

        switch (type) {
            case SOLAR:
                piste.add(new CasePisteExpertise(0, BonusExpertise.NULL));
                piste.add(new CasePisteExpertise(1, BonusExpertise.CONSTRUCT));
                piste.add(new CasePisteExpertise(1, BonusExpertise.NULL));
                piste.add(new CasePisteExpertise(3, BonusExpertise.NULL));
                piste.add(new CasePisteExpertise(3, BonusExpertise.RESOURCE));
                piste.add(new CasePisteExpertise(4, BonusExpertise.NULL));
                piste.add(new CasePisteExpertise(4, BonusExpertise.RESOURCE));
                piste.add(new CasePisteExpertise(5, BonusExpertise.EXPERTISE));
                piste.add(new CasePisteExpertise(5, BonusExpertise.NULL));
                piste.add(new CasePisteExpertise(6, BonusExpertise.CEP));
                break;
            case BIOMASS:
                piste.add(new CasePisteExpertise(1, BonusExpertise.CONSTRUCT));
                piste.add(new CasePisteExpertise(2, BonusExpertise.NULL));
                piste.add(new CasePisteExpertise(3, BonusExpertise.NULL));
                piste.add(new CasePisteExpertise(3, BonusExpertise.SOLAR));
                piste.add(new CasePisteExpertise(4, BonusExpertise.NULL));
                piste.add(new CasePisteExpertise(4, BonusExpertise.REFORESTATION));
                piste.add(new CasePisteExpertise(5, BonusExpertise.NULL));
                piste.add(new CasePisteExpertise(6, BonusExpertise.CEP));
                piste.add(new CasePisteExpertise(6, BonusExpertise.EXPERTISE));
                piste.add(new CasePisteExpertise(7, BonusExpertise.NULL));
                break;
            case RECYCLING:
                piste.add(new CasePisteExpertise(1, BonusExpertise.CONSTRUCT));
                piste.add(new CasePisteExpertise(2, BonusExpertise.NULL));
                piste.add(new CasePisteExpertise(3, BonusExpertise.NULL));
                piste.add(new CasePisteExpertise(3, BonusExpertise.FUSION));
                piste.add(new CasePisteExpertise(4, BonusExpertise.NULL));
                piste.add(new CasePisteExpertise(4, BonusExpertise.REFORESTATION));
                piste.add(new CasePisteExpertise(5, BonusExpertise.NULL));
                piste.add(new CasePisteExpertise(6, BonusExpertise.CEP));
                piste.add(new CasePisteExpertise(6, BonusExpertise.EXPERTISE));
                piste.add(new CasePisteExpertise(7, BonusExpertise.NULL));
                break;
            case FUSION:
                piste.add(new CasePisteExpertise(0, BonusExpertise.NULL));
                piste.add(new CasePisteExpertise(1, BonusExpertise.CONSTRUCT));
                piste.add(new CasePisteExpertise(1, BonusExpertise.NULL));
                piste.add(new CasePisteExpertise(3, BonusExpertise.NULL));
                piste.add(new CasePisteExpertise(3, BonusExpertise.RESOURCE));
                piste.add(new CasePisteExpertise(4, BonusExpertise.CEP));
                piste.add(new CasePisteExpertise(4, BonusExpertise.EXPERTISE));
                piste.add(new CasePisteExpertise(5, BonusExpertise.NULL));
                piste.add(new CasePisteExpertise(5, BonusExpertise.CEP));
                piste.add(new CasePisteExpertise(6, BonusExpertise.NULL));
                break;
            case REFORESTATION:
                piste.add(new CasePisteExpertise(0, BonusExpertise.NULL));
                piste.add(new CasePisteExpertise(1, BonusExpertise.NULL));
                piste.add(new CasePisteExpertise(1, BonusExpertise.CONSTRUCT));
                piste.add(new CasePisteExpertise(2, BonusExpertise.NULL));
                piste.add(new CasePisteExpertise(2, BonusExpertise.CEP));
                piste.add(new CasePisteExpertise(3, BonusExpertise.NULL));
                piste.add(new CasePisteExpertise(3, BonusExpertise.CEP));
                piste.add(new CasePisteExpertise(4, BonusExpertise.EXPERTISE));
                piste.add(new CasePisteExpertise(4, BonusExpertise.NULL));
                piste.add(new CasePisteExpertise(5, BonusExpertise.CEP));
                break;
        }
    }

    /**
     * Donne le bonus s'il existe pour un niveau d'expertise donne
     * @param expertise niveau a tester
     * @return
     */
    public BonusExpertise getSpecialBonus(int expertise) {
        // bonus de la case choisie
        BonusExpertise bonus = piste.get(expertise-1).getBonus();
        // si il y a un bonus different de l'autorisation de construction
        if (!bonus.equals(BonusExpertise.NULL) && !bonus.equals(BonusExpertise.CONSTRUCT))
            return bonus;
        return null;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public greenEnergyTypes getType() {
        return type;
    }

    public void setType(greenEnergyTypes type) {
        this.type = type;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public List<CasePisteExpertise> getPiste() {
        return piste;
    }

    public void setPiste(List<CasePisteExpertise> piste) {
        this.piste = piste;
    }
}

enum BonusExpertise {
    NULL("pas de bonus"),
    CONSTRUCT("Autorisation de construire une centrale du type indiqué"),
    SOLAR("1 d'expertise solaire"),
    BIOMASS("1 d'expertise biomasse"),
    RECYCLING("1 d'expertise recyclage"),
    FUSION("1 d'expertise fusion"),
    REFORESTATION("1 d'expertise reforestation"),
    RESOURCE("1 cube de ressource technologique"),
    CEP("1 CEP sur le continent choisi"),
    EXPERTISE("1 d'expertise dans l'energie choisie");

    String description;

    BonusExpertise(String description) {
        this.description = description;
    }
}

class CasePisteExpertise {
    // le numero en haut de la case
    private int numero;
    // image du bonus de la case
    private Image imageBonus = null;
    // bonus de la case
    private BonusExpertise bonus;

    public CasePisteExpertise(int numero, BonusExpertise bonus) {
        this.numero = numero;
        this.bonus = bonus;
        if (bonus != BonusExpertise.NULL)
            imageBonus = new Image(getClass().getResourceAsStream("images/BonusExpertise/"+bonus+".png"));
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Image getImageBonus() {
        return imageBonus;
    }

    public void setImageBonus(Image imageBonus) {
        this.imageBonus = imageBonus;
    }

    public BonusExpertise getBonus() {
        return bonus;
    }

    public void setBonus(BonusExpertise bonus) {
        this.bonus = bonus;
    }
}
