package org.bearmetal.pits_inventory_tracking_system;

import javafx.application.Application;

/** Launches the main application.
 * Only used in uber JARs for PITS.
 * See https://stackoverflow.com/questions/59974282/how-to-bundle-the-javafx-sdk-directly-in-the-output-jar
 */
public class JarLauncher {
    public static void main(String[] args) {
        System.out.println("Launching application through JarLauncher");
        Application.launch(MainApplication.class, args);
    }
}
