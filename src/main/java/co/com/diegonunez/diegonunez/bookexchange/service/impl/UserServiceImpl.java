package co.com.diegonunez.diegonunez.bookexchange.service.impl;

import co.com.diegonunez.diegonunez.bookexchange.dto.UserDto;
import co.com.diegonunez.diegonunez.bookexchange.entity.User;
import co.com.diegonunez.diegonunez.bookexchange.repository.IUserRepository;
import co.com.diegonunez.diegonunez.bookexchange.service.IUserService;
import co.com.diegonunez.diegonunez.bookexchange.util.Role;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

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
    public String login(UserDto user) throws BadCredentialsException {
        String message = "Incorrect username or password. Please check your credentials.";
        UserDetails userFound = userRepository.getUserByUsername(user.getUsername())
                .orElseThrow(() -> new BadCredentialsException(message));

        String passwordEncrypted = userFound.getPassword();

        boolean passwordMatch = passwordEncoder.matches(user.getPassword(), passwordEncrypted);
        if(!passwordMatch){
            throw new BadCredentialsException(message);
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        return jwtService.getToken(userFound);
    }

    @Override
    public User register(@Valid User user) throws SQLIntegrityConstraintViolationException {
        //Realizar la validación para cuando el usuario ya está registrado (user_name) SQLIntegrityConstraintViolationException
        Optional<User> userName = userRepository.getUserByUsername(user.getUsername());
        if( !userName.isPresent()){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(Role.USER);
            return userRepository.save(user);
        }
        throw new SQLIntegrityConstraintViolationException("The username already exist");
    }
}
