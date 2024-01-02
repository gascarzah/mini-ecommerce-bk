package com.example.demo.api.web;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.domain.Book;
import com.example.demo.api.domain.SalesItem;
import com.example.demo.api.domain.SalesOrder;
import com.example.demo.api.exception.BadRequestException;
import com.example.demo.api.exception.ResourceNotFoundException;
import com.example.demo.api.repository.BookRepository;
import com.example.demo.api.repository.SalesItemRepository;
import com.example.demo.api.repository.SalesOrderRepository;
import com.example.demo.api.service.StorageService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class HomeController {

    private BookRepository bookRepository;
    private SalesOrderRepository salesOrderRepository;
    private SalesItemRepository salesItemRepository;
    private StorageService storageService;

    @GetMapping("/last-books")
    List<Book> getLastBooks() {
        return bookRepository.findTop6ByOrderByCreatedAtDesc();
    }

    @GetMapping("/books")
    Page<Book> getBooks(@PageableDefault(sort = "title", direction = Sort.Direction.ASC, size = 5) Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @GetMapping("/books/{slug}")
    public ResponseEntity<Book> getBookBySlug(@PathVariable String slug) {
        Book book = bookRepository
                .findBySlug(slug)
                .orElse(null);

        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<SalesOrder> getOrder(@PathVariable Integer id) {
        SalesOrder salesOrder = salesOrderRepository
                .findById(id)
                .orElse(null);

        if (salesOrder != null) {
            return ResponseEntity.ok(salesOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/orders/{orderId}/items/{itemId}/book/download")
    Resource downloadBookFromSalesItem(
            @PathVariable Integer orderId,
            @PathVariable Integer itemId
    ) {
        SalesOrder salesOrder = salesOrderRepository
                .findById(orderId)
                .orElseThrow(ResourceNotFoundException::new);

        if (salesOrder.getPaymentStatus().equals(SalesOrder.PaymentStatus.PENDING)) {
            throw new BadRequestException("The order has not been paid yet.");
        }
        SalesItem salesItem = salesItemRepository
                .findByIdAndOrder(itemId, salesOrder)
                .orElseThrow(ResourceNotFoundException::new);

        if (salesItem.getDownloadsAvailable() > 0) {
            salesItem.setDownloadsAvailable(
                    salesItem.getDownloadsAvailable() - 1
            );
            salesItemRepository.save(salesItem);
            return storageService.loadAsResource(salesItem.getBook().getFilePath());
        } else {
            throw new BadRequestException("Can't download this file anymore.");
        }
    }

}
