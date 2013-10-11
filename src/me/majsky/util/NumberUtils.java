package me.majsky.util;

public class NumberUtils {

    public static boolean isPrime(int i){
        for(int j = 2;j<i;j++)
            if(i%j == 0)
                return false;
        return true;
    }
    
    public static boolean isDividible(int i, int divider){
        return i%divider == 0;
    }
}
