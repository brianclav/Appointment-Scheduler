package model;

/**
 * This class models Customers.
 *
 * It is used to construct Customers. It is also used to access and manipulate data specific to Customers.
 *
 * @author Brian Clavadetscher
 */
public class Customer {
    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private String division;
    private String country;

    /**
     * The constructor for Customer objects.
     *
     * @param id The customer ID
     * @param name The customer name
     * @param address The customer address
     * @param postalCode The customer postal code
     * @param phone The customer phone number
     * @param division The customer division
     * @param country The customer country
     */
    public Customer(int id, String name, String address, String postalCode, String phone, String division, String country) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.division = division;
        this.country = country;
    }

    /**
     * The getter for the customer ID.
     *
     * @return The ID
     */
    public int getId() {
        return id;
    }

    /**
     * The setter for the customer ID.
     *
     * @param id The ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * The getter for the customer name.
     *
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * The setter for the customer name.
     *
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The getter for the customer address.
     *
     * @return The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * The setter for the customer address.
     *
     * @param address The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * The getter for the customer postal code.
     *
     * @return The postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * The setter for the customer postal code.
     *
     * @param postalCode The postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * The getter for the customer phone number.
     *
     * @return The phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * The setter for the customer phone number.
     *
     * @param phone The phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * The getter for the customer division.
     *
     * @return The division
     */
    public String getDivision() {
        return division;
    }

    /**
     * The setter for the customer division.
     *
     * @param division The division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * The getter for the customer country.
     *
     * @return The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * The setter for the customer country.
     *
     * @param country The country
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
