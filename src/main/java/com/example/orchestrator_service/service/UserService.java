package com.example.orchestrator_service.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    public boolean checkOrderExistsForUser(String userId, UUID orderId) {
        return true;
    }
}
