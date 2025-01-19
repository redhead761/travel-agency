package com.epam.finaltask.service.impl;

import com.epam.finaltask.dto.TravelAgencyUserDetails;
import com.epam.finaltask.model.JwtBlackList;
import com.epam.finaltask.repository.JwtBlackListRepository;
import com.epam.finaltask.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    private final JwtBlackListRepository jwtBlacklistRepository;

    private final String secretKey;
    private final long jwtExpiration;

    public JwtServiceImpl(@Value("${security.jwt.secret-key}") String secretKey,
                          @Value("${security.jwt.expiration-time}") long jwtExpiration,
                          JwtBlackListRepository jwtBlacklistRepository) {
        this.jwtBlacklistRepository = jwtBlacklistRepository;
        this.secretKey = secretKey;
        this.jwtExpiration = jwtExpiration;
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, (TravelAgencyUserDetails) userDetails, jwtExpiration);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && isNotInBlacklist(token);
    }

    @Override
    public void saveToBlacklist(String token) {
        JwtBlackList jwtBlacklist = new JwtBlackList(token, extractExpiration(token));
        jwtBlacklistRepository.save(jwtBlacklist);
    }

    @Override
    public void deleteExpiredTokens() {
        jwtBlacklistRepository.deleteAllByExpirationTimeBefore(new Date());
    }

    boolean isNotInBlacklist(String token) {
        return !jwtBlacklistRepository.existsById(token);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private String buildToken(Map<String, Object> extraClaims,
                              TravelAgencyUserDetails userDetails,
                              long expiration) {
        extraClaims.put("id", userDetails.getUser().getId());
        extraClaims.put("role", userDetails.getUser().getRole());
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
