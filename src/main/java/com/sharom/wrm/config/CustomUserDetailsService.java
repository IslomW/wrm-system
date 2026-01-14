package com.sharom.wrm.config;

import com.sharom.wrm.entity.User;
import com.sharom.wrm.exception.BadRequestAlertException;
import com.sharom.wrm.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        User user = userRepo.findByUserName(username)
                .orElseThrow(BadRequestAlertException::userNotFound);

        return new CustomUserDetails(user);
    }
}
