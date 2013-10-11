package me.majsky.util;

import java.util.Map;
import java.util.Map.Entry;

public class MapUtils {
    public static <K, V> K findKeyByValue(Map<K, V> map, V value){
        for(Entry<K, V> e:map.entrySet())
            if(e.getValue().equals(value))
                return e.getKey();
        return null;
    }
}
