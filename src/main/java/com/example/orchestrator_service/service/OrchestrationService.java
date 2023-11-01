package com.example.orchestrator_service.service;

import com.example.orchestrator_service.dto.DebitBalanceDTO;
import com.example.orchestrator_service.dto.ProductsDTO;
import com.example.orchestrator_service.dto.OrderRequestDTO;
import com.example.orchestrator_service.dto.PaymentRequestDTO;
import com.example.orchestrator_service.helper.MessageToDTOConverter;
import org.springframework.stereotype.Service;

@Service
public class OrchestrationService {

    private final UserService userService;

    private final ProductService productService;

    private final PaymentService paymentService;

    private final OrderService orderService;

    public OrchestrationService(UserService userService, ProductService productService, PaymentService paymentService, OrderService orderService) {
        this.userService = userService;
        this.productService = productService;
        this.paymentService = paymentService;
        this.orderService = orderService;
    }

    public String createPaymentRequest(String message) {
        PaymentRequestDTO paymentRequestDTO =  MessageToDTOConverter.convertToPaymentRequestDTO(message);
        return createPaymentRequest(paymentRequestDTO);
    }



    public String createPaymentRequest(PaymentRequestDTO paymentRequestDTO) {
        if(!userService.checkOrderExistsForUser(paymentRequestDTO.getUserId(), paymentRequestDTO.getOrderId())) {
            return "User or Order does not exists";
        }

        OrderRequestDTO orderRequestDTO = orderService.getOrder(paymentRequestDTO.getOrderId());
        //TODO: handle error here
        if(!productService.deductProducts(new ProductsDTO(orderRequestDTO.getProducts()))) {
            return "Products not available";
        }
        if(!paymentService.debitBalance(new DebitBalanceDTO(orderRequestDTO.getUserId(), orderRequestDTO.getTotalPrice()))) {
            productService.addProducts(new ProductsDTO(orderRequestDTO.getProducts()));
            return "Not enough balance";
        }


        return "Purchase succesful";
    }
}
