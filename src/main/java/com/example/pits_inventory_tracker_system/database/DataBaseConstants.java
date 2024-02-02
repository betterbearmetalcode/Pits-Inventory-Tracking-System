package com.example.pits_inventory_tracker_system.database;

import java.util.HashMap;
import java.util.Map;

public class DataBaseConstants {
    public static final String CONNECTION_PATH = "jdbc:sqlite:pits.db";
    public record Table(HashMap<String, String> rowMap) {
        public String getRow(String row) {
            return rowMap.get(row);
        }
    }
    public static final Table LOCATIONS = new Table(new HashMap<>(Map.of(
            "id", "Locations.location_id",
            "name","Locations.location_name"
    )));
    public static final Table ITEMS = new Table(new HashMap<>(Map.of(
            "id", "Item.item_id",
            "name","Item.item_name",
            "desc","Item.item_desc",
            "available","Item.item_available",
            "part #","Item.item_part_number",
            "info","Item.item_info",
            "packed","Item.packed",
            "photo path","Item.photo_path"
    )));

    public static final Table EVENTS = new Table(new HashMap<>(Map.of(
            "item id", "Events.item_id",
            "id","Events.event_id",
            "desc","Events.event_desc"
    )));
    public static final Table LOCATION_ITEM = new Table(new HashMap<>(Map.of(
            "item id", "Location_Item.item_id",
            "location id","Location_Item.location_id",
            "details","Location_Item.details"
    )));
    public static final Table ITEM_EVENT = new Table(new HashMap<>(Map.of(
            "item id", "Item_Event.item_id",
            "event id","Item_Event.event_id",
            "timestamp","Item_Event.timestamp",
            "item_delta","Item_Event.item_delta",
            "details","Item_Event.details"
    )));
}

