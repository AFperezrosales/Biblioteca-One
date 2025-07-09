package com.libros.librosAluraOne;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibrosAluraOneApplication implements CommandLineRunner {

	private final Biblioteca biblioteca;

	public LibrosAluraOneApplication(Biblioteca biblioteca) {
		this.biblioteca = biblioteca;
	}

	public static void main(String[] args) {
		SpringApplication.run(LibrosAluraOneApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		biblioteca.menuBiblioteca();
	}
}
