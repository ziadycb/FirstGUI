package View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * The goal of this application is a demo representation of a search engine ,the GUI is made using JavaFX it is loaded in this main class.
 * The user input is processed in the class controller it is also the brain of the entire program {@link Controller.Controller}
 * Finally the information is stored in a mongoDB database the connection is managed by the class MongoConnection {@link Model.MongoConnnection}
 * @author Ziad Yaacoub
 */
public class Main extends Application {

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Search Demo");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }


}
