package DAO;

import javafx.collections.ObservableList;
import model.Appointment;

import java.time.LocalDateTime;

/**
 * Appointment Dao interface.
 *
 * It defines the standard operations to be performed on the Appointment model object.
 *
 * @author Brian Clavadetscher
 */
public interface AppointmentDao {
    /**
     * Gets all appointments.
     *
     * @return All appointments.
     */
    ObservableList<Appointment> getAllAppointments();

    /**
     * Adds an appointment to the database.
     *
     * @param title Appointment title
     * @param description Appointment description
     * @param location Appointment location
     * @param contactName Appointment contact name
     * @param type Appointment type
     * @param startDateTime Appointment start date and time
     * @param endDateTime Appointment end date and time
     * @param createDate Appointment creation date
     * @param createdBy Appointment created by user
     * @param customerId Appointment customer ID
     * @param userId Appointment user ID
     */
    void addAppointment(String title, String description, String location, int contactName, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime createDate, String createdBy, int customerId, int userId);

    /**
     * Updates an appointment in the database.
     *
     * @param title Appointment title
     * @param description Appointment description
     * @param location Appointment location
     * @param contactName Appointment contact name
     * @param type Appointment type
     * @param startDateTime Appointment start date and time
     * @param endDateTime Appointment end date and time
     * @param updateDate Appointment update date
     * @param updatedBy Appointment updated by user
     * @param customerId Appointment customer ID
     * @param userId Appointment user ID
     * @param apptId Appointment ID
     */
    void updateAppointment(String title, String description, String location, int contactName, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime updateDate, String updatedBy, int customerId, int userId, int apptId);

    /**
     * Deletes an appointment in the database.
     *
     * @param appointmentId Appointment ID
     */
    void deleteAppointment(int appointmentId);
}