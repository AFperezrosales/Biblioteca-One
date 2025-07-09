package com.libros.librosAluraOne;

import com.libros.librosAluraOne.DTO.AutorDTO;
import com.libros.librosAluraOne.DTO.LibroRecordDTO;
import com.libros.librosAluraOne.DTO.ResultadosDTO;
import com.libros.librosAluraOne.models.Autor;
import com.libros.librosAluraOne.models.LibroEntity;
import com.libros.librosAluraOne.repository.ILibroRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class Biblioteca {

    @Autowired
    ILibroRespository libroRespository;

        public void menuBiblioteca(){

            Scanner leer = new Scanner(System.in);
            boolean salir = true;
            int op = 0;
            while (true){
                if (op == 6 ){
                    salir= false;
                    break;
                }
                System.out.println("""
                    *******************************************
                    sea bienvenido/a a la biblioteca =]
                    
                    1) Buscar libro Por el nombre en api y guardarlo en db
                    2) Listar todos los libros de la base de datos
                    3) Buscar autores vivos en un año especifico (API)
                    4) Buscar por nombre del autor (API)
                    5) Buscar por idioma del libro (API)
                    6) Salir
                    Elija una opción valida:
                    *******************************************
                    """);

                op = leer.nextInt();
                leer.nextLine();
                switch (op){
                    case 1:
                        System.out.println("escribé el libro a buscar");
                        String busqueda = leer.nextLine();
                        consultaApi(busqueda);
                        break;
                    case 2:
                        System.out.println("Listar libros en la base de datos:");
                        listarLibrosEnLaBaseDeDatos();
                        break;
                    case 3:
                        System.out.println("escribe la fecha para buscar autores que esten vivos dicho año");
                        String year = leer.nextLine();
                        buscarEnApiAutoresVivosEnAlgunaFecha(year);
                        break;
                    case 4:
                        System.out.println("Escriba el nombre del autor a buscar: ");
                        String name = leer.nextLine();
                        buscarPorNombreDeAutor(name);
                        break;
                    case 5:
                        System.out.println("""
                                1) español:
                                2) ingles:
                                3) portugues:
                                """);
                        int opcion = leer.nextInt();
                        buscarPorIdioma(opcion);
                    case 6:
                        op = 6;
                        break;
                    default:
                        System.out.println("caso desconocido");
                        break;
                }

            }


        }


    public void consultaApi(String busqueda) {
        busqueda = busqueda.replace(" ", "%20");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://gutendex.com/books/?search=" + busqueda))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();

            ConvierteDato convierteDato = new ConvierteDato();
            ResultadosDTO respuesta = convierteDato.obtenerDatos(json, ResultadosDTO.class);

            List<LibroRecordDTO> libros = respuesta.results();

            List<LibroEntity> librosAGuardar = libros.stream()
                    .map(LibroEntity::new)
                    .collect(Collectors.toList());


            
            for (LibroRecordDTO libro : libros) {
                System.out.println("Título: " + libro.titulo());
                for (AutorDTO autor : libro.autorList()) {
                    System.out.println("Autor/es: " + autor.nombre() + " (" + autor.nacimiento() + " - " + autor.fechaDeMuerte() + ")");
                }
                System.out.println("Idioma/as: " + String.join(", ", libro.idiomas()));
                System.out.println("-----------------------------------------------------------------");
            }
            libroRespository.saveAll(librosAGuardar);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void listarLibrosEnLaBaseDeDatos(){
            List<LibroEntity> libros = libroRespository.findAll();

            for (LibroEntity libro : libros){
                System.out.println("Titulo: " + libro.getTitulo());
                for (Autor autor : libro.getAutores()){
                    System.out.println("Autor/es: " + autor.getNombre() + " " + autor.getNacimiento() + " " + autor.getFechaDeMuerte());
                }
                System.out.println("Idioma/as: " + String.join(", ", libro.getIdiomas()));
                System.out.println("-----------------------------------------------------------------");
            }


    }

    public void buscarEnApiAutoresVivosEnAlgunaFecha(String fechaDeMuerte){
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://gutendex.com/books?author_year_end=" + fechaDeMuerte))
                .header("User-Agent", "Java HttpClient") // 👈 agregar esto
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();

            ConvierteDato convierteDato = new ConvierteDato();
            ResultadosDTO respuesta = convierteDato.obtenerDatos(json, ResultadosDTO.class);

            List<LibroRecordDTO> libros = respuesta.results();
        //prueba que onda
            for (LibroRecordDTO libro : libros) {
                System.out.println("Título: " + libro.titulo());
                for (AutorDTO autor : libro.autorList()) {
                    System.out.println("Autor/es: " + autor.nombre() + " (" + autor.nacimiento() + " - " + autor.fechaDeMuerte() + ")");
                }
                System.out.println("Idioma/as: " + String.join(", ", libro.idiomas()));
                System.out.println("-----------------------------------------------------------------");
            }

            List<LibroEntity> librosAGuardar = libros.stream()
                    .map(LibroEntity::new)
                    .collect(Collectors.toList());

            for (LibroRecordDTO libro : libros) {
                System.out.println("Título: " + libro.titulo());
                for (AutorDTO autor : libro.autorList()) {
                    System.out.println("Autor/es: " + autor.nombre() + " (" + autor.nacimiento() + " - " + autor.fechaDeMuerte() + ")");
                }
                System.out.println("Idioma/as: " + String.join(", ", libro.idiomas()));
                System.out.println("-----------------------------------------------------------------");
            }

            libroRespository.saveAll(librosAGuardar);

        }catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void buscarPorNombreDeAutor(String busqueda){
        String finalBusqueda = busqueda;
        busqueda = busqueda.replace(" ", "%20");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://gutendex.com/books/?search=" + busqueda + "%20great"))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();

            ConvierteDato convierteDato = new ConvierteDato();
            ResultadosDTO respuesta = convierteDato.obtenerDatos(json, ResultadosDTO.class);

            List<LibroRecordDTO> libros = respuesta.results();

            List<LibroEntity> librosAGuardar = libros.stream()
                    .filter(libro -> libro.autorList().stream()
                            .anyMatch(autor -> autor.nombre().toLowerCase().contains(finalBusqueda.toLowerCase()))
                    )
                    .map(LibroEntity::new)
                    .collect(Collectors.toList());



            for (LibroEntity libro : librosAGuardar) {
                System.out.println("Título: " + libro.getTitulo());
                for (Autor  autor : libro.getAutores()) {
                    System.out.println("Autor/es: " + autor.getNombre() + " (" + autor.getNacimiento() + " - " + autor.getFechaDeMuerte() + ")");
                }
                System.out.println("Idioma/as: " + String.join(", ", libro.getIdiomas()));
                System.out.println("-----------------------------------------------------------------");
            }
            libroRespository.saveAll(librosAGuardar);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    public void buscarPorIdioma(int busqueda){
            String locura="";
            switch (busqueda){
                case 1:
                    locura="es";
                    break;
                case 2:
                    locura="en";
                    break;
                case 3:
                    locura="pt";
                    break;
                default:
                    throw new RuntimeException("Esa opcion no esta vaya a lavarse el ocote");


            }
        System.out.println("https://gutendex.com/books?languages=" + locura);
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://gutendex.com/books?languages=" + locura))
                .header("User-Agent", "Java HttpClient") // 👈 agregar esto
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();

            ConvierteDato convierteDato = new ConvierteDato();
            ResultadosDTO respuesta = convierteDato.obtenerDatos(json, ResultadosDTO.class);

            List<LibroRecordDTO> libros = respuesta.results();

            List<LibroEntity> librosAGuardar = libros.stream()
                    .map(LibroEntity::new)
                    .collect(Collectors.toList());



            for (LibroEntity libro : librosAGuardar) {
                System.out.println("Título: " + libro.getTitulo());
                for (Autor  autor : libro.getAutores()) {
                    System.out.println("Autor/es: " + autor.getNombre() + " (" + autor.getNacimiento() + " - " + autor.getFechaDeMuerte() + ")");
                }
                System.out.println("Idioma/as: " + String.join(", ", libro.getIdiomas()));
                System.out.println("-----------------------------------------------------------------");
            }

            libroRespository.saveAll(librosAGuardar);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
