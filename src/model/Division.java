package model;

/**
 * This class models Divisions.
 *
 * It is used to construct Divisions. It is also used to access and manipulate data specific to Divisions.
 *
 * @author Brian Clavadetscher
 */
public class Division {
    private int id;
    private String name;
    private int countryId;

    /**
     * This is the constructor for the Division object.
     *
     * @param id The division id
     * @param name The division name
     * @param countryId The division's country ID
     */
    public Division(int id, String name, int countryId) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
    }

    /**
     * The getter for the division ID.
     *
     * @return The division ID
     */
    public int getId() {
        return id;
    }

    /**
     * The setter for the division ID.
     *
     * @param id The division ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * The getter for the Division name.
     *
     * @return The division name
     */
    public String getName() {
        return name;
    }

    /**
     * The setter for the division name.
     *
     * @param name The division name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The getter for the division country ID.
     *
     * @return The division's country ID
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * The setter for the division's country ID.
     *
     * @param countryId The division's coutnry ID
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}
