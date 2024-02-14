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

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main.fxml"));
        try {
            DatabaseManager.connect("PITSdb");
        } catch (SQLException err) {
            System.out.println("Couldn't connect to database!");
            err.printStackTrace();
        }
        HashMap<String, String> tables = new HashMap<String, String>();
        tables.put("locations", "location_id INTEGER PRIMARY KEY, location_name TEXT, is_category INTEGER, parent_category INTEGER");
        DatabaseManager.setTables(tables);
        DatabaseManager.ensureTables();
        Scene scene = new Scene(fxmlLoader.load(), 1600, 900);
        stage.setTitle("2046 Pits Inventory Tracker System");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}