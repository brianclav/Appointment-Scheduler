package model;

/**
 * This class models Contacts.
 *
 * It is used to construct Contacts. It is also used to access and manipulate data specific to Contacts.
 *
 * @author Brian Clavadetscher
 */
public class Contact {
    private int id;
    private String name;
    private String email;

    /**
     * This is the constructor for Contact objects.
     *
     * @param id The contact ID
     * @param name The contact name
     * @param email The contact email
     */
    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * The getter for the contact ID.
     *
     * @return The ID
     */
    public int getId() {
        return id;
    }

    /**
     * The setter for the contact ID.
     *
     * @param id The ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * The getter for the contact name.
     *
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * The setter for the contact name.
     *
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The getter for the contact email.
     *
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * The setter for the contact email.
     *
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}