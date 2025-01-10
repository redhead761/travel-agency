package com.epam.finaltask.repository;

import com.epam.finaltask.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT NOT EXISTS (SELECT 1 FROM User u WHERE u.username = :username)")
    boolean doesNotExistByUsername(@Param("username") String username);

    Optional<User> findByUsername(String username);

    @Query("SELECT NOT EXISTS (SELECT 1 FROM User u WHERE u.phoneNumber = :phoneNumber)")
    boolean doesNotExistsByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
