package com.example.orchestrator_service.consumer;

import com.example.orchestrator_service.enums.KafkaTopics;
import com.example.orchestrator_service.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CheckoutConsumer {

    private final CheckoutService checkoutService;
    private final RestTemplate restTemplate;

    private static final String topicName = KafkaTopics.CHECKOUT_TOPIC.getTopicName();
    private final Set<String> processedEventIds = ConcurrentHashMap.newKeySet();

    @Autowired
    public CheckoutConsumer(CheckoutService checkoutService, RestTemplate restTemplate) {
        this.checkoutService = checkoutService;
        this.restTemplate = restTemplate;
    }

    @KafkaListener(topics = "checkout_topic", groupId = "group_1", containerFactory = "kafkaListenerContainerFactory")
    public void listenGroupFoo(String message) {
        System.out.println("Received Message in group foo: " + message);
        this.checkoutService.processCheckout(message);


    }
}
