package me.majsky.util.save;

import java.util.Arrays;

import me.majsky.util.save.TagBuilder.AutoRegister;
@AutoRegister(6)
public class SaveTagByteArray extends SaveTagBase{

    private byte[] data;
    
    @Override
    public byte[] write() {
        return data;
    }

    @Override
    public void read(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    public SaveTagByteArray setData(byte[] data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return Arrays.toString(data);
    }
    
    
}
