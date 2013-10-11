package me.majsky.util.ms;

import me.majsky.util.ms.interfaces.IArgsDecoder;

public class StringArgsDecoder implements IArgsDecoder{

    @Override
    public Object decode(String s) {
        return s.split(";");
    }
    
    private static StringArgsDecoder instance;
    
    public static StringArgsDecoder instance(){
        if(instance == null)
            new StringArgsDecoder();
        return instance;
    }
    
    public StringArgsDecoder(){
        instance = this;
    }
}
