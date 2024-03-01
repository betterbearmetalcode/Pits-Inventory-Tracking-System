package org.bearmetal.pits_inventory_tracking_system;

import java.util.ArrayList;

import org.bearmetal.pits_inventory_tracking_system.models.Event;
import org.bearmetal.pits_inventory_tracking_system.models.Item;
import org.bearmetal.pits_inventory_tracking_system.models.Location;

public class ApplicationCache {
    public static ArrayList<Item> items = new ArrayList<Item>();
    public static ArrayList<Location> locations = new ArrayList<Location>();
    public static ArrayList<Event> events = new ArrayList<Event>();
}
