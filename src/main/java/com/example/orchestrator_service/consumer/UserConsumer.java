package com.example.orchestrator_service.consumer;

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
public class UserConsumer {
    private final KafkaMessager kafkaMessager;
    private final Set<String> postUserAndOrderCheckEvents = ConcurrentHashMap.newKeySet();
    public UserConsumer(KafkaMessager kafkaMessager) {
        this.kafkaMessager = kafkaMessager;
    }

    @KafkaListener(topics = "post_check_user_and_order", groupId = "group_1", containerFactory = "kafkaListenerContainerFactory")
    public String postUserAndOrderCheckListener(String message, Acknowledgment acknowledgment) {
        //TODO: Check message to find if last action failed
        if (MessageToDTOConverter.getField(message, "status").equals("fail")) {
            EventParser.setEventToRollback(MessageToDTOConverter.getField(message, "eventId"));
            acknowledgment.acknowledge();
            return "failed";
        }
        //TODO: if failed then reverse action(s)
        //TODO: set isRollback to true and send message to next topic
        String eventId = MessageToDTOConverter.getField(message, "eventId");
        if (postUserAndOrderCheckEvents.contains(eventId)) {
            acknowledgment.acknowledge();
            return "Completed";
        } else {
            postUserAndOrderCheckEvents.add(eventId);
        }
        String nextTopic =  EventParser.getNextStep(eventId);
        if(nextTopic.equals("Completed")){
            return "Completed";
        }
        String response =  kafkaMessager.sendMessage(nextTopic, message);
        acknowledgment.acknowledge();
        return response;
    }
}
