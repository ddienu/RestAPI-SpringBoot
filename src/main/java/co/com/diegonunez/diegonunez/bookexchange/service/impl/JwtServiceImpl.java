package co.com.diegonunez.diegonunez.bookexchange.service.impl;

import co.com.diegonunez.diegonunez.bookexchange.entity.User;
import co.com.diegonunez.diegonunez.bookexchange.repository.IUserRepository;
import co.com.diegonunez.diegonunez.bookexchange.service.IJwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements IJwtService {

    @Value("${SECRET_KEY}")
    private String SECRET_KEY;

    private final IUserRepository userRepository;
    @Autowired
    public JwtServiceImpl(IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }

    private String getToken(Map<String, Object> extraClaims, UserDetails user) {
        User userFounded = userRepository.getUserByUsername(user.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userFounded.getId().toString())
                .claim("username", user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*10))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {

        return getAllClaims(token).get("username", String.class);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }

    private Claims getAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpirationDate(String token){
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        return getExpirationDate(token).before(new Date());
    }
}
