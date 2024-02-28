package org.bearmetal.pits_inventory_tracking_system.utils;

import java.io.IOException;

import org.bearmetal.pits_inventory_tracking_system.ApplicationVars;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PageLoader {
    public void openNewPage(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bearmetal/pits_inventory_tracking_system/" + fxmlFile));
            Parent root = loader.load();

            Scene scene = new Scene(root, ApplicationVars.windowWidth, ApplicationVars.windowHeight);

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