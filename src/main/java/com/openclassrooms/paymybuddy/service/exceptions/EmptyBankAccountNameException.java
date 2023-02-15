package com.openclassrooms.paymybuddy.service.exceptions;

public class EmptyBankAccountNameException extends Exception{
    public EmptyBankAccountNameException(String message) {
        super(message);
    }
}
