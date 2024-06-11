package co.com.diegonunez.diegonunez.bookexchange.controller;

import co.com.diegonunez.diegonunez.bookexchange.entity.Book;
import co.com.diegonunez.diegonunez.bookexchange.dto.ResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


public interface IBookController {

    ResponseEntity<ResponseDto> getAllBooks() throws EntityNotFoundException;
    ResponseEntity<ResponseDto> findBooksByName(@PathVariable String bookName) throws EntityNotFoundException;
    ResponseEntity<ResponseDto> getBooksByAuthor(@PathVariable String bookAuthor) throws EntityNotFoundException;
    ResponseEntity<ResponseDto> getBooksByGenre(@PathVariable String bookGenre) throws EntityNotFoundException;
    ResponseEntity<ResponseDto> getBookByISBN(@PathVariable String isbn) throws BadRequestException;
    ResponseEntity<ResponseDto> createBook(@RequestBody Book book) throws DuplicateKeyException;
    ResponseEntity<ResponseDto> updateBook(@PathVariable String isbn, @RequestBody Book bookToUpdate) throws EntityNotFoundException;
    ResponseEntity<ResponseDto> deleteBookByISBN(@PathVariable String isbn) throws EntityNotFoundException, BadRequestException;

}
