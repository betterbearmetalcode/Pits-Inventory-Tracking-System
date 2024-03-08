package org.bearmetal.pits_inventory_tracking_system.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/** A location where items are stored.
 * <p>
 * Items reference Locations in their <code>locationID</code> field.
 * <p>
 * @author Colin Rice
 */
public class Location extends BaseModel{
    protected Integer locationid;
    protected String locationname;
    protected Integer iscategory;
    protected Integer parentcategory;
    protected ArrayList<Item> childItems = new ArrayList<Item>();

    public ArrayList<Item> getChildItems() {
        return this.childItems;
    }

    public void setChildItems(ArrayList<Item> children) {
        this.childItems = children;
    }

    public Integer getLocationID() {
        return this.locationid;
    }

    public void setLocationID(Integer locationid) {
        this.locationid = locationid;
    }

    public String getLocationName() {
        return this.locationname;
    }

    public void setLocationName(String locationname) {
        this.locationname = locationname;
    }

    public Boolean getIsCategory() {
        return this.iscategory == 1;
    }

    public void setIsCategory(Boolean iscategory) {
        if (iscategory){
            this.iscategory = 1;
        } else {
            this.iscategory = 0;
        }
    }

    public Integer getParentCategory() {
        return this.parentcategory;
    }

    public void setParentCategory(Integer parentcategory) {
        this.parentcategory = parentcategory;
    }

    private void setupFields(HashMap<String, Object> locationData){
        for (Map.Entry<String, Object> pair : locationData.entrySet()){
            ModelUtils.setField(this, pair.getKey().replace("_", ""), pair.getValue());
        }
    }

    /** Constructs a Location from existing data.
     * 
     * @param locationData
     * A Locations table result (HashMap<String, Object>) containing Location information.
     */
    public Location(HashMap<String, Object> locationData){
        setupFields(locationData);
    }

    public Location(HashMap<String, Object> locationData, ArrayList<Item> childItems){
        this.childItems = childItems;
        setupFields(locationData);
    }

    public Location(){
        
    }
}
