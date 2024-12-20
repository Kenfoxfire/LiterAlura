package com.kenfoxfire.LiterAlura.service;

import com.kenfoxfire.LiterAlura.dto.AutorDTO;
import com.kenfoxfire.LiterAlura.model.Autor;
import com.kenfoxfire.LiterAlura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;


    public List<AutorDTO> getAllAutores() {

        var listaAutores = autorRepository.findAllByOrderByNameAsc();
        if (listaAutores.isEmpty()) {
            System.out.println("No hay libros guardados");
            return new ArrayList<>();
        }
        return convierteDatos(listaAutores);
    }


    public List<AutorDTO> getAllAutoresByYearAlive(int year){
        var listaAutores = autorRepository.findAuthorsAliveInYear(year);
        if (listaAutores.isEmpty()) {
            System.out.println("No hay Autores vivos en ese año");
            return new ArrayList<>();
        }
        return convierteDatos(listaAutores);
    }

    private List<AutorDTO> convierteDatos(List<Autor> autores) {
        return autores.stream()
                .map(autor -> new AutorDTO(
                        autor.getBirthYear(),
                        autor.getDeathYear(),
                        autor.getName()
                        ))
                .collect(Collectors.toList());
    }





}
