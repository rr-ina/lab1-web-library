package com.library;

import java.util.Objects;
import java.util.List;
import java.util.ArrayList;

public class Reader
{
    private String id;
    private String name; 
    private List<Book> borrowedBooks; 
    
    public Reader(String id, String name) 
    {
        this.id = id;
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    public Reader() {
        this.borrowedBooks = new ArrayList<>();
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id;}
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name;}

    public List<Book> getBorrowedBooks() { return borrowedBooks; }
    public void setBorrowedBooks(List<Book> borrowedBooks) { this.borrowedBooks = borrowedBooks; }

    public boolean returnBook(Book book) 
    {
        if(borrowedBooks.contains(book))
        {
            borrowedBooks.remove(book);
            book.setBorrowed(false);
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reader reader = (Reader) o;
        return Objects.equals(id, reader.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Reader{id='" + id + "', name='" + name + "', borrowedBooks=" + borrowedBooks + "}";
    }
}
