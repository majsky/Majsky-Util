package me.majsky.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class DriveUtils {
	private DriveUtils(){}
	
	private static final String SCRIPT_FOLDER = "/data/DriveUtils/";
	private static final String OPEN_ALL_SCRIPT_FILE = SCRIPT_FOLDER + "openAll.vbs";
	private static final String OPEN_ONE_SCRIPT_FILE = SCRIPT_FOLDER + "openOne.vbs";
	private static final String CLOSE_ONE_SCRIPT_FILE = SCRIPT_FOLDER + "closeOne.vbs";
	private static final String CLOSE_ALL_SCRIPT_FILE = SCRIPT_FOLDER + "closeAll.vbs";
	private static final String COUNT_DRIVES_SCRIPT_FILE = SCRIPT_FOLDER + "count.vbs";
	
	public static void openAll() throws IOException, InterruptedException{
		if(!ResourceLoader.isLoaded(OPEN_ALL_SCRIPT_FILE))
			ResourceLoader.load(OPEN_ALL_SCRIPT_FILE);
		File temp = ResourceLoader.saveToTemp(OPEN_ALL_SCRIPT_FILE);
		Runtime.getRuntime().exec("wscript " + temp.getAbsolutePath()).waitFor();
	}
	
	public static void open(char driveLetter) throws IOException, InterruptedException{
		if(!ResourceLoader.isLoaded(OPEN_ONE_SCRIPT_FILE))
			ResourceLoader.load(OPEN_ONE_SCRIPT_FILE);
		File temp = ResourceLoader.saveToTemp(OPEN_ONE_SCRIPT_FILE);
		Runtime.getRuntime().exec("wscript " + temp.getAbsolutePath() + " " + driveLetter + ":\\").waitFor();
	}
	
	public static void close(char driveLetter) throws IOException, InterruptedException{
		if(!ResourceLoader.isLoaded(CLOSE_ONE_SCRIPT_FILE))
			ResourceLoader.load(CLOSE_ONE_SCRIPT_FILE);
		File temp = ResourceLoader.saveToTemp(CLOSE_ONE_SCRIPT_FILE);
		Runtime.getRuntime().exec("wscript " + temp.getAbsolutePath() + " " + driveLetter + ":\\").waitFor();
	}
	
	public static void closeAll() throws IOException, InterruptedException{
		if(!ResourceLoader.isLoaded(CLOSE_ALL_SCRIPT_FILE))
			ResourceLoader.load(CLOSE_ALL_SCRIPT_FILE);
		File temp = ResourceLoader.saveToTemp(CLOSE_ALL_SCRIPT_FILE);
		Runtime.getRuntime().exec("wscript " + temp.getAbsolutePath()).waitFor();
	}
	
	public static int countDrives() throws IOException, InterruptedException{
		if(!ResourceLoader.isLoaded(COUNT_DRIVES_SCRIPT_FILE))
			ResourceLoader.load(COUNT_DRIVES_SCRIPT_FILE);
		File temp = ResourceLoader.saveToTemp(COUNT_DRIVES_SCRIPT_FILE);
		Process process = Runtime.getRuntime().exec("CScript " + temp.getAbsolutePath());
		process.waitFor();
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String text;
		while((text = reader.readLine()) != null)
			if(StringUtils.isInteger(text))
				return Integer.parseInt(text);
		return 0;
	}
}
