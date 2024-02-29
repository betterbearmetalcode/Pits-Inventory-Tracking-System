package org.bearmetal.pits_inventory_tracking_system;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

import org.bearmetal.pits_inventory_tracking_system.controllers.ImportController;
import org.bearmetal.pits_inventory_tracking_system.utils.DatabaseManager;

/**
 * Handles initial data import.
 * <p>
 * Implements Runnable to allow for imports without blocking the Application thread.
 * @author Colin Rice
 */
public class ImportBackend implements Runnable {

    private static Random rand = new Random();

    private static final String[] CATEGORY_EDGE_CASES = {"3D-"};

    // Map of category name to category ID
    private static HashMap<String, Integer> categoryMapping = new HashMap<String, Integer>();
    private static ArrayList<Integer> locationIDWorkingSet = new ArrayList<Integer>();
    private static ArrayList<Integer> itemIDWorkingSet = new ArrayList<Integer>();
    private static Integer currentLocation;
    private static String previousItemName = "";
    public static ReentrantLock importThreadLock = new ReentrantLock();
    private File in;

    private static Integer generateID() {
        Integer id = rand.nextInt(100000000, 999999999);
        // If either of our two working sets contains our ID, skip it.
        // Hopefully this doesn't cause too much recursion?
        if (locationIDWorkingSet.contains(id) || itemIDWorkingSet.contains(id)) {
            return generateID();
        }
        return id;
    }

    private static Boolean doesLocationExist(String locationName){
        try{
            Object[] checkParams = {locationName.strip()};
            ArrayList<HashMap<String, Object>> ret = DatabaseManager.exec("SELECT * FROM locations WHERE location_name=?", checkParams);
            if (ret.size() == 0){
                return false; // No results
            }
            currentLocation = (Integer) ret.get(0).get("location_id");
            System.out.println("This location already exists, skipping");
            return true;
        } catch (SQLException err){ //No results returned
            return false;
        }
    }

    private static Boolean doesItemExist(String itemName, String itemDescription, Integer locationID){
        try{
            Object[] checkParams = {itemName, itemDescription, locationID};
            ArrayList<HashMap<String, Object>> ret = DatabaseManager.exec("SELECT * FROM items WHERE item_name=? AND item_description=? AND location_id=?", checkParams);
            if (ret.size() == 0){
                return false; // No results
            }
            System.out.println("Item '" + itemName + "' with description '" + itemDescription + "' already exists, skipping");
            return true;
        } catch (SQLException err){
            return false;
        }
    }

    private static void addItem(Integer itemID, String itemName, String itemDescription, Integer itemQuantity, Integer itemAvailable, String itemVendor, Integer partNumber, String itemInfo, Integer packed, Integer locationID) throws SQLException{
        if (doesItemExist(itemName, itemDescription, locationID)){
            return;
        }
        Object[] params = {itemID, itemName, itemDescription, itemQuantity, itemAvailable, itemVendor, partNumber, itemInfo, packed, locationID};
        DatabaseManager.execNoReturn("INSERT INTO items VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", params);
        //System.out.println("Added item " + itemID + ", name '" + itemName + "', description '" + itemDescription + "'");
    }

    private static void addLocation(Integer locationID, String locationName, Integer isCategory, Integer parentCategory)
            throws SQLException {
        if (doesLocationExist(locationName)){
            //System.out.println("Location exists already.");
            return;
        }
        Object[] params = { locationID, locationName, isCategory, parentCategory };
        locationIDWorkingSet.add(locationID);
        DatabaseManager.execNoReturn("INSERT INTO locations VALUES(?, ?, ?, ?)", params);
        //System.out.println("Added location " + locationID);
        previousItemName = "";
        currentLocation = locationID;
    }

    private static Boolean locationContainsCategoryEdgeCase(String locationName){
        for (String edgeCase : CATEGORY_EDGE_CASES){
            if (locationName.contains(edgeCase)){
                System.out.println("Category edge case " + edgeCase + " detected");
                return true;
            }
        }
        return false;
    }

