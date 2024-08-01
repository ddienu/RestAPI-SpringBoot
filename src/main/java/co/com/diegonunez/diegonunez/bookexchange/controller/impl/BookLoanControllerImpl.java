package co.com.diegonunez.diegonunez.bookexchange.controller.impl;

import co.com.diegonunez.diegonunez.bookexchange.controller.IBookLoanController;
import co.com.diegonunez.diegonunez.bookexchange.dto.Data;
import co.com.diegonunez.diegonunez.bookexchange.dto.ResponseDto;
import co.com.diegonunez.diegonunez.bookexchange.service.impl.BookLoanServiceImpl;
import co.com.diegonunez.diegonunez.bookexchange.service.impl.JwtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/v1/bookLoan")
public class BookLoanControllerImpl implements IBookLoanController {

    private final BookLoanServiceImpl bookLoanService;
    private final JwtServiceImpl jwtService;
    @Autowired
    public BookLoanControllerImpl(BookLoanServiceImpl bookLoanService, JwtServiceImpl jwtService){
        this.bookLoanService = bookLoanService;
        this.jwtService = jwtService;
    }
    @Override
    @PostMapping(path = "/requestBook/{bookISBN}")
    public ResponseEntity<ResponseDto> requestBook(@RequestHeader("Authorization") String token, @PathVariable String bookISBN) {
        Integer userId = jwtService.getUserIdFromToken(token);
        bookLoanService.requestBook(userId, bookISBN);
        return new ResponseEntity<>(
                new ResponseDto(
                        Data.builder()
                                .message("Book loan requested successfully")
                                .build()
                ), HttpStatus.OK
        );
    }

    @Override
    @PostMapping(path = "/returnBook/{bookISBN}")
    public ResponseEntity<ResponseDto> returnBook(@RequestHeader("Authorization") String token, @PathVariable String bookISBN) {
        Integer userId = jwtService.getUserIdFromToken(token);
        bookLoanService.returnBook(userId, bookISBN);
        return new ResponseEntity<>(
                new ResponseDto(
                        Data.builder()
                                .message("Book returned successfully")
                                .build()
                ), HttpStatus.OK
        );
    }
}
