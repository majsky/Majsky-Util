package me.majsky.util.save;

import java.nio.ByteBuffer;

import me.majsky.util.save.TagBuilder.AutoRegister;

@AutoRegister(9)
public class SaveTagShort extends SaveTagBase {

    private short value;
    
    @Override
    public byte[] write() {
        ByteBuffer bb = ByteBuffer.allocate(2);
        bb.putShort(value);
        return bb.array();
    }

    @Override
    public void read(byte[] data) {
        ByteBuffer bb = ByteBuffer.wrap(data);
        value = bb.getShort();
    }

    public short getValue() {
        return value;
    }

    public SaveTagShort setValue(short value) {
        this.value = value;
        return this;
    }
}
