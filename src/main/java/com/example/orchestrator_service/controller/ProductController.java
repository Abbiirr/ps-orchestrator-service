package com.example.orchestrator_service.controller;

import com.example.orchestrator_service.dto.ProductRequestDTO;
import com.example.orchestrator_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("")
    public String getAllProducts() {
        return this.service.getAllProducts();
    }

    @PostMapping("/deduct")
    public String deduct(@RequestBody final ProductRequestDTO requestDTO){
        return this.service.deductProduct(requestDTO);
    }

    @PostMapping("/add")
    public void add(@RequestBody final ProductRequestDTO requestDTO){
        this.service.addProduct(requestDTO);
    }


}
