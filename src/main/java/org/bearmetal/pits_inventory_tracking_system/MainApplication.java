package org.bearmetal.pits_inventory_tracking_system;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import org.bearmetal.pits_inventory_tracking_system.utils.DatabaseManager;
import org.bearmetal.pits_inventory_tracking_system.utils.ErrorHandler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        Thread.setDefaultUncaughtExceptionHandler(ErrorHandler::showFatalErrorDialog);
        try {
            HashMap<String, String> tables = new HashMap<String, String>();
            tables.put("locations",
                    "location_id INTEGER PRIMARY KEY, location_name TEXT, is_category INTEGER, parent_category INTEGER");
            tables.put("items",
                    "item_id INTEGER PRIMARY KEY, item_name TEXT, item_description TEXT, item_quantity INTEGER, item_available INTEGER, item_vendor TEXT, item_part_number TEXT, item_info TEXT, packed INTEGER, location_id INTEGER, photo_path TEXT");
            tables.put("events",
                    "item_id INTEGER, event_id INTEGER PRIMARY KEY, event_type INTEGER, event_bulk INTEGER, event_timestamp INTEGER, event_desc TEXT, item_delta INTEGER, item_state INTEGER");
            DatabaseManager.setTables(tables);
            ApplicationSetup.doInitialSetup();
            FXMLLoader initialSceneLoader;
            ApplicationSetup.doDatabaseSetup();
            ImportBackend.importFromFile(new File("testData.csv"));
            if (ApplicationVars.isFirstRun) {
                initialSceneLoader = new FXMLLoader(MainApplication.class.getResource("import.fxml"));
            } else {
                initialSceneLoader = new FXMLLoader(MainApplication.class.getResource("main.fxml"));
            }
            Scene scene = new Scene(initialSceneLoader.load(), ApplicationVars.windowWidth,
                    ApplicationVars.windowHeight);
            stage.setTitle("2046 Pits Inventory Tracker System");
            stage.setScene(scene);
            stage.setResizable(true);
            stage.show();
        } catch (Exception err) {
            ErrorHandler.showFatalErrorDialog(err);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}