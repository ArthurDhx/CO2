package CO2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class ViewMenuActionHbox extends HBox {
    //TODO : A delete
    public Button btnTest;

    Model model ;

    // Les différents ChoiceDialog s'affichant selon l'action choisis
    ChoiceDialog<Continent> dialogProposerProjet;
    ChoiceDialog<Subvention> dialogSubvention;
    ChoiceDialog<Subvention> dialogDeplacerScientifiqueProjet;
    ChoiceDialog<SommetTile> dialogDeplacerScientifiqueSommet;
    ChoiceDialog<Scientifique> dialogChoisirScientifique;
    ChoiceDialog<Subvention> dialogMettreEnPlaceProjet;
    ChoiceDialog<Subvention> dialogConstruireCentrale;
    ChoiceDialog<String> dialogAcheterVendreCEP;
    ChoiceDialog<String> dialogChoisirRevenu;
    ChoiceDialog<String> dialogChoisirRecherche;
    ChoiceDialog<String> dialogActionScientifiqueAfterRecherche;
    ChoiceDialog<String> dialogActionScientifique;
    ChoiceDialog<String> dialogBuyCEPBy;
    ChoiceDialog<Continent> dialogBuyCEPByContinent;
    ChoiceDialog<LobbyCard> dialogJouerCarteLobby;
    ChoiceDialog<String> dialogMajeurMineur ;
    ChoiceDialog<OnuCard> dialogMarqueOnuCard ;

    // Les boutons associe aux actions principales
    Button btnActionPrincipale;
    Button btnProposerProjet;
    Button btnMettreProjet;
    Button btnConstruire;

    // Les boutons associe aux actions gratuites
    Button btnActionGratuite;

    Button btnMarche;

    Button btnDeplacerScientifique;
    Button btnScientifique1;
    Button btnScientifique2;
    Button btnScientifique3;
    Button btnScientifique4;
    Button btnDeplacerScientifiqToProject;
    Button btnDeplacerScientifiqToSommet;
    Button btnDeplacerScientifiqToReserve;

    Button btnMarquerPointOnu;
    Button btnJouerCarteLobby;

    // Button present dans le menu
    Button btnFinTour;
    Button btnCancelAction;
    Button btnCancelActionScientifique;

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
        btnDeplacerScientifique = new Button("Déplacer un scientifique");
        btnScientifique1 = new Button("Scientifique n°1");
        btnScientifique2 = new Button("Scientifique n°2");
        btnScientifique3 = new Button("Scientifique n°3");
        btnScientifique4 = new Button("Scientifique n°4");
        btnDeplacerScientifiqToProject = new Button("Déplacer le scientifque sur un projet");
        btnDeplacerScientifiqToSommet = new Button("Déplacer le scientifque sur un sommet");
        btnDeplacerScientifiqToReserve = new Button("Déplacer le scientifque dans la réserve");
        btnMarche = new Button("Marché au CEP");
        btnJouerCarteLobby = new Button("Jouer une carte lobby");
        btnMarquerPointOnu = new Button("Marquer les points d'une carte ONU");
        btnFinTour = new Button("Fin du tour");

        btnCancelAction = new Button("Annuler");
        btnCancelActionScientifique = new Button("Annuler");
        //TODO : A delete
        btnTest = new Button("Test");
        this.getChildren().addAll(btnActionPrincipale, btnActionGratuite,btnFinTour,btnTest);
    }

    /**
     * Affiche le menu pour choisir le scientifique à utiliser
     */
    public void displayActionChoixScientifique() {
        this.getChildren().removeAll(this.getChildren());

        List<Scientifique> scientifiques = model.getCurrentPLayer().getScientifiques();
        // si il y a un scientifiques et qu'il n'est pas sur un sommet ont peut le effectuer une action
        if (scientifiques.size() == 1 && scientifiques.get(0).getSommetTile() == null)  {
            this.getChildren().add(btnScientifique1);
        }
        else if (scientifiques.size() == 2) {
            if (scientifiques.get(0).getSommetTile() == null) this.getChildren().add(btnScientifique1);
            if (scientifiques.get(1).getSommetTile() == null) this.getChildren().add(btnScientifique2);
        }
        else if (scientifiques.size() == 3) {
            if (scientifiques.get(0).getSommetTile() == null) this.getChildren().add(btnScientifique1);
            if (scientifiques.get(1).getSommetTile() == null) this.getChildren().add(btnScientifique2);
            if (scientifiques.get(2).getSommetTile() == null) this.getChildren().add(btnScientifique3);
        } else if (scientifiques.size() == 4) {
            if (scientifiques.get(0).getSommetTile() == null) this.getChildren().add(btnScientifique1);
            if (scientifiques.get(1).getSommetTile() == null) this.getChildren().add(btnScientifique2);
            if (scientifiques.get(2).getSommetTile() == null) this.getChildren().add(btnScientifique3);
            if (scientifiques.get(3).getSommetTile() == null) this.getChildren().add(btnScientifique4);
        }
        this.getChildren().addAll(btnCancelActionScientifique);
    }

    /**
     * Affiche le menu des actions du scientifique
     */
    public void displayActionScientifique() {
        this.getChildren().removeAll(this.getChildren());
        //if (!actionFaite[0]) this.getChildren().add(btnDeplacerScientifiqToProject);
        Scientifique scientifique = model.getCurrentPLayer().getCurrentScientifique();
        if (scientifique.getSubvention() == null && scientifique.getSommetTile() == null ){
            // le  scientifique est dans la reserve, il peut aller que sur un projet
            this.getChildren().add(btnDeplacerScientifiqToProject);
        }
        if (scientifique.getSubvention() != null && scientifique.getSommetTile() == null ){
            // le  scientifique est sur un projet , il peut aller que sur un sommet, aller sur un autre projet et revenir à la réserve
            this.getChildren().addAll(btnDeplacerScientifiqToProject,btnDeplacerScientifiqToSommet,btnDeplacerScientifiqToReserve);
        }
        // Si le scientifique est sur un sommet alors il ne peut plus rien faire avant la fin du sommet = controllé dans displayActionGratuite();
        this.getChildren().addAll(btnCancelActionScientifique);
    }

    /**
     * Affiche le menu des actions principale
     */
    public void displayActionPrincipale() {
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
        dialogProposerProjet.setTitle("Proposer un projet");
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
                if((!subventionsInContinent.get(j).isEmpty()) && (!subventionsInContinent.get(j).getProject().isMisEnPlace())){
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
     * Affiche le menu permettant de choisir un projet à mettre en place
     */
    public void displayConstruireCentraleChoiceDialog(){
        //On récupère les projets
        Continent[] continent = model.getContinents();
        ArrayList<Subvention> subventions = new ArrayList<>();
        for(int i = 0; i<continent.length; i++){
            ArrayList<Subvention> subventionsInContinent = continent[i].getSubventions();
            for(int j = 0; j<subventionsInContinent.size(); j++){
                if(subventionsInContinent.get(j).getProject() != null && subventionsInContinent.get(j).getProject().isMisEnPlace() ){
                    subventions.add(subventionsInContinent.get(j));
                }
            }
        }
        System.out.println(subventions);
        //Si aucun projet n'est mis en place, on ne fait rien
        if(subventions.isEmpty()) return;
        dialogConstruireCentrale = new ChoiceDialog<Subvention>(
                subventions.get(0), // Choix par défaut
                subventions
        );
        dialogConstruireCentrale.setTitle("Construire une centrale");
        dialogConstruireCentrale.setHeaderText("Veuillez choisir un projet déjà mis en place");
        dialogConstruireCentrale.setContentText("Projet :");
    }

    /**
     * Affiche les actions gratuites disponible sur le menu
     */
    public void displayActionGratuite() {
        // Récupere les actions faite par le joueur
        boolean[] actionFaite = model.getCurrentPLayer().getActionGratuiteDone();
        this.getChildren().removeAll(this.getChildren());
        // si l'action n'est pas faite et que tous les scientifiques ne sont pas sur un sommet alors afficher l'action
        if (!actionFaite[0] && !model.getCurrentPLayer().isAllScientifiqueIsOnSommet()) this.getChildren().add(btnDeplacerScientifique);
        if (!actionFaite[2]) this.getChildren().add(btnMarche);
        if (!actionFaite[3]) this.getChildren().addAll(btnJouerCarteLobby,btnMarquerPointOnu);

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
                if(!subventionsInContinent.get(j).isEmpty() && !subventionsInContinent.get(j).isStaffed()){
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
            greenEnergyTypes energy =  model.getCurrentPLayer().getCurrentScientifique().getSubject().getEnergy();
            if(continent[i].getSommetTile().haveEnergy(energy) && !continent[i].getSommetTile().isStaffed(model.getCurrentPLayer().getCurrentScientifique().getSubject())) {
                //si le sommet à l'énergie du scientifique et que cette énergie n'est pas occupé alors il se déplace
                sommetTiles.add(continent[i].getSommetTile());
            }
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
     * Affiche le ChoiceDialog permettant de changer de scientifique
     */
    public void displayScientifiqueChoiceDialog(){
        List<Scientifique> scientifiques = model.getCurrentPLayer().getScientifiques();
        List<Scientifique> choices = new ArrayList<>();
        for(Scientifique sc: scientifiques){
            if(sc.getSommetTile() == null){
                choices.add(sc);
            }
        }
        if(choices.isEmpty()) return;
        dialogChoisirScientifique = new ChoiceDialog<Scientifique>(
                choices.get(0),
                choices
        );
        dialogChoisirScientifique.setTitle("Choisir un scientifique");
        dialogChoisirScientifique.setHeaderText("Veuillez choisir un scientifique");
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

        this.getChildren().add(btnTest);
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

        //TODO : A delete
        btnTest.setOnAction(handler);
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
        btnDeplacerScientifique.setOnAction(handler);
        btnScientifique1.setOnAction(handler);
        btnScientifique2.setOnAction(handler);
        btnScientifique3.setOnAction(handler);
        btnScientifique4.setOnAction(handler);
        btnDeplacerScientifiqToProject.setOnAction(handler);
        btnDeplacerScientifiqToSommet.setOnAction(handler);
        btnDeplacerScientifiqToReserve.setOnAction(handler);
        btnCancelActionScientifique.setOnAction(handler);
        btnMarche.setOnAction(handler);
        btnJouerCarteLobby.setOnAction(handler);
        btnMarquerPointOnu.setOnAction(handler);
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

    public void dialogChoisirRevenu(greenEnergyTypes energyType, int revenu) {
        // listes des possibilites de revenu
        List<String> repartitions = new ArrayList<>();
        if (revenu>1) {
            for (int i = 0; i < revenu+1; i++)
                repartitions.add((revenu - i) + " points de victoire - " + i + " argent");
        } else {
            repartitions.add("1 points de victoire - 0 argent");
            repartitions.add("0 points de victoire - 1 argent");
        }

        dialogChoisirRevenu = new ChoiceDialog<>(
                repartitions.get(0), repartitions
        );

        dialogChoisirRevenu.setTitle("Répartition du revenu pour l'expertise " + energyType);
        dialogChoisirRevenu.setHeaderText("Choisissez comment repartir vos " + revenu + " points de revenu");
        dialogChoisirRevenu.setContentText("Repartition: ");
    }

    /**
     * Après avoir mis en place un projet sur la case "recherche en collaboration"
     * mise en place du dialogue demandant si le joueur veut déplacer un scientifique ou en ajouter un à sa réserve
     */
    public void displayChoisirRechecheChoiceDialog(){
        if (model.getCurrentPLayer().isAllScientifiqueIsOnSommet()){
            dialogChoisirRecherche = new ChoiceDialog<>(
                    "Ajouter un scientifique à la réserve",
                    "Ajouter un scientifique à la réserve"
            );
        } else {
            dialogChoisirRecherche = new ChoiceDialog<>(
                    "Déplacer un scientifique",
                    "Déplacer un scientifique",
                    "Ajouter un scientifique à la réserve"
            );
        }

        dialogChoisirRecherche.setTitle("Recherche en collaboration");
        dialogChoisirRecherche.setHeaderText("Quelle action voulez-vous faire ?");
        dialogChoisirRecherche.setContentText("Choix: ");
    }

    /**
     * Affiche le choicedialog pour jouer la carte
     */
    public void displayPlayLobbyCardChoiceDialog(){
        List<LobbyCard> choices = model.getCurrentPLayer().getLobbyCards();
        dialogJouerCarteLobby = new ChoiceDialog<>(
                choices.get(0),
                choices
        );
        dialogJouerCarteLobby.setTitle("Jouer une carte de lobby");
        dialogJouerCarteLobby.setHeaderText("Quelle carte choissisez vous ?");
        dialogJouerCarteLobby.setContentText("Carte : ");
    }

    public void displayMarqueOnuChoiceDialog() {
        List<OnuCard> choices = model.getOnuCardsInGame();
        dialogMarqueOnuCard = new ChoiceDialog<>(
                choices.get(0),
                choices
        );
        dialogMarqueOnuCard.setTitle("Marquer les points du carte Onu");
        dialogMarqueOnuCard.setHeaderText("Quelle carte choissisez vous ?");
        dialogMarqueOnuCard.setContentText("Carte : ");
    }

    /**
     * Permet de choisir comment déplacer un scientifique après avoir mis un projet sur une case recherche en collaboration
     */
    public void displayActionScientifiqueChoice(){
        ArrayList<String> choices = new ArrayList<>();
        List<Scientifique> lstScientifique = model.getCurrentPLayer().getScientifiques();
        for (int i = 0; i < lstScientifique.size(); i++) {
            if (lstScientifique.get(i).getSommetTile() == null){
                choices.add("Scientifique n°" + (i+1));
            }
        }
        if (choices.isEmpty()){
            dialogActionScientifique = new ChoiceDialog<>("");
        } else{
            dialogActionScientifique = new ChoiceDialog<>(
                choices.get(0),
                choices
            );
        }

        dialogActionScientifique.setTitle("Choisir un scientifique");
        dialogActionScientifique.setHeaderText("Quel scientifique choisir ?");
        dialogActionScientifique.setContentText("Scientifiques : ");
    }

    /**
     * Permet de choisir comment déplacer un scientifique après avoir mis un projet sur une case recherche en collaboration
     */
    public void displayActionScientifiqueAfterRecherche(){
        ArrayList<String> choices = new ArrayList<>();
        Scientifique scientifique = model.getCurrentPLayer().getCurrentScientifique();
        if (scientifique.getSubvention() == null && scientifique.getSommetTile() == null ){
            // le  scientifique est dans la reserve, il peut aller que sur un projet
            this.getChildren().add(btnDeplacerScientifiqToProject);
            choices.add("Déplacer sur un projet");
        }
        if (scientifique.getSubvention() != null && scientifique.getSommetTile() == null ){
            // le  scientifique est sur un projet , il peut aller que sur un sommet, aller sur un autre projet et revenir à la réserve
            this.getChildren().addAll(btnDeplacerScientifiqToProject,btnDeplacerScientifiqToSommet,btnDeplacerScientifiqToReserve);
            choices.add("Déplacer sur un projet");
            choices.add("Déplacer sur un sommet");
            choices.add("Déplacer dans la réserve");
        }
        dialogActionScientifiqueAfterRecherche = new ChoiceDialog<>(
                choices.get(0),
                choices
        );
        dialogActionScientifiqueAfterRecherche.setTitle("Recherche en collaboration");
        dialogActionScientifiqueAfterRecherche.setHeaderText("Comment déplacer un scientifique ?");
        dialogActionScientifiqueAfterRecherche.setContentText("Choix: ");
    }

    /**
     * Permet de savoir si le joueur veut payer avec ses CEP ou avec ceux d'un continent qu'il controle
     */
    public void displayBuyCEPBy(){
        ArrayList<String> choices = new ArrayList<>();
        choices.add("ma réserve");
        if(!model.getCurrentPLayer().getContinentsControlles().isEmpty()) choices.add("la réserve d'un continent controllé");
        dialogBuyCEPBy = new ChoiceDialog<>(
                choices.get(0),
                choices

        );
        dialogBuyCEPBy.setTitle("Mettre en place un projet");
        dialogBuyCEPBy.setHeaderText("Vous devez payer 1 CEP pour mettre en place un projet.");
        dialogBuyCEPBy.setContentText("Payer 1 CEP depuis: ");
    }

    /**
     * Permet de savoir avec quel continent controllé il compte payer les CEP
     */
    public void displayBuyCEPByContinent(){
        List<Continent> choices = model.getCurrentPLayer().getContinentsControlles();
        dialogBuyCEPByContinent = new ChoiceDialog<>(
                choices.get(0),
                choices
        );
        dialogBuyCEPByContinent.setTitle("Mettre en place un projet");
        dialogBuyCEPByContinent.setHeaderText("Avec les CEP de quel continent voulez vouz payer ?");
        dialogBuyCEPByContinent.setContentText("Choix: ");
    }

    public void displayMajeurMineurChoiceDialog() {
        String[] choice = {"Majeur","Mineur"};
        dialogMajeurMineur = new ChoiceDialog<>(
                choice[0],
                choice
        );
        dialogMajeurMineur.setTitle("Jouer une carte lobby");
        dialogMajeurMineur.setHeaderText("Choisir le type :");
    }


}
