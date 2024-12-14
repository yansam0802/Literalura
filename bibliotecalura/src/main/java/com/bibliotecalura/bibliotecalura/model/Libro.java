package com.bibliotecalura.bibliotecalura.model;

import com.bibliotecalura.bibliotecalura.dto.DatosLibro;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Integer idLibro;

    @Column(unique = true)
    private String titulo;

    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Autor> datosAutor;

    @Enumerated(EnumType.STRING)
    private DatosLenguaje lenguaje;
    private Integer totalDeDescargas;

    private Libro(){}

    public Libro(DatosLibro datosLibro){
        this.idLibro = datosLibro.id();
        this.titulo = datosLibro.titulo();
        this.datosAutor = datosLibro.autor().stream()
                .map(d -> new Autor(d)) // Convertir DatosAutor a Autor
                .collect(Collectors.toList());
        this.lenguaje = DatosLenguaje.fromString(datosLibro.lenguaje().get(0).trim());
        this.totalDeDescargas = datosLibro.totalDeDescargas();
    }

    @Override
    public String toString() {
        return  "ID = " + idLibro +
                ", TÍTULO = '" + titulo + '\'' +
                ", AUTOR = " + datosAutor +
                ", LENGUAJE = " + lenguaje +
                ", TOTAL DE DESCARGAS = " + totalDeDescargas;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Integer getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    public List<Autor> getDatosAutor() {
        return datosAutor;
    }

    public void setDatosAutor(List<Autor> datosAutor) {
        datosAutor.forEach(a -> a.setLibro(this));
        this.datosAutor = datosAutor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutor() {
        return datosAutor;
    }

    public void setAutor(List<Autor> datosAutor) {
        datosAutor.forEach(a -> a.setLibro(this));
         this.datosAutor = datosAutor;
    }

    public DatosLenguaje getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(DatosLenguaje lenguaje) {
        this.lenguaje = lenguaje;
    }

    public Integer getTotalDeDescargas() {
        return totalDeDescargas;
    }

    public void setTotalDeDescargas(Integer totalDeDescargas) {
        this.totalDeDescargas = totalDeDescargas;
    }
}
