package com.kenfoxfire.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("Title") String title,
        @JsonAlias("subjects") List<String> subjects,
        @JsonAlias("authors") List<DatosPersona> authors,
        @JsonAlias("languages") List<String> languages,
        @JsonAlias("copyright") boolean copyright,
        @JsonAlias("media_type") String mediaType,
        @JsonAlias("download_count") Integer downloadCount
) {
}
