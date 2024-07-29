package co.com.diegonunez.diegonunez.bookexchange.controller;

import co.com.diegonunez.diegonunez.bookexchange.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

public interface IUserFavoriteBooksController {
    ResponseEntity<ResponseDto> getUserFavoriteBooks(@RequestHeader("Authorization") String token);
    ResponseEntity<ResponseDto> addFavoriteBooks(@RequestHeader("Authorization") String token, @PathVariable String bookISBN);
    ResponseEntity<ResponseDto> eraseFavoriteBook(@RequestHeader("Authorization") String token, @PathVariable String bookISBN);

}
