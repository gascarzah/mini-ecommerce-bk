package com.example.demo.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.api.domain.Book;
import com.example.demo.api.domain.Category;


public interface BookRepository extends JpaRepository<Book, Integer> {

    Optional<Book> findBySlug(String slug);
    boolean existsBySlug(String slug);

    // buscar todos los libros que tengan como slug un valor dado; pero
    // se debe excluir de la búsqueda aquel libro que tenga como id otro valor dado.
    Optional<Book> findBySlugAndIdNot(String slug, Integer id);
    boolean existsBySlugAndIdNot(String slug, Integer id);

    // retorna los 6 últimos libros en base a la fecha de creación en una lista
    List<Book> findTop6ByOrderByCreatedAtDesc();

    List<Book> findByCategory(Category category);
}
