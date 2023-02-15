package com.openclassrooms.paymybuddy.service.exceptions;

public class EmptyEmailException extends Exception{
    public EmptyEmailException(String message) {
        super(message);
    }
}
