package com.example.orchestrator_service.consumer;

import com.example.orchestrator_service.enums.KafkaTopics;
import com.example.orchestrator_service.helper.EventParser;
import com.example.orchestrator_service.helper.KafkaMessager;
import com.example.orchestrator_service.helper.MessageToDTOConverter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PaymentConsumer {

    private final KafkaMessager kafkaMessager;
    private final String paymentRequestTopic;
    private final Set<String> payEvents = ConcurrentHashMap.newKeySet();
    private final Set<String> debitBalanceEvents = ConcurrentHashMap.newKeySet();

    public PaymentConsumer(KafkaMessager kafkaMessager) {
        this.kafkaMessager = kafkaMessager;
        this.paymentRequestTopic = KafkaTopics.PAYMENT_REQUEST.getTopicName();
    }


    @KafkaListener(topics = "payment_request", groupId = "group_1", containerFactory = "kafkaListenerContainerFactory")
    public String payEventListener(String message, Acknowledgment acknowledgment) {
        String eventId = MessageToDTOConverter.getField(message, "eventId");
        if(payEvents.contains(eventId)){
            acknowledgment.acknowledge();
            return "Completed";
        }
        else {
            payEvents.add(eventId);
        }
        String response = kafkaMessager.publishNextTopic(message);
        acknowledgment.acknowledge();
        return response;
    }

    @KafkaListener(topics = "post_debit_balance", groupId = "group_1", containerFactory = "kafkaListenerContainerFactory")
    public String debitBalanceListener(String message, Acknowledgment acknowledgment) {
        //TODO: Check message to find if last action failed
        if (MessageToDTOConverter.getField(message, "status").equals("fail")) {
            EventParser.setEventToRollback(MessageToDTOConverter.getField(message, "eventId"));
//            acknowledgment.acknowledge();
//            return "failed";
        }
        //TODO: if failed then reverse action(s)

        String eventId = MessageToDTOConverter.getField(message, "eventId");
        if (debitBalanceEvents.contains(eventId)) {
            acknowledgment.acknowledge();
            return "Completed";
        } else {
            debitBalanceEvents.add(eventId);
        }
        EventParser.setEventToRollback(eventId);
        //TODO: set isRollback to true and send message to next topic

        String nextTopic = EventParser.getNextStep(eventId);
        if (nextTopic.equals("Completed")) {
            return "Completed";
        }
        String response = kafkaMessager.sendMessage(nextTopic, message);
        acknowledgment.acknowledge();
        return response;
    }


}
