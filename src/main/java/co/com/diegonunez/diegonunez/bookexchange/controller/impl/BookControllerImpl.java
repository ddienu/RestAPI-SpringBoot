package co.com.diegonunez.diegonunez.bookexchange.controller.impl;

import co.com.diegonunez.diegonunez.bookexchange.controller.IBookController;
import co.com.diegonunez.diegonunez.bookexchange.entity.Book;
import co.com.diegonunez.diegonunez.bookexchange.service.dto.BodyResponseDto;
import co.com.diegonunez.diegonunez.bookexchange.service.dto.HeaderDto;
import co.com.diegonunez.diegonunez.bookexchange.service.dto.ResponseDto;
import co.com.diegonunez.diegonunez.bookexchange.service.impl.BookServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

            BodyResponseDto bodyResponseDto = new BodyResponseDto();
            bodyResponseDto.setBooks(books);

            HeaderDto headerDto = new HeaderDto("Success", HttpStatus.OK.value(), "Books founded");

            ResponseDto responseDto = new ResponseDto(headerDto, bodyResponseDto);

            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            BodyResponseDto bodyResponseDto = new BodyResponseDto();
            bodyResponseDto.setBooks(null);

            HeaderDto headerDto = new HeaderDto("Failed", HttpStatus.NOT_FOUND.value(), "Error listing books");

            ResponseDto responseDto = new ResponseDto(headerDto, bodyResponseDto);
            return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/name/{bookName}", produces = "application/json")
    @Override
    public ResponseEntity<List<Book>> findBooksByName(@PathVariable String bookName) throws Exception {
        try {
            return new ResponseEntity<>(bookService.findBooksByName(bookName), HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping(path = "/author/{bookAuthor}", produces = "application/json")
    @Override
    public ResponseEntity<List<Book>> getBooksByAuthor(@PathVariable String bookAuthor) throws Exception {
        try {
            return new ResponseEntity<>(bookService.getBooksByAuthor(bookAuthor), HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
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
    public ResponseEntity<Book> createBook(@RequestBody Book book) throws Exception {
        try {
            return new ResponseEntity<>(bookService.createBook(book), HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
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
