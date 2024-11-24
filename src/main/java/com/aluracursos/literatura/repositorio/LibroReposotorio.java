package com.aluracursos.literatura.repositorio;
import com.aluracursos.literatura.modelos.Autores;
import com.aluracursos.literatura.modelos.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


public interface LibroReposotorio extends JpaRepository<Libros,Long> {
  Libros findByTitulo(String titulo);
  List<Libros>findByLenguajesContaining(String lenguaje);

}
