package br.com.actios.actios_backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidade que representa um organizador de eventos.
 * <p>
 * Contém informações básicas do organizador e pode estar vinculado a uma faculdade.
 * Atualiza automaticamente a data de modificação quando campos principais são alterados.
 * Utiliza {@code @JsonBackReference} para evitar serialização circular com Faculdade.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Entity
@Table(name = "organizadores")
public class Organizador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_organizador")
    private Integer idOrganizador;

    @Column(name = "nome", length = 100, nullable = true)
    private String nome;

    @Column(name = "email", length = 100, unique = true, nullable = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "id_faculdade", nullable = true)
    @JsonBackReference
    private Faculdade faculdade;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    /**
     * Construtor padrão sem parâmetros.
     */
    public Organizador() {
        // Construtor vazio necessário para JPA
    }

    /**
     * Obtém o ID único do organizador.
     *
     * @return ID do organizador
     */
    public Integer getIdOrganizador() {
        return idOrganizador;
    }

    /**
     * Define o ID único do organizador.
     *
     * @param idOrganizador ID do organizador (deve ser positivo)
     */
    public void setIdOrganizador(Integer idOrganizador) {
        this.idOrganizador = idOrganizador;
    }

    /**
     * Obtém o nome do organizador.
     *
     * @return Nome do organizador ou null se não definido
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do organizador e atualiza a data de modificação.
     *
     * @param nome Nome do organizador (até 100 caracteres)
     */
    public void setNome(String nome) {
        this.nome = nome;
        this.dataAtualizacao = LocalDateTime.now();
    }

    /**
     * Obtém o email do organizador.
     *
     * @return Email único do organizador ou null se não definido
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o email do organizador e atualiza a data de modificação.
     *
     * @param email Email do organizador (deve ser único, até 100 caracteres)
     * @throws IllegalArgumentException Se o email for inválido
     */
    public void setEmail(String email) {
        this.email = email;
        this.dataAtualizacao = LocalDateTime.now();
    }

    /**
     * Obtém a faculdade associada ao organizador.
     *
     * @return Instância de Faculdade ou null se não vinculado
     */
    public Faculdade getFaculdade() {
        return faculdade;
    }

    /**
     * Define a faculdade associada ao organizador e atualiza a data de modificação.
     *
     * @param faculdade Instância de Faculdade
     */
    public void setFaculdade(Faculdade faculdade) {
        this.faculdade = faculdade;
        this.dataAtualizacao = LocalDateTime.now();
    }

    /**
     * Obtém a data da última atualização dos dados do organizador.
     *
     * @return Data/hora da última atualização
     */
    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    /**
     * Define manualmente a data de atualização.
     * <p>
     * Normalmente não necessário, pois é atualizado automaticamente nos setters.
     *
     * @param dataAtualizacao Data/hora da atualização
     */
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}