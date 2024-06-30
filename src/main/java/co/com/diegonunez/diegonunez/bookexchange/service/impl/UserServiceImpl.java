package co.com.diegonunez.diegonunez.bookexchange.service.impl;

import co.com.diegonunez.diegonunez.bookexchange.dto.UserDto;
import co.com.diegonunez.diegonunez.bookexchange.entity.User;
import co.com.diegonunez.diegonunez.bookexchange.repository.IUserRepository;
import co.com.diegonunez.diegonunez.bookexchange.service.IUserService;
import co.com.diegonunez.diegonunez.bookexchange.util.Role;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.sql.SQLIntegrityConstraintViolationException;

@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;
    @Autowired
    public UserServiceImpl(IUserRepository userRepository, PasswordEncoder passwordEncoder
            , JwtServiceImpl jwtService, AuthenticationManager authenticationManager ){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String login(UserDto user) throws AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        UserDetails userFound = userRepository.getUserByUsername(user.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String passwordEncrypted = userFound.getPassword();

        boolean passwordMatch = passwordEncoder.matches(user.getPassword(), passwordEncrypted);
        if(!passwordMatch){
            throw new AuthenticationException("Incorrect password");
        }
        return jwtService.getToken(userFound);
    }

    @Override
    public User register(@Valid User user) throws SQLIntegrityConstraintViolationException {
        //Realizar la validación para cuando el usuario ya está registrado (user_name) SQLIntegrityConstraintViolationException
        String userName = user.getUsername();
        if( userName == null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(Role.USER);
            return userRepository.save(user);
        }
        throw new SQLIntegrityConstraintViolationException("The user name already exist");
    }
}
