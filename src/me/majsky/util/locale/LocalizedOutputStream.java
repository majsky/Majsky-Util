package me.majsky.util.locale;

import java.io.OutputStream;
import java.io.PrintStream;

public class LocalizedOutputStream extends PrintStream{

    OutputStream os;
    
    public LocalizedOutputStream(OutputStream arg0) {
        super(arg0);
        os = arg0;
    }

    
}
