package com.example.orchestrator_service.service;

import com.example.orchestrator_service.constants.OrderAPIEndpoints;
import com.example.orchestrator_service.dto.OrderRequestDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class OrderService {



    private final RestTemplate restTemplate;

    private HttpHeaders headers;


    public OrderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    public String createOrder(OrderRequestDTO requestDTO) {
        String response = restTemplate.exchange(OrderAPIEndpoints.getGetAllOrdersUrl(), HttpMethod.POST,
                new HttpEntity<>(requestDTO, headers), String.class).getBody();
        return response;
    }



    public String getAll() {
        String response = restTemplate.exchange(OrderAPIEndpoints.getCreateOrderUrl(), HttpMethod.GET,
                new HttpEntity<>(headers), String.class).getBody();
        return response;
    }

    public boolean checkOrderExistsForUserEndpoint(String userId, UUID orderId) {
        return true;
    }

    public OrderRequestDTO getOrder(UUID orderId) {
        return new OrderRequestDTO();
    }
}
