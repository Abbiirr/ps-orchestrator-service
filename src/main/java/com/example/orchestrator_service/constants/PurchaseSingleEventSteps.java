package com.example.orchestrator_service.constants;

import com.example.orchestrator_service.enums.KafkaTopics;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Component
public class PurchaseSingleEventSteps {

    public static final String STEP_1_TOPIC = KafkaTopics.CHECK_USER_AND_ORDER.getTopicName();
//    public static final String STEP_1_MESSAGE = ;
//    public static final String STEP_2_URL = UserAPIEndpoints.getCreateOrderForUserEndpoint();

    public static final String STEP_2_TOPIC = KafkaTopics.GET_ORDER.getTopicName();
    public static final String STEP_3_TOPIC = KafkaTopics.DEDUCT_PRODUCTS.getTopicName();
    public static final String STEP_4_TOPIC = KafkaTopics.DEBIT_BALANCE.getTopicName();
    public static final String STEP_5_TOPIC = KafkaTopics.PAYMENT_COMPLETE.getTopicName();

    public static final String STEP_4_TOPIC_REVERSE = KafkaTopics.ADD_PRODUCTS.getTopicName();
    public static final String STEP_5_TOPIC_REVERSE = KafkaTopics.CREDIT_BALANCE.getTopicName();

}
