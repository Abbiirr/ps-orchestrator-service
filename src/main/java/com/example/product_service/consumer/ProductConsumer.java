package com.example.product_service.consumer;

import com.example.product_service.helper.KafkaMessager;
import org.springframework.kafka.annotation.KafkaListener;

public class ProductConsumer {
    private final KafkaMessager kafkaMessager;

    public ProductConsumer(KafkaMessager kafkaMessager) {
        this.kafkaMessager = kafkaMessager;
    }

    @KafkaListener(topics = "order_payment_request", groupId = "group_1", containerFactory = "kafkaListenerContainerFactory")
    public String productDeductedListener(String message) {
        //TODO: Check User exists & order exists for that user & balance is sufficient(optional)
//        return kafkaMessager.sendMessage("check_user_status", message);
        //TODO: Check product available & deduct
//        return kafkaMessager.sendMessage("deduct_product", message);
        //TODO: Check balance is sufficient & debit balance
        return kafkaMessager.sendMessage("debit_payment", message);
        //TODO: if any returns error, reverse action(s)
    }
}
