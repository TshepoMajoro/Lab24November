package org.springtech.lab24november.Exceptions;

public class DuplicateResourceException extends Exception{
    public DuplicateResourceException(String message){
        super(message);
    }
}
