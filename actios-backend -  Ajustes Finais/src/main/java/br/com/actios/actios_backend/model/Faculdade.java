package br.com.actios.actios_backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.*;

/**
 * Entidade que representa uma faculdade ou instituição de ensino.
 * <p>
 * Contém informações básicas sobre a instituição e mantém um relacionamento
 * com organizadores de eventos. Gerencia a lista de organizadores vinculados
 * à faculdade com métodos para adição, remoção e consulta.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Entity
@Table(name = "faculdades")
public class Faculdade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_faculdade")
    private Integer idFaculdade;

    @Column(name = "nome")
    private String nome;

    @Column(name = "localizacao")
    private String localizacao;

    @Column(name = "site")
    private String site;

    @OneToMany(mappedBy = "faculdade", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Organizador> organizadores = new HashSet<>();

    /**
     * Obtém o ID único da faculdade.
     *
     * @return ID da faculdade
     */
    public Integer getIdFaculdade() {
        return idFaculdade;
    }

    /**
     * Define o ID único da faculdade.
     *
     * @param idFaculdade ID da faculdade (deve ser positivo)
     */
    public void setIdFaculdade(Integer idFaculdade) {
        this.idFaculdade = idFaculdade;
    }

    /**
     * Obtém o nome da faculdade.
     *
     * @return Nome da faculdade
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome da faculdade.
     *
     * @param nome Nome da faculdade (não pode ser nulo ou vazio)
     * @throws IllegalArgumentException Se o nome for nulo ou vazio
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém a localização da faculdade.
     *
     * @return Endereço ou localização física da faculdade
     */
    public String getLocalizacao() {
        return localizacao;
    }

    /**
     * Define a localização da faculdade.
     *
     * @param localizacao Endereço ou localização física
     */
    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    /**
     * Obtém o site oficial da faculdade.
     *
     * @return URL do site ou null se não disponível
     */
    public String getSite() {
        return site;
    }

    /**
     * Define o site oficial da faculdade.
     *
     * @param site URL completa do site (deve ser válida se informada)
     */
    public void setSite(String site) {
        this.site = site;
    }

    /**
     * Obtém a lista de organizadores vinculados à faculdade (coleção imutável).
     *
     * @return Conjunto imutável de organizadores
     */
    public Set<Organizador> getOrganizadores() {
        return Collections.unmodifiableSet(organizadores);
    }

    /**
     * Adiciona um organizador à faculdade.
     *
     * @param organizador Organizador a ser vinculado
     * @throws IllegalArgumentException Se o organizador for nulo
     */
    public void adicionarOrganizador(Organizador organizador) {
        if (organizador == null) {
            throw new IllegalArgumentException("Organizador não pode ser nulo");
        }
        organizadores.add(organizador);
        organizador.setFaculdade(this);
    }

    /**
     * Remove um organizador da faculdade.
     *
     * @param organizador Organizador a ser desvinculado
     */
    public void removerOrganizador(Organizador organizador) {
        organizadores.remove(organizador);
        organizador.setFaculdade(null);
    }

    /**
     * Conta o número total de organizadores vinculados à faculdade.
     *
     * @return Quantidade de organizadores
     */
    public int contarOrganizadores() {
        return organizadores.size();
    }

    /**
     * Obtém uma lista de todos os organizadores ativos da faculdade.
     *
     * @return Lista copiável de organizadores
     */
    public List<Organizador> getOrganizadoresAtivos() {
        return new ArrayList<>(organizadores);
    }
}