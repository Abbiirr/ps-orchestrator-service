package com.example.orchestrator_service.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaymentAPIEndpoints {

    @Value("${external.payment_service.ip}")
    private static String paymentServiceIp;

    @Value("${external.payment_service.port}")
    private static String paymentServicePort;

    @Value("${endpoint.payment}")
    private static String paymentEndpoint;

    @Value("${endpoint.debit}")
    private static String debitPaymentEndpoint;

    @Value("${endpoint.credit}")
    private static String creditPaymentEndpoint;

    @Value("${endpoint.get.all}")
    private static String getAllEndpoint;

    private static String getPaymentUrl() {
        return "http://" + paymentServiceIp + ":" + paymentServicePort + paymentEndpoint;
    }

    public static String getDebitPaymentUrl() {
        return getPaymentUrl() + debitPaymentEndpoint;
    }

    public static String getCreditPaymentUrl() {
        return getPaymentUrl() + creditPaymentEndpoint;
    }

    public static String getGetAllPaymentsUrl() {
        return getPaymentUrl() + getAllEndpoint;
    }
}
