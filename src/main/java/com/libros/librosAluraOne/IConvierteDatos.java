package com.libros.librosAluraOne;

public interface IConvierteDatos {

    <T> T obtenerDatos(String json, Class<T> clase);

}
