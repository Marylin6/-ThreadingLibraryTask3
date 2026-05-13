package com.javacore.multithreading.book;

import com.javacore.multithreading.book.impl.AvailableBookState;

public class Book {

    private final int id;
    private BookState state;

    public Book(int id) {
        this.id = id;
        state = new AvailableBookState();
    }

    public int getId() {
        return id;
    }

    public BookState getState() {
        return state;
    }

    public void setState(BookState state) {
        this.state = state;
    }
}