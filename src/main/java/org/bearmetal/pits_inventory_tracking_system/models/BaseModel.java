package org.bearmetal.pits_inventory_tracking_system.models;

import java.lang.reflect.Field;

public class BaseModel {

    public void setField(String fieldName, Object value) {
        Field field;
        try {
            field = getClass().getDeclaredField(fieldName);
            field.set(this, value);
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

}
