package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;
import utility.DBConnection;

import java.sql.*;

/**
 * Division Dao implementation.
 *
 * Retrieves data from database.
 *
 * @author Brian Clavadetscher
 */
public class DivisionDaoImpl implements DivisionDao {

    /**
     * Gets all Division rows from the Division Table of the database, turns them into Division objects, and adds those objects to an observable list.
     *
     * @return Observable list of all divisions as Division objects
     */
    @Override
    public ObservableList<Division> getAllDivisions() {

        ObservableList<Division> divisionList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM First_Level_Divisions";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                Division D = new Division(divisionId, divisionName, countryId);
                divisionList.add(D);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return divisionList;
    }
}
