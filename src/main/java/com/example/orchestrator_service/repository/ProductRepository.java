package com.example.orchestrator_service.repository;

import com.example.orchestrator_service.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, String> {}
