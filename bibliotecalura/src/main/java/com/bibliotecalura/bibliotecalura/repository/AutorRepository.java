package com.bibliotecalura.bibliotecalura.repository;

import com.bibliotecalura.bibliotecalura.model.Autor;
import com.bibliotecalura.bibliotecalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface AutorRepository extends JpaRepository<Autor, Long> {

        @Query(value = "SELECT a FROM autores a WHERE a.fecha_de_fallecimiento >= :fechaDeFallecimiento", nativeQuery = true)
        List<Autor> fechaDeNacimientoYFallecimiento(Integer fechaDeFallecimiento);

        @Query(value = "SELECT * FROM autores a WHERE LOWER(a.nombre) LIKE LOWER(CONCAT('%', :autor, '%'))", nativeQuery = true)
        Optional<Autor> buscarAutorPorNombre(@Param("autor") String autor);

        // List<Autor> findByFechaDeNacimientoLessThanEqualAndFechaDeFallecimientoGreaterThanEqual(Integer fechaDeNacimiento, Integer fechaDeFallecimiento);
}
