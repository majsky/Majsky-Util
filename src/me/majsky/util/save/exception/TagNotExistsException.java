package me.majsky.util.save.exception;

public class TagNotExistsException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public TagNotExistsException(int id){
        super(String.format("SaveTag slot %s is empty!", id));
    }
}
