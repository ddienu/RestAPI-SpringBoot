package co.com.diegonunez.diegonunez.bookexchange.exception;

public class InvalidISBNException extends RuntimeException{

    public InvalidISBNException(String message){
        super(message);
    }
}
