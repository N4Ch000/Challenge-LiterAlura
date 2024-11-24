package com.aluracursos.literatura.modelos;
import jakarta.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")

public class Libros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String titulo;
    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Autores autor;
    @Column(name = "nombre_autor")
    private String nombreAutor;
    @Column(name = "lenguajes")
    private String lenguajes;
    private Double numeroDeDescargas;

    public Libros() {
    }

    public Libros(DatosLibros datosLibros, Autores autor) {
        this.titulo = datosLibros.titulo();
        setLenguajes(datosLibros.languages());
        this.nombreAutor = autor.getNombre();
        this.autor = autor;
        this.numeroDeDescargas = datosLibros.numeroDeDescargas();
    }

    public Libros(Optional<DatosLibros> libroBuscado) {
    }


    @Override
    public String toString() {
        return  "--------------- Libro ---------------" + "\n" +
                "Título: " + titulo + "\n" +
                "Autor: " + nombreAutor + "\n" +
                "Idioma: " + lenguajes + "\n" +
                "Número de descargas: " + numeroDeDescargas + "\n" +
                "------------------------------------" + "\n";

    }

    public Long getId() {
        return Id;
    }
    public void setId(Long id) {
        Id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getNombreAutor() {
        return nombreAutor;
    }
    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }
    public Autores getAutor() {
        return autor;
    }
    public void setAutor(Autores autor) {
        this.autor = autor;
    }

    public List<String> getLenguajes() {
        return Arrays.asList(lenguajes.split(","));
    }

    public void setLenguajes(List<String> lenguajes) {
        this.lenguajes = String.join(",",lenguajes);
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }
    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }
}
