package co.com.diegonunez.diegonunez.bookexchange.service.impl;

import co.com.diegonunez.diegonunez.bookexchange.entity.Book;
import co.com.diegonunez.diegonunez.bookexchange.exception.DuplicateISBNException;
import co.com.diegonunez.diegonunez.bookexchange.exception.InvalidISBNException;
import co.com.diegonunez.diegonunez.bookexchange.exception.NoBookFoundException;
import co.com.diegonunez.diegonunez.bookexchange.repository.IBookRepository;
import co.com.diegonunez.diegonunez.bookexchange.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Book> getAllBooks() throws NoBookFoundException {
            List<Book> bookList = bookRepository.findAll();
            if( bookList.isEmpty()){
                throw new NoBookFoundException("No books founded");
            }
                return bookList;
    }
    @Override
    public Book findBooksByName(String bookName) throws NoBookFoundException {
            Book bookByName = bookRepository.findBookByBookName(bookName);
            if( bookByName == null){
                throw new NoBookFoundException("No book with name "+bookName+" founded");
            }

            return bookByName;
    }
    @Override
    public Book getBookByISBN(String isbn) throws NoBookFoundException, InvalidISBNException {
            validateIsbn(isbn);
            Book bookByIsbn = bookRepository.getBookByBookISBN(isbn);
            if( bookByIsbn == null){
                throw new NoBookFoundException("Book with ISBN:"+isbn+", not founded");
            }
            return bookByIsbn;
    }
    @Override
    public List<Book> getBooksByAuthor(String bookAuthor) throws NoBookFoundException {

            List<Book> booksByAuthor = bookRepository.findBooksByBookAuthor(bookAuthor);
            if( booksByAuthor.isEmpty()){
                throw new NoBookFoundException("No books by author "+bookAuthor+" founded");
            }
        return booksByAuthor;
    }
    @Override
    public List<Book> getBooksByGenre(String bookGenre) throws NoBookFoundException {
            List<Book> booksByGenre = bookRepository.getBooksByBookGenre(bookGenre);
            if( booksByGenre.isEmpty()){
                throw new NoBookFoundException("Books by genre "+bookGenre+" not founded");
            }
            return booksByGenre;
    }
    @Override
    public Book createBook(Book book) throws DuplicateISBNException {
            Book bookFound = bookRepository.getBookByBookISBN(book.getBookISBN());
            if( bookFound == null ){
                return bookRepository.save(book);
            }
                throw new DuplicateISBNException("The ISBN already exist");
    }
    @Override
    public Book updateBook(String isbn, Book bookToUpdate) throws NoBookFoundException {
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
            throw new NoBookFoundException("No book founded to update with ISBN " + isbn);
    }
    @Override
    public void deleteBookByISBN(String isbn) throws NoBookFoundException, InvalidISBNException {
           validateIsbn(isbn);
           Book book = bookRepository.getBookByBookISBN(isbn);
           if( book == null ){
               throw new NoBookFoundException("No book found");
           }
           bookRepository.deleteBookByBookISBN(isbn);
    }
    @Override
    public void validateIsbn(String isbn) throws InvalidISBNException {
        if( isbn.length() != 13 && isbn.length() != 10 ){
            throw new InvalidISBNException("The ISBN must contain 10 or 13 numbers");
        }
    }
}
