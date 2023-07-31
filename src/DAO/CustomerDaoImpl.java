package DAO;

import model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utility.DBConnection;

import java.sql.*;

/**
 * Customer Dao implementation.
 *
 * Saves and Retrieves data to and from database.
 *
 * @author Brian Clavadetscher
 */
public class CustomerDaoImpl implements CustomerDao {

    /**
     * Gets all Customer data from the Customer, Divisions, and Country Tables of the database, turns them into Customer objects, and adds those objects to an observable list.
     *
     * @return Observable list of all customer as Customer objects
     */
    @Override
    public ObservableList<Customer> getAllCustomers() {

        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM ((Customers " +
                    "INNER JOIN First_Level_Divisions ON Customers.Division_ID = First_Level_Divisions.Division_ID)" +
                    "INNER JOIN Countries ON Countries.Country_ID = First_Level_Divisions.Country_ID)";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostalCode = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");
                String customerDivision = rs.getString("Division");
                String customerCountry = rs.getString("Country");
                Customer C = new Customer(customerId, customerName, customerAddress, customerPostalCode, customerPhone,
                        customerDivision, customerCountry);
                customerList.add(C);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerList;
    }

    /**
     * Adds a row to the customer table of the database using a customer object.
     *
     * @param name customer name
     * @param address customer address
     * @param postalCode customer postal code
     * @param phone customer phone number
     * @param division customer division
     * @param creationDate customer creation date
     * @param createdByUser customer created by user
     */
    @Override
    public void addCustomer(String name, String address, String postalCode, String phone, int division, String creationDate, String createdByUser) {
        try {
            String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID, Create_Date, " +
                    "Created_by, Last_Update, Last_Updated_by) VALUES (?,?,?,?,?,?,?,?,?)";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setInt(5, division);
            ps.setString(6, creationDate);
            ps.setString(7, createdByUser);
            ps.setString(8, creationDate);
            ps.setString(9, createdByUser);

            ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Updates a row of the customer table of the database using a customer object.
     *
     * @param name Customer name
     * @param address customer address
     * @param postalCode customer postal code
     * @param phone customer phone number
     * @param division customer division
     * @param updateDate customer update date
     * @param updatedByUser customer updated by user
     * @param customerId customer ID
     */
    @Override
    public void updateCustomer(String name, String address, String postalCode, String phone, int division, String updateDate, String updatedByUser, int customerId) {
        try {
            String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?," +
                    "Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE customer_ID = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setString(5, updateDate);
            ps.setString(6, updatedByUser);
            ps.setInt(7, division);
            ps.setInt(8, customerId);

            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Deletes a row of the customer table of the database using a customer ID.
     *
     * @param customerId The customer ID
     */
    @Override
    public void deleteCustomer(int customerId) {
        try {
            String sql = "DELETE FROM customers WHERE customer_ID = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setInt(1, customerId);

            ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
