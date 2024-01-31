package com.example.pits_inventory_tracker_system.utils;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class PageLoader {
    public void openNewPage(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pits_inventory_tracker_system/" + fxmlFile));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), root);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            fadeTransition.play();

            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
