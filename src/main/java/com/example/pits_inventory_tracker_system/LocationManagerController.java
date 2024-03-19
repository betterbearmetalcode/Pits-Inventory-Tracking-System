package com.example.pits_inventory_tracker_system;

import com.example.pits_inventory_tracker_system.utils.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class LocationManagerController {
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
    protected  void onAddLocationsClick(ActionEvent event) {pageLoader.openNewPage(event, "addLocations.fxml");}
    @FXML
    protected void onRemoveLocationsClick(ActionEvent event) {pageLoader.openNewPage(event, "removeLocations.fxml");}
    @FXML
    protected void onEditLocationsClick (ActionEvent event) {pageLoader.openNewPage(event, "editLocations.fxml");}
}
