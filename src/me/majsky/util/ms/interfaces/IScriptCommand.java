package me.majsky.util.ms.interfaces;


public interface IScriptCommand{
    public void exec(Object args);
    public boolean chcekArgs(Object args);
    public String usage();
    public IArgsDecoder getDecoder();
}
