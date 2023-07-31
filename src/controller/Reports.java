package controller;

import DAO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import model.Contact;
import model.Country;
import model.Customer;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Reports Class and methods.
 *
 * It contains the logic for the various buttons and fields found on the Reports screen.
 *
 * This method contains a lambda expression on line 186.
 *
 * @author Brian Clavadetscher
 */
public class Reports implements Initializable {
    int monthTypeCount = 0;

    Stage stage;
    Parent scene;

    ContactDao contactDao = new ContactDaoImpl();
    AppointmentDao appointmentDao = new AppointmentDaoImpl();
    CountryDao countryDao = new CountryDaoImpl();
    CustomerDao customerDao = new CustomerDaoImpl();

    ObservableList<Contact> allContacts = FXCollections.observableArrayList(contactDao.getAllContacts());
    ObservableList<Country> allCountries = FXCollections.observableArrayList(countryDao.getAllCountries());
    ObservableList<Customer> allCustomers = FXCollections.observableArrayList(customerDao.getAllCustomers());
    ObservableList<Appointment> allAppointments = FXCollections.observableArrayList(appointmentDao.getAllAppointments());


    @FXML
    private TableColumn<Appointment, String> appointmentDescrCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> appointmentEndCol;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIdCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> appointmentStartCol;

    @FXML
    private TableColumn<Appointment, String> appointmentTitleCol;

    @FXML
    private TableColumn<Appointment, String> appointmentTypeCol;

    @FXML
    private TableColumn<Appointment, Integer> apptCustomerIdCol;

    @FXML
    private ComboBox<Month> apptMonthComboBox;

    @FXML
    private ComboBox<String> apptTypeComboBox;

    @FXML
    private TableView<Appointment> contactScheduleTableView;

    @FXML
    private ComboBox<String> countryComboBox;

    @FXML
    private TableColumn<Customer, String> customerAddressCol;

    @FXML
    private TableColumn<Customer, String> customerDivisionCol;

    @FXML
    private TableColumn<Customer, Integer> customerIDCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private TableColumn<Customer, String> customerPhoneCol;

    @FXML
    private TableColumn<Customer, String> customerPostalCodeCol;

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private ComboBox<String> report2ContactComboBox;

    @FXML
    private Label totalCustomerApptTxt;

    /**
     * Takes the user to the Appointment menu.
     *
     * @param event The appointment menu button is activated.
     * @throws IOException If load fails
     */
    @FXML
     void onActionApptMenu(ActionEvent event) throws IOException {
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
     * Takes the user to the Reports menu.
     *
     * @param event The Reports menu button is activated.
     * @throws IOException If load fails
     */
    @FXML
     void onActionReports(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Reports.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
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
     * Populates the customer table with a list of customers by country.
     *
     * This method contains a lambda expression.
     *
     * The lambda reduces lines of code and improves readability.
     *
     * @Lambda customers.setPredicate(customer -> customer.getCountry().equals(countryComboBox.getValue()));
     */
    @FXML
    void onActionCustomerByCountryComboBox() {
        FilteredList<Customer> customers = new FilteredList<>(allCustomers);
        customers.setPredicate(customer -> customer.getCountry().equals(countryComboBox.getValue()));
        customerTable.setItems(customers);
    }

    /**
     * Gets and displays Report 1 if the appointment type combobox is not empty.
     */
    @FXML
    void onActionMonthComboBox() {
        monthTypeCount = 0;
        totalCustomerApptTxt.setText("0");
        if(!apptTypeComboBox.getSelectionModel().isEmpty()) {
            for (Appointment a : allAppointments) {
                if (a.getType().equals(apptTypeComboBox.getValue()) &&
                        a.getStart().getMonth() == apptMonthComboBox.getValue()) {

                    monthTypeCount++;
                }
            }
            totalCustomerApptTxt.setText(String.valueOf(monthTypeCount));
        }
    }

    /**
     * Gets and displays Report 1 if the appointment month combobox is not empty.
     */
    @FXML
    void onActionTypeComboBox() {
        monthTypeCount =0;
        totalCustomerApptTxt.setText("0");

        if(!apptMonthComboBox.getSelectionModel().isEmpty()){
            for(Appointment a : allAppointments){
                if(a.getType().equals(apptTypeComboBox.getValue()) &&
                        a.getStart().getMonth() == apptMonthComboBox.getValue()){

                    monthTypeCount++;
                }
            }
            totalCustomerApptTxt.setText(String.valueOf(monthTypeCount));
        }
    }

    /**
     * Gets and displays Report 2 when a contact is selected.
     *
     * This method contains a lambda expression.
     *
     * The lambda reduces lines of code and improves readability.
     */
    @FXML
    void onActionScheduleComboBox() {
        FilteredList<Appointment> appointments = new FilteredList<>(allAppointments);
        //set the table with customers filtered by country
        String selectedContact = null;
        for(Contact c : allContacts){
            if(report2ContactComboBox.getValue().equals(c.getName())){
                selectedContact = c.getName();
                break;
            }
        }

        String finalSelectedContact = selectedContact;
        appointments.setPredicate(appointment -> appointment.getContactName().equals(finalSelectedContact));
        contactScheduleTableView.setItems(appointments);
    }

    /**
     * Initializes the controller Reports.
     *
     * Prepares tables for data insertion. Populates the ComboBoxes.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerDivisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        appointmentDescrCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        ObservableList<String> contacts = FXCollections.observableArrayList();
        for(Contact c : allContacts) {
            contacts.add(c.getName());
        }
        report2ContactComboBox.setItems(contacts);

        ObservableList<String> countries = FXCollections.observableArrayList();
        //create the Countries combo box options
        for(Country c : allCountries) {
            countries.add(c.getName());
        }
        countryComboBox.setItems(countries);

        ObservableList<String> types = FXCollections.observableArrayList();
        //create the Countries combo box options
        for(Appointment a : allAppointments) {
            types.add(a.getType());
        }
        apptTypeComboBox.setItems(types);

        ObservableList<Month> months = FXCollections.observableArrayList();
        for(int i = 1; i < 13; i++){
            months.add(Month.of(i));
        }
        apptMonthComboBox.setItems(months);
    }
}
