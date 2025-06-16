package br.com.actios.actios_backend.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Classe que representa a chave primária composta para a entidade VinculoCursoUsuario.
 * <p>
 * Implementa {@link Serializable} para permitir serialização e é usada como ID composto
 * na entidade de junção entre Usuario e Curso. Deve implementar equals() e hashCode()
 * para garantir o correto funcionamento do JPA.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
public class VinculoCursoUsuarioId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer usuario;
    private Integer curso;

    /**
     * Construtor padrão sem argumentos, requerido pelo JPA.
     */
    public VinculoCursoUsuarioId() {}

    /**
     * Construtor com todos os campos da chave composta.
     *
     * @param usuario ID do usuário (não pode ser nulo)
     * @param curso ID do curso (não pode ser nulo)
     * @throws IllegalArgumentException Se algum parâmetro for nulo
     */
    public VinculoCursoUsuarioId(Integer usuario, Integer curso) {
        if (usuario == null || curso == null) {
            throw new IllegalArgumentException("IDs de usuário e curso são obrigatórios");
        }
        this.usuario = usuario;
        this.curso = curso;
    }

    /**
     * Obtém o ID do usuário que compõe a chave primária.
     *
     * @return ID do usuário
     */
    public Integer getUsuario() {
        return usuario;
    }

    /**
     * Define o ID do usuário que compõe a chave primária.
     *
     * @param usuario ID do usuário (deve ser positivo)
     * @throws IllegalArgumentException Se o ID for nulo ou negativo
     */
    public void setUsuario(Integer usuario) {
        if (usuario == null || usuario < 0) {
            throw new IllegalArgumentException("ID do usuário deve ser positivo");
        }
        this.usuario = usuario;
    }

    /**
     * Obtém o ID do curso que compõe a chave primária.
     *
     * @return ID do curso
     */
    public Integer getCurso() {
        return curso;
    }

    /**
     * Define o ID do curso que compõe a chave primária.
     *
     * @param curso ID do curso (deve ser positivo)
     * @throws IllegalArgumentException Se o ID for nulo ou negativo
     */
    public void setCurso(Integer curso) {
        if (curso == null || curso < 0) {
            throw new IllegalArgumentException("ID do curso deve ser positivo");
        }
        this.curso = curso;
    }

    /**
     * Compara esta chave composta com outro objeto para igualdade.
     * <p>
     * Requerido para o correto funcionamento do JPA com chaves compostas.
     *
     * @param o Objeto a ser comparado
     * @return true se os objetos forem iguais, false caso contrário
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VinculoCursoUsuarioId)) return false;
        VinculoCursoUsuarioId that = (VinculoCursoUsuarioId) o;
        return Objects.equals(usuario, that.usuario) &&
                Objects.equals(curso, that.curso);
    }

    /**
     * Retorna o valor hash para esta chave composta.
     * <p>
     * Requerido para o correto funcionamento do JPA com chaves compostas.
     *
     * @return Valor hash calculado
     */
    @Override
    public int hashCode() {
        return Objects.hash(usuario, curso);
    }

    /**
     * Retorna uma representação em string desta chave composta.
     *
     * @return String no formato "usuario=X, curso=Y"
     */
    @Override
    public String toString() {
        return "usuario=" + usuario + ", curso=" + curso;
    }
}