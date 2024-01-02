package com.example.demo.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.api.domain.SalesItem;
import com.example.demo.api.domain.SalesOrder;

public interface SalesItemRepository extends JpaRepository<SalesItem, Integer> {

    Optional<SalesItem> findByIdAndOrder(Integer id, SalesOrder salesOrder);
    Optional<SalesItem> findByIdAndOrderId(Integer id, Integer orderId);

}
