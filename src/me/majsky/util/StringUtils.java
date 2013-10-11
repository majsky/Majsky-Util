package me.majsky.util;

public class StringUtils {
    public static boolean isNumber(String s){
        try{
            Double.valueOf(s);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
    
    public static boolean isInteger(String s){
        try{
            Integer.parseInt(s);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
}
