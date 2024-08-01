package co.com.diegonunez.diegonunez.bookexchange.exception;

public class BookLoanedException extends RuntimeException{

    public BookLoanedException(String message){
        super(message);
    }
}
