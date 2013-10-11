package me.majsky.util.save;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import me.majsky.util.ClassLister;
import me.majsky.util.save.exception.TagAlreadyExistsException;
import me.majsky.util.save.exception.TagNotExistsException;

@SuppressWarnings({ "unchecked", "deprecation" })
public class TagBuilder {
    
    private static Map<Integer,Class<? extends SaveTagBase>> tagTypes = new HashMap<>();
    private static int lastIndex = -1;
    
    
    public static void registerTag(int id, Class<? extends SaveTagBase> clazz){
        if(tagTypes.containsKey(id))
            throw new TagAlreadyExistsException(tagTypes.get(id), clazz, id);
        lastIndex = id;
        tagTypes.put(id, clazz);
    }
    
    public static int getTagID(Class<? extends SaveTagBase> clazz){
        if(!tagTypes.containsValue(clazz))
            return -1;
        
        for(Entry<Integer, Class<? extends SaveTagBase>> e:tagTypes.entrySet())
            if(e.getValue().equals(clazz))
                return (int)e.getKey();
        
        return -1;
    }
    
    public static Class<? extends SaveTagBase> getTagByID(int id){
        return tagTypes.get(id);
    }
    
    public static int getNextTagID(){
        return lastIndex+1;
    }
    
    public static SaveTagBase build(int id, byte[] data){
        Class<? extends SaveTagBase> clazz = tagTypes.get(id);
        if(clazz == null)
            throw new TagNotExistsException(id);
        SaveTagBase object = null;
        
        try{
            object = clazz.newInstance();
            object.read(data);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return object;
    }
    
    public static boolean autoRegister(Package pkg){
        try{
            List<Class<?>> list = ClassLister.getClassesForPackage(pkg);
            if(list == null)
                return false;
            for(Class<?> c:list){
                AutoRegister anno = c.getAnnotation(AutoRegister.class);
                if(anno == null)
                    continue;
                
                registerTag(anno.value(), (Class<? extends SaveTagBase>)c);
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    
    static{
        if(!autoRegister(TagBuilder.class.getPackage())){
            registerTag(0, SaveTagString.class);
            registerTag(1, SaveTagInteger.class);
            registerTag(2, SaveTagBoolean.class);
            registerTag(3, SaveTagFloat.class);
            registerTag(4, SaveTagDouble.class);
            registerTag(5, SaveTagLong.class);
            registerTag(6, SaveTagByteArray.class);
            registerTag(7, SaveTagList.class);
            registerTag(8, SaveTagCompound.class);
            registerTag(9, SaveTagShort.class);
        }
    }
    
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface AutoRegister{
        int value();
    }
}
