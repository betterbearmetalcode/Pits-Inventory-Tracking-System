package com.example.pits_inventory_tracker_system;

import com.example.pits_inventory_tracker_system.utils.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    @FXML
    AnchorPane mainPane;
    @FXML
    private TextField searchField;

    @FXML
    private VBox resultsBox;

    PageLoader pageLoader = new PageLoader();

    @FXML
    protected void onLocationManagerClick(ActionEvent event) {
        pageLoader.openNewPage(event, "locationManager.fxml");
    }

    @FXML
    protected void onItemsManagerClick(ActionEvent event) {
        pageLoader.openNewPage(event, "itemsManager.fxml");
    }

    @FXML
    protected void onCheckoutManagerClick(ActionEvent event) {
        pageLoader.openNewPage(event, "checkoutManager.fxml");
    }

    @FXML
    protected void onLogoClick(ActionEvent event) {
        pageLoader.openNewPage(event, "main.fxml");
    }

    @FXML
    protected void onSettingsClick(ActionEvent event) {
        pageLoader.openNewPage(event, "settings.fxml");
    }

    @FXML
    protected void onSearchClick(ActionEvent event) {
        pageLoader.openNewPage(event, "searchPage.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        // Other initialization code
        resultsBox.getStylesheets().add(getClass().getResource("/stylesheets/searchResultsButtons.css").toExternalForm());
    }

    @FXML
    private void onTextFieldUpdate() {
        String searchTerm = searchField.getText().trim();
        displaySearchResults(searchTerm);
    }

    private void displaySearchResults(String searchTerm) {
        // Clear previous search results
        resultsBox.getChildren().clear();

        // Only display results when search term is not empty
        if (!searchTerm.isEmpty()) {
            // Simulated shop tools list
            String[] shopTools = {"hammer", "screwdriver", "saw", "wrench", "pliers", "tape measure", "level", "utility knife",
                    "drill", "socket wrench", "chisel", "vise", "file", "hack saw", "pliers", "adjustable wrench", "crowbar",
                    "bolt cutter", "hand saw", "hacksaw", "clamp", "mallet", "allen wrench", "wire stripper", "hand drill",
                    "jack plane", "angle grinder", "power drill", "rivet gun", "pipe wrench"};

            // Simulated search results (replace with actual database interaction logic)
            for (String tool : shopTools) {
                // Check if the tool matches the search term
                if (tool.toLowerCase().contains(searchTerm.toLowerCase())) {
                    // Create a new Button for each matching tool
                    Button itemButton = new Button(tool);
                    itemButton.setOnAction(event -> {
                        System.out.println("Button clicked: " + tool);
                    });

                    // Add a thin black rectangle
                    Rectangle separator = new Rectangle();
                    separator.setWidth(resultsBox.getWidth() - 20); // Adjust width as needed
                    separator.setHeight(1); // Thin black line
                    separator.setStyle("-fx-fill: black;");

                    // Add the item Button and separator to the results VBox
                    resultsBox.getChildren().addAll(itemButton, separator);
                }
            }
        }
    }
}