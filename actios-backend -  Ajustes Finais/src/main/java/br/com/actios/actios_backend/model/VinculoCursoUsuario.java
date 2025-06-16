package br.com.actios.actios_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;

/**
 * Entidade que representa o vínculo entre um usuário e um curso.
 * <p>
 * Implementa uma relação many-to-many entre Usuario e Curso utilizando
 * uma chave primária composta (VinculoCursoUsuarioId). Serve como entidade
 * de junção com possibilidade de adicionar atributos adicionais no futuro.
 * Utiliza {@code @JsonIgnore} para evitar serialização circular nas relações.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Entity
@Table(name = "vinculo_curso_usuario")
@IdClass(VinculoCursoUsuarioId.class)
public class VinculoCursoUsuario {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnore
    private Usuario usuario;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_curso", nullable = false)
    @JsonIgnore
    private Curso curso;

    /**
     * Construtor padrão sem parâmetros, necessário para JPA.
     */
    public VinculoCursoUsuario() {}

    /**
     * Construtor com parâmetros para criar um vínculo entre usuário e curso.
     *
     * @param usuario Usuário a ser vinculado (não pode ser nulo)
     * @param curso Curso a ser vinculado (não pode ser nulo)
     * @throws IllegalArgumentException Se algum parâmetro for nulo
     */
    public VinculoCursoUsuario(Usuario usuario, Curso curso) {
        if (usuario == null || curso == null) {
            throw new IllegalArgumentException("Usuário e curso são obrigatórios");
        }
        this.usuario = usuario;
        this.curso = curso;
    }

    /**
     * Obtém o usuário vinculado ao curso.
     *
     * @return Instância do Usuario (não pode ser nula)
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Define o usuário vinculado ao curso.
     *
     * @param usuario Instância do Usuario (não pode ser nula)
     * @throws IllegalArgumentException Se o usuário for nulo
     */
    public void setUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário é obrigatório");
        }
        this.usuario = usuario;
    }

    /**
     * Obtém o curso vinculado ao usuário.
     *
     * @return Instância do Curso (não pode ser nula)
     */
    public Curso getCurso() {
        return curso;
    }

    /**
     * Define o curso vinculado ao usuário.
     *
     * @param curso Instância do Curso (não pode ser nula)
     * @throws IllegalArgumentException Se o curso for nulo
     */
    public void setCurso(Curso curso) {
        if (curso == null) {
            throw new IllegalArgumentException("Curso é obrigatório");
        }
        this.curso = curso;
    }

    /**
     * Compara este vínculo com outro objeto para igualdade.
     * <p>
     * Requerido para o correto funcionamento do JPA com chaves compostas.
     *
     * @param o Objeto a ser comparado
     * @return true se os objetos forem iguais, false caso contrário
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VinculoCursoUsuario)) return false;
        VinculoCursoUsuario that = (VinculoCursoUsuario) o;
        return Objects.equals(usuario, that.usuario) &&
                Objects.equals(curso, that.curso);
    }

    /**
     * Retorna o valor hash para este vínculo.
     * <p>
     * Requerido para o correto funcionamento do JPA com chaves compostas.
     *
     * @return Valor hash calculado
     */
    @Override
    public int hashCode() {
        return Objects.hash(usuario, curso);
    }
}