package com.javacore.multithreading.owner;

import com.javacore.multithreading.entity.Library;

public class LibraryOwner {

    private static final LibraryOwner INSTANCE = new LibraryOwner();
    private Library library;

    private LibraryOwner() {
    }

    public static LibraryOwner getInstance() {
        return INSTANCE;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }
}
