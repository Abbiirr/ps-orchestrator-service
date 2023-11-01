package com.example.orchestrator_service.helper;

import com.example.orchestrator_service.dto.PaymentRequestDTO;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MessageToDTOConverter {


    public static PaymentRequestDTO convertToPaymentRequestDTO(String message) {
        return new PaymentRequestDTO();
    }
    public static UUID getEventIdFromMessage(String message) {
        return UUID.randomUUID();
    }
}
