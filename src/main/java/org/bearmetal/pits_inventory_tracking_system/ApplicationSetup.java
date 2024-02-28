package org.bearmetal.pits_inventory_tracking_system;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import org.bearmetal.pits_inventory_tracking_system.utils.ConfigManager;
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
    public static void doInitialSetup() throws SQLException, FileNotFoundException{
        File databaseFile = new File("PITSdb.db");
        if (! databaseFile.isFile()){
            System.out.println("No database file found!");
            ApplicationVars.setIsFirstRun(true);
        } else {
            ApplicationVars.setIsFirstRun(false);
        }
        File configFile = new File("PITSconfig.ini");
        if (! configFile.isFile()){
            System.out.println("No configuration file found!");
            ConfigManager.createConfigFile();
        } else {
            System.out.println("Configuration file found.");
        }
    }
    
}
