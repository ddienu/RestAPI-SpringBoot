package co.com.diegonunez.diegonunez.bookexchange.controller.Impl;

import co.com.diegonunez.diegonunez.bookexchange.controller.IBookController;
import co.com.diegonunez.diegonunez.bookexchange.entity.Book;
import co.com.diegonunez.diegonunez.bookexchange.service.impl.BookServiceImpl;
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

    @Autowired
    private BookServiceImpl bookService;


    @Override
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() throws Exception {
        try{
            return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping(path = "/name/{bookName}", produces = "application/json")
    @Override
    public ResponseEntity<List<Book>> findBooksByName(@PathVariable String bookName) throws Exception {
        try{
            return new ResponseEntity<>(bookService.findBooksByName(bookName), HttpStatus.OK);
        }catch(Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping(path = "/author/{bookAuthor}", produces = "application/json")
    @Override
    public ResponseEntity<List<Book>> getBooksByAuthor(@PathVariable String bookAuthor) throws Exception {
        try{
            return new ResponseEntity<>(bookService.getBooksByAuthor(bookAuthor), HttpStatus.OK);
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping(path = "/genre/{bookGenre}", produces = "application/json")
    @Override
    public ResponseEntity<List<Book>> getBooksByGenre(@PathVariable String bookGenre) throws Exception {
        try{
            return new ResponseEntity<>(bookService.getBooksByGenre(bookGenre), HttpStatus.OK);
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @GetMapping(path = "/isbn/{ISBN}")
    @Override
    public ResponseEntity<Book> getBookByISBN(@PathVariable String ISBN) throws Exception{
        try{
            return new ResponseEntity<>(bookService.getBookByISBN(ISBN), HttpStatus.OK);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @PostMapping(produces = "application/json")
    @Override
    public ResponseEntity<Book> createBook(@RequestBody Book book) throws Exception {
        try{
            return new ResponseEntity<>(bookService.createBook(book), HttpStatus.OK);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PutMapping(path = "/{ISBN}", produces = "application/json")
    @Override
    public ResponseEntity<Book> updateBook(@PathVariable String ISBN, @RequestBody Book bookToUpdate) throws Exception {
        try{
            return new ResponseEntity<>(bookService.updateBook(ISBN, bookToUpdate), HttpStatus.OK);
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @DeleteMapping(path = "/{ISBN}", produces = "application/json")
    @Override
    public ResponseEntity<String> deleteBookByISBN(@PathVariable String ISBN) throws Exception {
        try{
            return new ResponseEntity<>(bookService.deleteBookByISBN(ISBN), HttpStatus.OK);
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }

    }
}
