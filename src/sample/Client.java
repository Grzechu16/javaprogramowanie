package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Created by Gregory on 2017-12-20.
 */
public class Client extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Controller controller = new Controller();
        loader.setController(controller);
        controller.start();
        Parent root = loader.load(getClass().getResource("questionnaire.fxml"));
        primaryStage.setTitle("Ankieta");
        primaryStage.setScene(new Scene(root, 900, 400));
        primaryStage.show();
    }

    public static void main(String args[]) throws IOException, ClassNotFoundException {
        launch(args);
    }

}
