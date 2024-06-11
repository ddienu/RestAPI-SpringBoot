package co.com.diegonunez.diegonunez.bookexchange.exception;

import co.com.diegonunez.diegonunez.bookexchange.dto.HeaderDto;
import co.com.diegonunez.diegonunez.bookexchange.dto.ResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
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
                        new HeaderDto("Error", HttpStatus.BAD_REQUEST.value(), errors.toString()),
                        null
                ), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseDto> handleEntityNotFoundException(EntityNotFoundException e) {
        String message = e.getMessage();

        if (message.equalsIgnoreCase("No books founded")) {
            return new ResponseEntity<>(
                    new ResponseDto(
                            new HeaderDto(ERROR, HttpStatus.NO_CONTENT.value(), e.getMessage()),
                            null), HttpStatus.OK
            );
        } else if (message.contains("No book with name")) {
            return new ResponseEntity<>(
                    new ResponseDto(
                            new HeaderDto(ERROR, HttpStatus.BAD_REQUEST.value(), message),
                            null), HttpStatus.BAD_REQUEST);
        } else if (message.contains("Book with ISBN")) {
            return new ResponseEntity<>(
                    new ResponseDto(
                            new HeaderDto(SUCCESS, HttpStatus.NO_CONTENT.value(), message),
                            null
                    ), HttpStatus.OK
            );
        }else if( message.contains("No books by author")){
            return new ResponseEntity<>(
                    new ResponseDto(
                            new HeaderDto(SUCCESS, HttpStatus.NO_CONTENT.value(), message ),
                            null
                    ), HttpStatus.OK
            );
        }else if( message.contains("Books by genre")){
            return new ResponseEntity<>(
                    new ResponseDto(
                            new HeaderDto(SUCCESS, HttpStatus.NO_CONTENT.value(), message),
                            null
                    ), HttpStatus.OK
            );
        }else if( message.contains("No book found")){
            return new ResponseEntity<>(new ResponseDto(
                    new HeaderDto(ERROR, HttpStatus.NO_CONTENT.value(), message),
                    null
            ), HttpStatus.OK
            );
        }else if( message.contains("No book founded to update with ISBN")){
            return new ResponseEntity<>(new ResponseDto(
                    new HeaderDto(ERROR, HttpStatus.NO_CONTENT.value(), message),
                    null
            ), HttpStatus.OK
            );
        }else if( message.contains("El usuario no ha sido registrado")){
            return new ResponseEntity<>(new ResponseDto(
                    new HeaderDto(ERROR, HttpStatus.NO_CONTENT.value(), message),
                    null
            ), HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                new ResponseDto(
                        new HeaderDto(ERROR, HttpStatus.NO_CONTENT.value(), e.getMessage()),
                        null), HttpStatus.NO_CONTENT
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseDto> badRequestExceptionHandler(BadRequestException e){
        String message = e.getMessage();

        if( message.contains("The ISBN must contain 10 or 13 numbers")){
            return new ResponseEntity<>(
                    new ResponseDto(
                            new HeaderDto(ERROR, HttpStatus.BAD_REQUEST.value(), message),
                            null), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(
                new ResponseDto(
                        new HeaderDto(ERROR, HttpStatus.NO_CONTENT.value(), e.getMessage()),
                        null), HttpStatus.NO_CONTENT
        );
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ResponseDto> duplicateKeyExceptionHandler(DuplicateKeyException e){
        String message = e.getMessage();

        if(message.contains("The ISBN already exist")){
            return new ResponseEntity<>(
                    new ResponseDto(
                            new HeaderDto(ERROR, HttpStatus.BAD_REQUEST.value(), message),
                            null), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(
                new ResponseDto(
                        new HeaderDto(ERROR, HttpStatus.NO_CONTENT.value(), e.getMessage()),
                        null), HttpStatus.NO_CONTENT
        );
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDto> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e){
        String message = e.getMessage();
        String [] parts = message.split(":");
        return new ResponseEntity<>(
                new ResponseDto(
                        new HeaderDto("Error", HttpStatus.BAD_REQUEST.value(), parts[0]),
                        null), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseDto> authenticationExceptionHandler(AuthenticationException e){
        String message = e.getMessage();

        return new ResponseEntity<>(new ResponseDto(
                new HeaderDto(ERROR, HttpStatus.UNAUTHORIZED.value(), message),
                null
        ), HttpStatus.OK
        );
    }
}


