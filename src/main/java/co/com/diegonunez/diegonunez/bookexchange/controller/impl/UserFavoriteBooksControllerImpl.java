package co.com.diegonunez.diegonunez.bookexchange.controller.impl;

import co.com.diegonunez.diegonunez.bookexchange.controller.IUserFavoriteBooksController;
import co.com.diegonunez.diegonunez.bookexchange.dto.Data;
import co.com.diegonunez.diegonunez.bookexchange.dto.ResponseDto;
import co.com.diegonunez.diegonunez.bookexchange.entity.Book;
import co.com.diegonunez.diegonunez.bookexchange.service.impl.UserFavoriteBookServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/v1/userFavoriteBooks")
public class UserFavoriteBooksControllerImpl implements IUserFavoriteBooksController {

    private final UserFavoriteBookServiceImpl userFavoriteBookService;

    public UserFavoriteBooksControllerImpl(UserFavoriteBookServiceImpl userFavoriteBooksService){
        this.userFavoriteBookService = userFavoriteBooksService;
    }
    @GetMapping
    @Override
    public ResponseEntity<ResponseDto> getUserFavoriteBooks(String token) {
        Integer userId = getUserIdFromToken(token);
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

    @Override
    public void addFavoriteBooks(String token, List<String> bookISBNs) {

    }

    private Integer getUserIdFromToken(String token) {
        // Eliminar el prefijo "Bearer " si está presente
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        Claims claims = Jwts.parser()
                .setSigningKey("nZ3qzQ5CvU/T4wB3KZLlv0tKhG8G/Jzq6D1G/ojjZCQ=")
                .parseClaimsJws(token)
                .getBody();
        return Integer.parseInt(claims.getSubject());
    }
}
