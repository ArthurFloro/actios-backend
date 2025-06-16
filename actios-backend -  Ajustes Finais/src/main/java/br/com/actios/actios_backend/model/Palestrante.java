package br.com.actios.actios_backend.model;

import jakarta.persistence.*;

/**
 * Entidade que representa um palestrante no sistema.
 * <p>
 * Armazena informações básicas como nome, contato e biografia do palestrante.
 * O email é único para cada palestrante cadastrado no sistema.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Entity
@Table(name = "palestrantes")
public class Palestrante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPalestrante;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String telefone;

    @Column(columnDefinition = "TEXT")
    private String biografia;

    /**
     * Obtém o ID único do palestrante.
     *
     * @return ID do palestrante
     */
    public Integer getIdPalestrante() {
        return idPalestrante;
    }

    /**
     * Define o ID único do palestrante.
     *
     * @param idPalestrante ID do palestrante (deve ser positivo)
     * @throws IllegalArgumentException Se o ID for nulo ou negativo
     */
    public void setIdPalestrante(Integer idPalestrante) {
        if (idPalestrante != null && idPalestrante < 0) {
            throw new IllegalArgumentException("ID do palestrante deve ser positivo");
        }
        this.idPalestrante = idPalestrante;
    }

    /**
     * Obtém o nome completo do palestrante.
     *
     * @return Nome do palestrante
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome completo do palestrante.
     *
     * @param nome Nome do palestrante (não pode ser nulo ou vazio)
     * @throws IllegalArgumentException Se o nome for nulo ou vazio
     */
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do palestrante é obrigatório");
        }
        this.nome = nome;
    }

    /**
     * Obtém o email do palestrante.
     *
     * @return Email único do palestrante
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o email do palestrante.
     *
     * @param email Email do palestrante (deve ser único e válido)
     * @throws IllegalArgumentException Se o email for nulo, vazio ou inválido
     */
    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email do palestrante é obrigatório");
        }
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Formato de email inválido");
        }
        this.email = email;
    }

    /**
     * Obtém o telefone do palestrante.
     *
     * @return Telefone do palestrante ou null se não informado
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Define o telefone do palestrante.
     *
     * @param telefone Telefone do palestrante (formato livre)
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * Obtém a biografia do palestrante.
     *
     * @return Biografia em formato TEXT ou null se não informada
     */
    public String getBiografia() {
        return biografia;
    }

    /**
     * Define a biografia do palestrante.
     *
     * @param biografia Texto descritivo sobre o palestrante
     */
    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }
}