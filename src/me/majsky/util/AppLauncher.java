package me.majsky.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import me.majsky.util.FileUtils.IFileFilter;
import me.majsky.util.reflection.MethodHelper;
import me.majsky.util.reflection.ReflectionHelper;

public class AppLauncher {
    
    public static void launch(Class<?> main, String[] args, ClassLoader cl) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        launch(main.getName(), args, cl);
    }
    
    public static void launch(String main, String[] args, ClassLoader cl) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        Class<?> clazz = Class.forName(main, true, cl);
        
        boolean useMain = true;
        
        TwoVariablesReturn<Method, AppLauncherEntry> method = ReflectionHelper.findMethodAndAnnotation(clazz, AppLauncherEntry.class);
        if(method != null){
            useMain = false;
            MethodHelper m = new MethodHelper(method.t);
            if(method.t1.value())
                m.call(null, (Object)args);
            else
                m.call(null);
            m.endUse();
        }
        
        if(useMain){
            Method m = clazz.getMethod("main", String[].class);
            m.invoke(null, (Object)args);
        }
    }
    
    public static URL getContainer() throws MalformedURLException{
        return getContainer(AppLauncher.class);
    }
    
    public static URL getContainer(Class<?> clazz) throws MalformedURLException{
        return FileUtils.toFile(clazz.getProtectionDomain().getCodeSource().getLocation()).toURI().toURL();
    }
    
    public static URL toURL(String abstractPath) throws MalformedURLException{
        URL file = new File(abstractPath).toURI().toURL();
        return file;
    }
    
    public static URL[] toURL(String... paths) throws MalformedURLException{
        List<URL> list = new ArrayList<>();
        for(String s:paths){
            if(s.equals("_SELF")){
                Class<?> clazz = ReflectionHelper.getCaller().getClass();
                list.add(getContainer(clazz));
            }else if(s.equals("_APPLAUNCHER")){
                list.add(getContainer());
            }else{
                list.add(toURL(s));
            }
        }
        return list.toArray(new URL[list.size()]);
    }
    
    public static URL[] fromFolder(String path) throws MalformedURLException{
        List<URL> list = new ArrayList<>();
        IFileFilter jarFilter = new IFileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().toLowerCase().endsWith(".jar");
            }
        };
        File[] files = FileUtils.listFolder(path, jarFilter);
        for(File f:files)
            list.add(toURL(f.getAbsolutePath()));
        return list.toArray(new URL[list.size()]);
    }
    
    public static void injectNative(String nativePath) throws IOException{
        ReflectionHelper.addNativeDir(new File(nativePath).getAbsolutePath());
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface AppLauncherEntry{
        boolean value() default false;
    }
    
    public static void printClassPath(URL[] libs) {
        printClassPath(System.out, libs);
    }
    
    public static void printClassPath(PrintStream w, URL[] libs) {
        w.print("Native path: ");
        w.println(System.getProperty("java.library.path"));
        w.print("Classpath: ");
        for(URL u:libs)
            w.print(u.toString() + ", ");
        w.println();
    }
}
