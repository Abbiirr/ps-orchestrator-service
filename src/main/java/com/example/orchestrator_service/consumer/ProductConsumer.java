package com.example.orchestrator_service.consumer;

import com.example.orchestrator_service.helper.EventParser;
import com.example.orchestrator_service.helper.KafkaMessager;
import com.example.orchestrator_service.helper.MessageToDTOConverter;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.UUID;

public class ProductConsumer {
    private final KafkaMessager kafkaMessager;

    public ProductConsumer(KafkaMessager kafkaMessager) {
        this.kafkaMessager = kafkaMessager;
    }

    @KafkaListener(topics = "post_deduct_products", groupId = "group_1", containerFactory = "kafkaListenerContainerFactory")
    public String productDeductedListener(String message) {
        return kafkaMessager.publishNextTopic(message);
    }
}
