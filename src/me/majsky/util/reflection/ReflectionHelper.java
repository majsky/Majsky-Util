package me.majsky.util.reflection;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import me.majsky.util.ArrayUtils;
import me.majsky.util.TwoVariablesReturn;


public class ReflectionHelper {
    
    public static void invokeStaticMethod(Class<?> clazz, String methodName, Object... args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        Method method = clazz.getDeclaredMethod(methodName, args!=null?ArrayUtils.getClass(args):null);
        method.setAccessible(true);
        method.invoke(null, args!=null?args:null);
        method.setAccessible(false);
    }
    
    public static <T> void invokeMethod(Class<T> clazz, T object, String methodName, Object... args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        Method method = clazz.getDeclaredMethod(methodName, ArrayUtils.getClass(args));
        method.setAccessible(true);
        method.invoke(object, args);
        method.setAccessible(false);
    }
    
    public static <T> void setValue(Class<T> clazz, T object, String fieldName, Object value) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
        boolean visible = true;
        Field f = clazz.getDeclaredField(fieldName);
        visible = f.isAccessible();
        if(!visible)
            f.setAccessible(true);
        f.set(object, value);
        if(!visible)
            f.setAccessible(false);
    }
    
    public static Method findMethod(Class<?> clazz, String name){
        for(Method m:clazz.getDeclaredMethods())
            if(m.getName().equals(name))
                return m;
        return null;
    }
    
    public static Method findMethodByAnnotation(Class<?> clazz, Class<? extends Annotation> annotation){
        for(Method m:clazz.getDeclaredMethods())
            if(m.isAnnotationPresent(annotation))
                return m;
        return null;
    }
    
    public static Field findFieldByAnnotation(Class<?> clazz, Class<? extends Annotation> annotation){
        for(Field f:clazz.getDeclaredFields())
            if(f.isAnnotationPresent(annotation))
                return f;
        return null;
    }
    
    public static Method findMethod(Class<?> clazz, String method, Class<?>[] args) throws NoSuchMethodException, SecurityException{
        return clazz.getMethod(method, args);
    }
    
    public static <T extends Annotation> TwoVariablesReturn<Method, T> findMethodAndAnnotation(Class<?> clazz, Class<T> annotation){
        Method m = findMethodByAnnotation(clazz, annotation);
        if(m == null)
            return null;
        return new TwoVariablesReturn<Method, T>(m, m.getAnnotation(annotation));
    }
    
    public static StackTraceElement getCaller(){
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if(stackTrace.length < 4)
            return null;
        return stackTrace[3];
    }
    
    public static boolean isPrimitive(Object o){
        String[] primitives = {"String", "Integer", "Boolean", "Long", "Short", "Double", "Float", "Byte"};
        primitives = ArrayUtils.contact("java.lang.", primitives);
        boolean retval = ArrayUtils.contains(primitives, o.getClass().getName());
      //System.out.println(o.getClass() + (retval?" is":" isnt") + " primitive");
        return retval;
    }
    
    /**
     * This enables the java.library.path to be modified at runtime
     * From a Sun engineer at http://forums.sun.com/thread.jspa?threadID=707176
     */
    public static void addNativeDir(String s) throws IOException {
        try {
            Field field = ClassLoader.class.getDeclaredField("usr_paths");
            field.setAccessible(true);
            String[] paths = (String[])field.get(null);
            for (int i = 0; i < paths.length; i++) {
                if (s.equals(paths[i])) {
                    return;
                }
            }
            String[] tmp = new String[paths.length+1];
            System.arraycopy(paths,0,tmp,0,paths.length);
            tmp[paths.length] = s;
            field.set(null,tmp);
            System.setProperty("java.library.path", System.getProperty("java.library.path") + File.pathSeparator + s);
        } catch (IllegalAccessException e) {
            throw new IOException("Failed to get permissions to set library path");
        } catch (NoSuchFieldException e) {
            throw new IOException("Failed to get field handle to set library path");
        }
    }
}
