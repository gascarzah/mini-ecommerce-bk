package com.example.demo.api.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.api.domain.Book;
import com.example.demo.api.domain.SalesItem;
import com.example.demo.api.domain.SalesOrder;
import com.example.demo.api.domain.User;
import com.example.demo.api.exception.ResourceNotFoundException;
import com.example.demo.api.repository.BookRepository;
import com.example.demo.api.repository.SalesOrderRepository;
import com.example.demo.api.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SalesOrderService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final SalesOrderRepository salesOrderRepository;

    public SalesOrder create(List<Integer> bookIds) {
        User user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            String email = authentication.getName();

            user = userRepository.findByEmail(email)
                    .orElse(null);
        }
        SalesOrder salesOrder = new SalesOrder();
        List<SalesItem> items = new ArrayList<>();
        float total = 0;

        for (int bookId : bookIds) {
            Book book = bookRepository
                    .findById(bookId)
                    .orElseThrow(ResourceNotFoundException::new);

            SalesItem salesItem = new SalesItem();
            salesItem.setBook(book);
            salesItem.setPrice(book.getPrice());
            salesItem.setDownloadsAvailable(3);
            salesItem.setOrder(salesOrder);

            items.add(salesItem);
            total += salesItem.getPrice();
        }
        salesOrder.setPaymentStatus(SalesOrder.PaymentStatus.PENDING);
        salesOrder.setCreatedAt(LocalDateTime.now());
        salesOrder.setTotal(total);
        salesOrder.setItems(items);
        salesOrder.setCustomer(user);

        return salesOrderRepository.save(salesOrder);
    }

    public SalesOrder updateForPaymentCompleted(Integer id) {
        SalesOrder salesOrder = salesOrderRepository
                .findById(id)
                .orElseThrow(RuntimeException::new);

        salesOrder.setPaymentStatus(SalesOrder.PaymentStatus.PAID);
        return salesOrderRepository.save(salesOrder);
    }

}
