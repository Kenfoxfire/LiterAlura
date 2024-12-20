package com.kenfoxfire.LiterAlura.service;

import com.kenfoxfire.LiterAlura.dto.LibroDTO;
import com.kenfoxfire.LiterAlura.dto.AutorDTO;
import com.kenfoxfire.LiterAlura.model.*;
import com.kenfoxfire.LiterAlura.repository.AutorRepository;
import com.kenfoxfire.LiterAlura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibroService {

    @Autowired
    private HttpService httpService;
    @Autowired
    private UtilsService utilsService;
    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private AutorRepository autorRepository;

    public void findLibroByTitle(String title) {
        var res = httpService.findLibroByTitle(title);
        List<DatosLibro> data = utilsService.transformData(res, ApiResponse.class).libros();
        var firstLibro = data.isEmpty() ? null : data.getFirst();
        if (firstLibro == null) {
            System.out.println("Libro no encontrado");
            return;
        }


        try {
            var existLibro = libroRepository.findLibroByTitle(firstLibro.title()).isEmpty();
            if (!existLibro) {
                System.out.println("Ya existe el libro con titulo " + firstLibro.title());
                return;
            }

            var autorName = "Desconocido";
            if (!firstLibro.authors().isEmpty()) {
                autorName = firstLibro.authors().getFirst().name();
            }

            // Buscar si el autor ya existe en la base de datos
            Autor autor = autorRepository.findAutorByName(autorName)
                    .orElseGet(() -> {
                        // Si no existe, guardarlo
                        Autor nuevoAutor = new Autor(firstLibro.authors().isEmpty() ? new DatosPersona(0, 0, "Desconocido") : firstLibro.authors().getFirst());
                        return autorRepository.save(nuevoAutor); // Asegúrate de tener un método para guardar autores
                    });

            // Crear el libro con el autor persistido
            Libro nuevoLibro = new Libro(firstLibro, autor);
            Libro save = libroRepository.save(nuevoLibro);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Error de integridad en la base de datos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al guardar el libro: " + e.getMessage());
        }
    }

    public List<LibroDTO> getAllLibros() {

        var listaLibros = libroRepository.findAllByOrderByTitleAsc();
        if (listaLibros.isEmpty()) {
            System.out.println("No hay libros guardados");
            return new ArrayList<>();
        }
        return convierteDatos(listaLibros);
    }

    private List<LibroDTO> convierteDatos(List<Libro> libros) {
        return libros.stream()
                .map(libro -> new LibroDTO(
                        libro.getTitle(),
                        libro.getSubjects(),
                        new AutorDTO(
                                libro.getAutor().getBirthYear(),
                                libro.getAutor().getDeathYear(),
                                libro.getAutor().getName()
                        ),
                        libro.getLanguage(),
                        libro.isCopyright(),
                        libro.getMediaType(),
                        libro.getDownloadCount()))
                .collect(Collectors.toList());
    }

    public int countLibrosByLanguage(String language) {
        return libroRepository.countByLanguage(language);
    }

}
