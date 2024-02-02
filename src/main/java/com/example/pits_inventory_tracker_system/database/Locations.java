package com.example.pits_inventory_tracker_system.database;

import java.sql.*;

import static com.example.pits_inventory_tracker_system.database.DataBaseConstants.LOCATIONS;

public class Locations {
    public static boolean addLocation(String name){
        if (name.contains("'"))
            throw new IllegalArgumentException("Illegal Character \"'\"");
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:pits.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            ResultSet rs = statement.executeQuery("SELECT " + LOCATIONS.getRow("name") + " FROM Locations");
            while(rs.next()) {
                if (rs.getString("location_name").equals(name))
                    return false;
            }
            statement.execute("INSERT INTO Locations (location_name) VALUES ('" + name + "');");
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
