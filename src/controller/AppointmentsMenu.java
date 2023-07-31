package controller;

import DAO.AppointmentDao;
import DAO.AppointmentDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * AppointmentMenu Class and methods.
 *
 * It contains the logic for the various buttons and fields found on the Appointment Menu screen.
 *
 * A lambda expression can be found on line 253.
 *
 * @author Brian Clavadetscher
 */
public class AppointmentsMenu implements Initializable {
    Stage stage;
    Parent scene;

    AppointmentDao appointmentDao = new AppointmentDaoImpl();

    ObservableList<Appointment> allAppointments = appointmentDao.getAllAppointments();

    @FXML
    private TableColumn<Appointment, String> appointmentContactCol;

    @FXML
    private TableColumn<Appointment, String> appointmentDescrCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> appointmentEndCol;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIDCol;

    @FXML
    private TableColumn<Appointment, String> appointmentLocationCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> appointmentStartCol;

    @FXML
    private TableColumn<Appointment, String> appointmentTitleCol;

    @FXML
    private TableColumn<Appointment, String> appointmentTypeCol;

    @FXML
    private TableColumn<Appointment, Integer> customerIdCol;

    @FXML
    private TableColumn<Appointment, Integer> userIdCol;

    @FXML
    private TableView<Appointment> appointmentsTableView;

    @FXML
    private RadioButton calendarViewAllRBtn;

    @FXML
    private RadioButton calendarViewCurMonthRBtn;

    @FXML
    private RadioButton calendarViewCurWeekRBtn;

    @FXML
    private ToggleGroup viewToggle;

    @FXML
    private Label notificationLabel;

    /**
     * Takes the user to the Add appointment screen.
     *
     * @param event The add appointment button is activated.
     * @throws IOException If load fails.
     */
    @FXML
     void onActionAddApptMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddAppointment.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Takes the user to the Appointment menu.
     *
     * @param event The appointment menu button is activated.
     * @throws IOException If load fails
     */
    @FXML
     void onActionAppointmentsMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AppointmentsMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Takes the user to the Customers menu.
     *
     * @param event The customers menu button is activated.
     * @throws IOException If load fails
     */
    @FXML
     void onActionCustomersMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CustomersMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /**
     * Deletes the selected appointment from the database and removes it from the table.
     */
    @FXML
     void onActionDeleteAppt() {
        //if not part was selected, display error
        if(appointmentsTableView.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No appointment was selected for modification!");
            alert.show();
        }
        //if a part was selected
        else {
            //display alert
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this appointment?");
            Optional<ButtonType> result = alert.showAndWait();

            //if the user clicked ok
            if (result.isPresent() && result.get() == ButtonType.OK) {

                appointmentDao.deleteAppointment(appointmentsTableView.getSelectionModel().getSelectedItem().getId());

                Alert deleteAlert = new Alert(Alert.AlertType.INFORMATION);
                deleteAlert.setTitle("Appointment Deleted");
                deleteAlert.setHeaderText("Appointment Deleted");
                deleteAlert.setContentText("Appointment ID: " + appointmentsTableView.getSelectionModel().getSelectedItem().getId() +
                        " of type '" + appointmentsTableView.getSelectionModel().getSelectedItem().getType() + "' was deleted");
                deleteAlert.showAndWait();


                appointmentsTableView.getItems().remove(appointmentsTableView.getSelectionModel().getSelectedItem());

                allAppointments = appointmentDao.getAllAppointments();
            }
            //if clicked cancel
        }
    }

    /**
     * Takes the user to the login menu.
     *
     * @param event The log off button is activated.
     * @throws IOException If load fails
     */
    @FXML
     void onActionLogOff(ActionEvent event) throws IOException {
        //display alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to log out?");
        Optional<ButtonType> result = alert.showAndWait();

        //if the user clicked ok
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Login.fxml")));
            stage.setScene(new Scene(scene));
            stage.centerOnScreen();
            stage.show();
        }
    }

    /**
     * Takes the user to the Update appointment screen.
     *
     * @param event The modify appointment button is activated.
     * @throws IOException If load fails.
     */
    @FXML
     void onActionModifyApptMenu(ActionEvent event) throws IOException {

        if(appointmentsTableView.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No appointment was selected for modification!");
            alert.show();
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyAppointment.fxml"));
            loader.load();

            ModifyAppointment MCController = loader.getController();

            MCController.sendAppointment(appointmentsTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Takes the user to the Reports menu.
     *
     * @param event The Reports menu button is activated.
     * @throws IOException If load fails
     */
    @FXML
     void onActionReportsMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Reports.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Filters the appointment table to view all.
     */
    @FXML
    void onActionViewAll() {
        appointmentsTableView.setItems(appointmentDao.getAllAppointments());
    }

    /**
     * Filters the appointment table to only current month.
     *
     * This method contains two lambda expressions. These expressions replace a for loop that contained a pair of if statements.
     *
     * Lambda reduces code size and improves readability.
     *
     * @Lambda allAppointments.stream().filter(appt - > appt.getStart ().getYear() == (LocalDate.now().getYear())).filter(appt -> appt.getStart().getMonth().equals(LocalDate.now().getMonth())).forEach(apptMonthFilter::add);
     */
    @FXML
     void onActionViewCurrentMonth() {
        ObservableList<Appointment> apptMonthFilter = FXCollections.observableArrayList();

        allAppointments.stream().filter(appt -> appt.getStart().getYear() == (LocalDate.now().getYear())).filter(
                appt -> appt.getStart().getMonth().equals(LocalDate.now().getMonth())).forEach(apptMonthFilter::add);
        appointmentsTableView.setItems(apptMonthFilter);
    }

    /**
     * Filters the appointment table to only current week.
     */
    @FXML
     void onActionViewCurrentWeek() {
        //initialize list and LocalDateTime variables
        ObservableList<Appointment> appointmentsWeekFilter = FXCollections.observableArrayList();
        LocalDateTime currentDT = LocalDateTime.now();
        LocalDateTime firstDayOfWeek = LocalDateTime.now();
        LocalDateTime lastDayOfWeek;

        //Determine the first day of week by finding the date on sunday
        int counter = 0;
        for(DayOfWeek d : DayOfWeek.values()){
            counter++;
            if(d == currentDT.getDayOfWeek()){
                if(d == DayOfWeek.SUNDAY) {
                    break;
                }
                firstDayOfWeek = firstDayOfWeek.minusDays(counter);
                break;
            }
        }

        //determine the last day of the week by adding 6 days to the first day
        lastDayOfWeek = firstDayOfWeek.plusDays(6);

        //add appointments to the list that are between or equal to the first and last days of the week
        for(Appointment appt : allAppointments) {
            if ((appt.getStart().isAfter(firstDayOfWeek) || appt.getStart().isEqual(firstDayOfWeek)) &&
                    (appt.getStart().isBefore(lastDayOfWeek) || appt.getStart().isEqual(lastDayOfWeek))) {

                appointmentsWeekFilter.add(appt);
            }
        }
        appointmentsTableView.setItems(appointmentsWeekFilter);
    }

    /**
     * Initializes the controller AppointmentsMenu.
     *
     * Prepares appointments table for data insertion and populates it with all appointments. Determines the user's zone for display.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //These tell the table what appears in what column
        appointmentContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        appointmentDescrCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        //set the items on the table
        appointmentsTableView.setItems(allAppointments);

        notificationLabel.setText("Business hours are 08:00 to 22:00 EST. Time has been adjusted for zone: " + ZoneId.systemDefault());
    }
}
