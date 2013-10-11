package me.majsky.util.save.exception;

public class CantParentItselfException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CantParentItselfException(){
        super("Parent cant be children of itself!");
    }
}
