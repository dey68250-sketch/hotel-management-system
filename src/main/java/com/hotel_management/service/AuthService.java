package com.hotel_management.service;


import com.hotel_management.payload.LoginRequest;
import com.hotel_management.payload.LoginResponse;
import com.hotel_management.payload.RegisterRequest;

public interface AuthService {

    String register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}
