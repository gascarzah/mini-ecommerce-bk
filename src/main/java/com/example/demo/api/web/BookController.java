package com.example.demo.api.web;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.domain.Book;
import com.example.demo.api.domain.Category;
import com.example.demo.api.service.BookService;
import com.example.demo.api.web.dto.BookFormDTO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("/api/admin/books")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public Page<Book> paginate(
            @PageableDefault(sort = "title", direction = Sort.Direction.ASC, size = 5) Pageable pageable
    ) {
        return bookService.paginate(pageable);
    }

    @GetMapping("/list")
    public List<Book> list() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public Book get(@PathVariable Integer id) {
        return bookService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Book create(@RequestBody @Validated BookFormDTO bookFormDTO) {
        return bookService.create(bookFormDTO);
    }

    @PutMapping("/{id}")
    public Book update(@PathVariable Integer id, @RequestBody @Validated BookFormDTO bookFormDTO) {
        return bookService.update(id, bookFormDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        bookService.delete(id);
    }
    
    @GetMapping("/category/{id}")
    public List<Book> listByCategory(@PathVariable Integer id) {
    	System.out.println(id);
        return bookService.findByCategory(Category.builder().id(id).build());
    }
}
