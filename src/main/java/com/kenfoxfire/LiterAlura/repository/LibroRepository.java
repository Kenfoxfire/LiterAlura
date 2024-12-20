package com.kenfoxfire.LiterAlura.repository;

import com.kenfoxfire.LiterAlura.model.Libro;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {

    Optional<Libro> findLibroByTitle(String title);

    @EntityGraph(attributePaths = "autor")
    List<Libro> findAllByOrderByTitleAsc();

    int countByLanguage(String language);

}
