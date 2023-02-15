package com.openclassrooms.paymybuddy.service.exceptions;

public class NegativeAmountException extends Exception{
    public NegativeAmountException(String message) {
        super(message);
    }
}
