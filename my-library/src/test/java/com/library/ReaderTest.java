package com.library;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReaderTest {

    //тест повернення книги
    @Test
    void testReturnBookSuccess()
    {
        //given
        Book testBook = new Book("1", "Тривожні люди", "Фредрік Бакман");
        Reader testReader = new Reader("1", "Дарія");
        testBook.borrowBook();
        testReader.getBorrowedBooks().add(testBook);
        //when
        boolean result = testReader.returnBook(testBook);
        //then
        assertTrue(result, "Returning a borrowed book should succeed");
        assertFalse(testBook.isBorrowed(), "Book should not be borrowed after return");
        assertFalse(testReader.getBorrowedBooks().contains(testBook), "Book should be removed from reader's list");
    }

    //тест спроби повернення книги, яку не позичав користувач
    @Test
    void testReturnBookNotBorrowed() 
    {
        //given
        Book testBook = new Book("1", "Тривожні люди", "Фредрік Бакман");
        Reader testReader = new Reader("1", "Дарія");
        //when
        boolean result = testReader.returnBook(testBook);
        //then
        assertFalse(result, "Returning a non-borrowed book should fail");
        assertFalse(testBook.isBorrowed(), "Book status should not change");
        assertTrue(testReader.getBorrowedBooks().isEmpty(), "Reader's book list should remain empty");
    }

    @Test
    void testEqualsAndHashCode() 
    {
        //given
        Reader testReader1 = new Reader("1", "Дарія");
        Reader testReader2 = new Reader("1", "Дарина");
        Reader testReader3 = new Reader("2", "Дарія");
        //then
        assertEquals(testReader1, testReader2, "Readers with the same ID should be equal");
        assertEquals(testReader1.hashCode(), testReader2.hashCode(), "Readers with the same ID should have the same hashCode");
        assertNotEquals(testReader1, testReader3, "Readers with different IDs should not be equal");
    }
}
