package org.bearmetal.pits_inventory_tracking_system.models;

import java.lang.reflect.Field;

public class ModelUtils {
    public static void setField(BaseModel cls, String fieldName, Object value) {
        Field field;
        try {
            //ew
            System.out.println(cls.getClass().getName());
            field = cls.getClass().getDeclaredField(fieldName);
            field.set(cls, value);
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}
