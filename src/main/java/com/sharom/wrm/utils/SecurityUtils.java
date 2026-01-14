package com.sharom.wrm.utils;

import com.sharom.wrm.config.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

        private SecurityUtils() {}

    public static CustomUserDetails currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) return null;

        if (auth.getPrincipal() instanceof CustomUserDetails user) {
            return user;
        }

        return null;
    }

}
