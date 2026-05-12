package com.javacore.multithreading.book.impl;

import com.javacore.multithreading.book.BookState;

public class AvailableBookState implements BookState {

    @Override
    public String getName() {
        return "AVAILABLE";
    }
}
