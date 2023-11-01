package com.example.orchestrator_service.constants;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProductAPIEndpoints {

    @Value("${external.product_service.ip}")
    private static String productServiceIp;

    @Value("${external.product_service.port}")
    private static String productServicePort;

    @Value("${endpoint.product}")
    private static String productEndpoint;

    @Value("${endpoint.add}")
    private static String addProductEndpoint;

    @Value("${endpoint.deduct}")
    private static String deductProductEndpoint;

    @Value("${endpoint.get.all}")
    private static String getAllEndpoint;

    private static String getProductUrl() {
        return "http://" + productServiceIp + ":" + productServicePort + productEndpoint;
    }

    public static String getAddProductUrl() {
        return getProductUrl() + addProductEndpoint;
    }

    public static String getDeductProductUrl() {
        return getProductUrl() + deductProductEndpoint;
    }

    public static String getGetAllProductsUrl() {
        return getProductUrl() + getAllEndpoint;
    }
}

