package me.majsky.util.save;

import java.nio.ByteBuffer;

import me.majsky.util.save.TagBuilder.AutoRegister;
@AutoRegister(3)
public class SaveTagFloat extends SaveTagBase{
    private float value;

    @Override
    public byte[] write() {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putFloat(value);
        return bb.array();
    }

    @Override
    public void read(byte[] data) {
        ByteBuffer bb = ByteBuffer.wrap(data);
        value = bb.getFloat();
    }

    public float getValue() {
        return value;
    }

    public SaveTagFloat setValue(float value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "" + value;
    }

}
