package me.majsky.util.save;

import me.majsky.util.save.TagBuilder.AutoRegister;

@AutoRegister(0)
public class SaveTagString extends SaveTagBase {

    private String data;
    
    @Override
    public byte[] write() {
        return data.getBytes();
    }

    @Override
    public void read(byte[] data) {
        this.data = new String(data);
    }
    
    public String getString(){
        return data;
    }
    
    public SaveTagString setString(String string){
        data = string;
        return this;
    }

    @Override
    public String toString() {
        return data;
    }

}
