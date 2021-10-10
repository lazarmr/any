package com.any.facematch.exceptions;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(String message){
        super(message);
    }
}
