package com.example.pits_inventory_tracker_system.utils;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class PageLoader {
    public void openNewPage(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pits_inventory_tracker_system/" + fxmlFile));
            Parent content = loader.load();

            Pane rootPane = new Pane();
            rootPane.setStyle("-fx-background-color: black;");
            rootPane.getChildren().add(content);

            Scene scene = new Scene(rootPane);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            FadeTransition fadeInTransition = new FadeTransition(Duration.millis(1500), rootPane);
            fadeInTransition.setFromValue(0.0);
            fadeInTransition.setToValue(1.0);

            // Fade out transition
            FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(1500), rootPane);
            fadeOutTransition.setFromValue(1.0);
            fadeOutTransition.setToValue(0.0);

            // Sequentially play fadeInTransition and fadeOutTransition
            fadeInTransition.setOnFinished(e -> fadeOutTransition.play());
            fadeInTransition.play();

            // Set the new scene after fadeOutTransition completes
            fadeOutTransition.setOnFinished(e -> stage.setScene(scene));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
