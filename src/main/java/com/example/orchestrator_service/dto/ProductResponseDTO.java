package com.example.orchestrator_service.dto;

import com.example.orchestrator_service.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {

    private String orderId;
    private String userId;
    private String productId;
    private ProductStatus status;

}
