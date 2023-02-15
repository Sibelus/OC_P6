package com.openclassrooms.paymybuddy.service.exceptions;

public class EmptyPasswordException extends Exception{
    public EmptyPasswordException(String message) {
        super(message);
    }
}
