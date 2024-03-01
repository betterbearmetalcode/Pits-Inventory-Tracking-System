package org.bearmetal.pits_inventory_tracking_system.controllers;

import org.bearmetal.pits_inventory_tracking_system.ImportBackend;
import org.bearmetal.pits_inventory_tracking_system.utils.PageLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.stage.FileChooser.ExtensionFilter;

public class ImportController {
    PageLoader pageLoader = new PageLoader();
    private static Thread importThread;

    @FXML
    private ProgressIndicator importProgressIndicator;

    @FXML
    private Label importProgressText;

    @FXML
    private Label itemsSuccessful;

    @FXML
    private Label itemsInvalid;

    @FXML
    private Label itemsSkipped;

    @FXML
    private Pane importResultContainer;

    public void reportProgressCallback(String status){
        Platform.runLater(new Runnable() {
            public void run(){
                importProgressText.setText(status);
            }
        });
    }

    public void importDoneCallback(Integer importedItems, Integer invalidItems, Integer skippedItems){
        Platform.runLater(new Runnable() {
            public void run(){
                importProgressIndicator.setVisible(false);
                importProgressText.setText("Import finished. Click the button above to import data from another file.");
                importResultContainer.setVisible(true);
                itemsSuccessful.setText("" + importedItems + " items added");
                itemsInvalid.setText("" + invalidItems + " invalid items");
                itemsSkipped.setText("" + skippedItems + " items already existed");
            }
        });
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
        Thread importThread = new Thread(new ImportBackend(selectedFile, this));
        importProgressIndicator.setVisible(true);
        importProgressText.setVisible(true);
        importProgressText.setText("");
        importThread.start();
    }
}

