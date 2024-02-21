package org.bearmetal.pits_inventory_tracking_system;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import org.bearmetal.pits_inventory_tracking_system.utils.DatabaseManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {

    public static Boolean isFirstRun;

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        HashMap<String, String> tables = new HashMap<String, String>();
        tables.put("locations", "location_id INTEGER PRIMARY KEY, location_name TEXT, is_category INTEGER, parent_category INTEGER");
        tables.put("items", "item_id INTEGER, item_name TEXT PRIMARY KEY, item_description TEXT, item_quantity INTEGER, item_available INTEGER, item_vendor TEXT, item_part_number TEXT, item_info TEXT, packed INTEGER, location_id INTEGER, photo_path TEXT");
        tables.put("events", "item_id INTEGER, event_id INTEGER PRIMARY KEY, event_type INTEGER, event_bulk INTEGER, event_timestamp INTEGER, event_desc TEXT, item_delta INTEGER, item_state INTEGER");
        DatabaseManager.setTables(tables);
        ApplicationSetup.doInitialSetup();
        FXMLLoader initialSceneLoader;
        if (isFirstRun){
            initialSceneLoader = new FXMLLoader(MainApplication.class.getResource("import.fxml"));
        } else {
            initialSceneLoader = new FXMLLoader(MainApplication.class.getResource("main.fxml"));
        }
        Scene scene = new Scene(initialSceneLoader.load(), 1600, 900);
        stage.setTitle("2046 Pits Inventory Tracker System");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}