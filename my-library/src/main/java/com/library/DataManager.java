package com.library;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DataManager 
{
    private final ObjectMapper jsonHelper;

    public DataManager() 
    {
        this.jsonHelper = new ObjectMapper();
    }
    
    public DataManager(ObjectMapper objectMapper) {
        this.jsonHelper = objectMapper;
    }

    public void exportBooks(List<Book> books, String filePath) throws IOException 
    { 
        jsonHelper.writeValue(new File(filePath), books);
    }

    public void exportBooksSorted(List<Book> books, String filePath, Comparator<Book> sortComparator) throws IOException 
    {
        List<Book> sortedBooks = new ArrayList<>(books);
        if (sortComparator != null) {
            sortedBooks.sort(sortComparator);
        }
        jsonHelper.writeValue(new File(filePath), sortedBooks);
    }

    public List<Book> importBooks(String filePath) throws IOException 
    {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        return jsonHelper.readValue(file, jsonHelper.getTypeFactory().constructCollectionType(List.class, Book.class));
    }

    public void exportReaders(List<Reader> readers, String filePath) throws IOException 
    {
        jsonHelper.writeValue(new File(filePath), readers);
    }

    public void exportReadersSorted(List<Reader> readers, String filePath, Comparator<Reader> sortComparator) throws IOException {
        List<Reader> sortedReaders = new ArrayList<>(readers);
        if (sortComparator != null) {
            sortedReaders.sort(sortComparator);
        }
        jsonHelper.writeValue(new File(filePath), sortedReaders);
    }

    public List<Reader> importReaders(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        return jsonHelper.readValue(file, jsonHelper.getTypeFactory().constructCollectionType(List.class, Reader.class));
    }
}
