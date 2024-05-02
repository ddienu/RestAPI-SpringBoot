package co.com.diegonunez.diegonunez.bookexchange.controller.impl;

import co.com.diegonunez.diegonunez.bookexchange.controller.IBookController;
import co.com.diegonunez.diegonunez.bookexchange.entity.Book;
import co.com.diegonunez.diegonunez.bookexchange.service.dto.BodyResponseDto;
import co.com.diegonunez.diegonunez.bookexchange.service.dto.HeaderDto;
import co.com.diegonunez.diegonunez.bookexchange.service.dto.ResponseDto;
import co.com.diegonunez.diegonunez.bookexchange.service.impl.BookServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<ResponseDto> getAllBooks() throws EntityNotFoundException {
        try {
            List<Book> books = bookService.getAllBooks();
            if(!books.isEmpty()) {
                ResponseDto responseDto = new ResponseDto(
                        new HeaderDto("Success", HttpStatus.OK.value(), "Books founded"),
                        new BodyResponseDto(books)
                );
                return new ResponseEntity<>(responseDto, HttpStatus.OK);
            }
            return new ResponseEntity<>(
                    new ResponseDto(
                            new HeaderDto("Success", HttpStatus.NO_CONTENT.value(), "No Books founded"),
                            new BodyResponseDto(new ArrayList<>())
                    ),
                    HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            ResponseDto responseDto = new ResponseDto(
                    new HeaderDto("Failed", HttpStatus.NOT_FOUND.value(), "Error listing books"),
                    new BodyResponseDto());
            return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
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
                                new HeaderDto("Success", HttpStatus.OK.value(), "Book finded"),
                                new BodyResponseDto(book)
                        ), HttpStatus.OK);
            }

            return new ResponseEntity<>(
                    new ResponseDto(
                            new HeaderDto("Success", HttpStatus.NO_CONTENT.value(), "No Book finded"),
                            new BodyResponseDto()
                    ), HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(
                    new ResponseDto(
                    new HeaderDto("Error", HttpStatus.BAD_REQUEST.value(), "Error finding book"),
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
                                new HeaderDto("Success", HttpStatus.OK.value(), "Books by author "+bookAuthor+" finded."),
                                new BodyResponseDto(booksByAuthor)
                        ), HttpStatus.OK);
            }

            return new ResponseEntity<>(
                    new ResponseDto(
                            new HeaderDto("Success", HttpStatus.NO_CONTENT.value(), "No books by author "+booksByAuthor+" finded" ),
                            new BodyResponseDto()
                    ), HttpStatus.OK
            );

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(
                    new ResponseDto(
                            new HeaderDto("Error", HttpStatus.BAD_REQUEST.value(), "Error finding book"),
                            new BodyResponseDto()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/genre/{bookGenre}", produces = "application/json")
    @Override
    public ResponseEntity<List<Book>> getBooksByGenre(@PathVariable String bookGenre) throws Exception {
        try {
            return new ResponseEntity<>(bookService.getBooksByGenre(bookGenre), HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping(path = "/isbn/{isbn}")
    @Override
    public ResponseEntity<Book> getBookByISBN(@PathVariable String isbn) throws Exception {
        try {
            return new ResponseEntity<>(bookService.getBookByISBN(isbn), HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @PostMapping(produces = "application/json")
    @Override
    public ResponseEntity<ResponseDto> createBook(@Valid @RequestBody Book book) throws UnsupportedOperationException {
        Book bookCreated = bookService.createBook(book);
        try {
                return new ResponseEntity<>(
                        new ResponseDto(
                                new HeaderDto("Success", HttpStatus.CREATED.value(), "Book created successfully"),
                                new BodyResponseDto(bookCreated)
                        ), HttpStatus.CREATED
                );
            }catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }

    @PutMapping(path = "/{isbn}", produces = "application/json")
    @Override
    public ResponseEntity<Book> updateBook(@PathVariable String isbn, @RequestBody Book bookToUpdate) throws Exception {
        try {
            return new ResponseEntity<>(bookService.updateBook(isbn, bookToUpdate), HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @DeleteMapping(path = "/{isbn}", produces = "application/json")
    @Override
    public ResponseEntity<String> deleteBookByISBN(@PathVariable String isbn) throws EntityNotFoundException {
        try {
            return new ResponseEntity<>(bookService.deleteBookByISBN(isbn), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }
}
