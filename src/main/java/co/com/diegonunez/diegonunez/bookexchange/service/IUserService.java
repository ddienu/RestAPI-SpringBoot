package co.com.diegonunez.diegonunez.bookexchange.service;

import co.com.diegonunez.diegonunez.bookexchange.entity.User;

import javax.naming.AuthenticationException;

public interface IUserService {
    User login(User user) throws AuthenticationException;
    User register(User user);
}
