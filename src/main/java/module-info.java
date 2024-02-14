module org.bearmetal.pits_inventory_tracking_system {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens org.bearmetal.pits_inventory_tracking_system to javafx.fxml;
    exports org.bearmetal.pits_inventory_tracking_system;
}