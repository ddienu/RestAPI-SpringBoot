package co.com.diegonunez.diegonunez.bookexchange.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {

    String getToken(UserDetails user);
}
