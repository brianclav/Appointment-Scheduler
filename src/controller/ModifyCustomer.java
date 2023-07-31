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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;
import utility.DataTransport;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * ModifyCustomer Class and methods.
 *
 * It contains the logic for the various buttons and fields found on the Update Customer screen.
 *
 * @author Brian Clavadetscher
 */
public class ModifyCustomer implements Initializable {

    Stage stage;
    Parent scene;

    CustomerDao customerDao = new CustomerDaoImpl();
    CountryDao countryDao = new CountryDaoImpl();
    DivisionDao divisionDao = new DivisionDaoImpl();

    ObservableList<Country> countriesOb = FXCollections.observableArrayList(countryDao.getAllCountries());
    ObservableList<Division> divisionOb = FXCollections.observableArrayList(divisionDao.getAllDivisions());

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @FXML
    private TextField customerAddressTxtField;

    @FXML
    private ComboBox<String> customerCountryComboBox;

    @FXML
    private ComboBox<String> customerDivisionComboBox;

    @FXML
    private TextField customerIdTxtField;

    @FXML
    private TextField customerNameTxtField;

    @FXML
    private TextField customerPhoneTxtField;

    @FXML
    private TextField customerPostalCodeTxtField;

    /**
     * Returns to the Customer menu
     *
     * @param event The cancel button was activated
     * @throws IOException If load fails
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CustomersMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Validates that all customer data fields are not empty. Prepares customer data and writes it to the database. Reloads the customer menu.
     *
     * @param event The save button is activated.
     * @throws IOException If load fails.
     */
    @FXML
    void onActionSaveCustomer(ActionEvent event) throws IOException {
        //Fields cannot be blank
        String name = customerNameTxtField.getText();
        if(name.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "The Customer must have a name!");
            alert.showAndWait();
            return;
        }
        String address = customerAddressTxtField.getText();
        if(address.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "The Customer must have an address!");
            alert.showAndWait();
            return;
        }
        String phone = customerPhoneTxtField.getText();
        if(phone.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "The Customer must have a phone number!");
            alert.showAndWait();
            return;
        }
        String postalCode = customerPostalCodeTxtField.getText();
        if(postalCode.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "The Customer must have a postal code!");
            alert.showAndWait();
            return;
        }
        String division = customerDivisionComboBox.getValue();
        if(customerDivisionComboBox.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "The customer must have a division!");
            alert.showAndWait();
            return;
        }
        if(customerCountryComboBox.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "The customer must have a country!");
            alert.showAndWait();
            return;
        }

        //Convert the division string to division id
        ObservableList<Division> divisionList = FXCollections.observableArrayList(divisionDao.getAllDivisions());
        int divisionId = 0;
        for(Division d : divisionList){
            if(d.getName().equals(division)){
                divisionId = d.getId();
                break;
            }
        }

        //create additional data needed by database
        String updatedByUser = DataTransport.getCurrentUser().getName();
        String updateDate = LocalDateTime.now().format(dtf);
        int customerId = Integer.parseInt(customerIdTxtField.getText());

        customerDao.updateCustomer(name, address, postalCode, phone, divisionId, updateDate, updatedByUser, customerId);

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CustomersMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Sends Customer data to the ModifyCustomer screen. Prepares the ComboBoxes with valid options.
     *
     * @param customer The customer whose data will be sent.
     */
    public void sendCustomer(Customer customer){
        ObservableList<String> countries = FXCollections.observableArrayList();
        ObservableList<String> divisions = FXCollections.observableArrayList();

        //These set the text fields to the imported customer
        customerIdTxtField.setText(String.valueOf(customer.getId()));
        customerNameTxtField.setText(customer.getName());
        customerPhoneTxtField.setText(customer.getPhone());
        customerAddressTxtField.setText(customer.getAddress());
        customerPostalCodeTxtField.setText(customer.getPostalCode());
        customerCountryComboBox.setValue(customer.getCountry());
        customerDivisionComboBox.setValue(customer.getDivision());

        //the imported country
        String selectedCountry = (customer.getCountry());

        //create the Countries combo box options
        for(Country c : countriesOb) {
            countries.add(c.getName());
        }
        customerCountryComboBox.setItems(countries);

        int selectedCountryId = 0;
        for(Country c: countriesOb){
            if(selectedCountry.equals(c.getName())){
                selectedCountryId = c.getId();
                break;
            }
        }

        for (Division d : divisionOb) {
            if (d.getCountryId() == selectedCountryId){
                divisions.add(d.getName());
            }
        }
        customerDivisionComboBox.setItems(divisions);
    }

    /**
     * Filters the divisions ComboBox based on the country chosen.
     */
    @FXML
    void onActionCountryBox() {
        customerDivisionComboBox.setValue(null);
        ObservableList<String> divisions = FXCollections.observableArrayList();

        int selectedCountryId = 0;
        for(Country c: countriesOb){
            if(customerCountryComboBox.getValue().equals(c.getName())){
                selectedCountryId = c.getId();
                break;
            }
        }

        //create the divisions combo box options
        for (Division d : divisionOb) {

            if (d.getCountryId() == selectedCountryId){
                divisions.add(d.getName());
            }
        }
        customerDivisionComboBox.setItems(divisions);
    }

    /**
     * Initializes the ModifyCustomer controller.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
