package com.aluracursos.literatura.principal;
import com.aluracursos.literatura.modelos.*;
import com.aluracursos.literatura.repositorio.AutorRepositorio;
import com.aluracursos.literatura.repositorio.LibroReposotorio;
import com.aluracursos.literatura.services.ConsumoAPI;
import com.aluracursos.literatura.services.ConvierteDatos;

import java.util.*;

public class Principal {
    Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroReposotorio repositorio;
    private AutorRepositorio autorRepositorio;
    private List<Libros>librosBuscados;
    private List<Autores>autoresBuscados;


    public Principal(AutorRepositorio autorRepositorio, LibroReposotorio reposotorio){
        this.repositorio = reposotorio;
        this.autorRepositorio = autorRepositorio;
    }

    public void mostrarMenu() {
        var opcion =-1;
        while (opcion != 0){
            var menu = """
                    Elija una opción a través de un número:
                    1 - Buscar libro por título
                    2 - listar libros registrados
                    3 - listar autores registrados
                    4 - listar autores vivos en un determinado año
                    5 - listar libros por idiomas
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();
            switch (opcion){
                case 1:
                    buscarLibros();
                    break;
                case 2:
                    listarLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    autoresVivos();
                    break;
                case 5:
                    idiomasLibros();
                    break;
                case 0:
                    System.out.println("Muchas gracias por ocupar la aplicación");
                    break;
                default:
                    System.out.println("Debe elegir una opción válida");
                    break;
            }
        }
    }

    private ListaDeLibros getDatosLibros(){
        var titulo = teclado.next();
        var json = consumoApi.obtenerDatos(URL_BASE +"?search="+titulo.replace(" ","%20"));
        return conversor.obtenerDatos(json,ListaDeLibros.class);
    }
    private Libros crearLibro(DatosLibros datosLibros, Autores autor) {
        if (autor != null) {
            return new Libros(datosLibros, autor);
        } else {
            System.out.println("El autor no está registrado por lo tanto, no se puede crear el libro");
            return null;
        }
    }
    private void buscarLibros() {
        System.out.println("Ingrese el nombre del libro que desea buscar:");
        ListaDeLibros datosDeLibros = getDatosLibros();
        if (!datosDeLibros.resultados().isEmpty()) {
            DatosLibros datos = datosDeLibros.resultados().get(0);
            ListaDeAutores autores = datos.autores().get(0);
            Libros libro = null;
            Libros libroRepositorio = repositorio.findByTitulo(datos.titulo());
            if (libroRepositorio != null) {
                System.out.println("Este libro ya se encuentra registrado en la base de datos");
                System.out.println(libroRepositorio);
            } else {
                Autores autor = autorRepositorio.findByNombreIgnoreCase(datos.autores().get(0).nombre());
                if (autor != null) {
                    libro = crearLibro(datos, autor);
                    repositorio.save(libro);
                    System.out.println("Libro agregado a la base de datos");
                    System.out.println(libro);
                } else {
                    Autores autorEncontrado = new Autores(autores);
                    autorEncontrado = autorRepositorio.save(autorEncontrado);
                    libro = crearLibro(datos, autorEncontrado);
                    repositorio.save(libro);
                    System.out.println("Libro agregado a la base de datos");
                    System.out.println(libro);
                }
            }
        } else {
            System.out.println("Libro no encontrado");
        }
    }
    private void listarLibros() {
        librosBuscados = repositorio.findAll();
        if(librosBuscados.isEmpty()){
            System.out.println("No se encontró ningun libro");
        }else{
            librosBuscados.stream()
                    .sorted(Comparator.comparing(Libros::getTitulo))
                    .forEach(System.out::println);
        }
    }
    private void listarAutores() {
        autoresBuscados = autorRepositorio.findAll();
        if(autoresBuscados.isEmpty()){
            System.out.println("No se encontró autor(a)");
        }else{
            autoresBuscados.stream()
                    .sorted(Comparator.comparing(Autores::getNombre))
                    .forEach(System.out::println);
        }
    }
    private void autoresVivos() {
        System.out.println("Escriba el año en el que desea buscar: ");
        try {
            var fecha = teclado.nextInt();
            teclado.nextLine();
            if (fecha < 0) {
                System.out.println("La fecha no puede ser negativa, inténtelo otra vez.");
                return;
            }
            List<Autores> autoresPorFecha = autorRepositorio.findByNacimientoLessThanEqualAndFallecimientoGreaterThanEqual(fecha,fecha);
            if(autoresPorFecha.isEmpty()){
                System.out.println("No hay autores registrados para la fecha ingresada");
                return;
            }
            System.out.println("Los autores vivos en la fecha: " + fecha+ " son: ");
            autoresPorFecha.stream()
                            .sorted(Comparator.comparing(Autores::getNombre))
                            .forEach(System.out::println);
        } catch (InputMismatchException e) {
            System.out.println("Debe ingresar un número entero, no se permiten decimales. Inténtelo otra vez.");
            teclado.nextLine();
        }
    }
    private void idiomasLibros() {
        System.out.println("Escriba el idioma por el que desea realizar la búsqueda: ");
        String menu = """
                es - Español
                en - Inglés
                fr - Frances
                pt - Portugués
                """;
        System.out.println(menu);
        var idioma = teclado.nextLine();
        if(!idioma.equals("es")&&!idioma.equals("en")&&!idioma.equals("fr")&&!idioma.equals("pt")){
            System.out.println("El idioma ingresado no es válido, inténtelo nuevamente");
            return;
        }
        List<Libros> librosPorIdioma = repositorio.findByLenguajesContaining(idioma);
        if(librosPorIdioma.isEmpty()){
            System.out.println("No hay libros registrados en el idioma seleccionado: " +idioma);
            return;
        }
        System.out.println("Los libros registrodos en el idioma seleccionado son: ");
        librosPorIdioma.stream()
                .sorted(Comparator.comparing(Libros::getTitulo))
                .forEach(System.out::println);

    }
}
