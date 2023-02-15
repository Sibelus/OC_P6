package com.openclassrooms.paymybuddy.service.exceptions;

public class EmptyLastnameException extends Exception{
    public EmptyLastnameException(String message) {
        super(message);
    }
}
