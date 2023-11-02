package com.example.orchestrator_service.helper;

import com.example.orchestrator_service.constants.PurchaseSingleEventSteps;
import com.example.orchestrator_service.entity.Event;
import com.example.orchestrator_service.enums.EventStatus;
import com.example.orchestrator_service.enums.EventType;
import com.example.orchestrator_service.exception.EventNotFoundException;
import com.example.orchestrator_service.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EventParser {

    @Autowired
    private static EventRepository eventRepository;


    public EventParser(EventRepository eventRepository) {
        EventParser.eventRepository = eventRepository;
    }

   public static String getNextStep(String eventId) {
    try {
        Event event = findEventById(eventId);
        if(event == null){
            return "Event not found";
        }
//        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found with eventId: " + eventId));

        if (event.getEventStatus() == EventStatus.COMPLETED) {
            return "COMPLETED";
        }

        if (event.getEventType() == EventType.DIRECT) {
            return "DIRECT, no workflow needed";
        }

        String nextStep = "ERROR";

        if (event.getEventType() == EventType.PURCHASE_SINGLE) {
            if (!event.isRollBack()) {
                nextStep = getPurchaseSingleForwardStep(event);
                if (nextStep.equals("COMPLETED")) {
                    event.setEventStatus(EventStatus.COMPLETED);
                }
            } else {
                nextStep = getPurchaseSingleRollbackStep(event);
                if (nextStep.equals("COMPLETED")) {
                    event.setEventStatus(EventStatus.COMPLETED);
                }
            }
        }

        eventRepository.save(event);
        return nextStep;
    } catch (EventNotFoundException e) {
        // Handle the case where the Event is not found
        e.printStackTrace(); // Print the exception for debugging
        return "Event not found";
    }
}

    private static String getPurchaseSingleForwardStep(Event event) {
        int step = event.getEventStep();
        String nextStep = "ERROR";
        if (step == 1) {
            nextStep =  PurchaseSingleEventSteps.STEP_1_TOPIC;
        } else if (step == 2) {
            nextStep = PurchaseSingleEventSteps.STEP_2_TOPIC;
        } else if (step == 3) {
            nextStep = PurchaseSingleEventSteps.STEP_3_TOPIC;
        } else if (step == 4) {
            nextStep = PurchaseSingleEventSteps.STEP_4_TOPIC;
        } else if (step == 5) {
            nextStep = "COMPLETED";
        }
        event.setEventStep(step + 1);
        return nextStep;
    }

    private static String getPurchaseSingleRollbackStep(Event event) {
        int step = event.getEventStep();
        String nextStep = "ERROR";
         if (step == 5) {
            nextStep =  PurchaseSingleEventSteps.STEP_5_TOPIC_REVERSE;
        } else {
            nextStep =  "COMPLETED";
        }
         event.setEventStep(step - 1);
        return nextStep;
    }

    public static String setEventToRollback(String eventId){
        Event event = eventRepository.findById(eventId).orElseThrow();
        event.setRollBack(true);
        eventRepository.save(event);
        return "success";
    }

    public static Event findEventById(String eventId) {
    Event event = eventRepository.findById(eventId).orElse(null);
    if (event == null) {
        throw new EventNotFoundException("Event not found with eventId: " + eventId);
    }
    return event;
}
}
