package me.majsky.util.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MethodHelper {
    
    public final Method method;
    public final boolean isVisible;
    
    public MethodHelper(Method m){
        method = m;
        isVisible = m.isAccessible();
        if(!isVisible)
            method.setAccessible(true);
    }
    
    public void invoke(Object o, Object...args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        call(o, args);
    }
    
    public void call(Object o, Object...args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        if(!isStatic() && o == null)
            throw new IllegalAccessException("Method " + method.getName() + " cant be acessed in static way");
        method.invoke(o, args);
    }
    
    public boolean isStatic(){
        return Modifier.isStatic(method.getModifiers());
    }
    
    public void endUse(){
        if(!isVisible)
            method.setAccessible(false);
    }
}
