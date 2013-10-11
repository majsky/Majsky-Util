package me.majsky.util;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

import me.majsky.util.reflection.MethodHelper;


public class ConsoleOutputStream extends OutputStream{

    private Appendable a;
    private OutputStream os;
    
    public ConsoleOutputStream(Appendable console, OutputStream original){
        a = console;
        os = original;
    }
    
    @Override
    public void write(int arg0) throws IOException {
        os.write(arg0);
        a.append(new String(new byte[]{(byte)arg0}));
    }
    
    
    public static class Appendable{

        private MethodHelper m;
        private Object[] values;
        private Object o;
        private int argNum;
        
        public <T> Appendable(T object, Class<T> clazz, String method, Class<?>[] args, Object[] values, int argNum){
            try {
                m = new MethodHelper(clazz.getDeclaredMethod(method, args));
            } catch (NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
            this.values = values;
            this.argNum = argNum;
            o = object;
        }
        
        
        public void append(String s){
            values[argNum] = s;
            try {
                m.call(o, values);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
