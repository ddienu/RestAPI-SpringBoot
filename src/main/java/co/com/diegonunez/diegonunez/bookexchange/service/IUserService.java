package co.com.diegonunez.diegonunez.bookexchange.service;

import co.com.diegonunez.diegonunez.bookexchange.dto.UserDto;
import co.com.diegonunez.diegonunez.bookexchange.entity.User;

import javax.naming.AuthenticationException;

public interface IUserService {
    User login(UserDto user) throws AuthenticationException;
    User register(User user);
}
