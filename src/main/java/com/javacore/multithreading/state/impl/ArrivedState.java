package com.javacore.multithreading.state.impl;

import com.javacore.multithreading.state.ReaderState;

public class ArrivedState implements ReaderState {
    @Override
    public String getName() {
        return "ARRIVED";
    }
}
