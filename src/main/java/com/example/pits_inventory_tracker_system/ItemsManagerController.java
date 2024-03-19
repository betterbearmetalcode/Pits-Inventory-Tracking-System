package com.example.pits_inventory_tracker_system;

import com.example.pits_inventory_tracker_system.utils.PageLoader;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class ItemsManagerController {
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
    protected void onAddItemsClick(ActionEvent event) {pageLoader.openNewPage(event, "addItems.fxml");}
    @FXML
    protected void onRemoveItemsClick(ActionEvent event) {pageLoader.openNewPage(event, "removeItems.fxml");
    }
    @FXML
    protected void onEditItemsClick(ActionEvent event) {pageLoader.openNewPage(event, "editItems.fxml");}
}