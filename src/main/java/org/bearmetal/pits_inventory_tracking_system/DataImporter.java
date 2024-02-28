package org.bearmetal.pits_inventory_tracking_system;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import org.bearmetal.pits_inventory_tracking_system.utils.DatabaseManager;

/** Handles initial data import.
 * 
 * @author Colin Rice
 */
public class DataImporter {

    private static Random rand = new Random();

    //Map of category name to category ID
    private static HashMap<String, Integer> categoryMapping = new HashMap<String, Integer>();
    private static ArrayList<Integer> locationIDWorkingSet = new ArrayList<Integer>();
    private static ArrayList<Integer> itemIDWorkingSet = new ArrayList<Integer>();
    private static Integer currentLocation;

    private static Integer generateID(){
        Integer id = rand.nextInt(100000000, 999999999);
        //If either of our two working sets contains our ID, skip it.
        //Hopefully this doesn't cause too much recursion?
        if (locationIDWorkingSet.contains(id) || itemIDWorkingSet.contains(id)){
            return generateID();
        }
        return id;
    }

    private static void addLocation(Integer locationID, String locationName, Integer isCategory, Integer parentCategory) throws SQLException{
        Object[] params = {locationID, locationName, isCategory, parentCategory};
        locationIDWorkingSet.add(locationID);
        DatabaseManager.execNoReturn("INSERT INTO locations VALUES(?, ?, ?, ?)", params);
        System.out.println("Added location " + locationID);
        //Probably shouldn't do this but I think it'll work anyways.
        currentLocation = locationID;
    }

    private static void processLocation(String[] curDataSet) throws SQLException{
        String locationName = curDataSet[0].strip();
        //Is this location part of a category?
        if (locationName.contains("-")){
            String[] categoryPair = locationName.split("-", 2);
            String parentName = categoryPair[0].strip();
            String childName = categoryPair[1].strip();
            Integer parentID;
            System.out.println("Parent category: " + parentName);
            System.out.println("Child category: " + childName);
            //Do we have a parent category already registered?
            if (! categoryMapping.containsKey(parentName)){
                //No? Add it.
                parentID = generateID();
                addLocation(parentID, parentName, 1, 0);
            } else {
                parentID = categoryMapping.get(parentName);
            }
            //Now add this location.
            Integer childID = generateID();
            addLocation(childID, childName, 0, parentID);
        } else {
            Integer standaloneID = generateID();
            addLocation(standaloneID, locationName, 0, 0);
        }
    }

    private static Integer getInteger(String in){
        in = in.strip().replace("\"", "");
        if (in.equals("") || in.equals("FALSE")){
            return 0;
        } else if (in.equals("TRUE")) {
            return 1;
        }
        return Integer.parseInt(in);
    }

    private static void processItem(String[] curDataSet) throws SQLException{
        Integer itemID = generateID();
        String itemName = curDataSet[1];
        String itemDescription = curDataSet[2];
        Integer itemQuantity = getInteger(curDataSet[3]);
        Integer itemAvailable = getInteger(curDataSet[3]);
        String itemVendor = curDataSet[4];
        Integer partNumber = getInteger(curDataSet[5]);
        String itemInfo = curDataSet[6];
        Integer packed = getInteger(curDataSet[7]);
        Integer locationID = currentLocation;
        System.out.println("Added item " + itemID);
        Object[] params = {itemID, itemName, itemDescription, itemQuantity, itemAvailable, itemVendor, partNumber, itemInfo, packed, locationID, ""};
        DatabaseManager.execNoReturn("INSERT INTO items VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", params);
    }

    private static void parseLine(String line) throws SQLException{
        //Break the current line into individual columns.
        //This regex matches all commas EXCEPT commas in string literals.
        //Any commas in item names/descriptions would fuck us over otherwise 
        //Limit is set to -1 to allow empty strings so we can index columns properly even if one or more is empty.
        String[] intermediate = line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)", -1);
        if (intermediate[0].equals("") && intermediate[1].equals("") && intermediate[2].equals("")){
            System.out.println("Empty row, skipping.");
            return;
        }
        if (intermediate[0] != ""){
            if (intermediate[0].equals("Location")){
                System.out.println("Skipping column headers.");
                return;
            }
            processLocation(intermediate);
        } else {
            if (currentLocation.equals(null)){
                System.out.println("WARNING: Item before first location, ignoring");
                return;
            }
            try{
                processItem(intermediate);
            } catch (NumberFormatException err){
                System.out.println("ERROR: Invalid item quantity / part number / packed state. These values MUST be integers.");
                System.out.println("Skipping this item.");
            }
        }
    }

    /** Attempts to import data from a CSV file.
     *
     * @param inputFile
     * The file to import data from.   
     */
    public static void importFromFile(File inputFile) throws SQLException, FileNotFoundException{
        try (Scanner inputFileScanner = new Scanner(inputFile)){
            while (inputFileScanner.hasNextLine()){
                parseLine(inputFileScanner.nextLine());
            }
        }
    }

}
