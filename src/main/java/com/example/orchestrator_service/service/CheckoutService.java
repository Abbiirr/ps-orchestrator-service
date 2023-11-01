package com.example.orchestrator_service.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CheckoutService {

     @Value("${external.order_service.ip}")
    private String orderServiceIp;

    @Value("${external.order_service.port}")
    private int orderServicePort;

    @Value("${external.order_service.endpoint}")
    private String orderServiceEndpoint;

    private final RestTemplate restTemplate;

    public CheckoutService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void processCheckout(String message) {
        // Make REST POST request to the external API
        String url = orderServiceIp + ":" + orderServicePort + orderServiceEndpoint;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Set any required headers

        // Set the request body if needed
         String requestBody = message;
         HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

//        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        // Process the response as needed
        String responseBody = responseEntity.getBody();
        System.out.println("Response from external API: " + responseBody);

    }
}
