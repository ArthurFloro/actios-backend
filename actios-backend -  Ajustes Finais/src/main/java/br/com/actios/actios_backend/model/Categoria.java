package br.com.actios.actios_backend.model;

import jakarta.persistence.*;

/**
 * Entidade que representa uma categoria de eventos no sistema.
 * <p>
 * Categorias são utilizadas para classificar e organizar eventos
 * por áreas temáticas ou tipos de atividades.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Entity
@Table(name = "categorias")
public class Categoria {

    /**
     * Identificador único da categoria.
     * <p>
     * Gerado automaticamente pelo banco de dados com estratégia de auto-incremento.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCategoria;

    /**
     * Nome da categoria.
     * <p>
     * Deve ser único no sistema e não pode ser nulo.
     * Representa o nome público da categoria que será exibido aos usuários.
     */
    @Column(nullable = false, unique = true)
    private String nome;

    /**
     * Obtém o ID da categoria.
     *
     * @return ID único da categoria
     */
    public Integer getIdCategoria() {
        return idCategoria;
    }

    /**
     * Define o ID da categoria.
     *
     * @param idCategoria ID único da categoria (gerado automaticamente)
     */
    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    /**
     * Obtém o nome da categoria.
     *
     * @return Nome da categoria (não pode ser nulo)
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome da categoria.
     *
     * @param nome Nome da categoria (não pode ser nulo ou vazio)
     * @throws IllegalArgumentException Se o nome for nulo ou vazio
     */
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da categoria não pode ser nulo ou vazio");
        }
        this.nome = nome;
    }
}