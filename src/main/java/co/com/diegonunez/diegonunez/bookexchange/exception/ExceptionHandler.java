package co.com.diegonunez.diegonunez.bookexchange.exception;

import co.com.diegonunez.diegonunez.bookexchange.dto.BodyResponseDto;
import co.com.diegonunez.diegonunez.bookexchange.dto.HeaderDto;
import co.com.diegonunez.diegonunez.bookexchange.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto> handleValidationException(MethodArgumentNotValidException e){

        List<String> errors = new ArrayList<>();
        for( ObjectError message : e.getAllErrors()){
            errors.add(message.getDefaultMessage());
        }

        return new ResponseEntity<>(
                new ResponseDto(
                        new HeaderDto("Error", HttpStatus.BAD_REQUEST.value(), errors.toString()),
                        new BodyResponseDto()
                ), HttpStatus.BAD_REQUEST
        );
    }
}
