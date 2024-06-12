package co.com.diegonunez.diegonunez.bookexchange.service.impl;

import co.com.diegonunez.diegonunez.bookexchange.dto.UserDto;
import co.com.diegonunez.diegonunez.bookexchange.entity.User;
import co.com.diegonunez.diegonunez.bookexchange.repository.IUserRepository;
import co.com.diegonunez.diegonunez.bookexchange.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(IUserRepository userRepository, PasswordEncoder passwordEncoder ){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User login(UserDto user) throws AuthenticationException {
        User userFound = userRepository.getUserByEmail(user.getEmail());
        if( userFound == null){
            throw new UsernameNotFoundException("User not found");
        }
        String passwordEncrypted = userFound.getPassword();

        boolean passwordMatch = passwordEncoder.matches(user.getPassword(), passwordEncrypted);
        if(!passwordMatch){
            throw new AuthenticationException("Incorrect password");
        }
        return userFound;
    }

    @Override
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
