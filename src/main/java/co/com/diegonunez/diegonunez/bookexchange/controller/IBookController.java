package co.com.diegonunez.diegonunez.bookexchange.controller;

import co.com.diegonunez.diegonunez.bookexchange.entity.Book;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface IBookController {

    ResponseEntity<List<Book>> getAllBooks() throws Exception;
    ResponseEntity<List<Book>> findBooksByName(@PathVariable String bookName) throws Exception;
    ResponseEntity<List<Book>> getBooksByAuthor(@PathVariable String bookAuthor) throws Exception;
    ResponseEntity<List<Book>> getBooksByGenre(@PathVariable String bookGenre) throws Exception;
    ResponseEntity<Book> getBookByISBN(@PathVariable String ISBN) throws Exception;
    ResponseEntity<Book> createBook(@RequestBody Book book) throws Exception;
    ResponseEntity<Book> updateBook(@PathVariable String ISBN, @RequestBody Book bookToUpdate) throws Exception;
    ResponseEntity<String> deleteBookByISBN(@PathVariable String ISBN) throws Exception;

}
