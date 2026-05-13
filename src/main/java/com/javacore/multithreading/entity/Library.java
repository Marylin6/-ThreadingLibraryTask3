package com.javacore.multithreading.entity;

import com.javacore.multithreading.warehouse.Warehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Library {

    private final List<LibraryWoman> libraryWomen = new ArrayList<>();
    private final Semaphore semaphore;
    private final Warehouse warehouse;

    public Library(int womenAmount, Warehouse warehouse) {
        this.warehouse = warehouse;
        for (int i = 0; i < womenAmount; i++) {
            libraryWomen.add(new LibraryWoman(i));
        }
        semaphore = new Semaphore(womenAmount);
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }
}
