package com.epam.finaltask.service.impl;

import com.epam.finaltask.dto.TravelAgencyUserDetails;
import com.epam.finaltask.model.JwtBlackList;
import com.epam.finaltask.model.Role;
import com.epam.finaltask.model.User;
import com.epam.finaltask.repository.JwtBlackListRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.Key;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtServiceImplTest {

    @Mock
    private JwtBlackListRepository jwtBlacklistRepository;

    private JwtServiceImpl jwtService;

    private final String secretKey = Base64.getEncoder().encodeToString("thisIsASecureKeyWithEnoughLength!".getBytes());
    private final long jwtExpiration = 3600000L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtService = new JwtServiceImpl(secretKey, jwtExpiration, jwtBlacklistRepository);
    }

    @Test
    void testExtractUsername() {
        String token = createToken("user1");
        String username = jwtService.extractUsername(token);
        assertEquals("user1", username);
    }

    @Test
    void testGenerateToken() {
        TravelAgencyUserDetails userDetails = createUserDetails("user1", Role.USER);

        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);
    }

    @Test
    void testGenerateTokenWithExtraClaims() {
        TravelAgencyUserDetails userDetails = createUserDetails("user1", Role.USER);

        // Используем изменяемую HashMap для дополнительных данных
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("customClaim", "value");

        String token = jwtService.generateToken(extraClaims, userDetails);
        assertNotNull(token);
    }

    @Test
    void testIsTokenValid() {
        TravelAgencyUserDetails userDetails = createUserDetails("user1", Role.USER);
        String token = createToken("user1");

        when(jwtBlacklistRepository.existsById(token)).thenReturn(false);

        boolean isValid = jwtService.isTokenValid(token, userDetails);
        assertTrue(isValid);
    }

    @Test
    void testSaveToBlacklist() {
        String token = createToken("user1");

        jwtService.saveToBlacklist(token);

        ArgumentCaptor<JwtBlackList> captor = ArgumentCaptor.forClass(JwtBlackList.class);
        verify(jwtBlacklistRepository, times(1)).save(captor.capture());

        JwtBlackList savedToken = captor.getValue();
        assertEquals(token, savedToken.getToken());
        assertNotNull(savedToken.getExpirationTime());
    }

    @Test
    void testDeleteExpiredTokens() {
        jwtService.deleteExpiredTokens();
        verify(jwtBlacklistRepository, times(1)).deleteAllByExpirationTimeBefore(any(Date.class));
    }

    @Test
    void testIsNotInBlacklist() {
        String token = createToken("user1");
        when(jwtBlacklistRepository.existsById(token)).thenReturn(false);

        boolean notInBlacklist = jwtService.isNotInBlacklist(token);
        assertTrue(notInBlacklist);

        verify(jwtBlacklistRepository, times(1)).existsById(token);
    }

    @Test
    void testExtractClaim() {
        String token = createToken("user1");
        String username = jwtService.extractClaim(token, Claims::getSubject);
        assertEquals("user1", username);
    }

    @Test
    void testExtractExpiration() {
        String token = createToken("user1");
        Date expiration = jwtService.extractExpiration(token);
        assertNotNull(expiration);
    }

    @Test
    void testBuildToken() {
        TravelAgencyUserDetails userDetails = createUserDetails("user1", Role.USER);
        Map<String, Object> extraClaims = new HashMap<>();

        String token = jwtService.generateToken(extraClaims, userDetails);
        assertNotNull(token);
    }

    private String createToken(String username) {
        Key key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(key)
                .compact();
    }

    private TravelAgencyUserDetails createUserDetails(String username, Role role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword("password");
        user.setAccountStatus(true);
        user.setRole(role);

        return new TravelAgencyUserDetails(user);
    }

}
