package DAO;

import javafx.collections.ObservableList;
import model.Contact;

/**
 * Contact Dao interface.
 *
 * It defines the standard operations to be performed on the Contact model object.
 *
 * @author Brian Clavadetscher
 */
public interface ContactDao {
    /**
     * Gets all contacts from the database.
     *
     * @return All contacts.
     */
    ObservableList<Contact> getAllContacts();
}
