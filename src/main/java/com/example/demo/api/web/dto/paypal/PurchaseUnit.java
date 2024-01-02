package com.example.demo.api.web.dto.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PurchaseUnit {
    // aquí enviaremos el ID de un objeto SalesOrder que representa una venta persistida en nuestra BD
    // y nos servirá para saber a qué venta corresponde el pago de paypal
    @JsonProperty("reference_id")
    private String referenceId;

    private Amount amount;
    private List<OrderItem> items;
}