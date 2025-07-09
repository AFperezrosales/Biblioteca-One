package com.libros.librosAluraOne.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.libros.librosAluraOne.models.Autor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroRecordDTO(
      @JsonAlias("title") String titulo,
      @JsonAlias("authors") List<AutorDTO> autorList,
      @JsonAlias("languages") List<String> idiomas

) {
}
