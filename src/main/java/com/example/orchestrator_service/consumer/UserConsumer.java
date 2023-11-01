package com.example.orchestrator_service.consumer;

import com.example.orchestrator_service.helper.EventParser;
import com.example.orchestrator_service.helper.KafkaMessager;
import com.example.orchestrator_service.helper.MessageToDTOConverter;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.UUID;

public class UserConsumer {
    private final KafkaMessager kafkaMessager;

    public UserConsumer(KafkaMessager kafkaMessager) {
        this.kafkaMessager = kafkaMessager;
    }

    @KafkaListener(topics = "post_check_user_and_order", groupId = "group_1", containerFactory = "kafkaListenerContainerFactory")
    public String postUserAndOrderCheckListener(String message) {
        //TODO: Check message to find if last action failed
        //TODO: if failed then reverse action(s)
        //TODO: set isRollback to true and send message to next topic
        UUID eventId = MessageToDTOConverter.getEventIdFromMessage(message);
        String nextTopic =  EventParser.getNextStep(eventId);
        if(nextTopic.equals("Completed")){
            return "Completed";
        }
        return kafkaMessager.sendMessage(nextTopic, message);
    }
}
