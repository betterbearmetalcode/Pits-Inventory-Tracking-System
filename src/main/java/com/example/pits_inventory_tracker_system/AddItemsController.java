package com.example.pits_inventory_tracker_system;

import com.example.pits_inventory_tracker_system.utils.PageLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class AddItemsController {

    @FXML
    private ComboBox<String> ComboBox;

    private ObservableList<String> listTest = FXCollections.observableArrayList("option1", "option2", "option3");

    public void initialize() {
        ComboBox.setItems(listTest);
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
    protected void onBackClick(ActionEvent event) {pageLoader.openNewPage(event, "itemsManager.fxml");}
    @FXML
    protected void onClearClick(ActionEvent event) {pageLoader.openNewPage(event, "addItems.fxml");}
    @FXML
    protected  void onAddClick(ActionEvent event) {}


}
