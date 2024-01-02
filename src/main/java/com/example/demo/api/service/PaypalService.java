package com.example.demo.api.service;

import com.example.demo.api.domain.SalesOrder;
import com.example.demo.api.web.dto.paypal.OrderCaptureResponse;
import com.example.demo.api.web.dto.paypal.OrderResponse;

public interface PaypalService {
    OrderResponse createOrder(SalesOrder salesOrder, String returnUrl, String cancelUrl);

    OrderCaptureResponse captureOrder(String orderId);
}

