package br.com.actios.actios_backend.model;

import jakarta.persistence.*;

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
}
