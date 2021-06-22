package Controller;

import Model.MongoConnnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This is the controller class , it implements the interface Initializable which means that when controllers root element finishes getting processed
 * the method initialize will load.
 * The class take input from the View/GUI and sends them to be processed or added to the database.
 */
public class Controller implements Initializable {

   @FXML private Button button_Text;
   @FXML private TextField user_input;
   @FXML private TextField link_input;
   @FXML private ComboBox<String> combo;
   @FXML private TextArea result;
   @FXML private Label link_validation;
         private Mediator mediator;
         private MongoConnnection connect;


    /**
     * This method will run once the button is clicked , then the link the user
     * typed will be used to call the method AddWebToMongo from the  class mediator {@link Mediator#AddWebToMongo()}
     * to be processed there and later add it's metadata to the database.
     * Link validation is also done in this method using regex to check if the link is correct.
     * @throws IOException
     */
    public void handleButtonClick() throws IOException {
        String URL_REGEX = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";
        Pattern p = Pattern.compile(URL_REGEX);
        Matcher m = p.matcher(link_input.getText());

        if(m.find()) {
            mediator = new Mediator(link_input.getText());
            link_validation.setText("");
            mediator.AddWebToMongo();
        }
        else {
            link_validation.setText("Please enter a valid link");
        }
    }

    /**
     * This method initializes and event listener that waits for the user to type in the text field and
     * calls the fullTextSearch method {@link Model.MongoConnnection#fullTextSearch(String, String)}
     * to search for the users text in the database and display the links found.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connect = new MongoConnnection();
        user_input.textProperty().addListener((obs, oldText, newText) -> {
            System.out.println("textfield changed from " + oldText + " to " + newText);
            result.setText(connect.fullTextSearch(newText,combo.getValue()));
        });
    }
}
