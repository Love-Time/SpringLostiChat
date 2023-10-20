package com.example.demo.exception;

public class IncorrectOldPasswordException extends Exception{
    public IncorrectOldPasswordException() { super(); }
    public IncorrectOldPasswordException(String message) { super(message); }
    public IncorrectOldPasswordException(String message, Throwable cause) { super(message, cause); }
    public IncorrectOldPasswordException(Throwable cause) { super(cause); }


}
