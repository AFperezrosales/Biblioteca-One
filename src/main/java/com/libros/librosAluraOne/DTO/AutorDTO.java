package com.libros.librosAluraOne.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorDTO(
            @JsonAlias("name") String nombre,
            @JsonAlias("birth_year") Integer nacimiento,
            @JsonAlias("death_year") Integer fechaDeMuerte
    ) {}

