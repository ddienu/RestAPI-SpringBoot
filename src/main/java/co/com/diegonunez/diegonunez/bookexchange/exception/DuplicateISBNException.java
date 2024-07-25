package co.com.diegonunez.diegonunez.bookexchange.exception;

public class DuplicateISBNException extends RuntimeException{

    public DuplicateISBNException(String message){
        super(message);
    }
}
