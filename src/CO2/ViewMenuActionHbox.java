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
    ChoiceDialog<Subvention> dialogDeplacerScientifique;
    ChoiceDialog<Scientifique> dialogChoisirScientifique;


    // Les boutons associe aux actions principales
    Button btnActionPrincipale;
    Button btnProposerProjet;
    Button btnMettreProjet;
    Button btnConstruire;

    // Les boutons associe aux actions gratuites
    Button btnActionGratuite;
    Button btnMarche;
    Button btnDeplacerScientifiq;
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
        btnDeplacerScientifiq = new Button("Déplacer un scientifque");
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
        // TODO ; implementer une méthdoe dans le modele pour récupere les subvention libre
        // Récupere les subvention disponible dans le continent
        ArrayList<Subvention> subventions = continentChoisi.getSubventions();
        dialogSubvention = new ChoiceDialog<Subvention>(
                subventions.get(0), // choix par défaut
                subventions
        );
        dialogSubvention.setTitle("Choisir une subvention");
        dialogSubvention.setHeaderText("Veuillez choisir une Subvention");
        dialogSubvention.setContentText("Subvention :");
    }

    /**
     * Affiche les actions gratuites disponible sur le menu
     */
    public void displayActionGratuite() {
        // Récupere les actions faite par le joueur
        boolean[] actionFaite = model.getCurentPLayer().getActionGratuiteDone();
        this.getChildren().removeAll(this.getChildren());
        // Si l'action n'a pas déjà été faite affiche le bouton liée a l'action
        if (!actionFaite[0]) this.getChildren().add(btnDeplacerScientifiq);
        if (!actionFaite[1]) this.getChildren().add(btnMarche);
        if (!actionFaite[2]) this.getChildren().add(btnJouerCarte);
        this.getChildren().add(btnCancelAction);
    }

    /**
     * Affiche le ChoiceDialog qui permet de déplacer un scientifique
     */
    public void displayDeplacerScientifiqueChoiceDialog(){
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
        if(subventions.isEmpty()) return;
        dialogDeplacerScientifique = new ChoiceDialog<Subvention>(
                subventions.get(0), // Choix par défaut
                subventions
        );
        dialogDeplacerScientifique.setTitle("Déplacer un scientifique");
        dialogDeplacerScientifique.setHeaderText("Veuiller choisir un projet");
        dialogDeplacerScientifique.setContentText("Projet:");
    }

    /**
     * Affiche le ChoiceDialog permettant de récuperer l'expertise grace au scientifiq en fin de tour
     */
    public void displayFinTourScientifiqueChoiceDialog(){
        List<Scientifique> scientifiques = model.getCurentPLayer().getScientifiques();
        List<Scientifique> scientifiquesSurProjet = new ArrayList<>();
        for(Scientifique sc: scientifiques){
            if(sc != null){
                scientifiquesSurProjet.add(sc);
            }
        }
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
        if (!model.getCurentPLayer().isActionPrincipaleDone()) this.getChildren().add(btnActionPrincipale);
        if (!model.getCurentPLayer().isAllActionGratuiteDone()) this.getChildren().add(btnActionGratuite);
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
        btnDeplacerScientifiq.setOnAction(handler);
        btnMarche.setOnAction(handler);
        btnJouerCarte.setOnAction(handler);
    }
}
