package DAO;

import model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utility.DBConnection;

import java.sql.*;

/**
 * User Dao implementation.
 *
 * Retrieves data from database.
 *
 * @author Brian Clavadetscher
 */
public class UserDaoImpl implements UserDao {

    /**
     * Gets all User rows from the User Table of the database, turns them into User objects, and adds those objects to an observable list.
     *
     * @return Observable list of all users as User objects
     */
    @Override
    public ObservableList<User> getAllUsers() {

        ObservableList<User> userList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM Users";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String userPassword = rs.getString("Password");
                User U = new User(userId, userName, userPassword);
                userList.add(U);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }
}
