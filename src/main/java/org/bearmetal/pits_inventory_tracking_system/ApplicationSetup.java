package org.bearmetal.pits_inventory_tracking_system;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bearmetal.pits_inventory_tracking_system.models.Item;
import org.bearmetal.pits_inventory_tracking_system.models.Location;
import org.bearmetal.pits_inventory_tracking_system.models.Event;
import org.bearmetal.pits_inventory_tracking_system.utils.ConfigManager;
import org.bearmetal.pits_inventory_tracking_system.utils.DatabaseManager;


/**Handles application setup.
 * @author Colin Rice
 */
public class ApplicationSetup {

    private static void loadItems() throws SQLException{
        ArrayList<HashMap<String, Object>> ret = DatabaseManager.exec("SELECT * FROM items");
        if (ret == null){
            return;
        }
        for (HashMap<String, Object> itemData : ret){
            Item i = new Item(itemData);
            System.out.println("Added Item " + i.getID() + " to cache.");
            ApplicationCache.items.add(i);
        }
    }

    private static void loadLocations() throws SQLException{
        ArrayList<HashMap<String, Object>> ret = DatabaseManager.exec("SELECT * FROM locations");
        if (ret == null){
            return;
        }
        for (HashMap<String, Object> locationData : ret){
            Location i = new Location(locationData);
            System.out.println("Added Location " + i.getLocationID() + " to cache.");
            ApplicationCache.locations.add(i);
        }
    }

    private static void loadEvents() throws SQLException{
        ArrayList<HashMap<String, Object>> ret = DatabaseManager.exec("SELECT * FROM events");
        if (ret == null){
            return;
        }
        for (HashMap<String, Object> eventData : ret){
            Event i = new Event(eventData);
            ApplicationCache.events.add(i);
        }
    }

    /**
     * Constructs items / locations / events from data stored in the database.
     * @throws SQLException
     */
    public static void buildCaches() throws SQLException{
        loadItems();
        loadLocations();
        loadEvents();
    }

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
    public static void doInitialSetup() throws SQLException, FileNotFoundException, IOException, RuntimeException{
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
        File lockFile = new File("PITS.lock");
        if (lockFile.isFile()){
            throw new RuntimeException("<>Either another instance of PITS is already running or an instance didn't shut down cleanly.\nClose the other instance and relaunch PITS.\nIf this message appears again, exit PITS and remove PITS.lock.");
        }
        lockFile.createNewFile();
    }
    
}
