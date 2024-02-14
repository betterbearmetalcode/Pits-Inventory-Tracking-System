package org.bearmetal.pits_inventory_tracking_system;

import java.io.IOException;
import java.sql.SQLException;

import org.bearmetal.pits_inventory_tracking_system.utils.DatabaseManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main.fxml"));
        try {
            DatabaseManager.connect("PITSdb");
        } catch (SQLException err) {
            System.out.println("Couldn't connect to database!");
            err.printStackTrace();
        }
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