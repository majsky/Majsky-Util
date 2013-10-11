package me.majsky.util.debug;

import java.io.IOException;
import java.io.OutputStream;

public class DebugOutputStream extends OutputStream{

    private OutputStream stream;
    
    public DebugOutputStream(OutputStream wrapped){
        stream = wrapped;
    }
    
    @Override
    public void write(int i) throws IOException {
        System.out.println("Writing " + i);
        if(stream != null)
            stream.write(i);
    }

    @Override
    public void close() throws IOException {
        System.out.println("Closing stream");
        if(stream != null)
            stream.close();
    }

    @Override
    public void flush() throws IOException {
        System.out.println("Flushing stream");
        if(stream != null)
               stream.flush();
    }

}
