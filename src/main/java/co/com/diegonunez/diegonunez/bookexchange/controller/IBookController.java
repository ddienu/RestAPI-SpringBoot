package co.com.diegonunez.diegonunez.bookexchange.controller;

import co.com.diegonunez.diegonunez.bookexchange.entity.Book;
import co.com.diegonunez.diegonunez.bookexchange.service.dto.ResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IBookController {

    ResponseEntity<ResponseDto> getAllBooks() throws EntityNotFoundException;
    ResponseEntity<ResponseDto> findBooksByName(@PathVariable String bookName) throws EntityNotFoundException;
    ResponseEntity<ResponseDto> getBooksByAuthor(@PathVariable String bookAuthor) throws EntityNotFoundException;
    ResponseEntity<List<Book>> getBooksByGenre(@PathVariable String bookGenre) throws Exception;
    ResponseEntity<Book> getBookByISBN(@PathVariable String isbn) throws Exception;
    ResponseEntity<ResponseDto> createBook(@RequestBody Book book) throws UnsupportedOperationException;
    ResponseEntity<Book> updateBook(@PathVariable String isbn, @RequestBody Book bookToUpdate) throws Exception;
    ResponseEntity<String> deleteBookByISBN(@PathVariable String isbn) throws Exception;

}
