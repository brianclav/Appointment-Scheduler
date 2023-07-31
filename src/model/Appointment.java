package model;

import java.time.LocalDateTime;

/**
 * This class models Appointments.
 *
 * It is used to construct Appointments. It is also used to access and manipulate data specific to Appointments.
 *
 * @author Brian Clavadetscher
 */
public class Appointment {
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerId;
    private int userId;
    private String contactName;

    /**
     * This is the constructor for Appointment objects.
     *
     * @param id The appointment ID.
     * @param title The appointment title.
     * @param description The appointment description.
     * @param location The appointment location.
     * @param type The appointment type.
     * @param start The appointment start.
     * @param end The appointment end.
     * @param customerId The appointment customer ID.
     * @param userId The appointment User ID.
     * @param contactName The appointment contact name.
     */
    public Appointment(int id, String title, String description, String location, String type, LocalDateTime start,
                       LocalDateTime end, int customerId, int userId, String contactName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactName = contactName;
    }

    /**
     * The getter for the appointment ID.
     *
     * @return The ID.
     */
    public int getId() {
        return id;
    }

    /**
     * The setter for the appointment ID.
     *
     * @param id The ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * The getter for the appointment title
     *
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * The setter for the appointment title.
     *
     * @param title The title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * The getter for the appointment description.
     *
     * @return The description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * The setter for the appointment description.
     *
     * @param description The description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * The getter for the appointment location.
     *
     * @return The location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * The setter for the appointment location.
     *
     * @param location The location.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * The getter for the appointment type.
     *
     * @return The type1.
     */
    public String getType() {
        return type;
    }

    /**
     * The setter for the appointment type.
     *
     * @param type The type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * The getter for the appointment start.
     *
     * @return The start.
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     *
     * The setter for the appointment start.
     *
     * @param start The start.
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * The getter for the appointment end.
     *
     * @return The end.
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * The setter for the appointment end.
     *
     * @param end The end.
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * The getter for the appointment customer ID.
     *
     * @return The customer ID.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * The setter for the appointment customer ID.
     *
     * @param customerId The customer ID.
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * The getter for the appointment user ID.
     *
     * @return The user id.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * The setter for the appointment user ID.
     *
     * @param userId The user ID.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * The getter for the appointment contact name.
     *
     * @return The contact name.
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * The setter for the appointment contact name.
     *
     * @param contactName The contact name.
     */
    public void setContactName(String contactName) { this.contactName = contactName; }


}