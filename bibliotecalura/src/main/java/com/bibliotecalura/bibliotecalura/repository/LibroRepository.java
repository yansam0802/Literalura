package com.bibliotecalura.bibliotecalura.repository;

import com.bibliotecalura.bibliotecalura.model.Autor;
import com.bibliotecalura.bibliotecalura.model.DatosLenguaje;
import com.bibliotecalura.bibliotecalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByTituloContainsIgnoreCase(String nombreLibro);

    List<Libro> findByLenguaje(DatosLenguaje lenguaje);

    boolean existsByTitulo(String titulo);

}
