package com.example.mode;

public abstract class Mode {
    public Mode() {
        // Constructor can be used for initialization if needed
    }

    public abstract String readInput();
    protected abstract void executeCommand();
}
