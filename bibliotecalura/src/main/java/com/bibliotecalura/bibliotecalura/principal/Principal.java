package com.bibliotecalura.bibliotecalura.principal;

import com.bibliotecalura.bibliotecalura.dto.DatosResults;
import com.bibliotecalura.bibliotecalura.model.*;
import com.bibliotecalura.bibliotecalura.repository.AutorRepository;
import com.bibliotecalura.bibliotecalura.repository.LibroRepository;
import com.bibliotecalura.bibliotecalura.service.ConsumoAPI;
import com.bibliotecalura.bibliotecalura.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/";
    private LibroRepository repository;
    private AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.repository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraMenu() {

        var opcion = -1;

        while (opcion != 0) {
            try {
                var menu = """
                              |------------------|
                              |  MENÚ PRINCIPAL  |
                              |------------------|
                              Ingrese un número del 0 al 5...
                        
                        ||===============================||
                        || \033[31m1\033[39;49m - Buscar libro en general   ||
                        || \033[31m2\033[39;49m - Listar libros registrados || 
                        ||     en la base de datos       ||
                        || \033[31m3\033[39;49m - Buscar libro por titulo   ||
                        ||     en la base de datos       ||
                        || \033[31m4\033[39;49m - Listar libros por idioma  ||
                        || \033[31m5\033[39;49m - Lista autores registrados ||
                        || \033[31m6\033[39;49m - Lista autores vivos en un ||
                        ||     determinado año           ||
                        || \033[31m7\033[39;49m - Buscar autores por nombre ||
                        ||     en la base de datos       ||
                        || \033[31m8\033[39;49m - Estadisticas de Libros    ||
                        ||                               ||
                        || \033[32m0\033[39;49m - Salir                     ||
                        ||===============================||
                        """;

                System.out.println(menu);
                if (teclado.hasNextInt()) {
                    opcion = teclado.nextInt();
                    teclado.nextLine();

                    switch (opcion) {
                        case 1 -> buscarLibroPorTitulo();
                        case 2 -> mostrarLibrosBuscados();
                        case 3 -> buscarLibroPorTitloRegistrado();
                        case 4 -> listarLibrosPorIdioma();
                        case 5 -> listaAutoresRegistrados();
                        case 6 -> listarAutoresVivos();
                        case 7 -> buscarAutoresPorNombre();
                        case 8 -> estadisticaLibros();
                        case 0 -> System.out.println("Cerrando la aplicación...");
                        default -> System.out.println("Entrada inválida. Por favor, ingrese un número.");
                    }
                } else {
                    System.out.println("Entrada inválida. Por favor, ingrese un número.");
                    teclado.nextLine();
                }
            } catch (Exception e) {
                System.out.println("Ocurrió un error: " + e.getMessage());
                teclado.nextLine();
            }
        }
    }

    private DatosResults getDatosResult() {
        System.out.println("Introduce el nombre del libro: ");
        var nombreLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "%20"));
        System.out.println(json);
        DatosResults datos = conversor.obtenerDatos(json, DatosResults.class);
        return datos;
    }

    private void buscarLibroPorTitulo() {
        DatosResults datosResults = getDatosResult();

        // Validar si hay resultados
        if (datosResults.resultados().isEmpty()) {
            System.out.println("No se encontraron libros con el título proporcionado.");
            return;
        }

        var datosLibro = datosResults.resultados().get(0); // Obtener el primer resultado
        String tituloLibro = datosLibro.titulo();
        // Verificar si el libro ya existe en la base de datos
        if (repository.existsByTitulo(tituloLibro)) {
            System.out.println("El libro con el título '" + tituloLibro + "' ya existe en la base de datos.");
            return;
        }
        Libro libro = new Libro(datosLibro);
        System.out.println("Libro encontrado: " + tituloLibro);
        List<Autor> autores = datosLibro.autor().stream()
                .map(datosAutor -> {
                    Autor autor = new Autor(datosAutor);
                    autor.setLibro(libro);
                    return autor;
                })
                .toList();
        libro.setDatosAutor(autores);
        repository.save(libro);
        System.out.printf("""
                        
                        ---------------\033[31m LIBRO \033[39;49m---------------
                            ID: \033[32m %d \033[39;49m
                            TITULO: \033[32m %s \033[39;49m
                            AUTOR: \033[32m %s \033[39;49m
                            LENGUAJE: \033[34m %s \033[39;49m
                            TOTAL DESCARGAS: \033[32m %d \033[39;49m
                            -----------------------------------\n
                        """,
                datosLibro.id(),
                tituloLibro,
                datosLibro.autor().stream().map(a -> a.nombre()).collect(Collectors.joining(", ")),
                datosLibro.lenguaje().get(0),
                datosLibro.totalDeDescargas());
    }

    private void mostrarLibrosBuscados() {
        List<Libro> libros = repository.findAll();
        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(l -> System.out.printf("""
                                
                                ---------------\033[31m LIBRO \033[39;49m---------------
                                ID: \033[32m %d \033[39;49m
                                TITULO: \033[32m %s \033[39;49m
                                AUTOR: \033[32m %s \033[39;49m
                                LENGUAJE: \033[34m %s \033[39;49m
                                TOTAL DESCARGAS: \033[32m %d \033[39;49m
                                -----------------------------------
                                """,
                        l.getIdLibro(),
                        l.getTitulo(),
                        l.getAutor().stream().map(a -> a.getNombre()).collect(Collectors.joining(", ")),
                        l.getLenguaje(),
                        l.getTotalDeDescargas()));
    }

    private void buscarLibroPorTitloRegistrado() {
        System.out.println("Escribe el nombre del libro que deseas buscar: ");
        var nombreLibro = teclado.nextLine();

        Optional<Libro> libroBuscado = repository.findByTituloContainsIgnoreCase(nombreLibro);
        if (libroBuscado.isPresent()) {
            System.out.printf("""
                            
                            El libro buscado es:
                            ---------------\033[31m LIBRO \033[39;49m---------------
                            ID: \033[32m %d \033[39;49m
                            TITULO: \033[32m %s \033[39;49m
                            AUTOR: \033[32m %s \033[39;49m
                            LENGUAJE: \033[34m %s \033[39;49m
                            TOTAL DESCARGAS: \033[32m %d \033[39;49m
                            -----------------------------------
                            
                            """,
                    libroBuscado.get().getIdLibro(),
                    libroBuscado.get().getTitulo(),
                    libroBuscado.get().getAutor().stream().map(a -> a.getNombre()).collect(Collectors.joining(", ")),
                    libroBuscado.get().getLenguaje(),
                    libroBuscado.get().getTotalDeDescargas());
        } else {
            System.out.println("Libro no encontrado");
        }
    }

    private void buscarLibrosPorIdioma(DatosLenguaje lenguaje, String idioma) {
        List<Libro> librosPorIdioma = repository.findByLenguaje(DatosLenguaje.valueOf(lenguaje.name()));
        if (librosPorIdioma.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma: " + idioma);
        } else {
            System.out.println("Libros del idioma " + idioma + " encontrado:");
            System.out.println("La cantidad de libros del mismo idioma: " + librosPorIdioma.stream().count());
            librosPorIdioma.forEach(l -> System.out.printf("""
                            
                            ---------------\033[31m LIBRO \033[39;49m---------------
                            ID: \033[32m %d \033[39;49m
                            TITULO: \033[32m %s \033[39;49m
                            AUTOR: \033[32m %s \033[39;49m
                            LENGUAJE: \033[34m %s \033[39;49m
                            TOTAL DESCARGAS: \033[32m %d \033[39;49m
                            -----------------------------------
                            """,
                    l.getIdLibro(),
                    l.getTitulo(),
                    l.getAutor().stream().map(a -> a.getNombre()).collect(Collectors.joining(", ")),
                    l.getLenguaje(),
                    l.getTotalDeDescargas()));
        }
    }

    private void listarLibrosPorIdioma() {

        var opcion = -1;

        while (opcion != 0) { // Bucle para permitir múltiples intentos
            try {
                var menu = """
                            Selecciona el idioma del libro que deseas buscar:
                            1. Español
                            2. Inglés
                            3. Italiano
                            4. Francés
                            5. Portugués
                        
                            0. Salir
                        """;

                System.out.println(menu);
                if (teclado.hasNextInt()) {
                    opcion = teclado.nextInt();
                    teclado.nextLine();

                    switch (opcion) {
                        case 1 -> buscarLibrosPorIdioma(DatosLenguaje.ESPANOL, "Español");
                        case 2 -> buscarLibrosPorIdioma(DatosLenguaje.INGLES, "Inglés");
                        case 3 -> buscarLibrosPorIdioma(DatosLenguaje.ITALIANO, "Italiano");
                        case 4 -> buscarLibrosPorIdioma(DatosLenguaje.FRANCES, "Francés");
                        case 5 -> buscarLibrosPorIdioma(DatosLenguaje.PORTUGUES, "Portugués");
                        case 0 -> System.out.println("Saliendo de la búsqueda por idioma...");
                        default -> System.out.println("Opción no válida. Por favor, elige un número entre 0 y 5.");
                    }
                } else {
                    System.out.println("Entrada inválida. Por favor, ingrese un número.");
                    teclado.nextLine();
                }
            } catch (Exception e) {
                System.out.println("No se permite texto...");
                teclado.nextLine();
            }
        }
    }

    private void listaAutoresRegistrados() {
        List<Autor> autorList = autorRepository.findAll();
        autorList.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(a -> System.out.printf("""
                                
                                ---------------\033[31m AUTOR \033[39;49m---------------
                                AUTOR: \033[32m %s \033[39;49m
                                FECHA DE NACIMIENTO: \033[34m %d \033[39;49m
                                FECHA DE FALLECIMIENTO: \033[34m %d \033[39;49m
                                -----------------------------------
                                """,
                        a.getNombre(),
                        a.getFechaDeNacimiento(),
                        a.getFechaDeFallecimiento()));
    }

    private void listarAutoresVivos() {

        System.out.println("Ingrese año: ");
        if (teclado.hasNextInt()) {
            var fecha = teclado.nextInt();
            System.out.println("Año ingresado: " + fecha);

            List<Autor> autor = autorRepository.fechaDeNacimientoYFallecimiento(fecha);
            if (autor.isEmpty()) {
                System.out.println("Autor no encontrado");
            } else {
                autor.stream()
                        .forEach(a -> System.out.printf("""
                                        
                                        ---------------\033[31m AUTOR \033[39;49m---------------
                                        ID: \033[32m %d \033[39;49m
                                        TITULO: \033[32m %s \033[39;49m
                                        AUTOR: \033[32m %s \033[39;49m
                                        FECHA DE NACIMIENTO: \033[34m %d \033[39;49m
                                        FECHA DE FALLECIMIENTO: \033[32m %d \033[39;49m
                                        -----------------------------------
                                        """,
                                a.getId(),
                                a.getLibro().getTitulo(),
                                a.getNombre(),
                                a.getFechaDeNacimiento(),
                                a.getFechaDeFallecimiento()));
            }
        } else {
            System.out.println("ERROR: no puedes escribir texto, solo números enteros");
            teclado.nextLine();
        }
    }

    public void buscarAutoresPorNombre() {
        System.out.println("Escribe el nombre del autor que deseas buscar: ");
        var nombreAutor = teclado.nextLine();

        Optional<Autor> autor = autorRepository.buscarAutorPorNombre(nombreAutor);

        if (autor.isPresent()) {
            Autor a = autor.get();
            System.out.printf("""
                            
                            El autor encontrado es:
                            ---------------\033[31m AUTOR \033[39;49m---------------
                            AUTOR: \033[32m %s \033[39;49m
                            FECHA DE NACIMIENTO: \033[32m %s \033[39;49m
                            LIBRO: \033[32m %s \033[39;49m
                            
                            -----------------------------------
                            """,
                    a.getNombre(),
                    a.getFechaDeNacimiento(),
                    a.getLibro().getTitulo());
        } else {
            System.out.println("Autor no encontrado");
        }
    }

    private void estadisticaLibros() {
        System.out.println("""
                            \nEstadísticas de la base de datos
                            --------------------------------
                           """);
        List<Libro> libro = repository.findAll();

        if (!libro.isEmpty()) {
            DoubleSummaryStatistics estadisticas = libro.stream()
                    .collect(Collectors.summarizingDouble(Libro::getTotalDeDescargas));
            System.out.println("\nLibro más descargado: " + estadisticas.getMax());
            System.out.println("Libro menos descargado: " + estadisticas.getMin());
            System.out.printf("Promedio de descargas: %.2f%n ", estadisticas.getAverage());
        } else {
            System.out.println("No hay libros registrados en la base de datos");
        }
    }
}

