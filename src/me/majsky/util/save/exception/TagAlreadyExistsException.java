package me.majsky.util.save.exception;

import me.majsky.util.save.SaveTagBase;

public class TagAlreadyExistsException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public TagAlreadyExistsException(Class<? extends SaveTagBase> original, Class<? extends SaveTagBase> _new, int id){
        super(String.format("Cant add %s to slot %s because it contains %s", _new, id, original));
    }
}
