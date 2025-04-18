package com.library;

import java.util.ArrayList;
import java.util.List;

public class Library 
{
    private List<Book> books;
    private List<Reader> readers;

    public Library() 
    {
        this.books = new ArrayList<>();
        this.readers = new ArrayList<>();
    }

    public boolean addBook(Book book) { 
        if (findBookById(book.getId()) == null) {
            books.add(book);
            return true;
        }
        return false;
    }

    public boolean removeBook(Book book) { return books.remove(book); }

    public List<Book> getBooks() { return new ArrayList<>(books); }


    public boolean addReader(Reader reader) {
        if (readers.contains(reader)) { return false; }
        readers.add(reader);
        return true; 
    }

    public boolean removeReader(Reader reader) { return readers.remove(reader); }

    public List<Reader> getReaders() { return new ArrayList<>(readers); }


    public boolean borrowBook(Reader reader, Book book) {
        if (!readers.contains(reader) || !books.contains(book)) { return false; }
        if (book.borrowBook()) 
        {
            reader.getBorrowedBooks().add(book);
            return true;
        }
        return false;
    }

    public Book findBookById(String bookId) {
        for (Book book : books) {
            if (book.getId().equals(bookId)) {
                return book;
            }
        }
        return null;
    }

    public Reader findReaderById(String readerId) {
        for (Reader reader : readers) {
            if (reader.getId().equals(readerId)) {
                return reader;
            }
        }
        return null;
    }
    
}

