package com.hotel_management.payload;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
