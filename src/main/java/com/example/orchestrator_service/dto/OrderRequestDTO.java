package com.example.orchestrator_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    private UUID orderId;
    private String userId;

    //<productId, quantity>
    private HashMap<String, Integer> products;
    private Double totalPrice;

}
