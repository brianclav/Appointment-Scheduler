package DAO;

import model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utility.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * Appointment Dao implementation.
 *
 * Saves and retrieves data to and from database.
 *
 * @author Brian Clavadetscher
 */
public class AppointmentDaoImpl implements AppointmentDao {

    /**
     * Gets all appointment data from the appointment and contact tables of the database, turns them into Appointment objects, and adds those objects to an observable list.
     *
     * @return Observable list of all appointments as Appointment objects
     */
    @Override
    public ObservableList<Appointment> getAllAppointments() {

        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM Appointments " +
                    "INNER JOIN Contacts ON Appointments.Contact_ID = Contacts.Contact_ID";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                String appointmentLocation = rs.getString("Location");
                String appointmentType = rs.getString("Type");
                LocalDateTime appointmentStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appointmentEnd = rs.getTimestamp("End").toLocalDateTime();
                int apptCustomerID = rs.getInt("Customer_ID");
                int apptUserID = rs.getInt("User_ID");
                String apptContactName = rs.getString("Contact_Name");

                Appointment A = new Appointment(appointmentId, appointmentTitle, appointmentDescription,
                        appointmentLocation, appointmentType, appointmentStart, appointmentEnd,
                        apptCustomerID, apptUserID, apptContactName);

                appointmentList.add(A);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * Adds a row to the appointment table of the database using an appointment object.
     *
     * @param title Appointment title
     * @param description Appointment description
     * @param location Appointment location
     * @param contactId Appointment contact ID
     * @param type Appointment type
     * @param startDateTime Appointment start date and time
     * @param endDateTime Appointment end date and time
     * @param createDate Appointment creation date
     * @param createdBy Appointment created by user
     * @param customerId Appointment customer ID
     * @param userId Appointment user ID
     */
    @Override
    public void addAppointment(String title, String description, String location, int contactId, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime createDate, String createdBy, int customerId, int userId) {
        try {
            String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, " +
                    "Created_by, Last_Update, Last_Updated_by, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(startDateTime));
            ps.setTimestamp(6, Timestamp.valueOf(endDateTime));
            ps.setTimestamp(7, Timestamp.valueOf(createDate));
            ps.setString(8, createdBy);
            ps.setTimestamp(9, Timestamp.valueOf(createDate));
            ps.setString(10, createdBy);
            ps.setInt(11, customerId);
            ps.setInt(12, userId);
            ps.setInt(13, contactId);

            ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Updates a row in the appointment table of the database using an appointment object.
     *
     * @param title Appointment title
     * @param description Appointment description
     * @param location Appointment location
     * @param contactId Appointment contact ID
     * @param type Appointment type
     * @param startDateTime Appointment start date and time
     * @param endDateTime Appointment end date and time
     * @param updateDate Appointment update date
     * @param updatedBy Appointment updated by
     * @param customerId Appointment customer ID
     * @param userId Appointment user ID
     * @param apptId Appointment ID
     */
    @Override
    public void updateAppointment(String title, String description, String location, int contactId, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime updateDate, String updatedBy, int customerId, int userId, int apptId) {
        try {
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, " +
                    "Last_Update = ?, Last_Updated_by = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE appointment_ID = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(startDateTime));
            ps.setTimestamp(6, Timestamp.valueOf(endDateTime));
            ps.setTimestamp(7, Timestamp.valueOf(updateDate));
            ps.setString(8, updatedBy);
            ps.setInt(9, customerId);
            ps.setInt(10, userId);
            ps.setInt(11, contactId);
            ps.setInt(12, apptId);

            ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Deletes a row in the appointment table of the database using an appointment ID.
     *
     * @param appointmentId The appointment ID
     */
    @Override
    public void deleteAppointment(int appointmentId) {
        try {
            //add creation date, created by user, last update, last updated by,
            String sql = "DELETE FROM appointments WHERE appointment_ID = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setInt(1, appointmentId);

            ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}