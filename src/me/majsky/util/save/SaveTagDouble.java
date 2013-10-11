package me.majsky.util.save;

import java.nio.ByteBuffer;

import me.majsky.util.save.TagBuilder.AutoRegister;
@AutoRegister(4)
public class SaveTagDouble extends SaveTagBase {

    private double value;
    
    @Override
    public byte[] write() {
        ByteBuffer bb = ByteBuffer.allocate(8);
        bb.putDouble(value);
        return bb.array();
    }

    @Override
    public void read(byte[] data) {
        ByteBuffer bb = ByteBuffer.wrap(data);
        value = bb.getDouble();
    }

    public double getValue() {
        return value;
    }

    public SaveTagDouble setValue(double value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "" + value;
    }
    
    

}
