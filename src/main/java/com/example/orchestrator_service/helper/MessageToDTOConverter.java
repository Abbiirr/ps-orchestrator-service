package com.example.orchestrator_service.helper;

import com.example.orchestrator_service.dto.PaymentRequestDTO;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

@Component
public class MessageToDTOConverter {


    public static PaymentRequestDTO convertToPaymentRequestDTO(String message) {
        return new PaymentRequestDTO();
    }

    public static String getField(String message, String fieldName) {
        if (message == null || fieldName == null) {
            return null; // Handle null parameters
        }

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Parse the JSON message
            JsonNode jsonNode = objectMapper.readTree(message);

            // Check if the field exists in the JSON
            JsonNode fieldNode = jsonNode.get(fieldName);

            if (fieldNode != null && fieldNode.isTextual()) {
                return fieldNode.asText();
            } else {
                String jsonText = jsonNode.asText();

                // Now parse the jsonText to get the desired field
                JsonNode jsonTextAsNode = objectMapper.readTree(jsonText);

                // Check if the desired field exists in jsonTextAsNode
                JsonNode desiredFieldNode = jsonTextAsNode.get(fieldName);

                if (desiredFieldNode != null && desiredFieldNode.isTextual()) {
                    return desiredFieldNode.asText();
                }
            }

            return null; // Or throw an exception or return a default value as needed
        } catch (Exception e) {
            // Handle parsing or other exceptions
            e.printStackTrace(); // Print the exception for debugging
            return null; // Or throw an exception or return a default value as needed
        }
    }

    public static String setField(String message, String fieldName, Object value) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Parse the JSON message
            JsonNode jsonNode = objectMapper.readTree(message);

            JsonNode dataNode = objectMapper.valueToTree(value);

            // Create a new field "status" with the value "pending"
            ((ObjectNode) jsonNode).put(fieldName, dataNode);

            // Serialize the updated JSON back to a string
            String updatedMessage = objectMapper.writeValueAsString(jsonNode);

            return updatedMessage;
        } catch (Exception e) {
            // Handle parsing or other exceptions
            return null; // Or throw an exception or return a default value as needed
        }
    }

    public static String addStatus(String message, String status) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Parse the JSON message
            JsonNode jsonNode = objectMapper.readTree(message);

            // Create a new field "status" with the value "pending"
            ((ObjectNode) jsonNode).put("status", "ok");

            // Serialize the updated JSON back to a string
            String updatedMessage = objectMapper.writeValueAsString(jsonNode);

            return updatedMessage;
        } catch (Exception e) {
            // Handle parsing or other exceptions
            return null; // Or throw an exception or return a default value as needed
        }
    }
}
