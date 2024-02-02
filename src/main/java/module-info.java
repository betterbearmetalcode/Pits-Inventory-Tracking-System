module com.example.pits_inventory_tracker_system {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens com.example.pits_inventory_tracker_system to javafx.fxml;
    exports com.example.pits_inventory_tracker_system;
    exports com.example.pits_inventory_tracker_system.database;
    opens com.example.pits_inventory_tracker_system.database to javafx.fxml;
}