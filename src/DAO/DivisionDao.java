package DAO;

import javafx.collections.ObservableList;
import model.Division;

/**
 * Division Dao interface.
 *
 * It defines the standard operations to be performed on the Division model object.
 *
 * @author Brian Clavadetscher
 */
public interface DivisionDao {
    /**
     * Gets all divisions from the database.
     *
     * @return All divisions.
     */
    ObservableList<Division> getAllDivisions();
}
