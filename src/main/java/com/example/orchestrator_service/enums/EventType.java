package com.example.orchestrator_service.enums;

public enum EventType {
    DIRECT("DIRECT"),
    PURCHASE_SINGLE("PURCHASE_SINGLE")
    ;

    private final String type;

    EventType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return type;
    }
}
