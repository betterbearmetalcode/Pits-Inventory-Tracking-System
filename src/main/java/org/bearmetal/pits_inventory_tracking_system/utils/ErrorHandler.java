package org.bearmetal.pits_inventory_tracking_system.utils;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class ErrorHandler {
    
    public static EventHandler<ActionEvent> exitButtonHandler = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
            Platform.exit();
        }
    };

    public static void showFatalErrorDialog(Thread t, Throwable err){
        showFatalErrorDialog(err, t.getName() + " " + err.toString());
    }

    public static void showFatalErrorDialog(Exception err){
        showFatalErrorDialog(err, err.toString());
    }

    public static void showFatalErrorDialog(Throwable err, String message){
        err.printStackTrace();
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setTitle("Fatal Error");
        message = "An error occurred:\n" + message + "\nPITS cannot continue and must be relaunched.";
        Text errorMessage = new Text(message);
        Button exitButton = new Button("Exit");
        errorMessage.setFill(Color.YELLOW);
        exitButton.setOnAction(exitButtonHandler);
        VBox vbox = new VBox(errorMessage, exitButton);
        vbox.setBackground(Background.EMPTY);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(15));

        dialogStage.setScene(new Scene(vbox, Color.BLACK));
        dialogStage.show();
    }

}
