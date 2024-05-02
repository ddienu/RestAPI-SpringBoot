package co.com.diegonunez.diegonunez.bookexchange.service.impl;

import co.com.diegonunez.diegonunez.bookexchange.entity.Book;
import co.com.diegonunez.diegonunez.bookexchange.repository.IBookRepository;
import co.com.diegonunez.diegonunez.bookexchange.service.IBookService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements IBookService {

    private final IBookRepository bookRepository;

    @Autowired
    public BookServiceImpl(IBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() throws EntityNotFoundException {
        try{
            return bookRepository.findAll();
        }catch(Exception e){
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Override
    public Book findBooksByName(String bookName) throws EntityNotFoundException {
        try{
            return bookRepository.findBookByBookName(bookName);
        }catch(Exception e){
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Book getBookByISBN(String isbn) throws Exception {
        try{
            return bookRepository.getBookByBookISBN(isbn);
        }catch(Exception e){
            throw new EntityNotFoundException(e.getMessage());
        }
    }
    @Override
    public List<Book> getBooksByAuthor(String bookAuthor) throws EntityNotFoundException {
        try{
            return bookRepository.findBooksByBookAuthor(bookAuthor);
        }catch(EntityNotFoundException e){
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<Book> getBooksByGenre(String bookGenre) throws Exception {
        try{
            return bookRepository.getBooksByBookGenre(bookGenre);
        }catch(Exception e){
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Override
    public Book createBook(Book book) throws UnsupportedOperationException {
        try{
            return bookRepository.save(book);
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Book updateBook(String isbn, Book bookToUpdate) throws Exception {
        try{
            Optional<Book> bookIsPresent = Optional.ofNullable(bookRepository.getBookByBookISBN(isbn));
            if( bookIsPresent.isPresent() ){
                Book bookOriginalInfo = bookIsPresent.get();
                if( !bookOriginalInfo.getBookAuthor().equalsIgnoreCase(bookToUpdate.getBookAuthor()) && bookToUpdate.getBookAuthor() != null){
                    bookOriginalInfo.setBookAuthor(bookToUpdate.getBookAuthor());
                }
                if( !bookOriginalInfo.getBookCondition().equalsIgnoreCase(bookToUpdate.getBookCondition()) && bookToUpdate.getBookCondition() != null){
                    bookOriginalInfo.setBookCondition(bookToUpdate.getBookCondition());
                }
                if( !bookOriginalInfo.getBookDescription().equalsIgnoreCase(bookToUpdate.getBookDescription()) && bookToUpdate.getBookDescription() != null){
                    bookOriginalInfo.setBookDescription(bookToUpdate.getBookDescription());
                }
                if( !bookOriginalInfo.getBookGenre().equalsIgnoreCase(bookToUpdate.getBookGenre()) && bookToUpdate.getBookGenre() != null){
                    bookOriginalInfo.setBookGenre(bookToUpdate.getBookGenre());
                }
                if( !bookOriginalInfo.getBookImage().equalsIgnoreCase(bookToUpdate.getBookImage()) && bookToUpdate.getBookImage() != null){
                    bookOriginalInfo.setBookImage(bookToUpdate.getBookImage());
                }
                if( !bookOriginalInfo.getBookISBN().equalsIgnoreCase(bookToUpdate.getBookISBN()) && bookToUpdate.getBookISBN() != null){
                    bookOriginalInfo.setBookISBN(bookToUpdate.getBookISBN());
                }
                if( !bookOriginalInfo.getBookName().equalsIgnoreCase(bookToUpdate.getBookName()) && bookToUpdate.getBookName() != null){
                    bookOriginalInfo.setBookName(bookToUpdate.getBookName());
                }
                if( !bookOriginalInfo.getIsAvailable().equals(bookToUpdate.getIsAvailable()) && bookToUpdate.getIsAvailable() != null){
                    bookOriginalInfo.setIsAvailable(bookToUpdate.getIsAvailable());
                }
                if( !bookOriginalInfo.getBookAuthor().equalsIgnoreCase(bookToUpdate.getBookAuthor()) && bookToUpdate.getBookAuthor() != null){
                    bookOriginalInfo.setBookAuthor(bookToUpdate.getBookAuthor());
                }
                if( !bookOriginalInfo.getBookLanguage().equalsIgnoreCase(bookToUpdate.getBookLanguage()) && bookToUpdate.getBookLanguage() != null){
                    bookOriginalInfo.setBookLanguage(bookToUpdate.getBookLanguage());
                }
                if( !bookOriginalInfo.getUserId().equalsIgnoreCase(bookToUpdate.getUserId()) && bookToUpdate.getUserId() != null){
                    bookOriginalInfo.setUserId(bookToUpdate.getUserId());
                }
                if( !bookOriginalInfo.getBookRelease().equalsIgnoreCase(bookToUpdate.getBookRelease()) && bookToUpdate.getBookRelease() != null){
                    bookOriginalInfo.setBookRelease(bookToUpdate.getBookRelease());
                }
                if( !bookOriginalInfo.getBookPublisher().equalsIgnoreCase(bookToUpdate.getBookPublisher()) && bookToUpdate.getBookPublisher() != null) {
                    bookOriginalInfo.setBookPublisher(bookToUpdate.getBookPublisher());
                }
                bookRepository.save(bookOriginalInfo);
                return bookOriginalInfo;
            }else{
                throw new EntityNotFoundException();
            }
        }catch(Exception e){
            throw new BadRequestException();
        }
    }
    @Override
    public String deleteBookByISBN(String isbn) throws EntityNotFoundException {
        try{
           Book book = bookRepository.getBookByBookISBN(isbn);
           if(book != null ){
               bookRepository.deleteBookByBookISBN(isbn);
               return "{"
                       + "\"status\" : \"success\"" +
                       ", \"message\" : \"El libro ha sido eliminado satisfactoriamente\"" +
                       "}";
           }else{
               throw new EntityNotFoundException("El ISBN no se encuentra registrado en el sistema");
           }
        }catch(EntityNotFoundException e){
            throw new EntityNotFoundException(e.getMessage());
        }
    }
}
