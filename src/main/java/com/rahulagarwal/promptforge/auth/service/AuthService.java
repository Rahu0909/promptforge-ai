package com.rahulagarwal.promptforge.auth.service;

import com.rahulagarwal.promptforge.auth.dto.request.LoginRequest;
import com.rahulagarwal.promptforge.auth.dto.request.RefreshTokenRequest;
import com.rahulagarwal.promptforge.auth.dto.request.RegisterRequest;
import com.rahulagarwal.promptforge.auth.dto.response.LoginResponse;
import com.rahulagarwal.promptforge.auth.dto.response.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    LoginResponse refresh(RefreshTokenRequest request);
}