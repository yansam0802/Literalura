package com.bibliotecalura.bibliotecalura;

import com.bibliotecalura.bibliotecalura.principal.Principal;
import com.bibliotecalura.bibliotecalura.repository.AutorRepository;
import com.bibliotecalura.bibliotecalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BibliotecaluraApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepository;
	@Autowired
	private AutorRepository autorRepository;


	public static void main(String[] args) {
		SpringApplication.run(BibliotecaluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Principal principal = new Principal(libroRepository, autorRepository);
		principal.muestraMenu();

	}
}
