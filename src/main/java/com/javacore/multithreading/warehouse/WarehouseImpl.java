package com.javacore.multithreading.warehouse;

import com.javacore.multithreading.entity.Book;
import com.javacore.multithreading.book.impl.AvailableBookState;
import com.javacore.multithreading.book.impl.TakenBookState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WarehouseImpl implements Warehouse{

    private static final Logger logger = LogManager.getLogger();

    private final List<Book> books = new ArrayList<>();
    private final Lock lock = new ReentrantLock();

    public WarehouseImpl(int booksAmount) {
        for (int i = 0; i < booksAmount; i++) {
            books.add(new Book(i));
        }
    }

    public void getBooks(List<Integer> requestedIds) {
        lock.lock();

        try {
            for (int id : requestedIds) {
                Book book = books.get(id);
                if (book.getState() instanceof TakenBookState) {
                    logger.info("Book {} is already taken", id);
                    return;
                }

                book.setState(new TakenBookState());
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
                book.setState(new AvailableBookState());
            }
            logger.info("Return books to library.");

        } finally {
            lock.unlock();
        }
    }
}
