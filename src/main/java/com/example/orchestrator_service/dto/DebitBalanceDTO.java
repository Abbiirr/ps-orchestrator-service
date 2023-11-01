package com.example.orchestrator_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DebitBalanceDTO {

        private String userId;
        private double amount;
}
