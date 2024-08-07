package co.com.diegonunez.diegonunez.bookexchange.controller.impl;

import co.com.diegonunez.diegonunez.bookexchange.controller.IBookController;
import co.com.diegonunez.diegonunez.bookexchange.entity.Book;
import co.com.diegonunez.diegonunez.bookexchange.dto.Data;
import co.com.diegonunez.diegonunez.bookexchange.dto.ResponseDto;
import co.com.diegonunez.diegonunez.bookexchange.exception.DuplicateISBNException;
import co.com.diegonunez.diegonunez.bookexchange.exception.InvalidISBNException;
import co.com.diegonunez.diegonunez.bookexchange.exception.NoBookFoundException;
import co.com.diegonunez.diegonunez.bookexchange.service.impl.BookServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.InvalidPropertiesFormatException;
import java.util.List;
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/v1/book")
public class BookControllerImpl implements IBookController {

    private final BookServiceImpl bookService;
    @Autowired
    public BookControllerImpl(BookServiceImpl bookService) {
        this.bookService = bookService;
    }
    @Override
    @GetMapping
    public ResponseEntity<ResponseDto> getAllBooks() throws NoBookFoundException {
            List<Book> books = bookService.getAllBooks();
            return new ResponseEntity<>(
                    new ResponseDto(
                            Data.builder()
                                    .message("Books founded")
                                    .books(books)
                                    .build()
                    ), HttpStatus.OK
            );
    }
    @GetMapping(path = "/name/{bookName}", produces = "application/json")
    @Override
    public ResponseEntity<ResponseDto> findBooksByName(@PathVariable String bookName) throws NoBookFoundException {
            Book book = bookService.findBooksByName(bookName);
                return new ResponseEntity<>(
                        new ResponseDto(
                                Data.builder()
                                        .message("Book with name "+bookName+" founded")
                                        .book(book)
                                        .build()
                        ), HttpStatus.OK);
            }
    @GetMapping(path = "/author/{bookAuthor}", produces = "application/json")
    @Override
    public ResponseEntity<ResponseDto> getBooksByAuthor(@PathVariable String bookAuthor) throws NoBookFoundException {
            List<Book> booksByAuthor = bookService.getBooksByAuthor(bookAuthor);
                return new ResponseEntity<>(
                        new ResponseDto(
                                Data.builder()
                                        .message("Books by author "+bookAuthor+" founded.")
                                        .books(booksByAuthor)
                                        .build()
                        ), HttpStatus.OK);
    }
    @GetMapping(path = "/genre/{bookGenre}", produces = "application/json")
    @Override
    public ResponseEntity<ResponseDto> getBooksByGenre(@PathVariable String bookGenre) throws NoBookFoundException {
            List<Book> booksByGenre = bookService.getBooksByGenre(bookGenre);
                return new ResponseEntity<>(
                        new ResponseDto(
                                Data.builder()
                                        .message("Books by genre "+bookGenre+" founded")
                                        .books(booksByGenre)
                                        .build()
                        ), HttpStatus.OK
                );
        }
    @GetMapping(path = "/isbn/{isbn}")
    @Override
    public ResponseEntity<ResponseDto> getBookByISBN(@PathVariable String isbn) throws NoBookFoundException,InvalidISBNException {
            Book bookByISBN = bookService.getBookByISBN(isbn);
                return new ResponseEntity<>(
                        new ResponseDto(
                                Data.builder()
                                        .message("Book with ISBN:"+isbn+", founded")
                                        .book(bookByISBN)
                                        .build()
                        ), HttpStatus.OK
                );
            }
    @PostMapping(produces = "application/json")
    @Override
    public ResponseEntity<ResponseDto> createBook(@Valid @RequestBody Book book) throws DuplicateISBNException {
            Book bookCreated = bookService.createBook(book);
                return new ResponseEntity<>(
                        new ResponseDto(
                                Data.builder()
                                        .message("Book created successfully")
                                        .book(bookCreated)
                                        .build()
                        ), HttpStatus.CREATED
                );
        }
    @PutMapping(path = "/{isbn}", produces = "application/json")
    @Override
    public ResponseEntity<ResponseDto> updateBook(@PathVariable String isbn, @RequestBody Book bookToUpdate) throws NoBookFoundException, InvalidPropertiesFormatException {
        Book bookUpdated = bookService.updateBook(isbn, bookToUpdate);
            return new ResponseEntity<>(
                    new ResponseDto(
                            Data.builder()
                                    .message("Book updated successfully")
                                    .book(bookUpdated)
                                    .build()
                    ), HttpStatus.OK
            );
        }
    @DeleteMapping(path = "/{isbn}", produces = "application/json")
    @Override
    public ResponseEntity<ResponseDto> deleteBookByISBN(@PathVariable String isbn) throws NoBookFoundException, InvalidISBNException {
            bookService.deleteBookByISBN(isbn);
                return new ResponseEntity<>(new ResponseDto(
                        Data.builder()
                                .message("Book deleted successfully")
                                .build()
                ), HttpStatus.OK
                );
    }
}
