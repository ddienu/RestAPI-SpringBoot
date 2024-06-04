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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static co.com.diegonunez.diegonunez.bookexchange.util.Constants.SUCCESS;
import static co.com.diegonunez.diegonunez.bookexchange.util.Constants.ERROR;

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
        try {
            List<Book> books = bookService.getAllBooks();
            return new ResponseEntity<>(
                    new ResponseDto(
                            new HeaderDto(SUCCESS, HttpStatus.OK.value(), "Books founded"),
                            new BodyResponseDto(books)
                    ), HttpStatus.OK
            );
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseDto(
                    new HeaderDto(ERROR, HttpStatus.NO_CONTENT.value(), "No books founded"),
                    null),
                    HttpStatus.OK
            );
        }
    }

    @GetMapping(path = "/name/{bookName}", produces = "application/json")
    @Override
    public ResponseEntity<ResponseDto> findBooksByName(@PathVariable String bookName) throws EntityNotFoundException {
        try {
            Book book = bookService.findBooksByName(bookName);
            if( book != null ){
                return new ResponseEntity<>(
                        new ResponseDto(
                                new HeaderDto(SUCCESS, HttpStatus.OK.value(), "Book with name "+bookName+" founded"),
                                new BodyResponseDto(book)
                        ), HttpStatus.OK);
            }

            return new ResponseEntity<>(
                    new ResponseDto(
                            new HeaderDto(SUCCESS, HttpStatus.NO_CONTENT.value(), "No book with name "+bookName+" founded"),
                            null
                    ), HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(
                    new ResponseDto(
                    new HeaderDto(ERROR, HttpStatus.BAD_REQUEST.value(), "Error finding book"),
                    new BodyResponseDto()), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(path = "/author/{bookAuthor}", produces = "application/json")
    @Override
    public ResponseEntity<ResponseDto> getBooksByAuthor(@PathVariable String bookAuthor) throws EntityNotFoundException {
        try {
            List<Book> booksByAuthor = bookService.getBooksByAuthor(bookAuthor);
            if( !booksByAuthor.isEmpty()){
                return new ResponseEntity<>(
                        new ResponseDto(
                                new HeaderDto(SUCCESS, HttpStatus.OK.value(), "Books by author "+bookAuthor+" founded."),
                                new BodyResponseDto(booksByAuthor)
                        ), HttpStatus.OK);
            }

            return new ResponseEntity<>(
                    new ResponseDto(
                            new HeaderDto(SUCCESS, HttpStatus.NO_CONTENT.value(), "No books by author "+bookAuthor+" founded" ),
                            null
                    ), HttpStatus.OK
            );

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(
                    new ResponseDto(
                            new HeaderDto(ERROR, HttpStatus.BAD_REQUEST.value(), "Error finding book"),
                            new BodyResponseDto()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/genre/{bookGenre}", produces = "application/json")
    @Override
    public ResponseEntity<ResponseDto> getBooksByGenre(@PathVariable String bookGenre) throws EntityNotFoundException {
        try {
            List<Book> booksByGenre = bookService.getBooksByGenre(bookGenre);
            if( !booksByGenre.isEmpty()){
                return new ResponseEntity<>(
                        new ResponseDto(
                                new HeaderDto(SUCCESS, HttpStatus.OK.value(), "Books by genre "+bookGenre+" founded"),
                                new BodyResponseDto(booksByGenre)
                        ), HttpStatus.OK
                );
            }
            return new ResponseEntity<>(
                    new ResponseDto(
                            new HeaderDto(SUCCESS, HttpStatus.NO_CONTENT.value(), "Books by genre "+bookGenre+" not founded"),
                            null
                    ), HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ResponseDto(
                            new HeaderDto(ERROR, HttpStatus.BAD_REQUEST.value(), "Error finding book by genre"),
                            new BodyResponseDto()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/isbn/{isbn}")
    @Override
    public ResponseEntity<ResponseDto> getBookByISBN(@PathVariable String isbn) throws BadRequestException {
        try {
            Book bookByISBN = bookService.getBookByISBN(isbn);
            if( bookByISBN != null){
                return new ResponseEntity<>(
                        new ResponseDto(
                                new HeaderDto(SUCCESS, HttpStatus.OK.value(), "Book with ISBN:"+isbn+", founded"),
                                new BodyResponseDto(bookByISBN)
                        ), HttpStatus.OK
                );
            }
            return new ResponseEntity<>(
                    new ResponseDto(
                            new HeaderDto(SUCCESS, HttpStatus.NO_CONTENT.value(), "Book with ISBN:"+isbn+", not founded"),
                            null
                    ), HttpStatus.OK
            );

        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ResponseDto(
                            new HeaderDto(ERROR, HttpStatus.BAD_REQUEST.value(), "Error finding book by ISBN"),
                            new BodyResponseDto()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(produces = "application/json")
    @Override
    public ResponseEntity<ResponseDto> createBook(@Valid @RequestBody Book book) throws UnsupportedOperationException {
        try {
            Book bookCreated = bookService.createBook(book);
                return new ResponseEntity<>(
                        new ResponseDto(
                                new HeaderDto(SUCCESS, HttpStatus.CREATED.value(), "Book created successfully"),
                                new BodyResponseDto(bookCreated)
                        ), HttpStatus.CREATED
                );
            }catch (UnsupportedOperationException e) {
            return new ResponseEntity<>(
                    new ResponseDto(
                            new HeaderDto(ERROR, HttpStatus.BAD_REQUEST.value(), "The ISBN already exist"),
                            null), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/{isbn}", produces = "application/json")
    @Override
    public ResponseEntity<ResponseDto> updateBook(@PathVariable String isbn, @RequestBody Book bookToUpdate) throws TransactionalException {
        try {
            Book bookUpdated = bookService.updateBook(isbn, bookToUpdate);
            if( bookUpdated != null){
                return new ResponseEntity<>(
                        new ResponseDto(
                                new HeaderDto(SUCCESS, HttpStatus.OK.value(), "Book updated successfully"),
                                new BodyResponseDto(bookUpdated)
                        ), HttpStatus.OK
                );
            }

            return new ResponseEntity<>(
                    new ResponseDto(
                            new HeaderDto(SUCCESS, HttpStatus.NO_CONTENT.value(), "Failed to updated book"),
                            null
                    ), HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ResponseDto(
                            new HeaderDto(ERROR, HttpStatus.BAD_REQUEST.value(), "Error updating book"),
                            null), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/{isbn}", produces = "application/json")
    @Override
    public ResponseEntity<ResponseDto> deleteBookByISBN(@PathVariable String isbn) throws EntityNotFoundException {
        try {
            bookService.deleteBookByISBN(isbn);
                return new ResponseEntity<>(new ResponseDto(
                        new HeaderDto(SUCCESS, HttpStatus.OK.value(), "Book deleted successfully"),
                        null
                ), HttpStatus.OK
                );
        }catch(EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseDto(
                    new HeaderDto(ERROR, HttpStatus.NO_CONTENT.value(), "No book found"),
                    null
            ), HttpStatus.OK
            );
        }
    }
}
