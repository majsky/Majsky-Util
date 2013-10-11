package me.majsky.util.test;

import me.majsky.util.ArgsPraser;

public class ArgsPraserTest {
    
    public static void main(String[] args){        
        ArgsPraser praser = new ArgsPraser(args);
        
        for(String s:praser.nameSet()){
            System.out.println(s);
            System.out.print("\t");
            System.out.println(praser.getArgumentData(s));
        }
    }
}
