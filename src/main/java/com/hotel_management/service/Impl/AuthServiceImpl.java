package com.hotel_management.service.Impl;

import com.hotel_management.entity.Admin;
import com.hotel_management.entity.Role;
import com.hotel_management.payload.LoginRequest;
import com.hotel_management.payload.LoginResponse;
import com.hotel_management.payload.RegisterRequest;
import com.hotel_management.repository.AdminRepository;
import com.hotel_management.security.JwtService;
import com.hotel_management.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @Override
    public String register(RegisterRequest request) {

        if (adminRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        Admin admin = new Admin();

        admin.setUsername(request.getUsername());
        admin.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        admin.setRole(Role.ADMIN);   // <-- Add here

        adminRepository.save(admin);

        return "Admin registered successfully";
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        Admin admin = adminRepository.findByUsername(
                request.getUsername()
        ).orElseThrow(() ->
                new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                admin.getPassword())) {

            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtService.generateToken(
                admin.getUsername()
        );

        return new LoginResponse(token);
    }
}
