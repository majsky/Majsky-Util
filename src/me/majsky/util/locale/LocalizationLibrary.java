package me.majsky.util.locale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javax.swing.ProgressMonitorInputStream;

import me.majsky.util.ArrayUtils;

public class LocalizationLibrary {
    private static ResourceBundle bundle;
    
    public static void init(String folder, String locale, String countryCode){
        init(folder, new Locale(locale, countryCode));
    }
    
    public static void init(String folder, Locale locale){
        bundle = ResourceBundle.getBundle(folder + "/lang", locale);
    }
    
    public static String localize(String key){
        try{
            return bundle.getString(key);
        }catch(MissingResourceException e){
            return key;
        }
    }
    
    public static boolean canLocalize(String key){
        return bundle.containsKey(key);
    }
    
    @SuppressWarnings("resource")
    public static Locale[] getLocalizations(String folder, boolean showProgress){
        if(!folder.startsWith("/"))
            folder = "/" + folder;
        List<Locale> list = new ArrayList<>();
        try{
            InputStream is;
            
            if(showProgress)
                is = new ProgressMonitorInputStream(null, "Reading localizations...", LocalizationLibrary.class.getResourceAsStream(folder + "/langs.txt"));
            else
                is = LocalizationLibrary.class.getResourceAsStream(folder + "/langs.txt");
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while((line = reader.readLine()) != null){
                String[] strings = line.split("_");
                list.add(new Locale(strings[0], strings[1]));
            }
            reader.close();
            is.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return list.toArray(new Locale[list.size()]);
    }
    
    public static boolean showLanguageSelectionDialog(Locale[] locales, String folder){
        String[] localized = new String[locales.length];
        
        for(int i=0;i<locales.length;i++)
            localized[i] = locales[i].getDisplayLanguage();
        
        String lang = (String)JOptionPane.showInputDialog(null, "Select your language", "Language selection", JOptionPane.QUESTION_MESSAGE, null, localized, localized[0]);
        
        if(lang == null)
            return false;
        
        int i = ArrayUtils.getIndex(localized, lang);
        
        if(i == -1)
            return false;
        
        init(folder, locales[i]);
        return true;
    }
}
