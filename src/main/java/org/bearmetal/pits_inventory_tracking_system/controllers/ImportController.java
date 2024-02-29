package org.bearmetal.pits_inventory_tracking_system.controllers;

import org.bearmetal.pits_inventory_tracking_system.ImportBackend;
import org.bearmetal.pits_inventory_tracking_system.utils.PageLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.stage.FileChooser.ExtensionFilter;

public class ImportController {
    PageLoader pageLoader = new PageLoader();
    static Thread importThread;

    @FXML
    private static ProgressIndicator importProgressIndicator;

    @FXML
    private static Label importProgressText;

    //ew. we need this to be static to let us use it from our static methods in ImportBackend
    public static void reportProgressCallback(String status){
        importProgressText.setText(status);
    }

    @FXML
    protected void onImportButtonClicked(ActionEvent event) throws SQLException, FileNotFoundException, InterruptedException{
        if (ImportBackend.importThreadLock.isLocked() && importThread != null){
            //Wait until our import thread exits.
            importThread.join();
        }
        FileChooser importFileChooser = new FileChooser();
        importFileChooser.setTitle("Open a CSV file to import");
        importFileChooser.getExtensionFilters().addAll(new ExtensionFilter(".csv files", "*.csv"), new ExtensionFilter("All files", "*.*"));
        importFileChooser.setInitialDirectory(new File("."));
        Window currentWindow = ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = importFileChooser.showOpenDialog(currentWindow);
        if (selectedFile == null){
            return;
        }
        //Offload ImportBackend work to another thread.
        Thread importThread = new Thread(new ImportBackend(selectedFile));
        importThread.start();
    }
}

