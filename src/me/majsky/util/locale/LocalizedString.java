package me.majsky.util.locale;

public class LocalizedString implements CharSequence{
    
    private String data;
    
    public LocalizedString(String string){
        if(LocalizationLibrary.canLocalize(string))
            data = LocalizationLibrary.localize(string);
        else
            data = string;
    }
    
    @Override
    public char charAt(int index) {
        return data.charAt(index);
    }

    @Override
    public int length() {
        return data.length();
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return data.subSequence(start, end);
    }
    
    @Override
    public String toString(){
        return data;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof LocalizedString){
            LocalizedString localizedString = (LocalizedString) obj;
            return localizedString.data.equals(this.data);
        }else if(obj instanceof String)
            return obj.equals(this.toString());
        
        return false;
    }
}
