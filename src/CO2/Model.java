package CO2;

public class Model {

    final static int STATE_INIT = 1; // Title
	final static int STATE_PLAY = 2; // Game

	int state;
    int width;
    int height;
	int nbJoueur;

    public Model() {
		state = STATE_INIT;
		width = 1000;
		height = 600;
		nbJoueur = 1;
    }

    public void startGame() {
		state = STATE_PLAY;
    }
}
