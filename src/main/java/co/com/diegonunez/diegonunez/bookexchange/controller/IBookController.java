package co.com.diegonunez.diegonunez.bookexchange.controller;

import co.com.diegonunez.diegonunez.bookexchange.entity.Book;
import co.com.diegonunez.diegonunez.bookexchange.dto.ResponseDto;
import co.com.diegonunez.diegonunez.bookexchange.exception.DuplicateISBNException;
import co.com.diegonunez.diegonunez.bookexchange.exception.InvalidISBNException;
import co.com.diegonunez.diegonunez.bookexchange.exception.NoBookFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


public interface IBookController {

    ResponseEntity<ResponseDto> getAllBooks() throws NoBookFoundException;
    ResponseEntity<ResponseDto> findBooksByName(@PathVariable String bookName) throws NoBookFoundException;
    ResponseEntity<ResponseDto> getBooksByAuthor(@PathVariable String bookAuthor) throws NoBookFoundException;
    ResponseEntity<ResponseDto> getBooksByGenre(@PathVariable String bookGenre) throws NoBookFoundException;
    ResponseEntity<ResponseDto> getBookByISBN(@PathVariable String isbn) throws NoBookFoundException, InvalidISBNException;
    ResponseEntity<ResponseDto> createBook(@RequestBody Book book) throws DuplicateISBNException;
    ResponseEntity<ResponseDto> updateBook(@PathVariable String isbn, @RequestBody Book bookToUpdate) throws NoBookFoundException;
    ResponseEntity<ResponseDto> deleteBookByISBN(@PathVariable String isbn) throws NoBookFoundException, InvalidISBNException;

}
