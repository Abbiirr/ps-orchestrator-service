package com.example.product_service.consumer;

import com.example.product_service.helper.KafkaMessager;
import org.springframework.kafka.annotation.KafkaListener;

public class PaymentConsumer {

    private final KafkaMessager kafkaMessager;

    public PaymentConsumer(KafkaMessager kafkaMessager) {
        this.kafkaMessager = kafkaMessager;
    }

    @KafkaListener(topics = "order_payment_request", groupId = "group_1", containerFactory = "kafkaListenerContainerFactory")
    public String payEventListener(String message) {
        //TODO: Check User exists
        return kafkaMessager.sendMessage("check_user_status", message);
        //TODO: Check product available
        //TODO: Check balance is sufficient
        //TODO: if any returns error, reverse action(s)
    }
}
