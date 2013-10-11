package me.majsky.util.save;

import java.nio.ByteBuffer;

import me.majsky.util.save.TagBuilder.AutoRegister;
@AutoRegister(1)
public class SaveTagInteger extends SaveTagBase {

    private int data;
    
    @Override
    public byte[] write() {
        if(("" + data).length() >= 4){
            ByteBuffer bb = ByteBuffer.allocate(4);
            bb.putInt(data);
            return bb.array();
        }
        
        return (""+data).getBytes();
    }

    @Override
    public void read(byte[] data) {
        if(data.length == 4){
            ByteBuffer bb = ByteBuffer.wrap(data);
            this.data = bb.getInt();
        }else{
            this.data = Integer.parseInt(new String(data));
        }
    }
    
    public SaveTagInteger setValue(int data){
        this.data = data;
        return this;
    }
    
    public int getValue(){
        return data;
    }

    @Override
    public String toString() {
        return "" + data;
    }

}
