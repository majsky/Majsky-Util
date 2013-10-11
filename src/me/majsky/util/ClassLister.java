package me.majsky.util;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
@Deprecated
public class ClassLister {

    private static Class<?> loadClass(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }

    private static void processDirectory(File directory, String pkgname, ArrayList<Class<?>> classes) throws Exception{
        String[] files = directory.list();
        for (int i = 0; i < files.length; i++) {
            String fileName = files[i];
            String className = null;
            if (fileName.endsWith(".class")) {
                className = pkgname + '.' + fileName.substring(0, fileName.length() - 6);
            }
            if (className != null) {
                classes.add(loadClass(className));
            }
            File subdir = new File(directory, fileName);
            if (subdir.isDirectory()) {
                processDirectory(subdir, pkgname + '.' + fileName, classes);
            }
        }
    }

    private static void processJarfile(URL resource, String pkgname, ArrayList<Class<?>> classes) throws Exception{
        String relPath = pkgname.replace('.', '/');
        String resPath = resource.getPath();
        String jarPath = resPath.replaceFirst("[.]jar[!].*", ".jar").replaceFirst("file:", "");
        ZipFile jarFile = null;
        jarFile = new ZipFile(jarPath);         
        Enumeration<? extends ZipEntry> entries = jarFile.entries();
        while(entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            String entryName = entry.getName();
            String className = null;
            if(entryName.endsWith(".class") && entryName.startsWith(relPath) && entryName.length() > (relPath.length() + "/".length())) {
                className = entryName.replace('/', '.').replace('\\', '.').replace(".class", "");
            }
            if (className != null) {
                classes.add(loadClass(className));
            }
        }
        jarFile.close();
    }

    public static List<Class<?>> getClassesForPackage(Package pkg) throws Exception{
        ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
        String pkgname = pkg.getName();
        String relPath = pkgname.replace('.', '/');
        URL resource = ClassLister.class.getResource(relPath);
        if (resource == null)
            return null;
        if(resource.toString().startsWith("jar:"))
            processJarfile(resource, pkgname, classes);
        else
            processDirectory(new File(resource.getPath()), pkgname, classes);

        return classes;
    }
}
