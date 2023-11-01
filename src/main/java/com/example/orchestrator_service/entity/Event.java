package com.example.orchestrator_service.entity;


import com.example.orchestrator_service.enums.EventStatus;
import com.example.orchestrator_service.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@RedisHash("Event")
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@EntityScan
@Builder
public class Event {
    @Id
    private UUID eventId;
    private EventStatus eventStatus;
    private EventType eventType;
    private int eventStep;
    private boolean isRollBack;
}
