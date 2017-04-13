package com.epam.testServer.storage;

import com.epam.testServer.bean.Book;
import com.epam.testServer.storage.exception.StorageException;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yauheni_Borbut on 4/13/2017.
 */
@XmlRootElement
public class Storage {

    private static final String BOOK_NOT_FOUND_EXCEPTION_TEXT = "Book not found";

    private List<Book> books = new ArrayList<>();

    {
        books.add(new Book(1, "first book", "John", "adventure"));
        books.add(new Book(2, "second book", "John", "adventure"));
        books.add(new Book(3, "third book", "John", "adventure"));
    }

    private static Storage instance;

    private Storage() {

    }

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

    @XmlElementWrapper(name = "books")
    @XmlElementRefs({
            @XmlElementRef(name = "book", type = Book.class, required = true)})
    public List<Book> getBooks() {
        return books;
    }
}
