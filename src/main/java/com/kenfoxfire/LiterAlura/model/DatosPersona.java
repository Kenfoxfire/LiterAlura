package com.kenfoxfire.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosPersona(
        @JsonAlias("birth_year") Integer birthYear,
        @JsonAlias("death_year") Integer deathYear,
        @JsonAlias("name") String name
) {}
