package br.com.actios.actios_backend.model;

import jakarta.persistence.*;

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

    public Integer getIdPalestrante() {
        return idPalestrante;
    }

    public void setIdPalestrante(Integer idPalestrante) {
        this.idPalestrante = idPalestrante;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }
}

