package me.majsky.util.save.internal;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;


public class SaveTagHeader {
    public final byte tagType;
    public final String tagName;
    public final int tagLength;
    public boolean corrupted;
    
    protected SaveTagHeader(InputStream is) throws IOException{
        corrupted = false;
        tagType = (byte)is.read();
        if(tagType == -1)
            corrupted = true;
        int nameLength = is.read();
        if(nameLength == -1)
            corrupted = true;
        byte[] nameBuff = new byte[nameLength];
        int read = is.read(nameBuff, 0, nameLength);
        if(read == -1)
            corrupted = true;

        byte[] lengthBuff = new byte[4];
        is.read(lengthBuff, 0, 4);
        
        ByteBuffer bb = ByteBuffer.wrap(lengthBuff);
        tagName = new String(nameBuff);
        tagLength = bb.getInt();
    }
    
    public SaveTagHeader(int type, String name, int length){
        tagType = (byte)type;
        tagName = name;
        tagLength = length;
        corrupted = false;
    }
}
