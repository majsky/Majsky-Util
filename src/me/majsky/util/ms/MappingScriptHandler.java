package me.majsky.util.ms;

import java.util.HashMap;
import java.util.Map;

import me.majsky.util.ms.interfaces.IScriptCommand;
import me.majsky.util.ms.interfaces.IScriptExceptionHandler;

public class MappingScriptHandler {
    
    private Map<String, IScriptCommand> commandHandlers;
    private IScriptExceptionHandler exceptionHandler;
    
    public MappingScriptHandler(){
        commandHandlers = new HashMap<>();
    }
    
    public void registerCommand(String command, IScriptCommand handler){
        if(command != null && handler != null)
            commandHandlers.put(command, handler);
        else
            throw new IllegalArgumentException();
    }
    
    @SuppressWarnings("unused")
    private CommandInfo getInfo(String command){
        String[] cmd = command.split(" ");
        if(!commandHandlers.containsKey(cmd[0])){
            if(exceptionHandler != null)
                exceptionHandler.noSuchCommand(cmd[0]);
            return null;
        }
        IScriptCommand handler = commandHandlers.get(cmd[0]);
        Object o = handler.getDecoder().decode(command.substring(command.indexOf("\""), command.lastIndexOf("\"")));
        return new CommandInfo(o, handler);
    }
    
    
    private class CommandInfo{
        @SuppressWarnings("unused")
        private Object args;
        @SuppressWarnings("unused")
        private IScriptCommand handler;
        
        private CommandInfo(Object args, IScriptCommand handler) {
            super();
            this.args = args;
            this.handler = handler;
        }
    }
}
