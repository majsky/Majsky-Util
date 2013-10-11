package me.majsky.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    public static void extract(ZipFile file, File target) throws IOException{
        Enumeration<? extends ZipEntry> enumeration = file.entries();
        while(enumeration.hasMoreElements()){
            ZipEntry e = enumeration.nextElement();
            File nat = new File(target, e.getName());
            if(!nat.getParentFile().exists())
                nat.getParentFile().mkdirs();
            if(e.isDirectory()){
                nat.mkdirs();
                continue;
            }
            OutputStream os = new FileOutputStream(nat);
            InputStream is = file.getInputStream(e);
            byte[] buff = new byte[1024];
            int j;
            while((j = is.read(buff, 0, buff.length)) != -1)
                os.write(buff, 0, j);
            is.close();
            os.close();
        }
    }
    
    public static void packZip(File output, File source) throws IOException{
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(output)));
        BufferedInputStream in = null;
        byte[] data  = new byte[1000];
        String files[] = source.list();
        for (int i=0; i<files.length; i++){
            File f = new File(source, files[i]);
            if(f.isDirectory())
                continue;
            in = new BufferedInputStream(new FileInputStream(f), 1000);  
            out.putNextEntry(new ZipEntry(files[i])); 
            int count;
            while((count = in.read(data,0,1000)) != -1){
                out.write(data, 0, count);
            }
            out.closeEntry();
        }
        out.flush();
        out.close();
    }
}