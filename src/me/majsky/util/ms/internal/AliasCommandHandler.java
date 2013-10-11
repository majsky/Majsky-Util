package me.majsky.util.ms.internal;

import me.majsky.util.ms.StringArgsDecoder;
import me.majsky.util.ms.interfaces.IArgsDecoder;
import me.majsky.util.ms.interfaces.IScriptCommand;

public class AliasCommandHandler implements IScriptCommand{

    @Override
    public void exec(Object args) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean chcekArgs(Object args) {
        if(args instanceof String[]){
            String[] a = (String[]) args;
            if(a.length == 0)
                return true;
        }
        return false;
    }

    @Override
    public String usage() {
        return null;
    }

    @Override
    public IArgsDecoder getDecoder() {
        return StringArgsDecoder.instance();
    }

}
