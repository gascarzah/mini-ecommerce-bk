package com.example.demo.api.web;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.domain.Category;
import com.example.demo.api.service.CategoryService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/admin/categories")
@AllArgsConstructor

public class CategoryController {

	CategoryService service;

	@GetMapping
	public ResponseEntity<List<Category>> getAllCategorys() {
		List<Category> list = service.getAllCategories();
		System.out.println(list.size());
		return new ResponseEntity<List<Category>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable("id") Integer id) {
		Category entity = service.getCategoryById(id);
		System.out.println(entity);
		return new ResponseEntity<Category>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping("/save/{id}")
	public ResponseEntity<Category> createOrUpdateCategory(@RequestBody Category category) {
		Category categorySaved = service.guardar(category);
		return new ResponseEntity<Category>(categorySaved, new HttpHeaders(), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public HttpStatus deleteCategoryById(@PathVariable("id") Integer id) {
		service.deleteCategoryById(id);
		return HttpStatus.OK;
	}
}
