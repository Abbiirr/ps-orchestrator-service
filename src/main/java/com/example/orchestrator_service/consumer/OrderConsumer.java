package com.example.orchestrator_service.consumer;

import com.example.orchestrator_service.helper.EventParser;
import com.example.orchestrator_service.helper.KafkaMessager;
import com.example.orchestrator_service.helper.MessageToDTOConverter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OrderConsumer {

    private final KafkaMessager kafkaMessager;
    private final Set<String> postOrderCheckoutEvents = ConcurrentHashMap.newKeySet();
    private final Set<String> postGetOrderEvents = ConcurrentHashMap.newKeySet();

    public OrderConsumer(KafkaMessager kafkaMessager) {
        this.kafkaMessager = kafkaMessager;
    }

    @KafkaListener(topics = "order_payment_request", groupId = "group_1", containerFactory = "kafkaListenerContainerFactory")
    public String postOrderCheckoutListener(String message, Acknowledgment acknowledgment) {
        //TODO: Check User exists & order exists for that user & balance is sufficient(optional)
//        return kafkaMessager.sendMessage("check_user_status", message);
        //TODO: Check product available & deduct
//        return kafkaMessager.sendMessage("deduct_product", message);
        //TODO: Check balance is sufficient & debit balance
//        return kafkaMessager.sendMessage("debit_payment", message);
        //TODO: if any returns error, reverse action(s)
        String response;
        if (message.equals("success")) {
             response = kafkaMessager.sendMessage("order_created", message);
             acknowledgment.acknowledge();
        } else {
            response = kafkaMessager.sendMessage("order_failed", message);
            acknowledgment.acknowledge();
        }
        return response;
    }

    @KafkaListener(topics = "post_get_order", groupId = "group_1", containerFactory = "kafkaListenerContainerFactory")
    public String postGetOrderListener(String message, Acknowledgment acknowledgment) {

        //TODO: Check message to find if last action failed
        if(MessageToDTOConverter.getField(message, "status").equals("fail")){
            EventParser.setEventToRollback(MessageToDTOConverter.getField(message, "eventId"));
            acknowledgment.acknowledge();
            return "failed";
        }
        //TODO: if failed then reverse action(s)
        //TODO: set isRollback to true and send message to next topic
        String eventId = MessageToDTOConverter.getField(message, "eventId");
        if(postGetOrderEvents.contains(eventId)){
            acknowledgment.acknowledge();
            return "Completed";
        }
        else {
            postGetOrderEvents.add(eventId);
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
