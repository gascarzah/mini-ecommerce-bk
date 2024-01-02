package com.example.demo.api.web;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.domain.SalesOrder;
import com.example.demo.api.service.PaypalService;
import com.example.demo.api.service.SalesOrderService;
import com.example.demo.api.web.dto.PaypalCaptureDTO;
import com.example.demo.api.web.dto.PaypalOrderDTO;
import com.example.demo.api.web.dto.paypal.OrderCaptureResponse;
import com.example.demo.api.web.dto.paypal.OrderResponse;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {
    private final SalesOrderService salesOrderService;
    private final PaypalService paypalService;

    @PostMapping("/paypal/create")
    public PaypalOrderDTO createPaypalCheckout(@RequestParam String returnUrl,
                                               @RequestBody List<Integer> bookIds
            /* Principal principal */) {
        SalesOrder salesOrder = salesOrderService.create(bookIds);
        OrderResponse orderResponse = paypalService.createOrder(salesOrder, returnUrl, returnUrl);

        String approveUrl = orderResponse
                .getLinks()
                .stream()
                .filter(link -> link.getRel().equals("approve"))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .getHref();

        return new PaypalOrderDTO(approveUrl);
    }

    @PostMapping("/paypal/capture")
    public PaypalCaptureDTO capturePaypalCheckout(@RequestParam String token) {
        OrderCaptureResponse orderCaptureResponse = paypalService.captureOrder(token);

        boolean completed = orderCaptureResponse != null && orderCaptureResponse.getStatus().equals("COMPLETED");
        Integer orderId = null;

        if (completed) {
            orderId = Integer.parseInt(orderCaptureResponse.getPurchaseUnits().get(0).getReferenceId());
            salesOrderService.updateForPaymentCompleted(orderId);
        }
        return new PaypalCaptureDTO(completed, orderId);
    }

}
