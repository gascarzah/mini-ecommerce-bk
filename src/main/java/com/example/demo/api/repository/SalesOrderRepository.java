package com.example.demo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.api.domain.SalesOrder;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, Integer> {
}
