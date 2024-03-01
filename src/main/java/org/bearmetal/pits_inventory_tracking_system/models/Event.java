package org.bearmetal.pits_inventory_tracking_system.models;

import java.util.HashMap;
import java.util.Map;

import org.bearmetal.pits_inventory_tracking_system.models.ModelConstants.itemState;

public class Event extends BaseModel{
    protected Integer itemid;
    protected Integer eventid;
    protected Integer eventtype;
    protected Integer eventbulk;
    protected Integer eventtimestamp;
    protected String eventdesc;
    protected Integer itemdelta;
    protected Integer itemstate;

    public Integer getItemID() {
        return this.itemid;
    }

    public void setItemID(Integer itemID) {
        this.itemid = itemID;
    }

    public Integer getEventID() {
        return this.eventid;
    }

    public void setEventID(Integer eventID) {
        this.eventid = eventID;
    }

    public Integer getEventType() {
        return this.eventtype;
    }

    public void setEventType(Integer eventType) {
        this.eventtype = eventType;
    }

    public Boolean isEventBulk() {
        return getEventBulk();
    }

    public Boolean getEventBulk() {
        return this.eventbulk == 1;
    }

    public void setEventBulk(Boolean eventBulk) {
        if (eventBulk){
            this.eventbulk = 1;
        } else {
            this.eventbulk = 0;
        }
    }

    public Integer getEventTimestamp() {
        return this.eventtimestamp;
    }

    public void setEventTimestamp(Integer eventTimestamp) {
        this.eventtimestamp = eventTimestamp;
    }

    public String getEventDesc() {
        return this.eventdesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventdesc = eventDesc;
    }

    public Integer getItemDelta() {
        return this.itemdelta;
    }

    public void setItemDelta(Integer itemDelta) {
        this.itemdelta = itemDelta;
    }

    public itemState getItemState() {
        switch (this.itemstate){
            case 0:
                return ModelConstants.itemState.CHECKED_IN;
            case 1:
                return ModelConstants.itemState.LOANED;
            case 2:
                return ModelConstants.itemState.ON_ROBOT;
            case 3:
                return ModelConstants.itemState.DISCARDED;
            default:
                return ModelConstants.itemState.OTHER;
        }
    }

    public void setItemState(Integer itemState) {
        this.itemstate = itemState;
    }

    /** Constructs an Event from existing data.
     * 
     * @param eventData
     * A Events table result (HashMap<String, Object>) containing Location information.
     */
    public Event(HashMap<String, Object> eventData){
        for (Map.Entry<String, Object> pair : eventData.entrySet()){
            ModelUtils.setField(this, pair.getKey().replace("_", ""), pair.getValue());
        }
    }

}
