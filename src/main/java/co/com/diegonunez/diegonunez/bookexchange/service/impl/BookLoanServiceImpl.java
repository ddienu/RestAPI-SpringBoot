package co.com.diegonunez.diegonunez.bookexchange.service.impl;

import co.com.diegonunez.diegonunez.bookexchange.entity.Book;
import co.com.diegonunez.diegonunez.bookexchange.exception.BookLoanedException;
import co.com.diegonunez.diegonunez.bookexchange.exception.NoBookFoundException;
import co.com.diegonunez.diegonunez.bookexchange.repository.IBookRepository;
import co.com.diegonunez.diegonunez.bookexchange.service.IBookLoanService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookLoanServiceImpl implements IBookLoanService {

    private final IBookRepository bookRepository;

    @Autowired
    public BookLoanServiceImpl(IBookRepository bookRepository){
        this.bookRepository = bookRepository;
    }
    @Override
    @Transactional
    public void requestBook(Integer userId, String bookISBN) {
        //Variable to store the requested book by user
        Book bookToLoan = bookRepository.getBookByBookISBN(bookISBN);
        //Conditional to know if the book exist in the database if it is null it throws a custom exception.
        if( bookToLoan == null ){
            //throws a NoBookFoundException with a custom message.
            throw new NoBookFoundException("No book found by ISBN " + bookISBN);
        }
        //Conditional to know if the book requested is in loan already.
        if(Boolean.FALSE.equals(bookToLoan.getIsAvailable())){
            //If the book is in loan throws a BookLoanedException
            throw new BookLoanedException("The book requested is already loaned");
        }
        //If the book exist and the book in not on loan, it turns available to false.
        bookToLoan.setIsAvailable(false);
        //Set the userId into currentUserId to identify the id of the user that has the book on loan.
        bookToLoan.setCurrentUserId(userId);
        bookRepository.save(bookToLoan);
    }

    @Override
    @Transactional
    public void returnBook(Integer userId, String bookISBN) {

        //Variable to store the requested book by user
        Book bookToReturn = bookRepository.getBookByBookISBN(bookISBN);
        //Conditional to know if the book exist in the database if it is null it throws a custom exception.
        if( bookToReturn == null ){
            //throws a NoBookFoundException with a custom message.
            throw new NoBookFoundException("No book found by ISBN " + bookISBN);
        }
        //Conditional to know if the book requested is in loan already.
        if(Boolean.TRUE.equals(bookToReturn.getIsAvailable())){
            //If the book is in loan throws a BookLoanedException
            throw new BookLoanedException("The book requested is not on loan");
        }
        //Conditional to know if the user that wants to return a book is the same that register the loan.
        if( !bookToReturn.getCurrentUserId().equals(userId) ){
            //If the condition is false then the application throws an exception with a custom message.
            throw new BookLoanedException("This user did not borrow the book ");
        }
        //Set the book status to true.
        bookToReturn.setIsAvailable(true);
        //set the current user to null.
        bookToReturn.setCurrentUserId(null);
        //save the book in the database with updated parameters.
        bookRepository.save(bookToReturn);
    }
}
