package com.example.product_service.consumer;

import com.example.product_service.enums.KafkaTopics;
import com.example.product_service.service.CheckoutService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CheckoutConsumer {

    CheckoutService checkoutService;

    private static final String topicName = KafkaTopics.CHECKOUT_TOPIC.getTopicName();
    @KafkaListener(topics = "checkout_topic", groupId = "group_1", containerFactory = "kafkaListenerContainerFactory")
    public void listenGroupFoo(String message) {
        System.out.println("Received Message in group foo: " + message);
        this.checkoutService.processCheckout();
    }
}
