package com.javacore.multithreading;

import com.javacore.multithreading.entity.Library;
import com.javacore.multithreading.entity.Reader;
import com.javacore.multithreading.exception.LibraryException;
import com.javacore.multithreading.parser.FileParser;
import com.javacore.multithreading.owner.LibraryOwner;
import com.javacore.multithreading.warehouse.Warehouse;
import com.javacore.multithreading.warehouse.WarehouseImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args)
            throws LibraryException {

        List<String> lines =
                FileParser.read(
                        "src/main/resources/config.txt"
                );

        String[] libraryData =
                lines.get(0).split(" ");

        int librarians =
                Integer.parseInt(
                        libraryData[0]
                );

        int booksAmount =
                Integer.parseInt(
                        libraryData[1]
                );

        Warehouse warehouse =
                new WarehouseImpl(
                        booksAmount
                );

        Library library =
                new Library(
                        librarians,
                        warehouse
                );

        LibraryOwner.getInstance().setLibrary(library);

        ExecutorService executor = Executors.newFixedThreadPool(lines.size() - 1);

        for (int i = 1; i < lines.size(); i++) {

            String[] data = lines.get(i).split(" ");

            int id = Integer.parseInt(data[0]);

            List<Integer> requestedBooks =
                    new ArrayList<>();

            for (int j = 1; j < data.length; j++) {
                requestedBooks.add(Integer.parseInt(data[j]));
            }

            Reader reader = new Reader(id, requestedBooks, library);

            executor.submit(reader);
        }
        executor.shutdown();

        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new LibraryException("Main thread interrupted");
        }
    }
}