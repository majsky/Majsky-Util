package me.majsky.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.majsky.util.reflection.ReflectionHelper;

public class ResourceLoader {
	private ResourceLoader(){}
	
	private static Map<String, Integer[]> files = new HashMap<>();
	
	public static boolean load(String file) throws IOException{
		if(!canLoad(file))
			return false;
		InputStream is = ResourceLoader.class.getResourceAsStream(file);
		List<Integer> buffer = new ArrayList<>();
		int i;
		while((i = is.read()) != -1)
			buffer.add(i);
		files.put(file, buffer.toArray(new Integer[0]));
		return true;
	}
	
	public static InputStream getResource(String name){
		if(!files.containsKey(name))
			return null;
		return new ByteArrayInputStream(convert(files.get(name)));
	}
	
	public static File saveToTemp(String name) throws IOException{
		if(!isLoaded(name))
			return null;
		String clazz = ReflectionHelper.getCaller().getClassName();
		File temp = File.createTempFile(clazz.substring(clazz.lastIndexOf('.') + 1), name.substring(name.lastIndexOf('.')));
		temp.deleteOnExit();
		FileOutputStream os = new FileOutputStream(temp);
		InputStream is = getResource(name);
		byte[] buff = new byte[1024];
		int i;
		while((i = is.read(buff, 0, buff.length)) > -1)
			os.write(buff, 0, i);
		is.close();
		os.close();
		return temp;
	}
	
	public static boolean canLoad(String file){
		if(null == ResourceLoader.class.getResource(file))
			return false;
		return true;
	}
	
	public static boolean isLoaded(String name){
		return files.containsKey(name);
	}
	
	private static byte[] convert(Integer[] array){
		byte[] retVal = new byte[array.length];
		for(int i = 0; i < array.length; i++)
			retVal[i] = Byte.parseByte(Integer.toString(array[i]));
		return retVal;
	}

}
