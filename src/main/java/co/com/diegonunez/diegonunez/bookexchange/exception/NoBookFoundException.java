package co.com.diegonunez.diegonunez.bookexchange.exception;

public class NoBookFoundException extends RuntimeException{
    public NoBookFoundException(String message){
        super(message);
    }
}
