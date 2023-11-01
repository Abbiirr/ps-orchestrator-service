package com.example.orchestrator_service.consumer;

import com.example.orchestrator_service.enums.KafkaTopics;
import com.example.orchestrator_service.helper.EventParser;
import com.example.orchestrator_service.helper.KafkaMessager;
import com.example.orchestrator_service.helper.MessageToDTOConverter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentConsumer {

    private final KafkaMessager kafkaMessager;
    private final String paymentRequestTopic;

    public PaymentConsumer(KafkaMessager kafkaMessager) {
        this.kafkaMessager = kafkaMessager;
        this.paymentRequestTopic = KafkaTopics.PAYMENT_REQUEST.getTopicName();
    }

    private String publishNextTopic(String message) {
        UUID eventId = MessageToDTOConverter.getEventIdFromMessage(message);
        String nextTopic =  EventParser.getNextStep(eventId);
        if(nextTopic.equals("Completed")){
            return "Completed";
        }
        return kafkaMessager.sendMessage(nextTopic, message);
    }

    @KafkaListener(topics = "payment_request", groupId = "group_1", containerFactory = "kafkaListenerContainerFactory")
    public String payEventListener(String message) {
//        UUID eventId = MessageToDTOConverter.getEventIdFromMessage(message);
//        String nextTopic =  EventParser.getNextStep(eventId);
//        if(nextTopic.equals("Completed")){
//            return "Completed";
//        }
//        return kafkaMessager.sendMessage(nextTopic, message);
        return publishNextTopic(message);
    }

    @KafkaListener(topics = "post_debit_balance", groupId = "group_1", containerFactory = "kafkaListenerContainerFactory")
    public String debitBalanceListener(String message) {
        return publishNextTopic(message);
    }


}
