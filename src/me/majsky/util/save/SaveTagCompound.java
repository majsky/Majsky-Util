package me.majsky.util.save;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import me.majsky.util.reflection.ReflectionHelper;
import me.majsky.util.save.TagBuilder.AutoRegister;
import me.majsky.util.save.exception.CantParentItselfException;
import me.majsky.util.save.internal.SaveTagInputStream;

@AutoRegister(8)
public class SaveTagCompound extends SaveTagBase implements Map<String, SaveTagBase>{
    
    public static final int _VERSION = 2;
    
    private Map<String, SaveTagBase> map;
    private boolean debug;
    
    public SaveTagCompound(){
        this(false);
    }
    
    public SaveTagCompound(boolean debug){
        this.debug = debug;
        map = new HashMap<String, SaveTagBase>();
    }
    
    public void read(InputStream stream) throws IOException{
        SaveTagInputStream is;
        if(!(stream instanceof SaveTagInputStream))
            is = new SaveTagInputStream(stream);
        else
            is = (SaveTagInputStream)stream;

        if(is.streamVersion != _VERSION)
            throw new RuntimeException("Unknown SaveData version " + is.streamVersion + " (Current loaded: " + _VERSION + ")");
        
        try{
            throw new IOException();
        }catch(IOException e){
            
        }
        /*
        while((in = stream.read()) != -1){
            int nameLegth = stream.read();
            byte[] namebuff = new byte[nameLegth];
            stream.read(namebuff, 0, nameLegth);
            byte[] lengthBuff = new byte[4];
            stream.read(lengthBuff, 0, 4);
            ByteBuffer bb = ByteBuffer.wrap(lengthBuff);
            int tagLength = bb.getInt();
            byte[] buff = new byte[tagLength];
            stream.read(buff, 0, tagLength);
            
            String name = new String(namebuff);
            put(name, TagBuilder.build(in, buff));
        }
        */
        stream.close();
    }
    
    public void write(OutputStream stream) throws IOException{
        stream.write(_VERSION);
        stream.flush();
        
        for(Entry<String, SaveTagBase> e:entrySet()){
            int tagID = TagBuilder.getTagID(e.getValue().getClass());
            byte[] nameData = e.getKey().getBytes();
            byte[] data = e.getValue().write();
            ByteBuffer bb = ByteBuffer.allocate(4);
            bb.putInt(data.length);
            
            if(debug)
                System.out.printf("Writing %s, type %s, id %s, data lenght %s, data %s\n", e.getKey(), e.getValue().getClass().getSimpleName(), tagID, data.length, Arrays.toString(data));
            
            stream.write(tagID);
            stream.write(nameData.length);
            stream.write(nameData);
            stream.write(bb.array());
            stream.write(data);
            stream.flush();
        }
        
        stream.flush();
        stream.close();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public Set<java.util.Map.Entry<String, SaveTagBase>> entrySet() {
        return map.entrySet();
    }

    @Override
    public SaveTagBase get(Object key) {
        return map.get(key);
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public SaveTagBase put(String key, SaveTagBase value) {
        if(value.equals(this))
            throw new CantParentItselfException();
        return map.put(key, value);
    }
    
    public SaveTagBase put(Object key, SaveTagBase value){
        return map.put(key.toString(), value);
    }
    
    @SuppressWarnings("unchecked")
    public SaveTagBase put(Object key, Object value) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        if(!ReflectionHelper.isPrimitive(value))
            return null;
        Class<? extends SaveTagBase> clazz = (Class<? extends SaveTagBase>)Class.forName("me.majsky.util.save.SaveTag" + value.getClass().getSimpleName());
        Field field = clazz.getDeclaredFields()[0];
        field.setAccessible(true);
        SaveTagBase tag = clazz.newInstance();
        field.set(tag, value);
        return map.put(key.toString(), tag);
    }

    @Override
    public void putAll(Map<? extends String, ? extends SaveTagBase> m) {
        if(m.containsValue(this))
            throw new CantParentItselfException();
        map.putAll(m);
    }

    @Override
    public SaveTagBase remove(Object key) {
        return map.remove(key);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public Collection<SaveTagBase> values() {
        return map.values();
    }

    @Override
    public byte[] write() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            this.write(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream.toByteArray();
    }

    @Override
    public void read(byte[] data) {
        ByteArrayInputStream stream = new ByteArrayInputStream(data);
        try {
            this.read(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static Object getData(SaveTagBase tag) throws IllegalArgumentException, IllegalAccessException{
        Class<? extends SaveTagBase> clazz = tag.getClass();
        Field field = clazz.getDeclaredFields()[0];
        field.setAccessible(true);
        Object data = field.get(tag);
        field.setAccessible(false);
        return data;
    }
    
    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        
        b.append("[");
        
        Iterator<Entry<String, SaveTagBase>> setIterator = entrySet().iterator();
        while(setIterator.hasNext()){
            Entry<String, SaveTagBase> e = setIterator.next();
            b.append("(");
            b.append(e.getValue().getClass().getSimpleName());
            b.append(")");
            b.append(e.getKey());
            b.append(": ");
            b.append(e.getValue().toString());
            if(setIterator.hasNext())
                b.append(", ");
        }
        
        b.append("]");
        
        return b.toString();
    }
}