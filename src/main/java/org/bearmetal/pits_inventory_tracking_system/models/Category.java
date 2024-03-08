package org.bearmetal.pits_inventory_tracking_system.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/** A Location that contains other Locations.
 * <p>
 * Assists in populating TreeView fields.
 */
public class Category extends Location {

    protected ArrayList<Location> childLocations;

    public ArrayList<Location> getChildLocations() {
        return this.childLocations;
    }

    public Category(HashMap<String, Object> locationData, ArrayList<Location> childLocations){
        for (Map.Entry<String, Object> pair : locationData.entrySet()){
            ModelUtils.setField(this, pair.getKey().replace("_", ""), pair.getValue());
        }
        this.childLocations = childLocations;
    }
    
}
