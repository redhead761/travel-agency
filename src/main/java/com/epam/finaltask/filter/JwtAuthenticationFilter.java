package com.epam.finaltask.filter;

import com.epam.finaltask.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String BEARER = "Bearer ";

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws IOException, ServletException {
        String jwt = extractJwtFromRequest(request);

        if (jwt == null) {
            proceedWithoutJwt(request, response, filterChain);
        } else {
            try {
                String username = jwtService.extractUsername(jwt);
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                if (username != null && (authentication == null || !authentication.isAuthenticated())) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    if (jwtService.isTokenValid(jwt, userDetails)) {
                        proceedWithValidJwt(request, userDetails);
                    } else {
                        log.warn("Invalid JWT token for user: {}", username);
                    }
                }

                filterChain.doFilter(request, response);

            } catch (Exception exception) {
                handlerExceptionResolver.resolveException(request, response, null, exception);
            }
        }
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
            return authorizationHeader.substring(BEARER.length());
        }
        return null;
    }

    private void proceedWithoutJwt(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain filterChain) throws IOException, ServletException {
        log.warn("JWT token is missing. Proceeding without authentication.");
        filterChain.doFilter(request, response);
    }

    private void proceedWithValidJwt(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
