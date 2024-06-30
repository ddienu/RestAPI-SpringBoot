package co.com.diegonunez.diegonunez.bookexchange.service;

import co.com.diegonunez.diegonunez.bookexchange.dto.UserDto;
import co.com.diegonunez.diegonunez.bookexchange.entity.User;

import javax.naming.AuthenticationException;
import java.sql.SQLIntegrityConstraintViolationException;

public interface IUserService {
    String login(UserDto user) throws AuthenticationException;
    User register(User user) throws SQLIntegrityConstraintViolationException;
}
