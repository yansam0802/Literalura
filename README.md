# Bibliotecalura

**Bibliotecalura** es una aplicación desarrollada en **Java 23** con **Spring Boot** y **Maven**, diseñada para gestionar y consultar información sobre libros y autores de manera eficiente. La aplicación utiliza la API **Gutendex** para acceder a una amplia base de datos de libros.

## Funcionalidades

1. **Buscar libro en general**
   - Permite realizar una búsqueda global de libros utilizando diversos criterios.

2. **Listar libros registrados**
   - Muestra todos los libros disponibles en la base de datos.

3. **Buscar libro por título**
   - Filtra los libros por título para encontrar coincidencias específicas.

4. **Listar libros por idioma**
   - Genera una lista de libros disponibles en un idioma específico.

5. **Listar autores registrados**
   - Muestra un listado de todos los autores registrados en la base de datos.

6. **Listar autores vivos en un determinado año**
   - Filtra y muestra los autores que estaban vivos en un año determinado.

7. **Buscar autores por nombre**
   - Permite buscar información específica de autores por su nombre.

8. **Estadísticas de libros**
   - Proporciona estadísticas generales sobre los libros registrados, como cantidad total, idiomas más comunes, entre otros.

9. **Salir**
   - Finaliza la ejecución del programa.

## Tecnologías Utilizadas

- **Java 23**
  - Lenguaje de programación principal utilizado para el desarrollo.

- **Spring Boot**
  - Framework para la creación de aplicaciones Java robustas y escalables.

- **Maven**
  - Herramienta de gestión de dependencias y construcción del proyecto.

- **Gutendex API**
  - API utilizada para acceder a datos sobre libros y autores de manera remota.

## Instalación

1. Clona el repositorio:
   ```bash
   git clone https://github.com/tu-usuario/bibliotecalura.git
   ```

2. Navega al directorio del proyecto:
   ```bash
   cd bibliotecalura
   ```

3. Compila el proyecto usando Maven:
   ```bash
   mvn clean install
   ```

4. Ejecuta la aplicación:
   ```bash
   mvn spring-boot:run
   ```

## Uso

1. Al iniciar la aplicación, se presenta un menú con las opciones disponibles.
2. Introduce el número correspondiente a la funcionalidad deseada.
3. Sigue las instrucciones que se muestran en pantalla para completar la acción.

## Contribuciones

Las contribuciones son bienvenidas. Por favor, sigue estos pasos para contribuir:

1. Haz un fork del repositorio.
2. Crea una rama para tu funcionalidad o corrección de errores:
   ```bash
   git checkout -b nueva-funcionalidad
   ```
3. Realiza tus cambios y confirma los commits:
   ```bash
   git commit -m "Agrega nueva funcionalidad"
   ```
4. Sube tus cambios a tu repositorio:
   ```bash
   git push origin nueva-funcionalidad
   ```
5. Abre un Pull Request en el repositorio principal.

---

¡Gracias por usar **Bibliotecalura**! Si tienes alguna pregunta o comentario, no dudes en crear un issue o contactar al desarrollador.


