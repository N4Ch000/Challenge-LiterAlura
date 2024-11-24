package com.aluracursos.literatura.repositorio;
import com.aluracursos.literatura.modelos.Autores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface AutorRepositorio extends JpaRepository<Autores,Long> {
Autores findByNombreIgnoreCase(String nombre);
List <Autores> findByNacimientoLessThanEqualAndFallecimientoGreaterThanEqual(int bird, int dead);
}
