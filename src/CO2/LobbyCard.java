package CO2;

public class LobbyCard<T> {

    // action de lobby majeur a faire
    private typeLobbyAction typeLobbyAction;

    // complement
    private T complement;

    public LobbyCard(typeLobbyAction typeLobbyAction, T complement) {
        this.typeLobbyAction = typeLobbyAction;
        this.complement = complement;
    }

    @Override
    public String toString() {
        return "LobbyCard{" +
                "typeLobbyAction=" + typeLobbyAction +
                ", complement=" + complement +
                '}';
    }
}
