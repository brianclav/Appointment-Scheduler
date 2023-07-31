package main;

import utility.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.*;

/**
 * The Main class creates the application.
 *
 * The app is used for scheduling appointments for contacts with customers.
 *
 * The Javadocs for this app can be found in the root folder of this project in a folder called "JavaDoc".
 *
 * @author Brian Clavadetscher
 */
public class Main extends Application {

    /**
     * It loads the FXML stage.
     *
     * It also translates the app title bar to English or French. If local language is unsupported, defaults to english with an alert.
     *
     * @param primaryStage The login stage.
     * @throws IOException If load fails
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        //if the user system language is not French or English, default to english
        if(!Locale.getDefault().getLanguage().equals("fr") && !Locale.getDefault().getLanguage().equals("en")){
            Locale defaultLocale = new Locale("en");
            Locale.setDefault(defaultLocale);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Local language is not supported");
            alert.setContentText("Defaulting to English");
            alert.showAndWait();
        }

        //get language bundle
        ResourceBundle rb = ResourceBundle.getBundle("language_files/lang", Locale.getDefault());

        //load stage with title in english or french
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Login.fxml")));
        if(Locale.getDefault().getLanguage().equals("fr")) {
            primaryStage.setTitle(rb.getString("Scheduler"));
        } else if (Locale.getDefault().getLanguage().equals("en")) {
            primaryStage.setTitle(rb.getString("Scheduler"));
        }
        primaryStage.setScene(new Scene(root, 380, 280));
        primaryStage.show();
    }

    /**
     * The main method.
     *
     * It launches the app and opens the connection to the database.
     *
     * @param args Arguments passed to the app.
     */
    public static void main(String[] args) {
        //Test French
        //Locale.setDefault(new Locale("fr"));

        //Test German
        //Locale.setDefault(new Locale("de"));

        //connect to database
        DBConnection.openConnection();

        //Launch args
        launch(args);

        //disconnect from database
        DBConnection.closeConnection();
    }
}
