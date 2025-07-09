package com.libros.librosAluraOne.repository;

import com.libros.librosAluraOne.models.LibroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILibroRespository extends JpaRepository <LibroEntity, Long> {
}
