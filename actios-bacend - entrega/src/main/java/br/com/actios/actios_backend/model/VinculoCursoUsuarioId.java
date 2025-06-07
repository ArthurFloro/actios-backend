package br.com.actios.actios_backend.model;

import java.io.Serializable;
import java.util.Objects;

public class VinculoCursoUsuarioId implements Serializable {
    
    private Integer usuario;
    private Integer curso;

    public VinculoCursoUsuarioId() {}
    
    public VinculoCursoUsuarioId(Integer usuario, Integer curso) {
        this.usuario = usuario;
        this.curso = curso;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    public Integer getCurso() {
        return curso;
    }

    public void setCurso(Integer curso) {
        this.curso = curso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VinculoCursoUsuarioId)) return false;
        VinculoCursoUsuarioId that = (VinculoCursoUsuarioId) o;
        return Objects.equals(usuario, that.usuario) &&
               Objects.equals(curso, that.curso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, curso);
    }
}
