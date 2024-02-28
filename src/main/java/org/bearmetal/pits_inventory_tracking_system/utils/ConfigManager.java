package org.bearmetal.pits_inventory_tracking_system.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;


/** Handles configuration data.
 * 
 * @author Colin Rice
 */
public class ConfigManager {

    private static HashMap<String, String> configData = new HashMap<String, String>();

    public static HashMap<String, String> getConfigData(){
        return configData;
    }

    public static void saveConfigData(){

    }

    public static void setConfigData(HashMap<String, String> d){
        configData = d;
    }

    /**
     * Opens and reads the configuration file. Adds configuration data to configData.
     * @throws RuntimeException
     */
    public static void readConfigFile() throws RuntimeException{
        File configFile = new File("PITSconfig.ini");
        if (! configFile.canRead()){
            throw new RuntimeException("No permission to read configuration file.");
        }
        try (Scanner configInput = new Scanner(configFile)) {
            while (configInput.hasNextLine()){
                String currentLine = configInput.nextLine().stripLeading();
                //Is our current line blank or a section header?
                if (currentLine.startsWith("[") || currentLine == ""){
                    continue;
                } else if (! currentLine.contains("=")){
                    System.err.println("A line in the configuration file is invalid!");
                    System.err.println(currentLine);
                }
                String[] configEntry = currentLine.split("=", 2);
                setConfigEntry(configEntry[0], configEntry[1]);
            }
        } catch (IOException err){
            throw new RuntimeException("Could not read configuration file.");
        }
    }

    private static void writeConfigEntry(String key, String value){

    }

    private static void setConfigEntry(String key, String value){
        configData.put(key, value);
    }

    public static void createConfigFile() throws RuntimeException{
        try{
            File config = new File("PITSconfig.ini");
            config.createNewFile();
            if (! config.canWrite()){
                throw new RuntimeException("No permission to write to configuration file.");
            }

        } catch (IOException err){
            throw new RuntimeException("Could not create configuration file.");
        }
    }

}