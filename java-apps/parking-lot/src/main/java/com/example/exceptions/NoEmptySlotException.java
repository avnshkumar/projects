package com.example.exceptions;

public class NoEmptySlotException extends RuntimeException {
    public NoEmptySlotException(String message) {
        super(message);
    }
}
