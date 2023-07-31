package DAO;

import model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utility.DBConnection;

import java.sql.*;

/**
 * Country Dao implementation.
 *
 * Retrieves data from database.
 *
 * @author Brian Clavadetscher
 */
public class CountryDaoImpl implements CountryDao {

    /**
     * Gets all country rows from the country Table of the database, turns them into country objects, and adds those objects to an observable list.
     *
     * @return Observable list of all country as Country objects
     */
    @Override
    public ObservableList<Country> getAllCountries() {

        ObservableList<Country> countryList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM Countries";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Country C = new Country(countryId, countryName);
                countryList.add(C);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return countryList;
    }
}
