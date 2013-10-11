package me.majsky.util.save.internal;

import java.io.IOException;
import java.io.InputStream;

public class SaveTagInputStream extends InputStream{
    
    public final int streamVersion;
    
    private InputStream is;
    private SaveTagHeader lastHeader;
    
    public SaveTagInputStream(InputStream in) throws IOException{
        is = in;
        streamVersion = in.read();
    }
    
    public SaveTagHeader readHeader() throws IOException{
        lastHeader = new SaveTagHeader(this);
        return lastHeader;
    }
    
    public boolean haveData() throws IOException{
        return available() > 0;
    }
    
    public void nextHeader() throws IOException{
        long skip = this.skip(lastHeader.tagLength);
        if(skip != lastHeader.tagLength)
            throw new IOException("Cant skip " + lastHeader.tagLength + " bytes");
    }
    
    @Override
    public int available() throws IOException {
        return is.available();
    }

    @Override
    public void close() throws IOException {
        is.close();
    }

    @Override
    public synchronized void mark(int readlimit) {
        is.mark(readlimit);
    }

    @Override
    public boolean markSupported() {
        return is.markSupported();
    }

    @Override
    public int read() throws IOException {
        return is.read();
    }

    @Override
    public synchronized void reset() throws IOException {
        is.reset();
    }

    @Override
    public long skip(long n) throws IOException {
        return is.skip(n);
    }

}
