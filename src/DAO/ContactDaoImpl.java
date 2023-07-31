package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import utility.DBConnection;

import java.sql.*;

/**
 * Appointment Dao implementation.
 *
 * Retrieves data from database.
 *
 * @author Brian Clavadetscher
 */
public class ContactDaoImpl implements ContactDao {

    /**
     * Gets all Contact rows from the Contact Table of the database, turns them into Contact objects, and adds those objects to an observable list.
     *
     * @return Observable list of all contacts as Contact objects
     */
    @Override
    public ObservableList<Contact> getAllContacts() {

        ObservableList<Contact> contactList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM Contacts";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");
                Contact C = new Contact(contactId, contactName, contactEmail);
                contactList.add(C);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contactList;
    }
}
