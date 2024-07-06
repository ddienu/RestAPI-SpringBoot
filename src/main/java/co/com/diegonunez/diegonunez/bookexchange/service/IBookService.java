package co.com.diegonunez.diegonunez.bookexchange.service;

import co.com.diegonunez.diegonunez.bookexchange.entity.Book;
import co.com.diegonunez.diegonunez.bookexchange.exception.DuplicateISBNException;
import co.com.diegonunez.diegonunez.bookexchange.exception.InvalidISBNException;
import co.com.diegonunez.diegonunez.bookexchange.exception.NoBookFoundException;

import java.util.InvalidPropertiesFormatException;
import java.util.List;

public interface IBookService {
    List<Book> getAllBooks() throws NoBookFoundException;
    Book findBooksByName(String bookName) throws NoBookFoundException;
    Book getBookByISBN(String isbn) throws NoBookFoundException, InvalidISBNException;
    List<Book> getBooksByAuthor(String bookAuthor) throws NoBookFoundException;
    List<Book> getBooksByGenre(String bookGenre) throws NoBookFoundException;
    Book createBook(Book book) throws DuplicateISBNException;
    Book updateBook(String isbn, Book book) throws NoBookFoundException, InvalidPropertiesFormatException;
    void deleteBookByISBN(String isbn) throws NoBookFoundException, InvalidISBNException;
}
