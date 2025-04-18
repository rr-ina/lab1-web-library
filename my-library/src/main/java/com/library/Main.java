package com.library;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Library library = new Library();
    private static Scanner scanner = new Scanner(System.in, "UTF-8");

    private static DataManager dataManager = new DataManager();
    private static final String exportUserPath = "data/users.json";
    private static final String exportUserPathSorted = "data/sorted_users.json";
    private static final String data_dir = "data/";
    private static final String exportBookPath = "data/books.json";
    private static final String exportBookPathSorted = "data/sorted_books.json";

    public static void main(String[] args) {
        // Додавання декількох книг для тестування
        library.addBook(new Book("1", "Маленький принц", "Антуан де Сент-Екзюпері"));
        library.addBook(new Book("2", "1984", "Джордж Орвелл"));
        library.addBook(new Book("3", "Тривожні люди", "Фредрік Бакман"));

        // Додавання декількох читачів для тестування
        library.addReader(new Reader("101", "Іван Петренко"));
        library.addReader(new Reader("102", "Марія Сидоренко"));

        Start();
    }

    public static void Start() {
        while (true) {
            System.out.println("\nОберіть дію:");
            System.out.println("1. Взяти книгу");
            System.out.println("2. Повернути книгу");
            System.out.println("3. Вивести актуальну інформацію (позичені книги)");
            System.out.println("4. Керування користувачами");
            System.out.println("5. Керування книгами");
            System.out.println("6. Вийти");
            System.out.print("Ваш вибір: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    borrowBook();
                    break;
                case "2":
                    returnBook();
                    break;
                case "3":
                    displayBorrowedBooks();
                    break;
                case "4":
                    manageUsers(); // Виклик методу керування користувачами
                    break;
                case "5":
                    manageBooks(); // Новий виклик
                    break;
                case "6":
                    System.out.println("Дякуємо за користування бібліотекою!");
                    return;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
    }

    private static void borrowBook() {
        System.out.print("Введіть ID читача: ");
        String readerId = scanner.nextLine();
        Reader reader = library.findReaderById(readerId);

        if (reader == null) {
            System.out.println("Читача з таким ID не знайдено.");
            return;
        }

        System.out.print("Введіть ID книги, яку хочете взяти: ");
        String bookId = scanner.nextLine();
        Book book = library.findBookById(bookId);

        if (book == null) {
            System.out.println("Книги з таким ID не знайдено.");
            return;
        }

        if (library.borrowBook(reader, book)) {
            System.out.println("Книга \"" + book.getTitle() + "\" успішно видана читачу \"" + reader.getName() + "\".");
        } else {
            System.out.println("Не вдалося видати книгу. Можливо, книга вже позичена або читача/книги не існує.");
        }
    }

    private static void returnBook() {
        System.out.print("Введіть ID читача: ");
        String readerId = scanner.nextLine();
        Reader reader = library.findReaderById(readerId);

        if (reader == null) {
            System.out.println("Читача з таким ID не знайдено.");
            return;
        }

        System.out.print("Введіть ID книги, яку хочете повернути: ");
        String bookId = scanner.nextLine();
        Book book = library.findBookById(bookId);

        if (book == null) {
            System.out.println("Книги з таким ID не знайдено.");
            return;
        }

        if (reader.returnBook(book)) {
            System.out.println(
                    "Книга \"" + book.getTitle() + "\" успішно повернута читачем \"" + reader.getName() + "\".");
        } else {
            System.out
                    .println("Не вдалося повернути книгу. Можливо, читач не брав цю книгу або читача/книги не існує.");
        }
    }

    private static void displayBorrowedBooks() {
        boolean hasBorrowedBooks = false;
        System.out.println("\nПозичені книги");

        for (Reader reader : library.getReaders()) {
            if (!reader.getBorrowedBooks().isEmpty()) {
                hasBorrowedBooks = true;
                System.out.println("Читач: " + reader.getName() + " (ID: " + reader.getId() + ")");
                for (Book book : reader.getBorrowedBooks()) {
                    System.out.println("- " + book.getTitle() + " (ID: " + book.getId() + ")");
                }
                System.out.println("---");
            }
        }
        if (!hasBorrowedBooks) {
            System.out.println("Наразі немає позичених книг.");
        }
    }

    private static void manageUsers() {
        while (true) {
            System.out.println("\nКерування користувачами");
            System.out.println("1. Додати користувача");
            System.out.println("2. Переглянути користувачів");
            System.out.println("3. Видалити користувача");
            System.out.println("4. Експортувати користувачів");
            System.out.println("5. Експортувати користувачів(сортовано)");
            System.out.println("6. Імпортувати користувачів");
            System.out.println("7. Повернутися до головного меню");
            System.out.print("Ваш вибір: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addUser();
                    break;
                case "2":
                    viewUsers();
                    break;
                case "3":
                    deleteUser();
                    break;
                case "4":
                    exportUsers();
                    break;
                case "5":
                    exportSortedUsers();
                    break;
                case "6":
                    importUsers();
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
    }

    private static void addUser() {
        System.out.print("Введіть ID нового користувача: ");
        String id = scanner.nextLine();
        System.out.print("Введіть ім'я нового користувача: ");
        String name = scanner.nextLine();
        Reader newReader = new Reader(id, name);
        if (library.addReader(newReader)) {
            System.out.println("Користувача \"" + name + "\" (ID: " + id + ") успішно додано.");
        } else {
            System.out.println("Помилка при додаванні користувача. Можливо, користувач з таким ID вже існує.");
        }
    }

    private static void viewUsers() {
        List<Reader> readers = library.getReaders();
        if (readers.isEmpty()) {
            System.out.println("Немає зареєстрованих користувачів.");
            return;
        }
        System.out.println("\n-Список користувачів");
        for (Reader reader : readers) {
            System.out.println("ID: " + reader.getId() + ", Ім'я: " + reader.getName());
        }
        System.out.println(" ");
    }

    private static void deleteUser() {
        System.out.print("Введіть ID користувача, якого потрібно видалити: ");
        String idToDelete = scanner.nextLine();
        Reader readerToDelete = library.findReaderById(idToDelete);

        if (readerToDelete == null) {
            System.out.println("Користувача з таким ID не знайдено.");
            return;
        }

        if (library.removeReader(readerToDelete)) {
            System.out.println(
                    "Користувача \"" + readerToDelete.getName() + "\" (ID: " + idToDelete + ") успішно видалено.");
        } else {
            System.out.println("Помилка при видаленні користувача.");
        }
    }

    private static void exportUsers() {
        try {
            java.io.File dataDir = new java.io.File("data");
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }

            dataManager.exportReaders(library.getReaders(), exportUserPath);
            System.out.println("Користувачів успішно експортовано до файлу: " + exportUserPath);
        } catch (IOException e) {
            System.err.println("Помилка при експорті користувачів: " + e.getMessage());
        }
    }

    private static void exportSortedUsers() {
        try {
            java.io.File dataDir = new java.io.File("data");
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }

            Comparator<Reader> comparator = Comparator.comparing(Reader::getName);

            dataManager.exportReadersSorted(library.getReaders(), exportUserPathSorted, comparator);
            System.out.println("Сортованих користувачів успішно експортовано до файлу: " + exportUserPathSorted);
        } catch (IOException e) {
            System.err.println("Помилка при експорті сортованих користувачів: " + e.getMessage());
        }
    }

    private static void importUsers() {
        System.out.print("Введіть назву файлу для імпорту користувачів: ");
        String fileName = scanner.nextLine();
        String filePath = data_dir + fileName;

        try {
            java.io.File dataDir = new java.io.File(data_dir);
            if (!dataDir.exists()) {
                System.out.println("Помилка: папка 'data' не знайдена.");
                return;
            }

            java.io.File importFile = new java.io.File(filePath);
            if (!importFile.exists()) {
                System.out.println("Помилка: файл '" + fileName + "' не знайдено в папці 'data'.");
                return;
            }

            List<Reader> importedReaders = dataManager.importReaders(filePath);
            library.getReaders().clear();
            int addedCount = 0;
            for (Reader reader : importedReaders) {
                if (library.addReader(reader)) {
                    addedCount++;
                }
            }

            if (addedCount > 0) {
                System.out.println("Користувачів успішно імпортовано.");
            } else {
                System.out.println("Імпорт завершено.");
            }

        } catch (IOException e) {
            System.err.println("Помилка при імпорті користувачів з файлу '" + fileName + "': " + e.getMessage());
        }

    }

    private static void manageBooks() {
        while (true) {
            System.out.println("\nКерування книгами");
            System.out.println("1. Додати книгу");
            System.out.println("2. Переглянути книги");
            System.out.println("3. Видалити книгу");
            System.out.println("4. Експортувати книги");
            System.out.println("5. Експортувати книги(сортовано)");
            System.out.println("6. Імпортувати книги");
            System.out.println("7. Повернутися до головного меню");
            System.out.print("Ваш вибір: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addBook();
                    break;
                case "2":
                    viewBooks();
                    break;
                case "3":
                    deleteBook();
                    break;
                case "4":
                    exportBooks();
                    break;
                case "5":
                    exportSortedBooks();
                    break;
                case "6":
                    importBooks();
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
    }
    
    private static void addBook() {
        System.out.print("Введіть ID нової книги: ");
        String id = scanner.nextLine();
        System.out.print("Введіть назву нової книги: ");
        String title = scanner.nextLine();
        System.out.print("Введіть автора нової книги: ");
        String author = scanner.nextLine();
        Book newBook = new Book(id, title, author);
        if (library.addBook(newBook)) {
            System.out.println("Книгу \"" + title + "\" (ID: " + id + ") успішно додано.");
        } else {
            System.out.println("Помилка при додаванні книги. Можливо, книга з таким ID вже існує.");
        }
    }

    private static void viewBooks() {
        List<Book> books = library.getBooks();
        if (books.isEmpty()) {
            System.out.println("Немає зареєстрованих книг.");
            return;
        }
        System.out.println("\n--- Список книг ---");
        for (Book book : books) {
            System.out.println("ID: " + book.getId() + ", Назва: " + book.getTitle() + ", Автор: " + book.getAuthor());
        }
        System.out.println("---");
    }

    private static void deleteBook() {
        System.out.print("Введіть ID книги, яку потрібно видалити: ");
        String idToDelete = scanner.nextLine();
        Book bookToDelete = library.findBookById(idToDelete);

        if (bookToDelete == null) {
            System.out.println("Книги з таким ID не знайдено.");
            return;
        }

        if (library.removeBook(bookToDelete)) {
            System.out.println("Книгу \"" + bookToDelete.getTitle() + "\" (ID: " + idToDelete + ") успішно видалено.");
        } else {
            System.out.println("Помилка при видаленні книги.");
        }
    }

    private static void exportBooks() {
        try {
            java.io.File dataDir = new java.io.File(data_dir);
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }
            dataManager.exportBooks(library.getBooks(), exportBookPath);
            System.out.println("Книги успішно експортовано до файлу: " + exportBookPath);
        } catch (IOException e) {
            System.err.println("Помилка при експорті книг: " + e.getMessage());
        }
    }

    private static void exportSortedBooks() {
        try {
            java.io.File dataDir = new java.io.File(data_dir);
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }
            Comparator<Book> comparator = Comparator.comparing(Book::getTitle);
            dataManager.exportBooksSorted(library.getBooks(), exportBookPathSorted, comparator);
            System.out.println("Сортовані книги успішно експортовано до файлу: " + exportBookPathSorted);
        } catch (IOException e) {
            System.err.println("Помилка при експорті сортованих книг: " + e.getMessage());
        }
    }

    private static void importBooks() {
        System.out.print("Введіть назву файлу для імпорту книг: ");
        String fileName = scanner.nextLine();
        String filePath = data_dir + fileName;
   
        try {
            java.io.File dataDir = new java.io.File(data_dir);
            if (!dataDir.exists()) {
                System.out.println("Помилка: папка 'data' не знайдена.");
                return;
            }
   
            java.io.File importFile = new java.io.File(filePath);
            if (!importFile.exists()) {
                System.out.println("Помилка: файл '" + fileName + "' не знайдено в папці 'data'.");
                return;
            }
   
            List<Book> importedBooks = dataManager.importBooks(filePath);
            int initialBookCount = library.getBooks().size();
            library.getBooks().clear();
            int addedCount = 0;
            for (Book book : importedBooks) {
                if (library.addBook(book)) {
                    addedCount++;
                }
            }
   
            if (addedCount > 0) {
                System.out.println("Книги успішно імпортовано.");
            } else {
                System.out.println("Імпорт завершено.");
            }
   
        } catch (IOException e) {
            System.err.println("Помилка при імпорті книг з файлу '" + fileName + "': " + e.getMessage());
        }
    }
}