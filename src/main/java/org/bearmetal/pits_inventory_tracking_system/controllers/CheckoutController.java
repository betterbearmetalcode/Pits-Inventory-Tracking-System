package org.bearmetal.pits_inventory_tracking_system.controllers;

import org.bearmetal.pits_inventory_tracking_system.utils.PageLoader;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class CheckoutController {
    @FXML
    private javafx.scene.control.ComboBox<String> reason;

    private ObservableList<String> reasonOptions = FXCollections.observableArrayList("I'm using it on the robot", "Another team is borrowing it", "It has been thrown away", "Item has been degraded (thrown away)");

    public void initialize() {
        reason.setItems(reasonOptions);
    }
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
    protected void onSearchClick(ActionEvent event) {pageLoader.openNewPage(event, "searchPage.fxml");}
    @FXML
    protected void onBackClick(ActionEvent event) {pageLoader.openNewPage(event, "checkoutManager.fxml");};
}
