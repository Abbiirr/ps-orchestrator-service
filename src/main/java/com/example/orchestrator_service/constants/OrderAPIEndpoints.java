package com.example.orchestrator_service.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OrderAPIEndpoints {

    @Value("${external.order_service.ip}")
    private static String orderServiceIp;

    @Value("${external.order_service.port}")
    private static String orderServicePort;

    @Value("${endpoint.order}")
    private static String orderEndpoint;

    @Value("${endpoint.create}")
    private static String createEndpoint;

    @Value("${endpoint.get.all}")
    private static String getAllEndpoint;

    private static String getOrderUrl() {
        return "http://" + orderServiceIp + ":" + orderServicePort + orderEndpoint;
    }

    public static String getCreateOrderUrl() {
        return getOrderUrl() + createEndpoint;
    }

    public static String getGetAllOrdersUrl() {
        return getOrderUrl() + getAllEndpoint;
    }
}
