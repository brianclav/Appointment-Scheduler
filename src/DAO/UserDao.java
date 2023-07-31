package DAO;

import javafx.collections.ObservableList;
import model.User;

/**
 * User Dao interface.
 *
 * It defines the standard operations to be performed on the User model object.
 *
 * @author Brian Clavadetscher
 */
public interface UserDao {
    /**
     * Gets all users from the database.
     *
     * @return All users.
     */
    ObservableList<User> getAllUsers();
}
