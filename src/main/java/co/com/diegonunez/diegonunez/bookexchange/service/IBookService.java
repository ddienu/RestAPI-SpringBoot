package co.com.diegonunez.diegonunez.bookexchange.service;

import co.com.diegonunez.diegonunez.bookexchange.entity.Book;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface IBookService {
    public List<Book> getAllBooks() throws Exception;
    List<Book> findBooksByName(String bookName) throws Exception;
    public Book getBookByISBN(String ISBN) throws Exception;
    public List<Book> getBooksByAuthor(String bookAuthor) throws Exception;
    public List<Book> getBooksByGenre(String bookGenre) throws Exception;
    public Book createBook(Book book) throws EntityNotFoundException;
    public Book updateBook(String ISBN, Book book) throws Exception;
    public String deleteBookByISBN(String ISBN) throws Exception;
}
