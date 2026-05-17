package com.javacore.multithreading.entity;

public class Book {

    private final int id;
    private boolean taken;

    public Book(int id) {
        this.id = id;
        this.taken = false;
    }

    public int getId() {
        return id;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }
}