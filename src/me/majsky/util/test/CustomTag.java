package me.majsky.util.test;

import me.majsky.util.save.SaveTagBase;
import me.majsky.util.save.TagBuilder.AutoRegister;
@AutoRegister(10)
public class CustomTag extends SaveTagBase{

    private byte _byte;
    
    @Override
    public byte[] write() {
        return new byte[]{_byte};
    }

    @Override
    public void read(byte[] data) {
        _byte = data[0];
    }
    
    
    public CustomTag setByte(byte b){
        _byte = b;
        return this;
    }
    
    public byte getByte(){
        return _byte;
    }

    @Override
    public String toString() {
        return "" + _byte;
    }
}
