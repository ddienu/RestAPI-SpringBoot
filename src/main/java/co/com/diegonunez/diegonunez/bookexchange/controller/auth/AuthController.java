package co.com.diegonunez.diegonunez.bookexchange.controller.auth;

import co.com.diegonunez.diegonunez.bookexchange.dto.BodyResponseDto;
import co.com.diegonunez.diegonunez.bookexchange.dto.HeaderDto;
import co.com.diegonunez.diegonunez.bookexchange.dto.ResponseDto;
import co.com.diegonunez.diegonunez.bookexchange.dto.UserDto;
import co.com.diegonunez.diegonunez.bookexchange.entity.User;
import co.com.diegonunez.diegonunez.bookexchange.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping(path = "/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserServiceImpl userService;

    @Autowired
    public AuthController(UserServiceImpl userService){
        this.userService = userService;
    }

    @PostMapping( path = "/login")
    public ResponseEntity<ResponseDto> login(@RequestBody UserDto user) throws AuthenticationException {
        String serviceResponse = userService.login(user);
        return new ResponseEntity<>(
                new ResponseDto(
                        new HeaderDto("Success", HttpStatus.OK.value(), "User login successfully"),
                        new BodyResponseDto(serviceResponse)
                ), HttpStatus.OK
        );
    }

    @PostMapping(path = "/register")
    public ResponseEntity<ResponseDto> register(@RequestBody User user){
        userService.register(user);
        return new ResponseEntity<>(
                new ResponseDto(
                        new HeaderDto("Success", HttpStatus.OK.value(), "User registered succesfully"),
                        null
                ), HttpStatus.OK
        );

    }
}
