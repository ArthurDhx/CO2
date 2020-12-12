package CO2;

public class LobbyCard<T> {

    // action de lobby majeur a faire
    private lobbyActionTypes lobbyActionType;

    // complement
    private T complement;

    public LobbyCard(lobbyActionTypes lobbyActionType, T complement) {
        this.lobbyActionType = lobbyActionType;
        this.complement = complement;
    }

    /**
     * Appeler par le joueur quand il joue un carte
     */
    public void play() {
        switch (lobbyActionType) {
            case PROPOSER:
                break;
            case METTRE:
                break;
            case CONSTRUIRE:
                break;
            case SOMMET:
                break;
            case MARCHE_ACHAT:
                break;
            case MARCHE_VENTE:
                break;
        }
    }

    @Override
    public String toString() {
        return "LobbyCard{" +
                "typeLobbyAction=" + lobbyActionType +
                ", complement=" + complement +
                '}';
    }
}
