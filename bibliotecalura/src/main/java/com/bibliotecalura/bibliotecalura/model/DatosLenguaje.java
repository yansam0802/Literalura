package com.bibliotecalura.bibliotecalura.model;

public enum DatosLenguaje {

            ESPANOL("es", "Español"),
            INGLES("en", "Inglés"),
            ITALIANO("it", "Italiano"),
            FRANCES("fr", "Francés"),
            PORTUGUES("pt", "Portugués");

            private String lenguaje;
            private String tipoLenguaje;

            DatosLenguaje (String lenguaje, String tipoLenguaje){
                this.lenguaje = lenguaje;
                this.tipoLenguaje = tipoLenguaje;
            }

            public static DatosLenguaje fromString(String text) {
                for (DatosLenguaje datosLenguaje : DatosLenguaje.values()) {
                    if (datosLenguaje.lenguaje.equalsIgnoreCase(text)){
                        return datosLenguaje;
                    }
                }
                throw new IllegalArgumentException("Ningún lenguaje encontrado: " + text);
            }

    public static DatosLenguaje fromTipoLenguaje(String text) {
        for (DatosLenguaje datosLenguaje : DatosLenguaje.values()) {
            if (datosLenguaje.tipoLenguaje.equalsIgnoreCase(text.trim())){
                return datosLenguaje;
            }
        }
        throw new IllegalArgumentException("Ningún lenguaje encontrado: " + text);
    }

}
