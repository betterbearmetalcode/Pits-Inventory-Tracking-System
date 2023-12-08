module com.example.pits_inventory_tracker_system {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.pits_inventory_tracker_system to javafx.fxml;
    exports com.example.pits_inventory_tracker_system;
}