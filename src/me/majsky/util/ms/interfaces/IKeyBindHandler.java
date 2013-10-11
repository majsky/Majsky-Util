package me.majsky.util.ms.interfaces;

public interface IKeyBindHandler {
    public boolean exists(String key);
    public void bind(String key, String command);
}
