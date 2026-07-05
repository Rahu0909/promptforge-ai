package com.rahulagarwal.promptforge.security.util;

import com.rahulagarwal.promptforge.security.model.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static CustomUserDetails currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (CustomUserDetails) authentication.getPrincipal();
    }

    public static UUID currentUserId() {
        return currentUser().getUserId();

    }

    public static String currentEmail() {
        return currentUser().getUsername();

    }

}