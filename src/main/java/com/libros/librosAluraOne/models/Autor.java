package com.libros.librosAluraOne.models;

import com.libros.librosAluraOne.DTO.AutorDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.IllegalFormatException;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
 public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Integer fechaDeMuerte;

    private Integer nacimiento;

    @ManyToMany(mappedBy = "autores", fetch = FetchType.EAGER,cascade = CascadeType.ALL)

    private List<LibroEntity> libros;

   public Autor(AutorDTO autorDTO) {
      this.nombre = autorDTO.nombre();
      this.nacimiento = autorDTO.nacimiento();
      this.fechaDeMuerte = autorDTO.fechaDeMuerte();
   }
}
