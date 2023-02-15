package com.openclassrooms.paymybuddy.service.exceptions;

public class InsufficientAmountException extends Exception{
    public InsufficientAmountException(String message) {
        super(message);
    }
}
