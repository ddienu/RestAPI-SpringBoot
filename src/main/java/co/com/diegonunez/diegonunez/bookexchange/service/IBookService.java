package co.com.diegonunez.diegonunez.bookexchange.service;

import co.com.diegonunez.diegonunez.bookexchange.entity.Book;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

public interface IBookService {
    List<Book> getAllBooks() throws EntityNotFoundException;
    Book findBooksByName(String bookName) throws EntityNotFoundException;
    Book getBookByISBN(String isbn) throws EntityNotFoundException, BadRequestException;
    List<Book> getBooksByAuthor(String bookAuthor) throws EntityNotFoundException;
    List<Book> getBooksByGenre(String bookGenre) throws EntityNotFoundException;
    Book createBook(Book book) throws DuplicateKeyException;
    Book updateBook(String isbn, Book book) throws EntityNotFoundException;
    void deleteBookByISBN(String isbn) throws EntityNotFoundException, BadRequestException;
    void validateIsbn(String isbn) throws BadRequestException;
}
