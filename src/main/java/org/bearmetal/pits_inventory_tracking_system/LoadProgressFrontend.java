package org.bearmetal.pits_inventory_tracking_system;

import java.lang.reflect.Method;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LoadProgressFrontend {
    private static Stage loadProgressStage;
    private static Text progressMessage;
    private static String DEFAULT_MESSAGE = "Loading...";
    private static Class loaderShownCallbackHolder;

    public static EventHandler<WindowEvent> shownHandler = new EventHandler<WindowEvent>(){
        @Override
        public void handle(WindowEvent event){

        }
    };

    private static void loaderShownCallbackDefaultImpl(){
        ;
    }

    private static void prepare(){
        loadProgressStage = new Stage();
        loadProgressStage.initModality(Modality.WINDOW_MODAL);
        loadProgressStage.setTitle("Loading");
        ProgressIndicator indicator = new ProgressIndicator();
        indicator.setStyle("-fx-accent:#fff120");
        progressMessage = new Text("Launching PITS...");

        progressMessage.setFill(Color.YELLOW);
        VBox vbox = new VBox(indicator, progressMessage);
        vbox.setBackground(Background.EMPTY);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(15));
        loadProgressStage.setScene(new Scene(vbox, Color.BLACK));
        System.out.println("Prepared loader.");
    }

    /** Updates the loader progress string.
     * 
     * @param message
     * The message to update the UI with.
     */
    public static void updateLoaderProgress(String message){
        progressMessage.setText(message);
    }

    /**Updates the loader progress string from a different Thread.
     * <p>
     * Schedules updateLoaderProgress to be called on the application thread soon.
     * @param message
     * The message to update the UI with.
     */
    public static void updateLoaderProgressFromOtherThread(String message){
        Platform.runLater(new Runnable() {
            public void run(){
                updateLoaderProgress(message);
            }
        });
    }

    public static void displayLoader(Method callback){
        if (loadProgressStage == null){
            prepare();
        }
        loadProgressStage.setOnShowing(shownHandler);
        loadProgressStage.show();
    }

    public static void hideLoader(){
        progressMessage.setText(DEFAULT_MESSAGE);
        loadProgressStage.hide();
    }
}
