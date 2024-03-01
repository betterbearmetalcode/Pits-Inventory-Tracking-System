package org.bearmetal.pits_inventory_tracking_system.models;

import java.util.HashMap;
import java.util.Map;

/** A location where items are stored.
 * <p>
 * Items reference Locations in their <code>locationID</code> field.
 * <p>
 * A Location can also be a "category", a location containing multiple locations.
 * The state of a Location is reflected by the value of the "isCategory" field.
 * @author Colin Rice
 */
public class Location extends BaseModel{
    protected Integer locationid;
    protected String locationname;
    protected Integer iscategory;
    protected Integer parentcategory;

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

    /** Constructs a Location from existing data.
     * 
     * @param locationData
     * A Locations table result (HashMap<String, Object>) containing Location information.
     */
    public Location(HashMap<String, Object> locationData){
        for (Map.Entry<String, Object> pair : locationData.entrySet()){
            ModelUtils.setField(this, pair.getKey().replace("_", ""), pair.getValue());
        }
    }
}
