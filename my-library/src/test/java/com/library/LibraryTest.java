package com.library;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    //тест позичення книги
    @Test
    void testBorrowBookSuccess() 
    {
        //given
        Library library = new Library();
        Book testBook = new Book("1", "Тривожні люди", "Фредрік Бакман");
        Reader testReader = new Reader("1", "Дарія");
        library.addBook(testBook);
        library.addReader(testReader);
        //when
        boolean borrowResult = library.borrowBook(testReader, testBook);
        //then
        assertTrue(borrowResult, "Borrowing should succeed");
        assertTrue(testBook.isBorrowed(), "Book should be marked as borrowed");
        assertTrue(testReader.getBorrowedBooks().contains(testBook), "Book should be in reader's list");
    }
    
    //тест спроби позичити книгу, якої нема в бібліотеці
    @Test
    void testBorrowNotExistsBook() 
    {
        //given
        Library library = new Library();
        Book testBook = new Book("1", "Тривожні люди", "Фредрік Бакман");
        Reader testReader = new Reader("1", "Дарія");
        library.addReader(testReader);
        //when
        boolean borrowResult = library.borrowBook(testReader, testBook);
        //then
        assertFalse(borrowResult, "Borrowing non-existent book should fail");
        assertFalse(testBook.isBorrowed(), "Book status should not change");
        assertTrue(testReader.getBorrowedBooks().isEmpty(), "Reader's book list should remain empty");
    }

    //тест спроби взяти книгу користувача, не записаного в бібліотеку
    @Test
    void testBorrowBookByNotExistsReader() 
    {
        //given
        Library library = new Library();
        Book testBook = new Book("1", "Тривожні люди", "Фредрік Бакман");
        Reader testReader = new Reader("1", "Дарія");
        library.addBook(testBook);
        //when
        boolean borrowResult = library.borrowBook(testReader, testBook);
        //then
        assertFalse(borrowResult, "Borrowing by unregistered reader should fail");
        assertFalse(testBook.isBorrowed(), "Book status should not change");
        assertTrue(testReader.getBorrowedBooks().isEmpty(), "Reader's book list should remain empty");
    }

}
