package me.majsky.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ArgsPraser {
    private Map<String, List<String>> args;
    
    public ArgsPraser(String[] arguments){
        this.args = new HashMap<String, List<String>>();
        this.args.put("unknown", new ArrayList<String>());
        
        String curName = null;
        for(String s:arguments){
            if(s.startsWith("-")){
                if(!args.containsKey(s.substring(1)))
                    args.put(s.substring(1), new ArrayList<String>());
                curName = s.substring(1);
                continue;
            }
            
            if(curName != null)
                args.get(curName).add(s);
            else
                args.get("unknown").add(s);
        }
    }
    
    public List<String> getArgumentData(String argName){
        return args.get(argName);
    }
    
    public boolean contains(String argName){
        return args.containsKey(argName);
    }
    
    public Set<String> nameSet(){
        return args.keySet();
    }
}
