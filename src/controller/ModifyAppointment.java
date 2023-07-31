package controller;

import DAO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import utility.DataTransport;
import utility.TimeConverter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * ModifyAppointment Class and methods.
 *
 * It contains the logic for the various buttons and fields found on the Update Appointment screen.
 *
 * @author Brian Clavadetscher
 */
public class ModifyAppointment implements Initializable {
    Stage stage;
    Parent scene;

    AppointmentDao appointmentDao = new AppointmentDaoImpl();
    CustomerDao customerDao = new CustomerDaoImpl();
    UserDao userDao = new UserDaoImpl();
    ContactDao contactDao = new ContactDaoImpl();

    ObservableList<User> allUsers = FXCollections.observableArrayList(userDao.getAllUsers());
    ObservableList<Contact> allContacts = FXCollections.observableArrayList(contactDao.getAllContacts());
    ObservableList<Customer> allCustomers = FXCollections.observableArrayList(customerDao.getAllCustomers());
    ObservableList<Appointment> allAppointments = FXCollections.observableArrayList(appointmentDao.getAllAppointments());

    @FXML
    private ComboBox<String> apptContactComboBox;

    @FXML
    private ComboBox<Integer> apptCustomerIdComboBox;

    @FXML
    private DatePicker apptDatePicker;

    @FXML
    private TextField apptDescrTxtField;

    @FXML
    private ComboBox<LocalTime> apptEndTime;

    @FXML
    private TextField apptIdTxtField;

    @FXML
    private TextField apptLocationTxtField;

    @FXML
    private ComboBox<LocalTime> apptStartTime;

    @FXML
    private TextField apptTitleTxtField;

    @FXML
    private TextField apptTypeTxtField;

    @FXML
    private ComboBox<Integer> apptUserIdComboBox;

    @FXML
    private Label timeAdjustedTxt;

