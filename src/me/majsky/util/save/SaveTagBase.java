package me.majsky.util.save;

public abstract class SaveTagBase {
    public abstract byte[] write();
    public abstract void read(byte[] data);
}
