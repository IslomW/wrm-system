package com.sharom.wrm.config;

import com.sharom.wrm.entity.User;
import com.sharom.wrm.entity.UserType;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {


    private final String id;
    private final String username;
    private final String password;
    private final UserType userType;
    private final String locationId;
    private final List<SimpleGrantedAuthority> authorities;


    public CustomUserDetails(User user) {
        this.id = user.getId();
        this.username = user.getUserName();
        this.password = user.getPassword();
        this.userType = user.getUserType();
        this.locationId = user.getLocationId();
        this.authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + user.getUserType().name())
        );
    }


    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
}
