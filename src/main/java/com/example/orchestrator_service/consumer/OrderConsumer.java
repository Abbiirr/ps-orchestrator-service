package com.example.orchestrator_service.consumer;

import com.example.orchestrator_service.helper.KafkaMessager;
import org.springframework.kafka.annotation.KafkaListener;

public class OrderConsumer {

    private final KafkaMessager kafkaMessager;

    public OrderConsumer(KafkaMessager kafkaMessager) {
        this.kafkaMessager = kafkaMessager;
    }

    @KafkaListener(topics = "order_payment_request", groupId = "group_1", containerFactory = "kafkaListenerContainerFactory")
    public String postOrderCheckoutListener(String message) {
        //TODO: Check User exists & order exists for that user & balance is sufficient(optional)
//        return kafkaMessager.sendMessage("check_user_status", message);
        //TODO: Check product available & deduct
//        return kafkaMessager.sendMessage("deduct_product", message);
        //TODO: Check balance is sufficient & debit balance
//        return kafkaMessager.sendMessage("debit_payment", message);
        //TODO: if any returns error, reverse action(s)
        if (message.equals("success")) {
            return kafkaMessager.sendMessage("order_created", message);
        } else {
            return kafkaMessager.sendMessage("order_failed", message);
        }
    }

    @KafkaListener(topics = "post_get_order", groupId = "group_1", containerFactory = "kafkaListenerContainerFactory")
    public String postGetOrderListener(String message) {

        return kafkaMessager.publishNextTopic(message);

    }
}
