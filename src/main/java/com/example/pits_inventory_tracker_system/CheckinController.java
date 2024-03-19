package com.example.pits_inventory_tracker_system;

import com.example.pits_inventory_tracker_system.utils.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class CheckinController {
    PageLoader pageLoader = new PageLoader();

   /*
    @FXML
    private ComboBox<String> item;
    @FXML
    private ComboBox<String> location;
    @FXML
    private ComboBox<String> qty;

    */

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
    protected void onBackClick(ActionEvent event) {pageLoader.openNewPage(event, "checkoutManager.fxml");}
    @FXML
    protected void onClearClick(ActionEvent event) {pageLoader.openNewPage(event, "checkin.fxml");}
    @FXML
    protected void onCheckinClick(ActionEvent event) {}
}
