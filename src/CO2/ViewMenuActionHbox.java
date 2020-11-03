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
    Button btnActionPrincipale;
    Button btnProposerProjet;
    ChoiceDialog<Continent> dialogProposerProjet;
    ChoiceDialog<Subvention> dialogSubvention;
    Button btnMettreProjet;
    Button btnConstruire;

    Button btnActionGratuite;
    Button btnDeplacerScientifiq;
    ChoiceDialog<Subvention> dialogDeplacerScientifique;
    Button btnMarche;
    Button btnJouerCarte;

    Button btnFinTour;
    ChoiceDialog<Scientifique> dialogChoisirScientifique;
    Button btnCancelAction;

    public ViewMenuActionHbox(Model model) {
        super(10);
        this.model = model ;
    }

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

    public void displayActionPrincipale() {
        this.getChildren().removeAll(this.getChildren());
        this.getChildren().addAll(btnProposerProjet,btnMettreProjet,btnConstruire,btnCancelAction);
    }

    public void displayActionGratuite() {
        boolean[] actionFaite = model.getCurentPLayer().getActionGratuiteDone();
        this.getChildren().removeAll(this.getChildren());
        if (!actionFaite[0]) this.getChildren().add(btnDeplacerScientifiq);
        if (!actionFaite[1]) this.getChildren().add(btnMarche);
        if (!actionFaite[2]) this.getChildren().add(btnJouerCarte);
        this.getChildren().add(btnCancelAction);
    }

    public void resetHbox() {
        this.getChildren().removeAll(this.getChildren());
        if (!model.getCurentPLayer().isActionPrincipaleDone()) this.getChildren().add(btnActionPrincipale);
        if (!model.getCurentPLayer().isAllActionGratuiteDone()) this.getChildren().add(btnActionGratuite);
        this.getChildren().add(btnFinTour);
    }

    public void setButtonActionPrincipaleControler(EventHandler<ActionEvent> handler) {
        btnProposerProjet.setOnAction(handler);
        btnMettreProjet.setOnAction(handler);
        btnConstruire.setOnAction(handler);
    }

    public void setButtonActionGratuiteControler(EventHandler<ActionEvent> handler) {
        btnDeplacerScientifiq.setOnAction(handler);
        btnMarche.setOnAction(handler);
        btnJouerCarte.setOnAction(handler);
    }

    public void displayProposerProjetChoiceDialog() {
        Continent[] continent = model.getContinents();
        dialogProposerProjet = new ChoiceDialog<Continent>(
                continent[0], // Choix par défaut
                continent[0],
                continent[1],
                continent[2],
                continent[3],
                continent[4],
                continent[5]
        );
        dialogProposerProjet.setTitle("Mettre en place un projet");
        dialogProposerProjet.setHeaderText("Veuillez choisir un continent");
        dialogProposerProjet.setContentText("Continent:");
    }

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
        if(subventions.isEmpty()) return;
        dialogDeplacerScientifique = new ChoiceDialog<Subvention>(
                subventions.get(0), // Choix par défaut
                subventions
        );
        dialogDeplacerScientifique.setTitle("Déplacer un scientifique");
        dialogDeplacerScientifique.setHeaderText("Veuiller choisir un projet");
        dialogDeplacerScientifique.setContentText("Projet:");
    }

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

    public void displayChoisirSubventionChoiceDialog(Continent continentChoisi) {
        // TODO ; implementer une méthdoe dans le modele pour récupere les subvention libre
        ArrayList<Subvention> subventions = continentChoisi.getSubventions();
        dialogSubvention = new ChoiceDialog<Subvention>(
                subventions.get(0),
                subventions.get(0),
                subventions.get(1),
                subventions.get(2)
        );
        dialogSubvention.setTitle("Choisir une subvention");
        dialogSubvention.setHeaderText("Veuillez choisir une Subvention");
        dialogSubvention.setContentText("Subvention :");
    }

    public void setButtonActionControler(EventHandler<ActionEvent> handler) {
        btnActionGratuite.setOnAction(handler);
        btnActionPrincipale.setOnAction(handler);
        btnCancelAction.setOnAction(handler);
        btnFinTour.setOnAction(handler);
    }
}
