package com.epam.testServer.storage;

import com.epam.testServer.bean.Book;
import com.epam.testServer.storage.exception.StorageException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yauheni_Borbut on 4/13/2017.
 */
public class Storage {

    private static final String BOOK_NOT_FOUND_EXCEPTION_TEXT = "Book not found";

    private List<Book> books = new ArrayList<>();

    private static Storage instance;

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    public void setBook(Book book) {
        books.add(book);
    }

    public Book getBook(int id) throws StorageException {
        Book resultBook = null;
        for (Book book : books) {
            if (book.getId() == id) {
                resultBook = book;
            }
        }
        if (resultBook == null) {
            throw new StorageException(BOOK_NOT_FOUND_EXCEPTION_TEXT);
        }

        return resultBook;
    }
}
