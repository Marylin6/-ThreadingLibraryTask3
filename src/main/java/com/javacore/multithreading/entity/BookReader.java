package com.javacore.multithreading.entity;

import com.javacore.multithreading.exception.LibraryException;
import com.javacore.multithreading.state.ReaderState;
import com.javacore.multithreading.storage.LibraryStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BookReader implements Runnable {

    private static final Logger logger = LogManager.getLogger();
    private final int id;
    private final List<Integer> requestedBooks;
    private ReaderState state;

    public BookReader(int id, List<Integer> requestedBooks) {
        this.id = id;
        this.requestedBooks = requestedBooks;
        state = ReaderState.ARRIVED;
    }

    @Override
    public void run() {
        LibraryStorage storage = LibraryStorage.getInstance();
        int booksCount = requestedBooks.size();
        logger.info("BookReader {} arrived", id);

        try {
            state = ReaderState.WAITING;

            logger.info("BookReader {} waiting for {} books",
                    id, booksCount);

            storage.getSemaphore().acquire(booksCount);

            try {
                state = ReaderState.TAKING;
                storage.getBooks(requestedBooks);

                state = ReaderState.READING;
                logger.info("BookReader {} is reading", id);
                TimeUnit.SECONDS.sleep(2);

                state = ReaderState.RETURNING;
                storage.returnBooks(requestedBooks);
                logger.info("BookReader {} returned books", id);

            } finally {
                storage.getSemaphore().release(booksCount);
            }

            state = ReaderState.LEFT;
            logger.info("BookReader {} left library", id);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(
                    new LibraryException("BookReader interrupted"));

        } catch (LibraryException e) {
            throw new RuntimeException(e);
        }
    }
}