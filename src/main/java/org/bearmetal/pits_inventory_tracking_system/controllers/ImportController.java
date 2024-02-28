package org.bearmetal.pits_inventory_tracking_system.controllers;

import org.bearmetal.pits_inventory_tracking_system.ImportBackend;
import org.bearmetal.pits_inventory_tracking_system.utils.PageLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.stage.FileChooser.ExtensionFilter;

public class ImportController {
    PageLoader pageLoader = new PageLoader();

    @FXML
    protected void onImportButtonClicked(ActionEvent event) throws SQLException, FileNotFoundException{
        FileChooser importFileChooser = new FileChooser();
        importFileChooser.setTitle("Open a CSV file to import");
        importFileChooser.getExtensionFilters().addAll(new ExtensionFilter(".csv files", "*.csv"), new ExtensionFilter("All files", "*.*"));
        Window currentWindow = ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = importFileChooser.showOpenDialog(currentWindow);
        if (selectedFile == null){
            return;
        }
        ImportBackend.importFromFile(selectedFile);
    }
}

