package model;

/**
 * This class models Users.
 *
 * It is used to construct Users. It is also used to access and manipulate data specific to Users.
 *
 * @author Brian Clavadetscher
 */
public class User {
    private int id;
    private String name;
    private String password;
    private String currentUser;

    /**
     * This is the constructor for User objects.
     *
     * @param id The User ID.
     * @param name The User name.
     * @param password The User password.
     */
    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    /**
     * The getter for the User ID.
     *
     * @return The User ID.
     */
    public int getId() {
        return id;
    }

    /**
     * The getter for the User name.
     *
     * @return The user name.
     */
    public String getName(){
        return name;
    }

    /**
     * The getter for the user password.
     *
     * @return The user password.
     */
    public String getPassword(){
        return password;
    }

}
