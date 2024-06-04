package co.com.diegonunez.diegonunez.bookexchange.controller;

import co.com.diegonunez.diegonunez.bookexchange.entity.Book;
import co.com.diegonunez.diegonunez.bookexchange.dto.ResponseDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.transaction.TransactionalException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


public interface IBookController {

    ResponseEntity<ResponseDto> getAllBooks() throws EntityNotFoundException;
    ResponseEntity<ResponseDto> findBooksByName(@PathVariable String bookName) throws EntityNotFoundException;
    ResponseEntity<ResponseDto> getBooksByAuthor(@PathVariable String bookAuthor) throws EntityNotFoundException;
    ResponseEntity<ResponseDto> getBooksByGenre(@PathVariable String bookGenre) throws EntityNotFoundException;
    ResponseEntity<ResponseDto> getBookByISBN(@PathVariable String isbn) throws BadRequestException;
    ResponseEntity<ResponseDto> createBook(@RequestBody Book book) throws UnsupportedOperationException;
    ResponseEntity<ResponseDto> updateBook(@PathVariable String isbn, @RequestBody Book bookToUpdate) throws TransactionalException;
    ResponseEntity<ResponseDto> deleteBookByISBN(@PathVariable String isbn) throws NonUniqueResultException;

}
