package com.example.demo.api.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

// pedido
@Data
@Entity
public class SalesOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Float total;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private LocalDateTime createdAt;

//    private Integer customerId;

    // varios pedidos pueden ser de un usuario
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private User customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<SalesItem> items;

    public enum PaymentStatus {
        PENDING,
        PAID
    }
}
