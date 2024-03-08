package org.bearmetal.pits_inventory_tracking_system.models;

import java.lang.reflect.Field;

public class ModelUtils {
    public static void setField(BaseModel target, String fieldName, Object value) {
        Field field;
        try {
            //Get the underlying class of the target.
            Class<?> cls = target.getClass();
            //We need to set fields on the superclass if it exists.
            //Not doing so may give us a NoSuchFieldException.
            //Get the superclass if applicable. I HATE THIS HELP
            Class<?> superClass = cls.getSuperclass();
            if (superClass != null && !(superClass.getTypeName().contains("BaseModel"))){
                System.out.println("using superClass " + superClass.getTypeName() );
                cls = superClass;
            }
            field = cls.getDeclaredField(fieldName);
            field.set(target, value);
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}
