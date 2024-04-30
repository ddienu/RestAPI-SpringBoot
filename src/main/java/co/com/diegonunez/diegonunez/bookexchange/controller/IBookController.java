package co.com.diegonunez.diegonunez.bookexchange.controller;

import co.com.diegonunez.diegonunez.bookexchange.entity.Book;
import co.com.diegonunez.diegonunez.bookexchange.service.dto.ResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IBookController {

    ResponseEntity<ResponseDto> getAllBooks() throws EntityNotFoundException;
    ResponseEntity<List<Book>> findBooksByName(@PathVariable String bookName) throws Exception;
    ResponseEntity<List<Book>> getBooksByAuthor(@PathVariable String bookAuthor) throws Exception;
    ResponseEntity<List<Book>> getBooksByGenre(@PathVariable String bookGenre) throws Exception;
    ResponseEntity<Book> getBookByISBN(@PathVariable String isbn) throws Exception;
    ResponseEntity<Book> createBook(@RequestBody Book book) throws Exception;
    ResponseEntity<Book> updateBook(@PathVariable String isbn, @RequestBody Book bookToUpdate) throws Exception;
    ResponseEntity<String> deleteBookByISBN(@PathVariable String isbn) throws Exception;

}
