package me.majsky.util;

import java.io.IOException;
import java.io.OutputStream;

public class StringOutputStream extends OutputStream{
    
    private StringBuilder sb = new StringBuilder();
    
    @Override
    public void write(int arg0) throws IOException {
        sb.append((char)arg0);
    }

    @Override
    public String toString() {
        return sb.toString();
    }
    
}
