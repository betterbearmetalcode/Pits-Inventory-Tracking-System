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
    protected Integer itemid;
    protected String itemname;
    protected String itemdescription;
    protected Integer itemquantity;
    protected String itemsuffix;
    protected Integer itemavailable;
    protected String itemvendor;
    protected String itempartnumber;
    protected String iteminfo;
    protected Integer packed;
    protected Integer locationid;
    protected String photopath;

    public String getPhotoPath(){
        return this.photopath;
    }

    public void setPhotoPath(String photoPath){
        this.photopath = photoPath;
    }

    public Integer getID() {
        return this.itemid;
    }

    public void setID(Integer ID) {
        this.itemid = ID;
    }

    public String getName() {
        return this.itemname;
    }

    public void setName(String name) {
        this.itemname = name;
    }

    public String getDescription() {
        return this.itemdescription;
    }

    public void setDescription(String description) {
        this.itemdescription = description;
    }

    public Integer getQuantity() {
        return this.itemquantity;
    }

    public void setQuantity(Integer quantity) {
        this.itemquantity = quantity;
    }

    public String getSuffix(){
        return this.itemsuffix;
    }

    public void setSuffix(String itemSuffix){
        this.itemsuffix = itemSuffix;
    }

    public Integer getAvailable() {
        return this.itemavailable;
    }

    public void setAvailable(Integer available) {
        this.itemavailable = available;
    }

    public String getVendor() {
        return this.itemvendor;
    }

    public void setVendor(String vendor) {
        this.itemvendor = vendor;
    }

    public String getPartNumber() {
        return this.itempartnumber;
    }

    public void setPartNumber(String partNumber) {
        this.itempartnumber = partNumber;
    }

    public String getExtraInfo() {
        return this.iteminfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.iteminfo = extraInfo;
    }

    public Boolean isPacked() {
        return getPacked();
    }

    public Boolean getPacked() {
        return this.packed == 1;
    }

    public void setPacked(Boolean packed) {
        if (packed){
            this.packed = 1;
        } else {
            this.packed = 0; 
        }
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
            ModelUtils.setField(this, pair.getKey().replace("_", ""), pair.getValue());
        }
    }

}