    /**
     * Returns to the Appointment menu
     *
     * @param event The cancel button was activated
     * @throws IOException If load fails
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AppointmentsMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Validates that all appointment data fields are not empty. Validates selected times. Prepares appointment data and writes it to the database. Reloads the appointment menu.
     *
     * @param event The save button is activated.
     * @throws IOException If load fails.
     */
    @FXML
    void onActionSaveAppt(ActionEvent event) throws IOException {
        //Fields cannot be blank
        String title = apptTitleTxtField.getText();
        if(title.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "The appointment must have a title!");
            alert.showAndWait();
            return;
        }
        String description = apptDescrTxtField.getText();
        if(description.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "The appointment must have a description!");
            alert.showAndWait();
            return;
        }
        String location = apptLocationTxtField.getText();
        if(location.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "The appointment must have a location!");
            alert.showAndWait();
            return;
        }
        String type = apptTypeTxtField.getText();
        if(type.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "The appointment must have a type!");
            alert.showAndWait();
            return;
        }

        int customerId;
        //check if customerID combobox is empty, if yes alert and return, if not empty assign value to int
        if(apptCustomerIdComboBox.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "The appointment needs a customer ID!");
            alert.showAndWait();
            return;
        } else {
            customerId = Integer.parseInt(apptCustomerIdComboBox.getValue().toString());
        }

        int userId;
        if(apptUserIdComboBox.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "The appointment needs a user ID!");
            alert.showAndWait();
            return;
        } else {
            userId = Integer.parseInt(apptUserIdComboBox.getValue().toString());
        }

        int contactId = 0;
        if(apptContactComboBox.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "The appointment needs a contact ID!");
            alert.showAndWait();
            return;
        } else {
            for(Contact c : allContacts){
                if(apptContactComboBox.getValue().equals(c.getName())){
                    contactId = c.getId();
                    break;
                }
            }
        }

        if(apptDatePicker.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "The appointment needs a scheduled date!");
            alert.showAndWait();
            return;
        }

        if(apptStartTime.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "The appointment needs a start time!");
            alert.showAndWait();
            return;
        }

        if(apptEndTime.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "The appointment needs an end time!");
            alert.showAndWait();
            return;
        }

        //create start time and end time from datepicker and start/end time comboboxes
        LocalDateTime startDateTime = LocalDateTime.of(apptDatePicker.getValue(), apptStartTime.getValue());
        LocalDateTime endDateTime = LocalDateTime.of(apptDatePicker.getValue(), apptEndTime.getValue());

        //if customer has an appointment that overlaps
        for(Appointment a: allAppointments) {
            if(a.getId() != Integer.parseInt(apptIdTxtField.getText())) {
                if (a.getCustomerId() == apptCustomerIdComboBox.getValue()) {
                    //new appointment is after an exisiting appointment startsAND it starts before the end of another appt
                    if (startDateTime.isEqual(a.getStart())) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "That start time matches the start time of an existing appointment for this customer!");
                        alert.showAndWait();
                        return;
                    } else if (endDateTime.isEqual(a.getEnd())) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "That end time overlaps with an existing appointment for this customer!");
                        alert.showAndWait();
                        return;
                    } else if (startDateTime.isAfter(a.getStart()) && startDateTime.isBefore(a.getEnd())) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "That start time occurs during an existing appointment for this customer!");
                        alert.showAndWait();
                        return;
                    } else if (endDateTime.isAfter(a.getStart()) && (endDateTime.isBefore(a.getEnd()) || endDateTime.isEqual(a.getEnd()))) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "That end time occurs during an existing appointment for this customer!");
                        alert.showAndWait();
                        return;
                    } else if (a.getStart().isAfter(startDateTime) && a.getStart().isBefore(endDateTime)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "This appointment overlaps with an existing appointment for this customer!");
                        alert.showAndWait();
                        return;
                    }
                }
            }
        }

        //Create database exclusive data
        String updatedBy = DataTransport.getCurrentUser().getName();
        LocalDateTime updateDate = LocalDateTime.now();
        int apptId = Integer.parseInt(apptIdTxtField.getText());

        //pass the information to the Dao for database insertion
        appointmentDao.updateAppointment(title, description, location, contactId, type, startDateTime, endDateTime, updateDate, updatedBy, customerId, userId, apptId);

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AppointmentsMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Filters the end times ComboBox based on the start time chosen.
     */
    @FXML
    void onActionStartTimeComboBox() {

        apptEndTime.setValue(null);

        ObservableList<LocalTime> endTimeOptions = FXCollections.observableArrayList();
        DateTimeFormatter dtfIntH = DateTimeFormatter.ofPattern("HH");
        DateTimeFormatter dtfIntM = DateTimeFormatter.ofPattern("mm");

        LocalTime selectedStart = TimeConverter.getTimeLocalizedToEST(apptStartTime.getValue());
        int total15MinInterval = Integer.parseInt(selectedStart.format(dtfIntM))/15;
        int totalQuarterHours = (Integer.parseInt(selectedStart.format(dtfIntH)) * 4) - 32;
        int counter = total15MinInterval + totalQuarterHours;

        selectedStart = TimeConverter.getTimeESTToLocalized(selectedStart);

        for(int i = 0; i < 56 - counter; i++) {
            selectedStart = selectedStart.plusMinutes(15);
            endTimeOptions.add(selectedStart);
        }

        apptEndTime.setItems(endTimeOptions);
    }

    /**
     * Sends Appointment data to the ModifyAppointment screen. Prepares the ComboBoxes with valid options.
     *
     * @param appointment The appointment data that will be sent.
     */
    public void sendAppointment(Appointment appointment){
        //These set the text fields to the imported appointment
        apptContactComboBox.setValue(appointment.getContactName());
        apptCustomerIdComboBox.setValue(appointment.getCustomerId());
        apptDatePicker.setValue(appointment.getStart().toLocalDate());
        apptDescrTxtField.setText(appointment.getDescription());
        apptEndTime.setValue(appointment.getEnd().toLocalTime());
        apptIdTxtField.setText(String.valueOf(appointment.getId()));
        apptLocationTxtField.setText(appointment.getLocation());
        apptStartTime.setValue(appointment.getStart().toLocalTime());
        apptTitleTxtField.setText(appointment.getTitle());
        apptTypeTxtField.setText(appointment.getType());
        apptUserIdComboBox.setValue(appointment.getUserId());

        //populate combo boxes startTime
        ObservableList<LocalTime> startTimeOptions = FXCollections.observableArrayList();
        //Initialize in EST
        LocalTime start = LocalTime.of(8,0,0,0);
        //Convert to system local timezone
        start = TimeConverter.getTimeESTToLocalized(start);

        for(int i = 0; i < 56; i++) {
            startTimeOptions.add(start);
            start = start.plusMinutes(15);
        }
        apptStartTime.setItems(startTimeOptions);

        //create the Contacts combo box options
        ObservableList<String> contacts = FXCollections.observableArrayList();
        for(Contact c : allContacts) {
            contacts.add(c.getName());
        }
        apptContactComboBox.setItems(contacts);

        //create the Customers combo box options
        ObservableList<Integer> customers = FXCollections.observableArrayList();
        for(Customer c : allCustomers) {
            customers.add(c.getId());
        }
        apptCustomerIdComboBox.setItems(customers);

        //create the Users combo box options
        ObservableList<Integer> users = FXCollections.observableArrayList();
        for(User u : allUsers) {
            users.add(u.getId());
        }
        apptUserIdComboBox.setItems(users);

        ObservableList<LocalTime> endTimeOptions = FXCollections.observableArrayList();
        LocalTime selectedStart = appointment.getStart().toLocalTime();
        for(int i = 0; i < 96; i++) {
            selectedStart = selectedStart.plusMinutes(15);
            endTimeOptions.add(selectedStart);
        }
        //populate combo boxes: startTime
        apptEndTime.setItems(endTimeOptions);
    }

    /**
     * Initializes the ModifyAppointment controller. Determines the zone to display.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeAdjustedTxt.setText("Time has been adjusted for zone: " + ZoneId.systemDefault());
    }
}
