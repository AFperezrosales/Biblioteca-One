package com.libros.librosAluraOne.models;

import com.libros.librosAluraOne.DTO.LibroRecordDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LibroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 1000, unique = true)
    private String titulo;

    private List<String> idiomas;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores;


    public LibroEntity(LibroRecordDTO libroRecordDTO) {
       this.titulo = libroRecordDTO.titulo();
       this.idiomas = libroRecordDTO.idiomas();
        this.autores = libroRecordDTO.autorList()
                .stream()
                .map(Autor::new)
                .collect(Collectors.toList());
    }
}
