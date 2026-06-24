package com.hotel_management.Controller;

import com.hotel_management.payload.DashboardResponse;
import com.hotel_management.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboard() {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        return ResponseEntity.ok(
                dashboardService.getDashboardData()
        );
    }
}
