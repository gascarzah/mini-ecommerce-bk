package com.example.demo.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.api.domain.Category;
import com.example.demo.api.repository.CategoryRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService  {

	CategoryRepository repository;
	
	
	
	public List<Category> getAllCategories() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	
	public Category getCategoryById(Integer id) {
		 Optional<Category> category = repository.findById(id);
         
	        if(category.isPresent()) {
	            return category.get();
	        } else {
	           
	        }
			return null;
	}

	@Transactional
	
	public Category guardar(Category categoria) {
		// TODO Auto-generated method stub
		return repository.save(categoria);
	}

	@Transactional
	
	public void deleteCategoryById(Integer id) {
		repository.deleteById(id);
		
	}

}
