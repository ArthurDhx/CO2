package CO2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ViewTitle {

    Model model;

    Group root;
    Pane paneIntro;
    Pane paneGame;

    Button btn;
    //ComboBox<String> comboBox;
    
    public ViewTitle(Model model) {

		this.model = model;
		root = new Group();
		paneIntro = new Pane();
		paneIntro.setPrefSize(model.width,model.height);
		paneGame = new Pane();
		paneGame.setPrefSize(model.width,model.height);

		btn = new Button();
		btn.setText("Lancer la partie");

		root.getChildren().add(paneIntro);
		btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #e46d31; -fx-border-color:  #E46D31;-fx-border-width: 2px; -fx-border-style: solid; -fx-border-radius: 30;-fx-font-size: 30px;-fx-fill-width: 150px;");
		btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #E46D31;-fx-background-radius:30  ;-fx-text-fill: white; -fx-font-size: 30px;-fx-cursor: HAND"));
		btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #E46D31; -fx-border-color:  #E46D31;-fx-border-width: 2px; -fx-border-style: solid; -fx-border-radius: 30;-fx-font-size: 30px;"));

		btn.setLayoutX(970);
		// Valeur pour plusieur joueur
		// btn.setLayoutY(600);
		btn.setLayoutY(400);

		Image imgMainTitle = new Image("CO2/images/CO2MainTitle.jpg");
		ImageView imageViewMainTitle = new ImageView(imgMainTitle);
		imageViewMainTitle.setX(0);
		imageViewMainTitle.setY(0);
		imageViewMainTitle.setFitWidth(model.width);
		imageViewMainTitle.setFitHeight(model.height);


		/* Le choix du joueur a été desactivé
		comboBox = new ComboBox<>();
		comboBox.getItems().addAll("1","2","3","4","5");
		comboBox.getSelectionModel().selectFirst();
		comboBox.setStyle("-fx-background-color: transparent; -fx-text-fill: #E46D31; -fx-border-color:  #E46D31;-fx-border-width: 2px; -fx-border-style: solid; -fx-border-radius: 30;-fx-font-size: 30px;-fx-cursor: HAND");
		comboBox.setLayoutX(1050);
		comboBox.setLayoutY(390);


		Label lbNbJoueur = new Label("Choisir le nombre de joueur : ");
		lbNbJoueur.setStyle("-fx-text-fill: #E46D31;-fx-font-size: 30;-fx-font-weight: bold");
		lbNbJoueur.setLayoutX(900);
		lbNbJoueur.setLayoutY(300);
		 */

		paneIntro.getChildren().add(imageViewMainTitle);
		// Choix du joueur desactivé
		//paneIntro.getChildren().add(lbNbJoueur);
		//paneIntro.getChildren().add(comboBox);
		paneIntro.getChildren().add(btn);
    }

	public void setButtonControler(EventHandler<ActionEvent> handler) {
		btn.setOnAction(handler);
		// Choix du joueur desactivé
		// comboBox.setOnAction(handler);
	}

    public void startGame() {
		root.getChildren().clear();
		root.getChildren().add(paneGame);
    }
}
