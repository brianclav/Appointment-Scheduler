package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import DAO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import model.Appointment;
import model.User;
import utility.DBConnection;
import utility.DataTransport;
import utility.UserLogger;


/**
 * Login Class and methods
 *
 * It contains the logic for the various buttons and fields found on the login screen.
 *
 * @author Brian Clavadetscher
 */
public class Login implements Initializable {
    //variables for advancing the stage
    Stage stage;
    Parent scene;

    //Dao is a new instance of DaoImpl
    UserDao userDao = new UserDaoImpl();
    AppointmentDao appointmentDao = new AppointmentDaoImpl();

    //create user and appointment lists from the database
    ObservableList<User> userList = userDao.getAllUsers();
    ObservableList<Appointment> allAppointments = appointmentDao.getAllAppointments();

    //get user Language
    ResourceBundle rb = ResourceBundle.getBundle("language_files/lang", Locale.getDefault());

    @FXML
    private Button exitBttn;

    @FXML
    private Button logInBttn;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField passwordTxtField;

    @FXML
    private Label userLocationTxt;

    @FXML
    private Label userNameLabel;

    @FXML
    private TextField userNameTxtField;


    /**
     * Exit button
     * Exits the program.
     */
    @FXML
    void onActionExit() {
        DBConnection.closeConnection();
        System.exit(0);
    }

    /**
     * Login button
     * <p>
     * Checks if a username and password pair exists in the database. Detects and translates English and French.
     * <p>
     * On successful login, tracks the logged in the User internally, logs the user externally, checks and alerts the
     * user to any appointment in the next 15 minutes. Loads the Appointments Menu.
     * <p>
     * Contains a lambda that replaces a for loop used to create an list of the current user's appointments.
     *
     * @param event The Login button is activated.
     * @throws IOException If load fails.
     */
    @FXML
     void onActionLogIn(ActionEvent event) throws IOException {
        boolean loginSuccess = false;
        //for each user in the user list from the database
        for (User U : userList) {
            //if the entered user/password pair matches a user/password pair in the database
            if (userNameTxtField.getText().equals(U.getName()) && passwordTxtField.getText().equals(U.getPassword())) {
                //so that failed login message doesn't trigger
                loginSuccess = true;
                //Give the DataTransporter the logged in User's details
                DataTransport.setCurrentUser(U);
                //Track the User's login in the external file "login_activity"
                UserLogger.trackUserLogin(U.getName(), true);

                //Create a list of the current user's appointments
                //This contains a Lambda statement
                ObservableList<Appointment> userAppointments = FXCollections.observableArrayList();
                allAppointments.stream().filter(a -> U.getId() == a.getUserId()).forEach(userAppointments::add);

                //Determine if an appointment is within 15 minutes and alert the user if yes in french or english
                boolean appointmentSoon = false;
                for (Appointment a : userAppointments) {
                    if (a.getStart().isAfter(LocalDateTime.now()) && a.getStart().isBefore(LocalDateTime.now().plusMinutes(16))) {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        if (Locale.getDefault().getLanguage().equals("fr")) {
                            alert.setTitle(rb.getString("AppointmentReminder"));
                            alert.setHeaderText(rb.getString("AppointmentReminder"));
                            alert.setContentText(rb.getString("AppointmentID") + ": " + a.getId() + " " + rb.getString("Begins") + " " + dtf.format(a.getStart()));
                        } else if (Locale.getDefault().getLanguage().equals("en")) {
                            alert.setTitle(rb.getString("AppointmentReminder"));
                            alert.setHeaderText(rb.getString("AppointmentReminder"));
                            alert.setContentText(rb.getString("AppointmentID") + ": " + a.getId() + " " + rb.getString("Begins") + " " + dtf.format(a.getStart()));
                        }
                        appointmentSoon = true;
                        alert.showAndWait();
                    }
                }
                //If an appointment is not within 15 minutes, display alert in french or english
                if (!appointmentSoon) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    if (Locale.getDefault().getLanguage().equals("fr")) {
                        alert.setTitle(rb.getString("AppointmentReminder"));
                        alert.setHeaderText(rb.getString("NoUpcoming"));
                    } else if (Locale.getDefault().getLanguage().equals("en")) {
                        alert.setTitle(rb.getString("AppointmentReminder"));
                        alert.setHeaderText(rb.getString("NoUpcoming"));
                    }
                    alert.showAndWait();
                }
                //Advance the stage to the appointment menu
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AppointmentsMenu.fxml")));
                stage.setScene(new Scene(scene));
                stage.centerOnScreen();
                stage.show();
                break;
            }
        }
        //If the login attempt was unsuccessful
        if (!loginSuccess) {
            //log the attempt externally
            UserLogger.trackUserLogin(userNameTxtField.getText(), false);
            //display error message in french or english
            if (Locale.getDefault().getLanguage().equals("fr")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("Error"));
                alert.setHeaderText(rb.getString("loginError"));
                alert.showAndWait();
            } else if (Locale.getDefault().getLanguage().equals("en")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("Error"));
                alert.setHeaderText(rb.getString("loginError"));
                alert.showAndWait();
            }
        }
    }

    /**
     * Initializes the controller Login.
     *
     * Translates all menu text to French or English. Checks and displays the user's zone.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //User Language is French or English
        if (Locale.getDefault().getLanguage().equals("fr")) {
            userNameLabel.setText(rb.getString("Username"));
            passwordLabel.setText(rb.getString("Password"));
            logInBttn.setText(rb.getString("Login"));
            exitBttn.setText(rb.getString("Exit"));
        } else if (Locale.getDefault().getLanguage().equals("en")) {
            userNameLabel.setText(rb.getString("Username"));
            passwordLabel.setText(rb.getString("Password"));
            logInBttn.setText(rb.getString("Login"));
            exitBttn.setText(rb.getString("Exit"));
        }

        //check for user region and set text to that location
        if (Locale.getDefault().getLanguage().equals("fr")) {
            userLocationTxt.setText(rb.getString("Zone") + ": " + ZoneId.systemDefault());
        } else if (Locale.getDefault().getLanguage().equals("en")) {
            userLocationTxt.setText(rb.getString("Zone") + ": " + ZoneId.systemDefault());
        }
    }
}
