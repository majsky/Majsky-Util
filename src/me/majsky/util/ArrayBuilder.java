package me.majsky.util;

import java.util.ArrayList;
import java.util.List;

public class ArrayBuilder<T>{
    private List<T> list;
    
    public ArrayBuilder() {
        list = new ArrayList<>();
    }
    
    public T[] toArray(T[] array){
        return list.toArray(array);
    }
    
    public void add(T t){
        list.add(t);
    }
    
    public void addAll(T[] array){
        for(T t:array)
            list.add(t);
    }
    
    public T removeLast(){
        return list.remove(list.size()-1);
    }
    
    public void clear(){
        list.clear();
    }
    
}
