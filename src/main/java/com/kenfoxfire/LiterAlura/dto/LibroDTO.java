package com.kenfoxfire.LiterAlura.dto;

import java.util.List;

public record LibroDTO(
        String title,
        List<String> subjects,
        AutorDTO author,
        String language,
        boolean copyright,
        String mediaType,
        Integer downloadCount
) {
}
