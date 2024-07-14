package co.com.diegonunez.diegonunez.bookexchange.controller;

import co.com.diegonunez.diegonunez.bookexchange.dto.ResponseDto;
import co.com.diegonunez.diegonunez.bookexchange.entity.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface IUserFavoriteBooksController {
    ResponseEntity<ResponseDto> getUserFavoriteBooks(@RequestHeader("Authorization") String token);
    void addFavoriteBooks(@RequestHeader("Authorization") String token, @RequestBody List<String> bookISBNs);

}