    private static void processLocation(String[] curDataSet) throws SQLException {
        String locationName = curDataSet[0].strip();
        // Is this location part of a category?
        if (locationName.contains("-") && ! locationContainsCategoryEdgeCase(locationName)) {
            String[] categoryPair = locationName.split("-", 2);
            String parentName = categoryPair[0].strip();
            String childName = categoryPair[1].strip();
            Integer parentID;
            //System.out.println("Parent category: " + parentName);
            //System.out.println("Child category: " + childName);
            // Do we have a parent category already registered?
            if (!doesLocationExist(parentName)) {
                // No? Add it.
                parentID = generateID();
                addLocation(parentID, parentName, 1, 0);
            } else {
                parentID = categoryMapping.get(parentName);
            }
            // Now add this location.
            Integer childID = generateID();
            addLocation(childID, childName, 0, parentID);
        } else {
            System.out.println("Adding standalone location " + locationName);
            Integer standaloneID = generateID();
            addLocation(standaloneID, locationName, 0, 0);
        }
    }

    private static Integer getInteger(String in) {
        in = in.strip().replace("\"", "");
        if (in.equals("") || in.equals("FALSE")) {
            return 0;
        } else if (in.equals("TRUE")) {
            return 1;
        }
        return Integer.parseInt(in);
    }

    private static void processItem(String[] curDataSet) throws SQLException {
        String itemName = curDataSet[1];
        String itemDescription = curDataSet[2];
        //Part of a set of parts (e.g different types of Torx screwdrivers)? Add the set name to the item name.
        if (previousItemName.length() > 0 && itemName.equals("")){
            itemName = previousItemName;
        }
        previousItemName = itemName;
        if(doesItemExist(curDataSet[1], curDataSet[2], currentLocation)){
            return;
        }
        Integer itemID = generateID();
        Integer itemQuantity = getInteger(curDataSet[3]);
        Integer itemAvailable = getInteger(curDataSet[3]);
        String itemVendor = curDataSet[4];
        Integer partNumber = getInteger(curDataSet[5]);
        String itemInfo = curDataSet[6];
        Integer packed = getInteger(curDataSet[7]);
        Integer locationID = currentLocation;
        addItem(itemID, itemName, itemDescription, itemQuantity, itemAvailable, itemVendor, partNumber, itemInfo, packed, locationID);
    }

    private static void parseLine(String line) throws SQLException {
        // Break the current line into individual columns.
        // This regex matches all commas EXCEPT commas in string literals.
        // Any commas in item names/descriptions would fuck us over otherwise
        // Limit is set to -1 to allow empty strings so we can index columns properly
        // even if one or more is empty.
        String[] raw = line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)", -1);
        //Strip leading/trailing whitespace in every column value.
        ArrayList<String> unstripped = new ArrayList<String>();
        for (String column : raw){
            unstripped.add(column.strip());
        }
        //Convert back to a String array.
        String[] intermediate = unstripped.toArray(new String[unstripped.size()]);
        //If the row is missing location name, item name, and item description,
        //it doesn't have enough information for us and we should skip it.
        if (intermediate[0].equals("") && intermediate[1].equals("") && intermediate[2].equals("")) {
            return;
        }
        if (intermediate[0] != "") {
            if (intermediate[0].equals("Location")) {
                System.out.println("Skipping column headers.");
                return;
            }
            processLocation(intermediate);
        } else {
            if (currentLocation.equals(null)) {
                System.out.println("WARNING: Item before first location, ignoring");
                return;
            }
            try {
                processItem(intermediate);
            } catch (NumberFormatException err) {
                ;//System.out.println("ERROR: Invalid item quantity / part number / packed state. These values MUST be integers.");
                //System.out.println("Skipping this item.");
            }
        }
    }

    /**
     * Attempts to import data from a CSV file.
     *
     * @param inputFile
     *                  The file to import data from.
     */
    public static void importFromFile(File inputFile) throws SQLException, FileNotFoundException {
        System.out.println("Importing data from " + inputFile.getName());
        try (Scanner inputFileScanner = new Scanner(inputFile)) {
            while (inputFileScanner.hasNextLine()) {
                parseLine(inputFileScanner.nextLine());
            }
        }
        System.out.println("Done.");
    }

    public ImportBackend(File inputFile){
        this.in = inputFile;
    }

    public void run(){
        importThreadLock.lock();
        try{
            importFromFile(in);
        } catch (Exception err){
            err.printStackTrace();
            //Report error to UI
        } finally {
            importThreadLock.unlock();
        }
    }

}
