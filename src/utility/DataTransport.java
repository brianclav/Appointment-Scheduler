package utility;

import model.User;

/**
 * DataTransport Class and methods.
 *
 * It contains the logic for transporting the current user data between controllers.
 *
 * @author Brian Clavadetscher
 */
public class DataTransport {
    private static User currentUser;

    /**
     * Sets the current user to the user who logged in.
     *
     * @param user The current user.
     */
    public static void setCurrentUser(User user) {
        DataTransport.currentUser = user;
    }

    /**
     * Gets the current logged in user.
     *
     * @return The logged in user.
     */
    public static User getCurrentUser() {
        return currentUser;
    }

}
