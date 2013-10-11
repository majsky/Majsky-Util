package me.majsky.util;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class CloseableHelper {

    private Map<String, Closeable> toClose;
    private final boolean debug;
    
    public CloseableHelper(){
        this(false);
    }
    
    public CloseableHelper(boolean debug){
        this.debug = debug;
        toClose = new HashMap<String, Closeable>();
    }
    
    public void open(Closeable closeable){
        open(closeable, closeable.toString());
    }
    
    public void open(Closeable closeable, String key){
        if(debug)
            System.out.println("[CloseableHelper] Registering " + key);
        toClose.put(key, closeable);
    }
    
    public void close(Closeable closeable) throws IOException{
        close(closeable.toString());
    }
    
    public void close(String key) throws IOException{
        if(debug)
            System.out.println("[CloseableHelper] Closing " + key);
        if(toClose.containsKey(key)){
            toClose.get(key).close();
            toClose.remove(key);
        }
    }
    
    public Iterator<Entry<String, Closeable>> iterator(){
        return toClose.entrySet().iterator();
    }
}
