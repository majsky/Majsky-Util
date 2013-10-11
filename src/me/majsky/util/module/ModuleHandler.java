package me.majsky.util.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleHandler {
    private Map<Integer, List<IModule>> modules;
    
    public ModuleHandler(){
        modules = new HashMap<Integer, List<IModule>>();
    }
    
    public void addModule(IModule module, int priority){
        if(!modules.containsKey(priority))
            modules.put(priority, new ArrayList<IModule>());
        modules.get(priority).add(module);
    }
    
    public void startModules(){
        List<Integer> list = new ArrayList<>();
        
        for(int e:modules.keySet())
            if(!list.contains(e))
                list.add(e);
        
        Integer[] order = list.toArray(new Integer[0]);
        Arrays.sort(order);
        
        for(int i:order){
            for(IModule m:modules.get(i)){
                m.start();
            }
        }
    }
    
    public void stopModules(){
        List<Integer> list = new ArrayList<>();
        
        for(int e:modules.keySet())
            if(!list.contains(e))
                list.add(e);
        
        Integer[] order = list.toArray(new Integer[0]);
        Arrays.sort(order);
        
        for(int i:order){
            for(IModule m:modules.get(i)){
                m.stop();
            }
        }
    }
    
    public void initModules(){
        List<Integer> list = new ArrayList<>();
        
        for(int e:modules.keySet())
            if(!list.contains(e))
                list.add(e);
        
        Integer[] order = list.toArray(new Integer[0]);
        Arrays.sort(order);
        
        for(int i:order){
            for(IModule m:modules.get(i)){
                if(!(m instanceof IAdvancedModule))
                    continue;
                IAdvancedModule mod = (IAdvancedModule)m;
                mod.init();
            }
        }
    }
    
    public void postInitModules(){
        List<Integer> list = new ArrayList<>();
        
        for(int e:modules.keySet())
            if(!list.contains(e))
                list.add(e);
        
        Integer[] order = list.toArray(new Integer[0]);
        Arrays.sort(order);
        
        for(int i:order){
            for(IModule m:modules.get(i)){
                if(!(m instanceof IAdvancedModule))
                    continue;
                IAdvancedModule mod = (IAdvancedModule)m;
                mod.postInit();
            }
        }
    }
}
