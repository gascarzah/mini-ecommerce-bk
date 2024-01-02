package com.example.demo.api.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.api.domain.Book;
import com.example.demo.api.domain.Category;
import com.example.demo.api.exception.BadRequestException;
import com.example.demo.api.exception.ResourceNotFoundException;
import com.example.demo.api.repository.BookRepository;
import com.example.demo.api.repository.CategoryRepository;
import com.example.demo.api.web.dto.BookFormDTO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    
    public Page<Book> paginate(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Integer id) {
        return bookRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public Book create(BookFormDTO bookFormDTO) {
        boolean slugExists = bookRepository.existsBySlug(bookFormDTO.getSlug());

        if (slugExists) {
            throw new BadRequestException("The slug already exists.");
        }

//        Book book = new Book();
//        book.setTitle(bookFormDTO.getTitle());
//        book.setPrice(bookFormDTO.getPrice());
//        book.setSlug(bookFormDTO.getSlug());
//        book.setDesc(bookFormDTO.getDesc());
//        book.setCoverPath(bookFormDTO.getCoverPath());
//        book.setFilePath(bookFormDTO.getFilePath());

        Book book = new ModelMapper().map(bookFormDTO, Book.class);

        book.setCategory(categoryRepository.findById(Integer.parseInt(bookFormDTO.getCategory())).get());
        return bookRepository.save(book);
    }

    public Book update(Integer id, BookFormDTO bookFormDTO) {
        Book bookFromDb = findById(id);
        boolean slugExists = bookRepository.existsBySlugAndIdNot(bookFormDTO.getSlug(), id);

        if (slugExists) {
            throw new BadRequestException("The slug already exists.");
        }

        new ModelMapper().map(bookFormDTO, bookFromDb);

//        bookFromDb.setTitle(bookFormDTO.getTitle());
//        bookFromDb.setPrice(bookFormDTO.getPrice());
//        bookFromDb.setSlug(bookFormDTO.getSlug());
//        bookFromDb.setDesc(bookFormDTO.getDesc());
//        bookFromDb.setCoverPath(bookFormDTO.getCoverPath());
//        bookFromDb.setFilePath(bookFormDTO.getFilePath());

        return bookRepository.save(bookFromDb);
    }

    public void delete(Integer id) {
        Book bookFromDb = findById(id);
        bookRepository.delete(bookFromDb);
    }
    
    public List<Book> findByCategory(Category category){
    	
    	return bookRepository.findByCategory(category);
    }

}
