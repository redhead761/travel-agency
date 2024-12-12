package com.epam.finaltask.filter;

import com.epam.finaltask.repository.UserRepository;
import jakarta.servlet.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransactionIdFilter  implements Filter {

    @Value("${security.admin.name}")
    private String adminName;

    private final UserRepository userRepository;

    private static final String TRANSACTION_ID = "transactionId";
    private static final String USER_ID = "userId";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        MDC.put(TRANSACTION_ID, UUID.randomUUID().toString());
        MDC.put(USER_ID, getUserId());

        try {
            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }

    private String getUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return "NO USER ID";
        }

        String username = authentication.getName();

        if (username.equals(adminName)) {
            return "ADMIN";
        }

        return userRepository.findUserByUsername(username)
                .map(user -> String.valueOf(user.getId()))
                .orElse("USER NOT FOUND");
    }
}
