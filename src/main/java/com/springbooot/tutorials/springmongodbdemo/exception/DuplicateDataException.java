package com.springbooot.tutorials.springmongodbdemo.exception;

public class DuplicateDataException extends RuntimeException {

    public DuplicateDataException() {
        super();
    }

    public DuplicateDataException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
