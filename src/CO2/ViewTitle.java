package CO2;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.Group;

public class ViewTitle {

    Model model;

    Group root;
    Pane paneIntro;
    Pane paneGame;

    Button btn;
    ComboBox<String> comboBox;

    ViewGame currentLevel;
    
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
		btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #E46D31; -fx-border-color:  #E46D31;-fx-border-width: 2px; -fx-border-style: solid; -fx-border-radius: 30;-fx-font-size: 20px;-fx-fill-width: 150px;");
		btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #E46D31;-fx-background-radius:30  ;-fx-text-fill: white; -fx-font-size: 20px;-fx-cursor: HAND"));
		btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #E46D31; -fx-border-color:  #E46D31;-fx-border-width: 2px; -fx-border-style: solid; -fx-border-radius: 30;-fx-font-size: 20px;"));
		btn.setLayoutX(670);
		btn.setLayoutY(300);

		Image imgMainTitle = new Image("co2/images/CO2MainTitle.jpg");
		ImageView imageViewMainTitle = new ImageView(imgMainTitle);
		imageViewMainTitle.setX(0);
		imageViewMainTitle.setY(0);
		imageViewMainTitle.setPreserveRatio(true);

		comboBox = new ComboBox<>();
		comboBox.getItems().addAll("1","2","3","4","5");
		comboBox.getSelectionModel().selectFirst();
		comboBox.setStyle("-fx-background-color: transparent; -fx-text-fill: #E46D31; -fx-border-color:  #E46D31;-fx-border-width: 2px; -fx-border-style: solid; -fx-border-radius: 30;-fx-font-size: 20px;-fx-cursor: HAND");
		comboBox.setLayoutX(710);
		comboBox.setLayoutY(190);

		Label lbNbJoueur = new Label("Choisir le nombre de joueur : ");
		lbNbJoueur.setStyle("-fx-text-fill: #E46D31;-fx-font-size: 20;-fx-font-weight: bold");
		lbNbJoueur.setLayoutX(625);
		lbNbJoueur.setLayoutY(100);

		paneIntro.getChildren().add(imageViewMainTitle);
		paneIntro.getChildren().add(lbNbJoueur);
		paneIntro.getChildren().add(comboBox);
		paneIntro.getChildren().add(btn);
    }

	public void setButtonControler(EventHandler<ActionEvent> handler) {
		btn.setOnAction(handler);
		comboBox.setOnAction(handler);
	}

    public void startGame() {
		root.getChildren().clear();
		currentLevel = new ViewGame(model,paneGame);
		root.getChildren().add(paneGame);
    }
}
