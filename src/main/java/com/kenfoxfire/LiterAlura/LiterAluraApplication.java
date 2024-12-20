package com.kenfoxfire.LiterAlura;

import com.kenfoxfire.LiterAlura.dto.LibroDTO;
import com.kenfoxfire.LiterAlura.service.AutorService;
import com.kenfoxfire.LiterAlura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

    @Autowired
    private LibroService libroService;
    @Autowired
    private AutorService autorService;

    private final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        SpringApplication.run(LiterAluraApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

        String option = "";

        do {
            System.out.println("""
                    1- Buscar líbro por título y registrar el primero
                    2- Listar líbros registrados
                    3- Listar autores registrados
                    4- listar autores vivos en un determinado año
                    5- listar líbros por idioma
                    0- Salir
                    """);
            option = sc.nextLine();
            switch ( option ) {
                case "1":
                    System.out.println("Ingresa el Titulo del libro que deseas buscar y guardar");
                    libroService.findLibroByTitle(sc.nextLine());
                    break;
                case "2":
                    var libros = libroService.getAllLibros();
                    libros.forEach(l -> {
                        StringBuilder asignaturas = new StringBuilder("          ---------------------\n");
                        for (var asignatura : l.subjects()) {
                            asignaturas.append("             ").append(asignatura).append("\n");
                        }
                        asignaturas.append("           --------------------\n");
                        System.out.printf("""
                                ----------------------------------------------
                                Titulo:                 %s
                                Asignaturas:         \n %s
                                Autor:                  %s
                                Lenguaje:               %s
                                Tiene Copyright         %s
                                Tipo de Libro           %s
                                Cantidad de Descargas:  %s
                                ----------------------------------------------
                                """, l.title(), asignaturas, l.author(), l.language(), l.copyright() ? "Si" : "No", l.mediaType(), l.downloadCount());
                    });
                    break;
                case "3":
                    autorService.getAllAutores().forEach(a -> {
                        System.out.printf("""
                                ----------------------------------------------
                                Nombre:             %s
                                Año de nacimiento:  %s
                                Año de muerte:      %s
                                ----------------------------------------------
                                """, a.name(), a.birthYear(), a.deathYear());
                    });

                    break;
                case "4":
                    System.out.println("Ingrese el año a consultar");
                    var year =  sc.nextLine();
                    var yearInt = 0;
                    try {
                        yearInt = Integer.parseInt(year);
                    }catch (NumberFormatException e){
                        System.out.println(e.getMessage());;
                        System.out.println("Dato incorrecto");
                        return;
                    }
                    autorService.getAllAutoresByYearAlive(yearInt).forEach(a -> {
                    System.out.printf("""
                                ----------------------------------------------
                                Nombre:             %s
                                Año de nacimiento:  %s
                                Año de muerte:      %s
                                ----------------------------------------------
                                """, a.name(), a.birthYear(), a.deathYear());
                });
                    break;
                case "5":
                    System.out.println("""
                        Ingrese el Codigo de idioma
                        Ingles:  en
                        Frances: fr
                        """);
                    var language = sc.nextLine();
                    if (language.equalsIgnoreCase("en")||language.equalsIgnoreCase("fr")){
                        var cantLibros = libroService.countLibrosByLanguage(language);
                        System.out.println("Hay " + cantLibros + " libros en " + ( language.equalsIgnoreCase("en")? "Ingles" : "Frances" ));
                        continue;
                    }
                    System.out.println("Idioma no encontrado");
                    break;
                default:
                    break;
            }

        } while (!option.equals("0"));

    }
}
