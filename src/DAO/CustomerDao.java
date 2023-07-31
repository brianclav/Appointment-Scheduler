package DAO;

import javafx.collections.ObservableList;
import model.Customer;

/**
 * Customer Dao interface.
 *
 * It defines the standard operations to be performed on the Customer model object.
 *
 * @author Brian Clavadetscher
 */
public interface CustomerDao {
    /**
     * Gets all customers from the database.
     *
     * @return All customers.
     */
    ObservableList<Customer> getAllCustomers();

    /**
     * Adds a customer to the database.
     *
     * @param name Customer name
     * @param address Customer address
     * @param postalCode Customer postal code
     * @param phone Customer phone number
     * @param division Customer division
     * @param creationDate Customer creation date
     * @param createdByUser Customer created by user
     */
    void addCustomer(String name, String address, String postalCode, String phone, int division, String creationDate, String createdByUser);

    /**
     * Update customer in the database.
     *
     * @param name Customer name
     * @param address Customer address
     * @param postalCode Customer postal code
     * @param phone Customer phone number
     * @param division Customer division
     * @param updateDate Customer update date
     * @param updatedByUser Customer updated by user
     * @param customerId Customer ID
     */
    void updateCustomer(String name, String address, String postalCode, String phone, int division, String updateDate, String updatedByUser, int customerId);

    /**
     * Deletes a customer from the database.
     *
     * @param customerId Customer ID.
     */
    void deleteCustomer(int customerId);
}

