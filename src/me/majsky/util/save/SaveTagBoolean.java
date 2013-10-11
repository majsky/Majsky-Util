package me.majsky.util.save;

import me.majsky.util.save.TagBuilder.AutoRegister;

@AutoRegister(2)
public class SaveTagBoolean extends SaveTagBase {

    private boolean value;
    
    @Override
    public byte[] write() {
        return new byte[]{(byte)(value?1:0)};
    }

    @Override
    public void read(byte[] data) {
        value = data[0]==1;
    }

    public boolean getValue() {
        return value;
    }

    public SaveTagBoolean setValue(boolean value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "" + value;
    }
    
    

}
