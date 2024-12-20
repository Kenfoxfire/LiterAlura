package com.kenfoxfire.LiterAlura.repository;

import com.kenfoxfire.LiterAlura.model.Autor;
import com.kenfoxfire.LiterAlura.model.Libro;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository  extends JpaRepository<Autor,Long>  {


    Optional<Autor> findAutorByName(String name);

    List<Autor> findAllByOrderByNameAsc();

    @Query("SELECT a FROM Autor a WHERE :year BETWEEN a.birthYear AND COALESCE(a.deathYear, :year)")
    List<Autor> findAuthorsAliveInYear(@Param("year") int year);
}
