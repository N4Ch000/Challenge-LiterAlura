package com.aluracursos.literatura.modelos;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<ListaDeAutores> autores,
        @JsonAlias("languages") List<String> languages,
        @JsonAlias("download_count") Double numeroDeDescargas

) {

}
