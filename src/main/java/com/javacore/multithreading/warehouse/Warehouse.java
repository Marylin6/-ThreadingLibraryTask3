package com.javacore.multithreading.warehouse;

import java.util.List;

public interface Warehouse {
    void getBooks(List<Integer> requestedIds);
    void returnBooks(List<Integer> ids);
}
