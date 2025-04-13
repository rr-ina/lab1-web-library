package com.library;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    //тест позичання книги
    @Test
    void testBorrowBookSuccess()
    {
        //given
        Book testBook = new Book("1", "Тривожні люди", "Фредрік Бакман");
        //when
        boolean result = testBook.borrowBook();
        //then
        assertTrue(result, "Borrowing should succeed for a free book");
        assertTrue(testBook.isBorrowed(), "Book should be marked as borrowed");
    }

    //тест на спробу позичити вже позичену книгу
    @Test
    void testBorrowBookAlreadyBorrowed() 
    {
        // given 
        Book testBook = new Book("1", "Я бачу, вас цікавить пітьма", "Ілларіон Павлюк");
        testBook.borrowBook();  
        //when
        boolean result = testBook.borrowBook();
        //then
        assertFalse(result, "Borrowing an already borrowed book should fail");
        assertTrue(testBook.isBorrowed(), "Book should remain borrowed");
    }

    @Test
    //перевірка методів equals і hashCode
    void testEqualsAndHashCode() 
    {
        //given
        Book testBook1 = new Book("1", "Тривожні люди", "Фредрік Бакман");
        Book testBook2 = new Book("1", "Я бачу, вас цікавить пітьма", "Ілларіон Павлюк");
        Book testBook3 = new Book("2", "Тривожні люди", "Фредрік Бакман");
        //then
        assertEquals(testBook1, testBook2, "Books with the same ID should be equal");
        assertEquals(testBook1.hashCode(), testBook2.hashCode(), "Books with the same ID should have the same hashCode");
        assertNotEquals(testBook1, testBook3, "Books with different IDs should not be equal");
    }
}