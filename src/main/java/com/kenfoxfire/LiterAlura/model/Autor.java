package com.kenfoxfire.LiterAlura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true, nullable = false)
    private String name;

    private Integer birthYear;
    private Integer deathYear;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Libro> libros;

    public Autor(DatosPersona datosPersona) {
        this.name = datosPersona.name();
        this.birthYear = datosPersona.birthYear();
        this.deathYear = datosPersona.deathYear();
    }

    public Autor() {
    }

    public Long getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "Id=" + Id +
                ", nombre='" + name + '\'' +
                ", birthYear=" + birthYear +
                ", deathYear=" + deathYear +
                '}';
    }
}
