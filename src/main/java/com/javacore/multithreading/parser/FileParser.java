package com.javacore.multithreading.parser;

import com.javacore.multithreading.exception.LibraryException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileParser {

    public static List<String> read(String path)
            throws LibraryException {

        try {

            return Files.readAllLines(
                    Paths.get(path)
            );

        } catch (IOException e) {

            throw new LibraryException(
                    "Error reading config file"
            );
        }
    }
}