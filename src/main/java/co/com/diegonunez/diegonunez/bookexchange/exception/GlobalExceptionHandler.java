package co.com.diegonunez.diegonunez.bookexchange.exception;

import co.com.diegonunez.diegonunez.bookexchange.dto.Data;
import co.com.diegonunez.diegonunez.bookexchange.dto.ResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jdk.jfr.Experimental;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.InvalidPropertiesFormatException;

@RestControllerAdvice
public class GlobalExceptionHandler{
    //Exception that validates the entity in the request body.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto> handleValidationException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(
                new ResponseDto(
                        Data.builder()
                                .message(e.getBindingResult().getAllErrors().get(0).getDefaultMessage())
                                .build()
                ), HttpStatus.BAD_REQUEST
        );
    }
    //Exception that validates when a book cannot be founded in the database.
    @ExceptionHandler(NoBookFoundException.class)
    public ResponseEntity<ResponseDto> noBookFoundExceptionHandler(NoBookFoundException e) {
        String message = e.getMessage();

        return new ResponseEntity<>(
                new ResponseDto(
                        Data.builder()
                                .message(message)
                                .build()
                ), HttpStatus.OK);
    }
    //Exception that is thrown when a ISBN length is different of 10 or 13.
    @ExceptionHandler(InvalidISBNException.class)
    public ResponseEntity<ResponseDto> invalidISBNExceptionHandler(InvalidISBNException e){
        String message = e.getMessage();

        return new ResponseEntity<>(
                new ResponseDto(
                        Data.builder()
                                .message(message)
                                .build()
                ), HttpStatus.BAD_REQUEST);


    }
    //Exception is thrown when the user wants to upload a new book but the ISBN already exist in the database
    @ExceptionHandler(DuplicateISBNException.class)
    public ResponseEntity<ResponseDto> duplicateKeyExceptionHandler(DuplicateISBNException e){
        String message = e.getMessage();

        return new ResponseEntity<>(
                new ResponseDto(
                        Data.builder()
                                .message(message)
                                .build()
                ), HttpStatus.BAD_REQUEST);
    }
    //This exception is thrown when the body of the request is empty
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDto> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e){
        String message = e.getMessage();
        String [] parts = message.split(":");
        return new ResponseEntity<>(
                new ResponseDto(
                        Data.builder()
                                .message(parts[0])
                                .build()
                ), HttpStatus.BAD_REQUEST
        );
    }
    //SQLIntegrityException is thrown when the username given from user already exist.
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ResponseDto> sQLIntegrityConstraintViolationExceptionHandler(SQLIntegrityConstraintViolationException e){
        String message = e.getMessage();

        return new ResponseEntity<>(
                new ResponseDto(
                        Data.builder()
                                .message(message)
                                .build()
                ), HttpStatus.UNPROCESSABLE_ENTITY
        );
    }
    //This exception is thrown when the user credentials are incorrect.
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseDto> badCredentialsExceptionHandler(BadCredentialsException e){
        return new ResponseEntity<>(
                new ResponseDto(
                        Data.builder()
                                .message("Incorrect username or password. Please check your credentials.")
                                .build()
                ), HttpStatus.UNAUTHORIZED
        );
    }
    //This exception is thrown when the jwt is invalid
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ResponseDto> signatureExceptionHandler(SignatureException e){
        return new ResponseEntity<>(
                new ResponseDto(
                        Data.builder()
                                .message("Invalid token")
                                .build()
                ), HttpStatus.UNAUTHORIZED
        );
    }
    //This exception is thrown when the jwt expired
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ResponseDto> expiredJwtExceptionHandler(ExpiredJwtException e){
        return new ResponseEntity<>(
                new ResponseDto(
                        Data.builder()
                                .message("Token expired")
                                .build()
                ), HttpStatus.UNAUTHORIZED
        );
    }
    //This exception is thrown when the request body is empty or the fields are invalid
    @ExceptionHandler(InvalidPropertiesFormatException.class)
    public ResponseEntity<ResponseDto> invalidPropertiesFormatExceptionHandler(InvalidPropertiesFormatException e){
        String message = e.getMessage();
        return new ResponseEntity<>(new ResponseDto(
                Data.builder()
                        .message(message)
                        .build()
        ), HttpStatus.BAD_REQUEST
        );
    }
    //This exception is thrown when try to instantiate a Util class.
    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<ResponseDto> unsupportedOperationExceptionHandler(UnsupportedOperationException e){
        String message = e.getMessage();
        return new ResponseEntity<>(new ResponseDto(
                Data.builder()
                        .message(message)
                        .build()
        ), HttpStatus.BAD_REQUEST
        );
    }
    //Tnis exception is thrown when jwt is null or empty.
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseDto> accessDeniedExceptionHandler(AccessDeniedException e){
        return new ResponseEntity<>(new ResponseDto(
                Data.builder()
                        .message(e.getMessage())
                        .build()
        ), HttpStatus.UNAUTHORIZED
        );
    }

    //This exception is thrown when the jwt is malformed.
    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ResponseDto> malformedJwtExceptionHandler(MalformedJwtException e){
        return new ResponseEntity<>(
                new ResponseDto(
                        Data.builder()
                                .message("Invalid token")
                                .build()
                ), HttpStatus.BAD_REQUEST
        );
    }

    //This exception is thrown when user request a book and it's loaned already
    @ExceptionHandler(BookLoanedException.class)
    public ResponseEntity<ResponseDto> bookLoanedExceptionHandler(BookLoanedException e){
        return new ResponseEntity<>(
                new ResponseDto(
                        Data.builder()
                                .message(e.getMessage())
                                .build()
                ), HttpStatus.UNPROCESSABLE_ENTITY
        );
    }
}



