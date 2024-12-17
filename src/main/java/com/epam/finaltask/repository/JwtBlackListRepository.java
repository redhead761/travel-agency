package com.epam.finaltask.repository;

import com.epam.finaltask.model.JwtBlackList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface JwtBlackListRepository extends JpaRepository<JwtBlackList, String> {
    void deleteAllByExpirationTimeBefore(Date expirationTime);
}
