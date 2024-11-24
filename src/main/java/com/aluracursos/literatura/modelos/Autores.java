package com.aluracursos.literatura.modelos;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nombre;
    private Integer nacimiento;
    private Integer fallecimiento;
    @OneToMany(mappedBy = "autor",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List <Libros> libros = new ArrayList<>();

    public Autores(){}


    public Autores(ListaDeAutores autores) {
        this.nombre = autores.nombre();
        this.nacimiento = autores.nacimiento();
        this.fallecimiento = autores.fallecimiento();
    }

    @Override
    public String toString() {
        StringBuilder librosTitulos = new StringBuilder();
        for (Libros libro : libros) {
            librosTitulos.append(libro.getTitulo()).append(", ");
        }
        // Eliminar la última coma y espacio
        if (librosTitulos.length() > 0) {
            librosTitulos.setLength(librosTitulos.length() - 2);
        }

        return  "--------------- AUTOR ---------------" + "\n" +
                "Autor: " + nombre + "\n" +
                "Fecha de nacimiento: " + nacimiento + "\n" +
                "Fecha de fallecimiento: " + fallecimiento + "\n" +
                "Libros: " + librosTitulos + "\n";


    }
    public String getNombre() {
        return nombre;
    }

    public Integer getNacimiento() {
        return nacimiento;
    }

    public Integer getFallecimiento() {
        return fallecimiento;
    }

    public List<Libros> getLibros() {
        return libros;
    }
    public void setLibros(List<Libros> libros) {
        this.libros = libros;
    }
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNacimiento(Integer nacimiento) {
        this.nacimiento = nacimiento;
    }

    public void setFallecimiento(Integer fallecimiento) {
        this.fallecimiento = fallecimiento;
    }
}

