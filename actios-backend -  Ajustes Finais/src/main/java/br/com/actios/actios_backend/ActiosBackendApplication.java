package br.com.actios.actios_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal que inicia a aplicação Spring Boot.
 *
 * <p>Esta classe contém o método main que é o ponto de entrada da aplicação.
 * A anotação {@code @SpringBootApplication} habilita a configuração automática
 * e a varredura de componentes do Spring.</p>
 *
 * <p>A aplicação é um backend para o sistema Actios, responsável por gerenciar
 * operações relacionadas a usuários, cursos e suas associações.</p>
 */
@SpringBootApplication
public class ActiosBackendApplication {

	/**
	 * Método principal que inicia a aplicação Spring Boot.
	 *
	 * @param args Argumentos de linha de comando que podem ser passados para a aplicação
	 */
	public static void main(String[] args) {
		SpringApplication.run(ActiosBackendApplication.class, args);
	}

}