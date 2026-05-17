package com.javacore.multithreading.owner;

import com.javacore.multithreading.entity.LibraryStorage;

public class LibraryOwner {

    private static final LibraryOwner INSTANCE = new LibraryOwner();
    private LibraryStorage library;

    private LibraryOwner() {
    }

    public static LibraryOwner getInstance() {
        return INSTANCE;
    }

    public LibraryStorage getLibrary() {
        return library;
    }

    public void setLibrary(LibraryStorage library) {
        this.library = library;
    }
}
