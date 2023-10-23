package com.example.demo.exception;

public class ObjectNotFoundException extends Exception{
    public ObjectNotFoundException() { super(); }
    public ObjectNotFoundException(String message) { super(message); }
    public ObjectNotFoundException(String message, Throwable cause) { super(message, cause); }
    public ObjectNotFoundException(Throwable cause) { super(cause); }
}
