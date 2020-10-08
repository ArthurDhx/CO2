package CO2;

public class Model {

    final static int STATE_INIT = 1; // Title
	final static int STATE_PLAY = 2; // Game

	int state;
    int width;
    int height;
	int nbJoueur;

	//Tableau contenant les 6 tuiles de projet solaire
	TilesSolarProject[] tilesSolarProjects;

    public Model() {
		state = STATE_INIT;
		width = 1000;
		height = 600;
		nbJoueur = 1;
    }

    //Initialisation des attriubuts
    public void init(){
    	// Initialisation du tableau contenant les 6 tuiles de projet solaire
		tilesSolarProjects = new TilesSolarProject[6];
		for(int i = 0; i<tilesSolarProjects.length; i++){
			tilesSolarProjects[i] = new TilesSolarProject();
		}
	}

	//Permet de retourner les nombres de tuils projet solaire restantes dans la pile
	public int getNbSolarProject(){
    	int res = 0;
    	for(int i = 0; i<tilesSolarProjects.length; i++){
    		if(tilesSolarProjects[i] != null){
    			res += 1;
			}
		}
    	return res;
	}

    public void startGame() {
		state = STATE_PLAY;
    }
}
