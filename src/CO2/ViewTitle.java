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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

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

		Text text = new Text( 700, 50,
				"Dans les années 70, les gouvernements mondiaux font face à une demande d’énergie sans " +
						"précédent et des centrales de plus en plus polluantes sont construites n’importe où afin " +
						"de répondre à cette demande. Année après année, la pollution qu’elles génèrent augmente " +
						"et personne ne fait rien pour la réduire. De nos jours, l’impact de cette pollution est " +
						"devenu trop grand et l’humanité commence à réaliser que nous devons répondre à nos besoins " +
						"énergétiques via des sources d’énergie propre. Les compagnies ayant une compétence en " +
						"énergies propres et durables sont appelées pour proposer des projets qui fourniront " +
						"l’énergie nécessaire sans polluer l’environnement. Les gouvernements désirent financer ces " +
						"projets et investir dans leur mise en place. \n\n" +
						"Dans le jeu CO2, chaque joueur gère une compagnie répondant aux requêtes gouvernementales " +
						"pour de nouvelles centrales à énergies vertes. \n\n" +
						"Le but est de stopper l’augmentation de la pollution, tout en répondant à la demande " +
						"croissante d’énergie durable et bien sûr de faire des profits. \n\n" +
						"Vous aurez besoin d’assez de connaissances, d’argent et de ressources pour construire des " +
						"centrales propres. Des sommets sur l’énergie favoriseront la prise de conscience mondiale " +
						"et permettront aux compagnies de partager un peu de leur expertise, tout en apprenant encore " +
						"plus des autres. \n\n" +
						"Rappelez-vous que si la pollution n’est pas stoppée, ce sera la fin de la partie pour tout " +
						"le monde.\n");

		text.setFont(new Font(15));
		text.setWrappingWidth(800);
		text.setTextAlignment(TextAlignment.JUSTIFY);

		btn = new Button();
		btn.setText("Lancer la partie");

		root.getChildren().add(paneIntro);
		btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #e46d31; -fx-border-color:  #E46D31;-fx-border-width: 2px; -fx-border-style: solid; -fx-border-radius: 30;-fx-font-size: 30px;-fx-fill-width: 150px;");
		btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #E46D31;-fx-background-radius:30  ;-fx-text-fill: white; -fx-font-size: 30px;-fx-cursor: HAND"));
		btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #E46D31; -fx-border-color:  #E46D31;-fx-border-width: 2px; -fx-border-style: solid; -fx-border-radius: 30;-fx-font-size: 30px;"));

		btn.setLayoutX(970);
		// Valeur pour plusieur joueur
		// btn.setLayoutY(600);
		btn.setLayoutY(450);

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
		paneIntro.getChildren().add(text);
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
