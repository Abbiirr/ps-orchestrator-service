package com.example.orchestrator_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;

@Data
@AllArgsConstructor
public class ProductsDTO {

    private HashMap<String, Integer> products;
}
