package com.epam.finaltask.sheduler;

import com.epam.finaltask.service.impl.JwtServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExpiredTokenCleanupScheduler {
    private final JwtServiceImpl jwtServiceImpl;

    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanExpiredTokens() {
        jwtServiceImpl.deleteExpiredTokens();
        log.debug("Cleaned expired tokens");
    }
}
