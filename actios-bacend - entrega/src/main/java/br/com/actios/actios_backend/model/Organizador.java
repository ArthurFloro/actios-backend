package br.com.actios.actios_backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;

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

    public Organizador() {
        // vazio
    }

    // Getters e Setters

    public Integer getIdOrganizador() {
        return idOrganizador;
    }

    public void setIdOrganizador(Integer idOrganizador) {
        this.idOrganizador = idOrganizador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Faculdade getFaculdade() {
        return faculdade;
    }

    public void setFaculdade(Faculdade faculdade) {
        this.faculdade = faculdade;
        this.dataAtualizacao = LocalDateTime.now();
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
