package com.springbooot.tutorials.springmongodbdemo.exception;

public class UserAlreadyExists extends RuntimeException{

    public UserAlreadyExists(String exceptionMessage) {
        super(exceptionMessage);
    }
}
