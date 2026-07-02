package com.rahulagarwal.promptforge.auth.mapper;

import com.rahulagarwal.promptforge.auth.dto.response.RegisterResponse;
import com.rahulagarwal.promptforge.auth.entity.AuthUser;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public RegisterResponse toRegisterResponse(AuthUser user) {
        return new RegisterResponse(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                user.getAccountStatus(),
                user.getAuthProvider(),
                user.isEmailVerified(),
                user.getCreatedAt()
        );
    }
}