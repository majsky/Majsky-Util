package me.majsky.util.save.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class SaveTagOutputStream extends OutputStream{

    private OutputStream os;
    private boolean count;
    private int written;
    private SaveTagHeader lastHeader;
    
    public SaveTagOutputStream(OutputStream out){
        os = out;
    }
    
    public void writeHeader(SaveTagHeader header) throws IOException{
        if(lastHeader != null)
            if(written != lastHeader.tagLength)
                throw new IOException("Error while writing body of " + lastHeader.tagName + " (excepted lenght: " + lastHeader.tagLength +" gotten: "+ written +"+)");
        count = false;
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putInt(header.tagLength);
        
        write(header.tagType);
        write(header.tagName.length());
        write(header.tagName.getBytes());
        write(bb.array());
        
        written = 0;
        lastHeader = header;
        count = true;
    }
    
    @Override
    public void close() throws IOException {
        os.close();
    }

    @Override
    public void flush() throws IOException {
        os.flush();
    }

    @Override
    public void write(int b) throws IOException {
        os.write(b);
        if(count)
            written++;
    }

}
