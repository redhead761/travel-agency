package com.epam.finaltask.dto;

import com.epam.finaltask.model.User;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;

@Getter
public class TravelAgencyUserDetails implements UserDetails {
    private final String username;
    private final String password;
    private final boolean isActive;
    private final User user;
    private final Set<SimpleGrantedAuthority> authorities = new HashSet<>();

    public TravelAgencyUserDetails(User user) {
        this.user = user;
        username = user.getUsername();
        password = user.getPassword();
        isActive = user.isAccountStatus();
        authorities.addAll(user.getRole().getAuthorities());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
