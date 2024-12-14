package com.bibliotecalura.bibliotecalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosResults(
       @JsonAlias("results") List<DatosLibro> resultados
)
{}
