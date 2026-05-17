package com.javacore.multithreading.storage;

import com.javacore.multithreading.entity.Book;
import com.javacore.multithreading.exception.LibraryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LibraryStorage {

    private static final Logger logger = LogManager.getLogger();

    private static final LibraryStorage INSTANCE = new LibraryStorage();
    private final List<Book> books = new ArrayList<>();
    private final Lock lock = new ReentrantLock();
    private Semaphore semaphore;

    private LibraryStorage() {}

    public static LibraryStorage getInstance() {
        return INSTANCE;
    }

    public void init(int booksAmount) {

        for (int i = 0; i < booksAmount; i++) {
            books.add(new Book(i));
        }
        semaphore = new Semaphore(booksAmount);
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public void getBooks(List<Integer> ids) throws LibraryException {

        lock.lock();
        try {

            for (int id : ids) {
                Book book = books.get(id);

                if (book.isTaken()) {
                    throw new LibraryException(
                            "Book " + id + " already taken"
                    );
                }
            }

            for (int id : ids) {
                Book book = books.get(id);
                book.setTaken(true);

                logger.info("Book {} taken", id);
            }

        } finally {
            lock.unlock();
        }
    }

    public void returnBooks(List<Integer> ids) {

        lock.lock();
        try {

            for (int id : ids) {
                Book book = books.get(id);
                book.setTaken(false);
                logger.info("Book {} returned", id);
            }

        } finally {
            lock.unlock();
        }
    }
}