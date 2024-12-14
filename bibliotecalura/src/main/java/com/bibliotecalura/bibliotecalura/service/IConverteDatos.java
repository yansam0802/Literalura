package com.bibliotecalura.bibliotecalura.service;

public interface IConverteDatos {

    <T> T obtenerDatos(String json, Class<T> clase);
}
