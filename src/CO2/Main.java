package CO2;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
   
    @Override
    public void start(Stage stage) throws IOException {
        Model model = new Model();
        ViewTitle viewTitle = new ViewTitle(model);
        Scene scene = new Scene(viewTitle.root, model.width, model.height);
        ViewGame viewGame = new ViewGame(scene, model, viewTitle.paneGame);
        Controller control = new Controller(model, viewTitle, viewGame);
   
        stage.setTitle("Jeu de Société - CO2");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
