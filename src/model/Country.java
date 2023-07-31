package model;

/**
 * This class models Countries.
 *
 * It is used to construct Countries. It is also used to access and manipulate data specific to Countries.
 *
 * @author Brian Clavadetscher
 */
public class Country {
    private int id;
    private String name;

    /**
     * This is the constructor for the Country object.
     *
     * @param id The country id
     * @param name The country name
     */
    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * The getter for the country ID.
     *
     * @return The ID
     */
    public int getId() {
        return id;
    }

    /**
     * The getter for the Country name.
     *
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * The setter for the country ID.
     *
     * @param id The ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * The setter for the country name.
     *
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }
}
