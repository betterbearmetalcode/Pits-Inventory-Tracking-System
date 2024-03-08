package org.bearmetal.pits_inventory_tracking_system;

import java.util.ArrayList;
import java.util.HashMap;

import org.bearmetal.pits_inventory_tracking_system.models.Category;
import org.bearmetal.pits_inventory_tracking_system.models.Event;
import org.bearmetal.pits_inventory_tracking_system.models.Item;
import org.bearmetal.pits_inventory_tracking_system.models.Location;

public class ApplicationCache {
    public static ArrayList<Item> items = new ArrayList<Item>();
    public static ArrayList<Category> categories = new ArrayList<Category>();
    public static ArrayList<Event> events = new ArrayList<Event>();
    public static ArrayList<Location> standaloneLocations = new ArrayList<Location>();
    public static HashMap<Integer, Location> locationIDMap = new HashMap<Integer, Location>();
    public static HashMap<Integer, Item> itemIDMap = new HashMap<Integer, Item>(); 
}
