package org.bearmetal.pits_inventory_tracking_system;

import java.io.File;
import java.sql.SQLException;

import org.bearmetal.pits_inventory_tracking_system.utils.DatabaseManager;

/**Handles application setup.
 * @author Colin Rice
 */
public class ApplicationSetup {

    public static void doDatabaseSetup() throws SQLException{
        try {
            DatabaseManager.connect("PITSdb");
        } catch (SQLException err) {
            System.out.println("Couldn't connect to database!");
            err.printStackTrace();
        }
        DatabaseManager.ensureTables();
    }

    /**
     * Do some setup work before the main application starts.
     */
    public static void doInitialSetup() throws SQLException{
        File databaseFile = new File("PITSdb.db");
        if (! databaseFile.isFile()){
            System.out.println("No database file found!");
            MainApplication.isFirstRun = true;
        } else {
            MainApplication.isFirstRun = false;
        }
    }
    
}
