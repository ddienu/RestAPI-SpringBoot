package co.com.diegonunez.diegonunez.bookexchange.service.impl;

import co.com.diegonunez.diegonunez.bookexchange.entity.Book;
import co.com.diegonunez.diegonunez.bookexchange.repository.IBookRepository;
import co.com.diegonunez.diegonunez.bookexchange.service.IBookService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookServiceImpl implements IBookService {

    private final IBookRepository bookRepository;
    @Autowired
    public BookServiceImpl(IBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    @Override
    public List<Book> getAllBooks() throws EntityNotFoundException {
            List<Book> bookList = bookRepository.findAll();
            if( bookList.isEmpty()){
                throw new EntityNotFoundException("No books founded");
            }
                return bookList;
    }
    @Override
    public Book findBooksByName(String bookName) throws EntityNotFoundException {
            Book bookByName = bookRepository.findBookByBookName(bookName);
            if( bookByName == null){
                throw new EntityNotFoundException("No book with name "+bookName+" founded");
            }

            return bookByName;
    }
    @Override
    public Book getBookByISBN(String isbn) throws EntityNotFoundException, BadRequestException {
            validateIsbn(isbn);
            Book bookByIsbn = bookRepository.getBookByBookISBN(isbn);
            if( bookByIsbn == null){
                throw new EntityNotFoundException("Book with ISBN:"+isbn+", not founded");
            }
            return bookByIsbn;
    }
    @Override
    public List<Book> getBooksByAuthor(String bookAuthor) throws EntityNotFoundException {

            List<Book> booksByAuthor = bookRepository.findBooksByBookAuthor(bookAuthor);
            if( booksByAuthor.isEmpty()){
                throw new EntityNotFoundException("No books by author "+bookAuthor+" founded");
            }
        return booksByAuthor;
    }
    @Override
    public List<Book> getBooksByGenre(String bookGenre) throws EntityNotFoundException {
            List<Book> booksByGenre = bookRepository.getBooksByBookGenre(bookGenre);
            if( booksByGenre.isEmpty()){
                throw new EntityNotFoundException("Books by genre "+bookGenre+" not founded");
            }
            return booksByGenre;
    }
    @Override
    public Book createBook(Book book) throws DuplicateKeyException {
            Book bookFound = bookRepository.getBookByBookISBN(book.getBookISBN());
            if( bookFound == null ){
                return bookRepository.save(book);
            }
                throw new DuplicateKeyException("The ISBN already exist");
    }
    @Override
    public Book updateBook(String isbn, Book bookToUpdate) throws EntityNotFoundException {
            Book bookExist = bookRepository.getBookByBookISBN(isbn);
            if(bookExist != null){
                if( !bookExist.getBookAuthor().equalsIgnoreCase(bookToUpdate.getBookAuthor()) && bookToUpdate.getBookAuthor() != null){
                    bookExist.setBookAuthor(bookToUpdate.getBookAuthor());
                }
                if( !bookExist.getBookCondition().equalsIgnoreCase(bookToUpdate.getBookCondition()) && bookToUpdate.getBookCondition() != null){
                    bookExist.setBookCondition(bookToUpdate.getBookCondition());
                }
                if( !bookExist.getBookDescription().equalsIgnoreCase(bookToUpdate.getBookDescription()) && bookToUpdate.getBookDescription() != null){
                    bookExist.setBookDescription(bookToUpdate.getBookDescription());
                }
                if( !bookExist.getBookGenre().equalsIgnoreCase(bookToUpdate.getBookGenre()) && bookToUpdate.getBookGenre() != null){
                    bookExist.setBookGenre(bookToUpdate.getBookGenre());
                }
                if( !bookExist.getBookImage().equalsIgnoreCase(bookToUpdate.getBookImage()) && bookToUpdate.getBookImage() != null){
                    bookExist.setBookImage(bookToUpdate.getBookImage());
                }
                if( !bookExist.getBookISBN().equalsIgnoreCase(bookToUpdate.getBookISBN()) && bookToUpdate.getBookISBN() != null){
                    bookExist.setBookISBN(bookToUpdate.getBookISBN());
                }
                if( !bookExist.getBookName().equalsIgnoreCase(bookToUpdate.getBookName()) && bookToUpdate.getBookName() != null){
                    bookExist.setBookName(bookToUpdate.getBookName());
                }
                if( !bookExist.getIsAvailable().equals(bookToUpdate.getIsAvailable()) && bookToUpdate.getIsAvailable() != null){
                    bookExist.setIsAvailable(bookToUpdate.getIsAvailable());
                }
                if( !bookExist.getBookAuthor().equalsIgnoreCase(bookToUpdate.getBookAuthor()) && bookToUpdate.getBookAuthor() != null){
                    bookExist.setBookAuthor(bookToUpdate.getBookAuthor());
                }
                if( !bookExist.getBookLanguage().equalsIgnoreCase(bookToUpdate.getBookLanguage()) && bookToUpdate.getBookLanguage() != null){
                    bookExist.setBookLanguage(bookToUpdate.getBookLanguage());
                }
                if( !bookExist.getUserId().equalsIgnoreCase(bookToUpdate.getUserId()) && bookToUpdate.getUserId() != null){
                    bookExist.setUserId(bookToUpdate.getUserId());
                }
                if( !bookExist.getBookRelease().equalsIgnoreCase(bookToUpdate.getBookRelease()) && bookToUpdate.getBookRelease() != null){
                    bookExist.setBookRelease(bookToUpdate.getBookRelease());
                }
                if( !bookExist.getBookPublisher().equalsIgnoreCase(bookToUpdate.getBookPublisher()) && bookToUpdate.getBookPublisher() != null) {
                    bookExist.setBookPublisher(bookToUpdate.getBookPublisher());
                }
                return bookRepository.save(bookExist);
            }
            throw new EntityNotFoundException("No book founded to update with ISBN " + isbn);
    }
    @Override
    public void deleteBookByISBN(String isbn) throws EntityNotFoundException, BadRequestException {
           validateIsbn(isbn);
           Book book = bookRepository.getBookByBookISBN(isbn);
           if( book == null ){
               throw new EntityNotFoundException("No book found");
           }
           bookRepository.deleteBookByBookISBN(isbn);
    }
    public void validateIsbn(String isbn) throws BadRequestException {
        if( isbn.length() != 13 && isbn.length() != 10 ){
            throw new BadRequestException("The ISBN must contain 10 or 13 numbers");
        }
    }
}
