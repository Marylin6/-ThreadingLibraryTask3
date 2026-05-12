package com.javacore.multithreading.entity;

import com.javacore.multithreading.wirehouse.Warehouse;

import java.util.concurrent.Semaphore;

public class Library {

    private final Semaphore semaphore;

    private final Warehouse warehouse;

    public Library(Warehouse warehouse) {
        this.warehouse = warehouse;
        semaphore = new Semaphore(1);
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }
}
