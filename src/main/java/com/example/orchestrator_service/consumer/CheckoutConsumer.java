package com.example.orchestrator_service.consumer;

import com.example.orchestrator_service.enums.KafkaTopics;
import com.example.orchestrator_service.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CheckoutConsumer {

    private final CheckoutService checkoutService;
    private final RestTemplate restTemplate;

    private static final String topicName = KafkaTopics.CHECKOUT_TOPIC.getTopicName();

    @Autowired
    public CheckoutConsumer(CheckoutService checkoutService, RestTemplate restTemplate) {
        this.checkoutService = checkoutService;
        this.restTemplate = restTemplate;
    }

    @KafkaListener(topics = "checkout_topic", groupId = "group_1", containerFactory = "kafkaListenerContainerFactory")
    public void listenGroupFoo(String message) {
        System.out.println("Received Message in group foo: " + message);
        this.checkoutService.processCheckout(message);

//        // Make REST POST request to the external API
//        String url = "http://localhost:8082/order/create";
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        // Set any required headers
//
//        // Set the request body if needed
//         String requestBody = message;
//         HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
//
////        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
//
//        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
//        // Process the response as needed
//        String responseBody = responseEntity.getBody();
//        System.out.println("Response from external API: " + responseBody);
    }
}
