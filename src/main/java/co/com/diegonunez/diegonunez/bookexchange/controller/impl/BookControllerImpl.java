package co.com.diegonunez.diegonunez.bookexchange.controller.impl;

import co.com.diegonunez.diegonunez.bookexchange.controller.IBookController;
import co.com.diegonunez.diegonunez.bookexchange.entity.Book;
import co.com.diegonunez.diegonunez.bookexchange.dto.BodyResponseDto;
import co.com.diegonunez.diegonunez.bookexchange.dto.HeaderDto;
import co.com.diegonunez.diegonunez.bookexchange.dto.ResponseDto;
import co.com.diegonunez.diegonunez.bookexchange.service.impl.BookServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.TransactionalException;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static co.com.diegonunez.diegonunez.bookexchange.util.Constants.SUCCESS;
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
    public ResponseEntity<ResponseDto> getAllBooks() throws EntityNotFoundException {
            List<Book> books = bookService.getAllBooks();
            return new ResponseEntity<>(
                    new ResponseDto(
                            new HeaderDto(SUCCESS, HttpStatus.OK.value(), "Books founded"),
                            new BodyResponseDto(books)
                    ), HttpStatus.OK
            );
    }
    @GetMapping(path = "/name/{bookName}", produces = "application/json")
    @Override
    public ResponseEntity<ResponseDto> findBooksByName(@PathVariable String bookName) throws EntityNotFoundException {
            Book book = bookService.findBooksByName(bookName);
                return new ResponseEntity<>(
                        new ResponseDto(
                                new HeaderDto(SUCCESS, HttpStatus.OK.value(), "Book with name "+bookName+" founded"),
                                new BodyResponseDto(book)
                        ), HttpStatus.OK);
            }
    @GetMapping(path = "/author/{bookAuthor}", produces = "application/json")
    @Override
    public ResponseEntity<ResponseDto> getBooksByAuthor(@PathVariable String bookAuthor) throws EntityNotFoundException {
            List<Book> booksByAuthor = bookService.getBooksByAuthor(bookAuthor);
                return new ResponseEntity<>(
                        new ResponseDto(
                                new HeaderDto(SUCCESS, HttpStatus.OK.value(), "Books by author "+bookAuthor+" founded."),
                                new BodyResponseDto(booksByAuthor)
                        ), HttpStatus.OK);
    }
    @GetMapping(path = "/genre/{bookGenre}", produces = "application/json")
    @Override
    public ResponseEntity<ResponseDto> getBooksByGenre(@PathVariable String bookGenre) throws EntityNotFoundException {
            List<Book> booksByGenre = bookService.getBooksByGenre(bookGenre);
                return new ResponseEntity<>(
                        new ResponseDto(
                                new HeaderDto(SUCCESS, HttpStatus.OK.value(), "Books by genre "+bookGenre+" founded"),
                                new BodyResponseDto(booksByGenre)
                        ), HttpStatus.OK
                );
        }
    @GetMapping(path = "/isbn/{isbn}")
    @Override
    public ResponseEntity<ResponseDto> getBookByISBN(@PathVariable String isbn) throws BadRequestException {
            Book bookByISBN = bookService.getBookByISBN(isbn);
                return new ResponseEntity<>(
                        new ResponseDto(
                                new HeaderDto(SUCCESS, HttpStatus.OK.value(), "Book with ISBN:"+isbn+", founded"),
                                new BodyResponseDto(bookByISBN)
                        ), HttpStatus.OK
                );
            }
    @PostMapping(produces = "application/json")
    @Override
    public ResponseEntity<ResponseDto> createBook(@Valid @RequestBody Book book) throws DuplicateKeyException {
            Book bookCreated = bookService.createBook(book);
                return new ResponseEntity<>(
                        new ResponseDto(
                                new HeaderDto(SUCCESS, HttpStatus.CREATED.value(), "Book created successfully"),
                                new BodyResponseDto(bookCreated)
                        ), HttpStatus.CREATED
                );
        }
    @PutMapping(path = "/{isbn}", produces = "application/json")
    @Override
    public ResponseEntity<ResponseDto> updateBook(@PathVariable String isbn, @RequestBody Book bookToUpdate) throws EntityNotFoundException {
        Book bookUpdated = bookService.updateBook(isbn, bookToUpdate);
            return new ResponseEntity<>(
                    new ResponseDto(
                            new HeaderDto(SUCCESS, HttpStatus.OK.value(), "Book updated successfully"),
                            new BodyResponseDto(bookUpdated)
                    ), HttpStatus.OK
            );
        }
    @DeleteMapping(path = "/{isbn}", produces = "application/json")
    @Override
    public ResponseEntity<ResponseDto> deleteBookByISBN(@PathVariable String isbn) throws EntityNotFoundException, BadRequestException {
            bookService.deleteBookByISBN(isbn);
                return new ResponseEntity<>(new ResponseDto(
                        new HeaderDto(SUCCESS, HttpStatus.OK.value(), "Book deleted successfully"),
                        null
                ), HttpStatus.OK
                );
    }
}
