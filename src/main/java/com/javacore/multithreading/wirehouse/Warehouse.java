package com.javacore.multithreading.wirehouse;

public interface Warehouse {
    void getBooks(int amount);
    void returnBooks(int amount);
}
