package com.aluracursos.literatura;
import com.aluracursos.literatura.principal.Principal;
import com.aluracursos.literatura.repositorio.AutorRepositorio;
import com.aluracursos.literatura.repositorio.LibroReposotorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {
	@Autowired
	private LibroReposotorio libroreposotorio;
	@Autowired
	private AutorRepositorio autorRepositorio;
	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(autorRepositorio,libroreposotorio);
		principal.mostrarMenu();
	}
}
