package com.example.demo.api.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaypalCaptureDTO {
    private boolean completed;
    private Integer orderId;
}
