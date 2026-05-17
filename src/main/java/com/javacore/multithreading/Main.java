package com.javacore.multithreading;

import com.javacore.multithreading.entity.BookReader;
import com.javacore.multithreading.exception.LibraryException;
import com.javacore.multithreading.parser.LibraryParser;
import com.javacore.multithreading.storage.LibraryStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args)
            throws LibraryException {

        List<String> lines = LibraryParser.read(
                        "src/main/resources/config.txt"
                );

        int booksAmount = Integer.parseInt(lines.get(0));
        LibraryStorage.getInstance().init(booksAmount);
        int readersAmount = lines.size() - 1;
        ExecutorService executor = Executors.newFixedThreadPool(readersAmount);

        for (int i = 1; i < lines.size(); i++) {
            String[] data = lines.get(i).split(" ");
            int id = Integer.parseInt(data[0]);

            List<Integer> requested = new ArrayList<>();

            for (int j = 1; j < data.length; j++) {
                requested.add(Integer.parseInt(data[j]));
            }
            executor.submit(new BookReader(id, requested));
        }

        executor.shutdown();

        try {
            executor.awaitTermination(
                    1,
                    TimeUnit.MINUTES
            );
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new LibraryException("Main interrupted");
        }
    }
}