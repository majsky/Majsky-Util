package me.majsky.util;

public class AssertionHelper{
    
    public static void ASSERT(boolean bool){
        ASSERT(bool, bool);
    }
    
    public static void ASSERT(boolean bool, Object msg){
        if(!bool)
            throw new RuntimeException("Assertin failed: " + msg.toString());
    }
}
