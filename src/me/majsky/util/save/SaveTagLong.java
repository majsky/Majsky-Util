package me.majsky.util.save;

import java.nio.ByteBuffer;

import me.majsky.util.save.TagBuilder.AutoRegister;
@AutoRegister(5)
public class SaveTagLong extends SaveTagBase {

    private long value;
    
    @Override
    public byte[] write() {
        ByteBuffer bb = ByteBuffer.allocate(8);
        bb.putLong(value);
        return bb.array();
    }

    @Override
    public void read(byte[] data) {
        ByteBuffer bb = ByteBuffer.wrap(data);
        value = bb.getLong();
    }

    public long getValue() {
        return value;
    }

    public SaveTagLong setValue(long value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "" + value;
    }
    
}
