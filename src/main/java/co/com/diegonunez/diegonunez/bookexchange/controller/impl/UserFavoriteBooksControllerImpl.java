package co.com.diegonunez.diegonunez.bookexchange.controller.impl;

import co.com.diegonunez.diegonunez.bookexchange.controller.IUserFavoriteBooksController;
import co.com.diegonunez.diegonunez.bookexchange.dto.Data;
import co.com.diegonunez.diegonunez.bookexchange.dto.ResponseDto;
import co.com.diegonunez.diegonunez.bookexchange.entity.Book;
import co.com.diegonunez.diegonunez.bookexchange.service.impl.JwtServiceImpl;
import co.com.diegonunez.diegonunez.bookexchange.service.impl.UserFavoriteBookServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/v1/userFavoriteBooks")
public class UserFavoriteBooksControllerImpl implements IUserFavoriteBooksController {

    private final UserFavoriteBookServiceImpl userFavoriteBookService;

    private final JwtServiceImpl jwtService;
    @Value("${SECRET_KEY}")
    private String SECRET_KEY;

    public UserFavoriteBooksControllerImpl(UserFavoriteBookServiceImpl userFavoriteBooksService, JwtServiceImpl jwtService){
        this.userFavoriteBookService = userFavoriteBooksService;
        this.jwtService = jwtService;
    }
    @GetMapping
    @Override
    public ResponseEntity<ResponseDto> getUserFavoriteBooks(String token) {
        Integer userId = jwtService.getUserIdFromToken(token);
        List<Book> userFavoriteBooks = userFavoriteBookService.getUserFavoriteBooks(userId);
        return new ResponseEntity<>(
                new ResponseDto(
                        Data
                                .builder()
                                .message("User favorite books list")
                                .books(userFavoriteBooks)
                                .build()
                ), HttpStatus.OK
                );
    }

    @PostMapping(path = "/{bookISBN}")
    @Override
    public ResponseEntity<ResponseDto> addFavoriteBooks(String token, @PathVariable String  bookISBN) {
        Integer userId = jwtService.getUserIdFromToken(token);
        userFavoriteBookService.addFavoriteBooks(userId, bookISBN);
        return new ResponseEntity<>(
                new ResponseDto(
                        Data.builder()
                                .message("Book added to user favorite list")
                                .build()
                ), HttpStatus.OK
        );
    }
}
