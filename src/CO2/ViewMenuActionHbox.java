package CO2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class ViewMenuActionHbox extends HBox {
    Model model ;

    // Les différents ChoiceDialog s'affichant selon l'action choisis
    ChoiceDialog<Continent> dialogProposerProjet;
    ChoiceDialog<Subvention> dialogSubvention;
    ChoiceDialog<Subvention> dialogDeplacerScientifiqueProjet;
    ChoiceDialog<SommetTile> dialogDeplacerScientifiqueSommet;
    ChoiceDialog<Scientifique> dialogChoisirScientifique;
    ChoiceDialog<Subvention> dialogMettreEnPlaceProjet;
    ChoiceDialog<String> dialogAcheterVendreCEP;

    // Les boutons associe aux actions principales
    Button btnActionPrincipale;
    Button btnProposerProjet;
    Button btnMettreProjet;
    Button btnConstruire;

    // Les boutons associe aux actions gratuites
    Button btnActionGratuite;
    Button btnMarche;
    Button btnDeplacerScientifiqToProject;
    Button btnDeplacerScientifiqToSommet;
    Button btnJouerCarte;

    // Button present dans le menu
    Button btnFinTour;
    Button btnCancelAction;

    public ViewMenuActionHbox(Model model) {
        super(10);
        this.model = model ;
    }

    /**
     * Initialise le Menu des différentes actions possibles en jeu
     */
    public void init() {
        btnActionPrincipale = new Button("Action Principale");
        btnConstruire = new Button("Construire une centrale");
        btnProposerProjet = new Button("Proposer un projet");
        btnMettreProjet = new Button("Mettre en place un projet");

        btnActionGratuite = new Button("Action Gratuite");
        btnDeplacerScientifiqToProject = new Button("Déplacer un scientifque sur un projet");
        btnDeplacerScientifiqToSommet = new Button("Déplacer un scientifque sur un sommet");
        btnMarche = new Button("Marché au CEP");
        btnJouerCarte = new Button("Jouer une carte");
        btnFinTour = new Button("Fin du tour");

        btnCancelAction = new Button("Annuler");
        this.getChildren().addAll(btnActionPrincipale, btnActionGratuite,btnFinTour);

        btnFinTour = new Button("Fin du tour");
    }

    /**
     * Affiche le menu des actions principale a l'ecran
     */
    public void displayActionPrincipale() {
        // Enleve tout les elements present dans le menu de la fénetre
        this.getChildren().removeAll(this.getChildren());
        this.getChildren().addAll(btnProposerProjet,btnMettreProjet,btnConstruire,btnCancelAction);
    }

    /**
     * Affiche le ChoiceDialog qui permet de proposer un projet
     */
    public void displayProposerProjetChoiceDialog() {
        // Récupere les différents continent
        Continent[] continent = model.getContinents();
        // Creer les differents choix disponible
        dialogProposerProjet = new ChoiceDialog<Continent>(
                continent[0], // Choix par défaut
                continent
        );
        dialogProposerProjet.setTitle("Mettre en place un projet");
        dialogProposerProjet.setHeaderText("Veuillez choisir un continent");
        dialogProposerProjet.setContentText("Continent:");
    }

    /**
     * Affiche les Subvention disponible sur le continent choisi
     * @param continentChoisi Le continent choisi par l'utilisateur
     */
    public void displayChoisirSubventionChoiceDialog(Continent continentChoisi) {
        // Récupere les subvention disponible dans le continent
        ArrayList<Subvention> subventions = continentChoisi.getEmptySubventions();
        dialogSubvention = new ChoiceDialog<Subvention>(
                subventions.get(0), // choix par défaut
                subventions
        );
        dialogSubvention.setTitle("Choisir une subvention");
        dialogSubvention.setHeaderText("Veuillez choisir une Subvention");
        dialogSubvention.setContentText("Subvention :");
    }

    /**
     * Affiche le menu permettant de choisir un projet à mettre en place
     */
    public void displayMettreEnPlaceProjetChoiceDialog(){
        //On récupère les projets
        Continent[] continent = model.getContinents();
        ArrayList<Subvention> subventions = new ArrayList<>();
        for(int i = 0; i<continent.length; i++){
            ArrayList<Subvention> subventionsInContinent = continent[i].getSubventions();
            for(int j = 0; j<subventionsInContinent.size(); j++){
                if(!subventionsInContinent.get(j).isEmpty()){
                    subventions.add(subventionsInContinent.get(j));
                }
            }
        }
        System.out.println(subventions);
        //Si aucun projet n'est mis en place, on ne fait rien
        if(subventions.isEmpty()) return;
        dialogMettreEnPlaceProjet = new ChoiceDialog<Subvention>(
                subventions.get(0), // Choix par défaut
                subventions
        );
        dialogMettreEnPlaceProjet.setTitle("Mettre en place un projet");
        dialogMettreEnPlaceProjet.setHeaderText("Veuillez choisir un projet");
        dialogMettreEnPlaceProjet.setContentText("Projet :");
    }

    /**
     * Affiche les actions gratuites disponible sur le menu
     */
    public void displayActionGratuite() {
        // Récupere les actions faite par le joueur
        boolean[] actionFaite = model.getCurrentPLayer().getActionGratuiteDone();
        this.getChildren().removeAll(this.getChildren());
        // Si l'action n'a pas déjà été faite affiche le bouton liée a l'action
        if (!actionFaite[0]) this.getChildren().add(btnDeplacerScientifiqToProject);
        if (model.getCurrentPLayer().getCurrentScientifique().getSubvention() != null){
            if (!actionFaite[1]) this.getChildren().add(btnDeplacerScientifiqToSommet);
        }
        if (!actionFaite[2]) this.getChildren().add(btnMarche);
        if (!actionFaite[3]) this.getChildren().add(btnJouerCarte);

        this.getChildren().add(btnCancelAction);
    }

    /**
     * Affiche le ChoiceDialog qui permet de déplacer un scientifique sur un projet
     */
    public void displayDeplacerScientifiqueChoiceDialog(){
        //On récupère les projets
        Continent[] continent = model.getContinents();
        ArrayList<Subvention> subventions = new ArrayList<>();
        for(int i = 0; i<continent.length; i++){
            ArrayList<Subvention> subventionsInContinent = continent[i].getSubventions();
            for(int j = 0; j<subventionsInContinent.size(); j++){
                if(!subventionsInContinent.get(j).isEmpty()){
                    subventions.add(subventionsInContinent.get(j));
                }
            }
        }
        System.out.println(subventions);
        //Si aucun projet n'est mis en place, on ne fait rien
        if(subventions.isEmpty()) return;
        dialogDeplacerScientifiqueProjet = new ChoiceDialog<Subvention>(
                subventions.get(0), // Choix par défaut
                subventions
        );
        dialogDeplacerScientifiqueProjet.setTitle("Déplacer un scientifique");
        dialogDeplacerScientifiqueProjet.setHeaderText("Veuiller choisir un projet");
        dialogDeplacerScientifiqueProjet.setContentText("Projet:");
    }

    /**
     * Affiche le ChoiceDialog qui permet de déplacer un scientifique sur un sommet
     */
    public void displayDeplacerScientifiqueSommetChoiceDialog(){
        //On récupère les sommets
        Continent[] continent = model.getContinents();
        ArrayList<SommetTile> sommetTiles = new ArrayList<>();
        for(int i = 0; i< continent.length; i++){
            sommetTiles.add(continent[i].getSommetTile());
        }
        System.out.println(sommetTiles);
        //Si aucun sommet n'est mis en place, on ne fait rien

        dialogDeplacerScientifiqueSommet = new ChoiceDialog<SommetTile>(
                sommetTiles.get(0), // Choix par défaut
                sommetTiles
        );
        dialogDeplacerScientifiqueSommet.setTitle("Déplacer un scientifique");
        dialogDeplacerScientifiqueSommet.setHeaderText("Veuiller choisir un sommet");
        dialogDeplacerScientifiqueSommet.setContentText("Sommets :");
    }

    /**
     * Affiche le ChoiceDialog permettant de récuperer l'expertise grace au scientifiq en fin de tour
     */
    public void displayFinTourScientifiqueChoiceDialog(){
        List<Scientifique> scientifiques = model.getCurrentPLayer().getScientifiques();
        List<Scientifique> scientifiquesSurProjet = new ArrayList<>();
        for(Scientifique sc: scientifiques){
            if(sc.getContinent() != null){
                scientifiquesSurProjet.add(sc);
            }
        }
        if(scientifiquesSurProjet.isEmpty()) return;
        dialogChoisirScientifique = new ChoiceDialog<Scientifique>(
            scientifiquesSurProjet.get(0),
            scientifiquesSurProjet
        );
        dialogChoisirScientifique.setTitle("Choisir un scientifique");
        dialogChoisirScientifique.setHeaderText("Veuillez choisir un scientifique pour y récupérer l'expertise");
        dialogChoisirScientifique.setContentText("Scientifiques: ");
    }

    /**
     * Reset la hbox a son état initiale selon les actions disponible par le joueur
     */
    public void resetHbox() {
        this.getChildren().removeAll(this.getChildren());
        if (!model.getCurrentPLayer().isActionPrincipaleDone()) this.getChildren().add(btnActionPrincipale);
        if (!model.getCurrentPLayer().isAllActionGratuiteDone()) this.getChildren().add(btnActionGratuite);
        this.getChildren().add(btnFinTour);
    }

    /**
     * Associe le controlleur aux élement précise dans la fonction
     * @param handler Le controller a associé au elements
     */
    public void setButtonActionControler(EventHandler<ActionEvent> handler) {
        btnActionGratuite.setOnAction(handler);
        btnActionPrincipale.setOnAction(handler);
        btnCancelAction.setOnAction(handler);
        btnFinTour.setOnAction(handler);
    }

    /**
     * Associe le controlleur aux button d'action princiapale
     * @param handler Le controlleur a associé
     */
    public void setButtonActionPrincipaleControler(EventHandler<ActionEvent> handler) {
        btnProposerProjet.setOnAction(handler);
        btnMettreProjet.setOnAction(handler);
        btnConstruire.setOnAction(handler);
    }

    /**
     * Associe le controlleur aux button d'action gratuite
     * @param handler Le controlleur a associé
     */
    public void setButtonActionGratuiteControler(EventHandler<ActionEvent> handler) {
        btnDeplacerScientifiqToProject.setOnAction(handler);
        btnDeplacerScientifiqToSommet.setOnAction(handler);
        btnMarche.setOnAction(handler);
        btnJouerCarte.setOnAction(handler);
    }

    /**
     * Permet de choisir si l'on veut acheter ou vendre 1 CEP
     */
    public void displayMarcheCEP(){
        dialogAcheterVendreCEP = new ChoiceDialog<String>(
                "Acheter",
                "Acheter",
                        "Vendre"
        );
        dialogAcheterVendreCEP.setTitle("Marché au CEP");
        dialogAcheterVendreCEP.setHeaderText("Voulez-vous acheter ou vendre 1 CEP ?");
        dialogAcheterVendreCEP.setContentText("Choix: ");
    }

}
