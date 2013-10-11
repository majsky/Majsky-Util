package me.majsky.util.save;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

public class ClassSerializer {
    
    public static <T> T fromSaveTag(SaveTagCompound compound, Class<T> t) throws InstantiationException, IllegalAccessException{
        T object = t.newInstance();

        for(Field f:t.getDeclaredFields()){
            String name = f.getName();
            
            if(f.getAnnotation(SerializedName.class) != null)
                name = f.getAnnotation(SerializedName.class).value();
            
            boolean visible = f.isAccessible();
            if(!visible)
                f.setAccessible(true);
            
            if(compound.containsKey(name))
                f.set(object, SaveTagCompound.getData(compound.get(name)));
            
            if(!visible)
                f.setAccessible(false);
        }
        
        return object;
    }
    
    public static <T> SaveTagCompound toSaveTag(T object, Class<T> t) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, InstantiationException{
        SaveTagCompound compound = new SaveTagCompound();
        
        for(Field f:t.getDeclaredFields()){
            if(f.getAnnotation(Unserializable.class) != null)
                continue;
            
            String name = f.getName();
            
            if(f.getAnnotation(SerializedName.class) != null)
                name = f.getAnnotation(SerializedName.class).value();
            
            boolean visible = f.isAccessible();
            if(!visible)
                f.setAccessible(true);

            Object o = f.get(object);
            compound.put(name, o);
            
            if(!visible)
                f.setAccessible(false);
        }
        
        return compound;
    }
    
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface SerializedName{
        String value();
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Unserializable{}
}
