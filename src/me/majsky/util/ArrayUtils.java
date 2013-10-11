package me.majsky.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ArrayUtils {
    
    public static <T> boolean contains(T[] array, T object){
        for(int i=0;i<array.length;i++)
            if(array[i].equals(object))
                return true;
        return false;
    }
    
    public static <T> int getIndex(T[] array, T find){
        for(int i=0;i<array.length;i++)
            if(array[i].equals(find))
                return i;
        return -1;
    }
    
    public static String[] subString(String[] strings, int index){
        String[] retVal = new String[strings.length];
        for(int i=0;i<strings.length;i++)
            retVal[i] = strings[i].substring(index);
        return retVal;
    }
    
    public static String[] contact(String[] first, String[] second){
        if(first.length != second.length)
            throw new IllegalArgumentException("Array lenghts differ");
        for(int i=0;i<first.length;i++)
            first[i] = first[i].concat(second[i]);
        return first;
    }
    
    public static String[] contact(String[] first, String second){
        for(int i=0;i<first.length;i++)
            first[i] = first[i].concat(second);
        return first;
    }
    
    public static String[] contact(String first, String[] second){
        for(int i=0;i<second.length;i++)
            second[i] = first.concat(second[i]);
        return second;
    }
    
    public static String[] replaceAll(String[] strings, String regex, String replacement){
        String[] retVal = new String[strings.length];
        for(int i=0;i<strings.length;i++)
            retVal[i] = strings[i].replaceAll(regex, replacement);
        return retVal;
    }
    
    public static String[] getAbsolutePaths(File[] files){
        String[] retVal = new String[files.length];
        for(int i=0;i<files.length;i++)
            retVal[i] = files[i].getAbsolutePath();
        return retVal;
    }

    public static <T> T[] cast(Object[] array, Class<T> type, T[] toPopulate){
        for(int i=0;i<toPopulate.length;i++)
            toPopulate[i] = type.cast(array[i]);
        return toPopulate;
    }
    
    public static <T> void print(T[] array){
        for(T t:array)
            System.out.println(t);
    }
    
    public static Class<?>[] getClass(Object[] array){
        Class<?>[] retVal = new Class<?>[array.length];
        for(int i=0;i<array.length;i++)
            retVal[i] = array[i].getClass();
        return retVal;
    }
    
    public static <T> int countNulls(T[] array){
        if(array == null)
            return 1;
        int returnVal = 0;
        for(T t:array)
            if(t == null)
                returnVal++;
        return returnVal;
    }
    
    public static int[] min(int[] array, int cap){
        int[] retVal = new int[array.length];
        for(int i=0;i<array.length;i++)
            retVal[i] = Math.min(array[i], cap);
        return retVal;
    }
    
    
    public static Integer[] parse(String[] array){
        Integer[] toReturn = new Integer[array.length];
        for(int i=0;i<array.length;i++)
            toReturn[i] = Integer.parseInt(array[i]);
        return toReturn;
    }
    
    public static <T> String[] toString(T[] t){
        String[] buffer = new String[t.length];
        
        for(int i=0;i<t.length;i++)
            buffer[i] = t[i].toString();
        
        return buffer;
    }
    
    public static <T> List<T> convert(T[] t){
        List<T> returnVal = new ArrayList<>();
        for(T a:t)
            returnVal.add(a);
        return returnVal;
    }
    
    public static <T> T[] addToArray(T[] array, T element){
        List<T> returnVal = convert(array);
        returnVal.add(element);
        return returnVal.toArray(array);
    }
    
    public static String joinStrings(String[] strings, String joinRegex){
        StringBuilder result = new StringBuilder();
        for(String s:strings){
            result.append(s);
            result.append(joinRegex);
        }
        return result.toString();
    }
    
    public static <T> T[] extract(T[] array, int start, int end){
        List<T> list = new ArrayList<>();
        for(int i=start;i<end;i++)
            list.add(array[i]);
        return list.toArray(array);
    }

    public static boolean contains(int[] array, int object) {
        Integer[] ints = new Integer[array.length];
        for(int i=0;i<array.length;i++)
            ints[i] = Integer.valueOf(array[i]);
        return contains(ints, Integer.valueOf(object));
    }
}
