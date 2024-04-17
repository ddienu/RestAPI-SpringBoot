package co.com.diegonunez.diegonunez.bookexchange.controller.Auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {

    @PostMapping( path = "/login")
    public String login(){
        return "Successfull request from login";
    }

    @PostMapping(path = "/register")
    public String register(){
        return "Successfull request from register";
    }
}
