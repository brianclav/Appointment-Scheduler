package DAO;

import javafx.collections.ObservableList;
import model.Country;

/**
 * Country Dao interface.
 *
 * It defines the standard operations to be performed on the Country model object.
 *
 * @author Brian Clavadetscher
 */
public interface CountryDao {
    /**
     * Gets all countries from the database.
     *
     * @return All countries.
     */
    ObservableList<Country> getAllCountries();
}
