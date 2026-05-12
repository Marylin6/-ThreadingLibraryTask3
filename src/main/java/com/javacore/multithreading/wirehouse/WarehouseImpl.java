package com.javacore.multithreading.wirehouse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WarehouseImpl implements Warehouse{

    private static final Logger logger = LogManager.getLogger();

    private final int capacity;
    private int books;
    private final Lock lock = new ReentrantLock();

    public WarehouseImpl(int capacity, int books) {
        this.capacity = capacity;
        this.books = books;
    }

    public void getBooks(int amount) {
        lock.lock();

        try {
            if (books < amount) {
                logger.info("Library does not have enough books");
                return;
            }

            books -= amount;
            logger.info("Took {} books from library. Current: {}", amount, books);

        } finally {
            lock.unlock();
        }
    }

    public void returnBooks(int amount) {
        lock.lock();

        try {
            books += amount;
            logger.info("Unloaded {} books to library. Current: {}",
                    amount, books);

        } finally {
            lock.unlock();
        }
    }
}
