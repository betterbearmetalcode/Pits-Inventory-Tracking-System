package com.example.pits_inventory_tracker_system;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class MainController {


    @FXML
    private Label welcomeText;

    @FXML
    protected Button Button1;
    @FXML
    protected void onHelloButtonClick() {
        Button1.setStyle("fx-background-color: #000000");
    }

}




