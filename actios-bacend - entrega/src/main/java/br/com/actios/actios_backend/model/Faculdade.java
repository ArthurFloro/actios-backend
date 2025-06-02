package br.com.actios.actios_backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.*;

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

    // Getters e Setters

    public Integer getIdFaculdade() {
        return idFaculdade;
    }

    public void setIdFaculdade(Integer idFaculdade) {
        this.idFaculdade = idFaculdade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Set<Organizador> getOrganizadores() {
        return Collections.unmodifiableSet(organizadores);
    }

    public void adicionarOrganizador(Organizador organizador) {
        organizadores.add(organizador);
        organizador.setFaculdade(this);
    }

    public void removerOrganizador(Organizador organizador) {
        organizadores.remove(organizador);
        organizador.setFaculdade(null);
    }

    public int contarOrganizadores() {
        return organizadores.size();
    }

    public List<Organizador> getOrganizadoresAtivos() {
        return new ArrayList<>(organizadores);
    }
}
