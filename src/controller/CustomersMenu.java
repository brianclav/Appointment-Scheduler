package controller;

import DAO.AppointmentDao;
import DAO.AppointmentDaoImpl;
import DAO.CustomerDao;
import DAO.CustomerDaoImpl;
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
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Customer Menu Class and methods.
 *
 * It contains the logic for the various buttons and fields found on the Customer Menu screen.
 *
 * @author Brian Clavadetscher
 */
public class CustomersMenu implements Initializable {

    Stage stage;
    Parent scene;

    CustomerDao customerDao = new CustomerDaoImpl();
    AppointmentDao appointmentDao = new AppointmentDaoImpl();

    ObservableList<Appointment> allAppointments = appointmentDao.getAllAppointments();

    @FXML
    private TableColumn<Customer, String> customerCountryCol;

    @FXML
    private TableColumn<Customer, String> customerAddressCol;

    @FXML
    private TableColumn<Customer, Integer> customerIdCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private TableColumn<Customer, String> customerDivisionCol;

    @FXML
    private TableColumn<Customer, String> customerPhoneCol;

    @FXML
    private TableColumn<Customer, String> customerPostalCodeCol;

    @FXML
    private TableView<Customer> customersTableView;

    /**
     * Takes the user to the Add Customer menu.
     *
     * @param event The Add customer button is activated.
     * @throws IOException If load fails.
     */
    @FXML
     void onActionAddCustomerMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddCustomer.fxml")));
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
     * Deletes customer from the database and all appointments associated with that customer.
     */
    @FXML
     void onActionDeleteCustomer() {
        //if no part was selected for deletion
        if (customersTableView.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No customer was selected for deletion!");
            alert.show();
        }
        //A part was selected for deletion
        else {
            //display alert
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?");
            Optional<ButtonType> result = alert.showAndWait();

            //if the user clicked ok
            if (result.isPresent() && result.get() == ButtonType.OK) {
                //In the Inventory class, use the deletePart method with the input of the selected item on the part Table

                for(Appointment a : allAppointments) {
                    if(a.getCustomerId() == customersTableView.getSelectionModel().getSelectedItem().getId()){
                        appointmentDao.deleteAppointment(a.getId());
                    }
                }
                customerDao.deleteCustomer(customersTableView.getSelectionModel().getSelectedItem().getId());
                customersTableView.getItems().remove(customersTableView.getSelectionModel().getSelectedItem());
            }
            //if the user clicked cancel, nothing happens
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
     * Takes the user to the Update Customer menu.
     *
     * @param event The Modify customer button is activated.
     * @throws IOException If load fails.
     */
    @FXML
     void onActionModifyCustomerMenu(ActionEvent event) throws IOException {
        //if not part was selected, display error
        if(customersTableView.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No customer was selected for modification!");
            alert.show();
        }
        //if a part was selected
        else {
            //creates the FXMLLoader object "loader"
            FXMLLoader loader = new FXMLLoader();
            //tells "loader" what view to use
            loader.setLocation(getClass().getResource("/view/ModifyCustomer.fxml"));
            //tells "loader" to load
            loader.load();

            //The ModifyPart controller "MPController" is set to equal to the controller loader, which is associated
            //with ModifyPart fxml
            ModifyCustomer MCController = loader.getController();
            //MPController uses the sendPart method located in ModifyPart with the input of the selected part
            //the sendPart method will import the various attributes of the part input
            MCController.sendCustomer(customersTableView.getSelectionModel().getSelectedItem());

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
     * Initializes the controller Reports.
     *
     * Prepares customer table for data insertion and populates it with all customers.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Customer> customerList = FXCollections.observableArrayList(customerDao.getAllCustomers());

        //These tell the table what appears in what column
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerDivisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        customerCountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        //set the items on the table
        customersTableView.setItems(customerList);
    }
}
