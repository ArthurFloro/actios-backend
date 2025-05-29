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

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "localizacao", length = 100)
    private String localizacao;

    @Column(name = "site", length = 150)
    private String site;

    @OneToMany(mappedBy = "faculdade", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Organizador> organizadores = new HashSet<>();

    @OneToMany(mappedBy = "faculdade", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Curso> cursos = new HashSet<>();

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

    public Set<Curso> getCursos() {
        return Collections.unmodifiableSet(cursos);
    }

    public void adicionarCurso(Curso curso) {
        cursos.add(curso);
        curso.setFaculdade(this);
    }

    public void removerCurso(Curso curso) {
        cursos.remove(curso);
        curso.setFaculdade(null);
    }
}
