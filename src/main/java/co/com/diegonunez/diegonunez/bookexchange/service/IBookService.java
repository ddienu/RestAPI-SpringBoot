package co.com.diegonunez.diegonunez.bookexchange.service;

import co.com.diegonunez.diegonunez.bookexchange.entity.Book;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface IBookService {
    List<Book> getAllBooks() throws EntityNotFoundException;
    Book findBooksByName(String bookName) throws EntityNotFoundException;
    Book getBookByISBN(String isbn) throws Exception;
    List<Book> getBooksByAuthor(String bookAuthor) throws Exception;
    List<Book> getBooksByGenre(String bookGenre) throws Exception;
    Book createBook(Book book) throws EntityNotFoundException;
    Book updateBook(String isbn, Book book) throws Exception;
    String deleteBookByISBN(String isbn) throws EntityNotFoundException;
}
