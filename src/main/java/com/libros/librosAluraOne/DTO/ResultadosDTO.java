package com.libros.librosAluraOne.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResultadosDTO(
        Integer count,
        String next,
        String previous,
        List<LibroRecordDTO> results
) {}
