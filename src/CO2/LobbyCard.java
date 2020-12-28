package CO2;

public class LobbyCard<T> {

    // action de lobby majeur a faire
    private lobbyActionTypes lobbyActionType;

    // recompense de lobby mineur
    private lobbyMineurTypes lobbyMineurType;

    // complement
    private T complement;

    // variables toString
    private String action;
    private String recompense;

    public LobbyCard(lobbyActionTypes lobbyActionType, T complement, lobbyMineurTypes lobbyMineurType) {
        this.lobbyActionType = lobbyActionType;
        this.complement = complement;
        this.lobbyMineurType = lobbyMineurType;
        init();
    }

    /**
     * Change les attributs a afficher
     */
    private void init() {
        switch (lobbyActionType) {
            case PROPOSER:
                action = " proposé un projet sur ";
                if (complement instanceof Continent) {
                    action += "le continent " + ((Continent) complement).getName();
                    recompense = " 3 CEP";
                }
                if (complement instanceof subventionTypes) {
                    if (subventionTypes.ARGENT.equals(complement)) {
                        action += "la subvention argent";
                        recompense = " 3€";
                    } else if (subventionTypes.RESSOURCE.equals(complement)) {
                        action += "la subvention ressources technologiques";
                        recompense = " 1 ressource technologique";
                    } else if (subventionTypes.RECHERCHE.equals(complement)) {
                        action += "la subvention recherche";
                        recompense = " 1 nouveau scientifique";
                    }
                }
                break;
            case METTRE:
                action = " mis en place un projet d'energie " + complement;
                recompense = " 3 cubes de ressources technologiques";
                break;
            case CONSTRUIRE:
                action = " construit une centrale d'energie " + complement;
                recompense = " 3€ remboursés sur le coût de construction";
                break;
            case SOMMET:
                action = " un scientifique sur un sommet d'energie " + complement;
                recompense = " 1 d'expertise dans ce type d'energie";
                break;
            case MARCHE_ACHAT:
                action = " fait un achat de CEP au marché";
                recompense = " 2 points de victoire";
                break;
            case MARCHE_VENTE:
                action = " fait une vente de CEP au marché";
                recompense = " 3€";
                break;
        }
    }

    public lobbyActionTypes getLobbyActionType() {
        return lobbyActionType;
    }

    public void setLobbyActionType(lobbyActionTypes lobbyActionType) {
        this.lobbyActionType = lobbyActionType;
    }

    public T getComplement() {
        return complement;
    }

    public void setComplement(T complement) {
        this.complement = complement;
    }

    public lobbyMineurTypes getLobbyMineurType() {
        return lobbyMineurType;
    }

    public void setLobbyMineurType(lobbyMineurTypes lobbyMineurType) {
        this.lobbyMineurType = lobbyMineurType;
    }

    @Override
    public String toString() {
        return "Vous devez avoir"+ action +" pour obtenir la récompense de lobby majeur: "+ recompense +
                "\nOu choisissez, sans condition, la recompense de lobby mineur: " + lobbyMineurType.description;
    }
}
