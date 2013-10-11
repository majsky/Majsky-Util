package me.majsky.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    
    public static void listFiles(File folder, List<File> list){
        listFiles(folder, list, null);
    }
    
    public static boolean containsFile(File folder, File file){
        for(File f:folder.listFiles())
            if(f.getAbsolutePath().equals(file.getAbsolutePath()))
                return true;
        return false;
    }
    
    public static void listFiles(File folder, List<File> list, IFileFilter filter){
        if(list == null)
            list = new ArrayList<File>();
        for(File f:folder.listFiles()){
            if(f.isDirectory()){
                listFiles(f, list, filter);
                continue;
            }
            if(filter != null){
                if(filter.accept(f))
                    list.add(f);
            }else
                list.add(f);
        }
    }
    
    public static void copyFile(File from, File to) throws IOException{
        copyFile(from, to, 1024);
    }
    
    public static void copyFile(File from, File to, int buffsize) throws IOException{
        FileInputStream inputStream = new FileInputStream(from);
        FileOutputStream outputStream = new FileOutputStream(to);
        byte[] buff = new byte[buffsize];
        int i;
        while((i = inputStream.read(buff, 0, buff.length)) != -1)
            outputStream.write(buff, 0, i);
        inputStream.close();
        outputStream.close();
    }
    
    public static void move(File from, File to){
        from.renameTo(to);
    }
    
    public static long folderSize(File directory) {
        long length = 0;
        for (File file : directory.listFiles()) {
            if (file.isFile())
                length += file.length();
            else
                length += folderSize(file);
        }
        return length;
    }
    
    public static File toFile(URL url){
        try{
            return new File(url.toURI());
        }catch(URISyntaxException e){
            return new File(url.getPath());
        }
    }
    
    public static URL toURL(File file){
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public  static File[] listFolder(String folder){
        return listFolder(folder, null);
    }
    
    public static File[] listFolder(String folder, IFileFilter filter){
        File f = new File(folder);
        if(!f.exists() || !f.isDirectory())
            return null;
        if(filter == null)
            return f.listFiles();
        return f.listFiles(filter);
    }
    
    public static boolean delete(File folder){
        System.out.println(folder);
        if(folder.isFile())
            return folder.delete();
        
        for(File f:folder.listFiles()){
            if(f.isDirectory())
                delete(f);
            f.delete();
        }
        return folder.delete();
    }
    
    public static interface IFileFilter extends FileFilter{
        public boolean accept(File file);
    }
    
    public static class DirectoryFileFilter implements IFileFilter{
        public boolean accept(File file){return file.isDirectory();}
    }
    
    public static class FileFileFilter extends DirectoryFileFilter{
        public boolean accept(File file){return !super.accept(file);}
    }
}
