package me.majsky.util.debug;

import me.majsky.util.ArrayUtils;

public abstract class DebuggableAplication {
    
    public boolean isDebug;
    
    public abstract void startNormal(String[] args);
    public abstract void startDebug(String[] args);
    
    public final void start(String[] args){
        preStart(args);
        isDebug = ArrayUtils.contains(args, "-debug");
        if(isDebug){
            System.out.println("Debug mode activated");
            startDebug(args);
        }else{
            startNormal(args);
        }
        postStart(args);
    }
    
    public void preStart(String[] args){}
    public void postStart(String[] args){}
}
