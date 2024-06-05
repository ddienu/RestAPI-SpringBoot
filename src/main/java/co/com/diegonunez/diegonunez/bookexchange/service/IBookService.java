package co.com.diegonunez.diegonunez.bookexchange.service;

import co.com.diegonunez.diegonunez.bookexchange.entity.Book;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface IBookService {
    List<Book> getAllBooks() throws EntityNotFoundException;
    Book findBooksByName(String bookName) throws EntityNotFoundException;
    Book getBookByISBN(String isbn) throws Exception;
    List<Book> getBooksByAuthor(String bookAuthor) throws EntityNotFoundException;
    List<Book> getBooksByGenre(String bookGenre) throws Exception;
    Book createBook(Book book) throws UnsupportedOperationException;
    Book updateBook(String isbn, Book book) throws Exception;
    void deleteBookByISBN(String isbn) throws EntityNotFoundException, BadRequestException;
}
