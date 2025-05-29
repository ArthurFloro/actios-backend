package br.com.actios.actios_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "vinculo_curso_usuario")
@IdClass(VinculoCursoUsuarioId.class)
public class VinculoCursoUsuario {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @JsonIgnore
    private Usuario usuario;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_curso")
    @JsonIgnore
    private Curso curso;

    @Column(name = "concluido")
    private boolean concluido;

    public VinculoCursoUsuario() {}

    public VinculoCursoUsuario(Usuario usuario, Curso curso) {
        this.usuario = usuario;
        this.curso = curso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }
}

