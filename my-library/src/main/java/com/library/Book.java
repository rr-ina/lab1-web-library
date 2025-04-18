package com.library;

import java.util.Objects;

public class Book 
{
    private String id;
    private String title;
    private String author;
    private boolean isBorrowed;

    public Book(String id, String title, String author)
    {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isBorrowed = false;
    }

    public Book() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id;}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title;}

    public String getAuthor() { return author; }
    public void setIAuthor(String author) { this.author = author;}

    public boolean isBorrowed() { return isBorrowed; }
    public void setBorrowed(boolean borrowed) { isBorrowed = borrowed; }

    public boolean borrowBook()
    {
        if(isBorrowed) return false;

        isBorrowed = true;
        return true;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() 
    {
        return "Book{id='" + id + "', title='" + title + "', author='" + author + "', isBorrowed=" + isBorrowed + "}";
    }
    
}
