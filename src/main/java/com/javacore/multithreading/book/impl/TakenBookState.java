package com.javacore.multithreading.book.impl;

import com.javacore.multithreading.book.BookState;

public class TakenBookState implements BookState {

    @Override
    public String getName() {
        return "TAKEN";
    }
}
