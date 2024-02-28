package org.bearmetal.pits_inventory_tracking_system.controllers;

import org.bearmetal.pits_inventory_tracking_system.utils.PageLoader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ImportController {
    PageLoader pageLoader = new PageLoader();

    @FXML
    protected void onImportButtonClicked(ActionEvent event){
        System.out.println("Button clicked");
    }
}

