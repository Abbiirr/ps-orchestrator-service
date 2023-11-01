package com.example.orchestrator_service.controller;

import com.example.orchestrator_service.dto.PaymentRequestDTO;
import com.example.orchestrator_service.service.OrchestrationService;
import com.example.orchestrator_service.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService service;
    private final OrchestrationService orchestrationService;

    public PaymentController(PaymentService service, OrchestrationService orchestrationService) {
        this.service = service;
        this.orchestrationService = orchestrationService;
    }

    @PostMapping("/pay")
    public String checkout(@RequestBody final PaymentRequestDTO requestDTO){
        return this.orchestrationService.createPaymentRequest(requestDTO);
    }
}
