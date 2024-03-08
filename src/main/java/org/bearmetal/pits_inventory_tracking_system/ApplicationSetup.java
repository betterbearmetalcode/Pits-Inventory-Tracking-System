package org.bearmetal.pits_inventory_tracking_system;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bearmetal.pits_inventory_tracking_system.models.Item;
import org.bearmetal.pits_inventory_tracking_system.models.Location;
import org.bearmetal.pits_inventory_tracking_system.models.Category;
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
            ApplicationCache.itemIDMap.put(i.getID(), i);
        }
    }

    private static ArrayList<Item> getChildItems(HashMap<String, Object> locationData) throws SQLException{
        //Get items contained in this location.
        Object[] params = { (Integer) locationData.get("location_id") };
        System.out.println(params[0]);
        ArrayList<HashMap<String, Object>> itemData = DatabaseManager.exec("SELECT * FROM items WHERE location_id=? ORDER BY item_name", params);
        ArrayList<Item> childItems = new ArrayList<Item>();
        //Populate a list of items.
        for (HashMap<String, Object> itemID : itemData){
            Object rawItemID = itemID.get("item_id");
            Item item = ApplicationCache.itemIDMap.get(rawItemID);
            System.out.println("Adding child item " + item.getName());
            childItems.add(item);
        }
        return childItems;
    }

    private static void loadLocations() throws SQLException{
        ArrayList<HashMap<String, Object>> ret = DatabaseManager.exec("SELECT * FROM locations ORDER BY location_name");
        if (ret == null){
            return;
        }
        //Add raw locations to cache.
        for (HashMap<String, Object> locationData : ret){
            Location l = null;
            if (locationData.get("parent_category") == null){
                locationData.replace("parent_category", 0);
            }
            System.out.println(locationData.get("location_name") + " has parent category " + locationData.get("parent_category"));
            ArrayList<Item> childItems = getChildItems(locationData);
            //top-level location? create a Category.
            if ((Integer) locationData.get("parent_category") == 0){
                ArrayList<Location> childLocations = new ArrayList<Location>();
                //For each location in this Category
                for (HashMap<String, Object> child : ret){
                    if (child.get("parent_category") == null){
                        child.replace("parent_category", 0);
                    }
                    //If the parent category is the ID of the current location, add it to child locations.
                    if (child.get("parent_category").equals(locationData.get("location_id"))){
                        System.out.println("Adding child location " + child.get("location_id"));
                        childItems = getChildItems(child);
                        //Then add the new location.
                        childLocations.add(new Location(child, childItems));
                    }
                }
                Category newCategory = new Category(locationData, childLocations);
                newCategory.setChildItems(childItems);
                ApplicationCache.categories.add(newCategory);
                l = newCategory;
            } else {
                Location newLocation = new Location(locationData, childItems);
                l = newLocation;
            }
            //Then add the Location / Category to the ID mapping.
            if (ApplicationCache.locationIDMap.containsKey(l.getLocationID())){
                continue;
            }
            System.out.println("Added Location " + l.getLocationID() + " to cache.");
            ApplicationCache.locationIDMap.put(l.getLocationID(), l);
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
