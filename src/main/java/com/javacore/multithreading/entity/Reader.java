package com.javacore.multithreading.entity;

import com.javacore.multithreading.exception.LibraryException;
import com.javacore.multithreading.state.ReaderState;
import com.javacore.multithreading.state.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Reader implements Runnable {

    private static final Logger logger =
            LogManager.getLogger();

    private final int id;

    private final List<Integer> requestedBooks;

    private final LibraryStorage library;

    private ReaderState state;

    public Reader(int id,
                  List<Integer> requestedBooks,
                  LibraryStorage library) {

        this.id = id;
        this.requestedBooks = requestedBooks;
        this.library = library;

        state = new ArrivedState();
    }

    @Override
    public void run() {

        logger.info("Reader {} arrived", id
        );

        state = new TakingState();

        logger.info(
                "Reader {} wants books",
                id
        );

        try {
            library.getSemaphore().acquire();

            try {
                state = new ReadingState();

                library.getWarehouse()
                        .getBooks(requestedBooks);

                logger.info(
                        "Reader {} is reading",
                        id
                );

                TimeUnit.SECONDS.sleep(2);

                state = new ReturningState();

                library.getWarehouse()
                        .returnBooks(requestedBooks);

                logger.info(
                        "Reader {} returned books",
                        id
                );

            } finally {

                library.getSemaphore().release();
            }

            state = new LeavingState();

            logger.info(
                    "Reader {} left library",
                    id
            );

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            throw new RuntimeException(
                    new LibraryException("Reader thread interrupted")
            );
        }
    }
}