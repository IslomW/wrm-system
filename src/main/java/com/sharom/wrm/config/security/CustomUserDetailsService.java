package com.sharom.wrm.config.security;

import com.sharom.wrm.common.exception.NotFoundException;
import com.sharom.wrm.modules.user.model.entity.User;
import com.sharom.wrm.modules.user.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        User user = userRepo.findByUserName(username)
                .orElseThrow(NotFoundException::userNotFound);

        return new CustomUserDetails(user);
    }
}
