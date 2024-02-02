package com.example.pits_inventory_tracker_system.database;

import java.sql.*;

import static com.example.pits_inventory_tracker_system.database.DataBaseConstants.EVENTS;

public class Events {
    public static boolean addEvent(String name, String desc) {
        if (name.contains("'") || desc.contains("'"))
            throw new IllegalArgumentException("Illegal Character \"'\"");
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:pits.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            ResultSet rs = statement.executeQuery("SELECT " + EVENTS.getRow("name") + " FROM Events");
            while(rs.next()) {
                if (rs.getString("event_name").equals(name))
                    return false;
            }
            statement.execute("INSERT INTO Events (event_name, event_desc) VALUES ('"+ name +"', '"+ desc + "');");
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
