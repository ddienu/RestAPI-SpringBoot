package co.com.diegonunez.diegonunez.bookexchange.exception;

import co.com.diegonunez.diegonunez.bookexchange.dto.Data;
import co.com.diegonunez.diegonunez.bookexchange.dto.ResponseDto;
import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static co.com.diegonunez.diegonunez.bookexchange.util.Constants.ERROR;
import static co.com.diegonunez.diegonunez.bookexchange.util.Constants.SUCCESS;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto> handleValidationException(MethodArgumentNotValidException e) {

        List<String> errors = new ArrayList<>();
        for (ObjectError message : e.getAllErrors()) {
            errors.add(message.getDefaultMessage());
        }

        return new ResponseEntity<>(
                new ResponseDto(
                        new Data(errors.toString())
                ), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NoBookFoundException.class)
    public ResponseEntity<ResponseDto> noBookFoundExceptionHandler(NoBookFoundException e) {
        String message = e.getMessage();

        if (message.equalsIgnoreCase("No books founded")) {
            return new ResponseEntity<>(
                    new ResponseDto(
                            new Data(e.getMessage())
                            ), HttpStatus.NO_CONTENT
            );
        } else if (message.contains("No book with name")) {
            return new ResponseEntity<>(
                    new ResponseDto(
                            new Data(message)
                            ), HttpStatus.NO_CONTENT);
        } else if (message.contains("Book with ISBN")) {
            return new ResponseEntity<>(
                    new ResponseDto(
                            new Data(message)
                    ), HttpStatus.NO_CONTENT
            );
        }else if( message.contains("No books by author")){
            return new ResponseEntity<>(
                    new ResponseDto(
                            new Data( message )
                    ), HttpStatus.NO_CONTENT
            );
        }else if( message.contains("Books by genre")){
            return new ResponseEntity<>(
                    new ResponseDto(
                            new Data(message)
                    ), HttpStatus.NO_CONTENT
            );
        }else if( message.contains("No book found")){
            return new ResponseEntity<>(new ResponseDto(
                    new Data(message)
            ), HttpStatus.NO_CONTENT
            );
        }else if( message.contains("No book founded to update with ISBN")){
            return new ResponseEntity<>(new ResponseDto(
                    new Data(message)
            ), HttpStatus.NO_CONTENT
            );
        }
        return new ResponseEntity<>(
                new ResponseDto(
                        new Data(e.getMessage())
                        ), HttpStatus.NO_CONTENT
        );
    }

    @ExceptionHandler(InvalidISBNException.class)
    public ResponseEntity<ResponseDto> invalidISBNExceptionHandler(InvalidISBNException e){
        String message = e.getMessage();

        if( message.contains("The ISBN must contain 10 or 13 numbers")){
            return new ResponseEntity<>(
                    new ResponseDto(
                            new Data(message)
                            ), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(
                new ResponseDto(
                        new Data(e.getMessage())
                        ), HttpStatus.NO_CONTENT
        );
    }

    @ExceptionHandler(DuplicateISBNException.class)
    public ResponseEntity<ResponseDto> duplicateKeyExceptionHandler(DuplicateISBNException e){
        String message = e.getMessage();

        if(message.contains("The ISBN already exist")){
            return new ResponseEntity<>(
                    new ResponseDto(
                            new Data(message)), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(
                new ResponseDto(
                        new Data(e.getMessage())), HttpStatus.NO_CONTENT
        );
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDto> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e){
        String message = e.getMessage();
        String [] parts = message.split(":");
        return new ResponseEntity<>(
                new ResponseDto(
                        new Data(parts[0])), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseDto> authenticationExceptionHandler(AuthenticationException e){
        String message = e.getMessage();

        return new ResponseEntity<>(new ResponseDto(
                new Data(message)), HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseDto> usernameNotFoundExceptionHandler(UsernameNotFoundException e){
        String message = e.getMessage();

        return new ResponseEntity<>(new ResponseDto(
                new Data(message)), HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ResponseDto> sQLIntegrityConstraintViolationExceptionHandler(SQLIntegrityConstraintViolationException e){
        String message = e.getMessage();

        return new ResponseEntity<>(new ResponseDto(
                new Data(message)), HttpStatus.UNPROCESSABLE_ENTITY
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseDto> badCredentialsExceptionHandler(BadCredentialsException e){
        return new ResponseEntity<>(new ResponseDto(
                new Data("Incorrect username or password. Please check your credentials.")), HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto> illegalArgumentExceptionHandler(IllegalArgumentException e){
        String message = e.getMessage();
        return new ResponseEntity<>(new ResponseDto(
                new Data(message)), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ResponseDto> signatureExceptionHandler(SignatureException e){
        return new ResponseEntity<>(new ResponseDto(
                new Data("Invalid token")), HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ResponseDto> expiredJwtExceptionHandler(ExpiredJwtException e){
        return new ResponseEntity<>(new ResponseDto(
                new Data("Token expired")), HttpStatus.UNAUTHORIZED
        );
    }
}


