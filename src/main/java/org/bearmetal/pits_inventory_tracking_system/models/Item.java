package org.bearmetal.pits_inventory_tracking_system.models;

import java.util.HashMap;
import java.util.Map;

/** An item tracked by PITS.
 * <p>
 * Items are contained in Locations, see the <code>locationID</code> field for the associated Location.
 * <p>
 * @author Colin Rice
 */
public class Item extends BaseModel {
    private Integer id;
    private String name;
    private String description;
    private Integer quantity;
    private Integer available;
    private String vendor;
    private Integer partnumber;
    private String extraInfo;
    private Boolean packed;
    private Integer locationid;


    public Integer getID() {
        return this.id;
    }

    public void setID(Integer ID) {
        this.id = ID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getAvailable() {
        return this.available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public String getVendor() {
        return this.vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Integer getPartNumber() {
        return this.partnumber;
    }

    public void setPartNumber(Integer partNumber) {
        this.partnumber = partNumber;
    }

    public String getExtraInfo() {
        return this.extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public Boolean isPacked() {
        return this.packed;
    }

    public Boolean getPacked() {
        return this.packed;
    }

    public void setPacked(Boolean packed) {
        this.packed = packed;
    }

    public Integer getLocationID() {
        return this.locationid;
    }

    public void setLocationID(Integer locationID) {
        this.locationid = locationID;
    }

    /** Construct an Item from existing data.
     * 
     * @param itemData
     * A Items table result (HashMap<String, Object>) containing Item information.
     */
    public Item(HashMap<String, Object> itemData){
        for (Map.Entry<String, Object> pair : itemData.entrySet()){
            this.setField(pair.getKey().replace("_", ""), pair.getValue());
        }
    }

}
