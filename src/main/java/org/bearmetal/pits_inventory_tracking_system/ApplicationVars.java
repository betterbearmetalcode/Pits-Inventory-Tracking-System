package org.bearmetal.pits_inventory_tracking_system;

/** Variables shared between the application and utilities.
 * 
 */
public class ApplicationVars {
    public static Boolean isFirstRun;
    public static Double windowWidth = 1600.0;
    public static Double windowHeight = 900.0;

    public static void setIsFirstRun(Boolean i){
        isFirstRun = i;
    }

    public static void setWindowWidth(Double w){
        windowWidth = w;
    }

    public static void setWindowHeight(Double h){
        windowHeight = h;
    }

}
