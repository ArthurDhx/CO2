package CO2;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
   
    @Override
    public void start(Stage stage) {
        Model model = new Model();
        ViewTitle viewTitle = new ViewTitle(model);
        ViewGame viewGame = new ViewGame(model, viewTitle.paneGame);
        Controller control = new Controller(model, viewTitle, viewGame);
   
        stage.setTitle("Jeu de Société - CO2");
        Scene scene = new Scene(viewTitle.root, model.width, model.height);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
