package co.com.diegonunez.diegonunez.bookexchange.controller;

import co.com.diegonunez.diegonunez.bookexchange.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;

public interface IBookLoanController {
    ResponseEntity<ResponseDto> requestBook(@RequestHeader("Authorization") String token, String bookISBN);
    ResponseEntity<ResponseDto> returnBook(@RequestHeader("Authorization") String token, String bookISBN);

}
