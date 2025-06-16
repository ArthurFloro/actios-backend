package br.com.actios.actios_backend.repositorys;

import br.com.actios.actios_backend.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório para operações de persistência relacionadas à entidade {@link Curso}.
 * <p>
 * Oferece operações CRUD básicas através da extensão {@link JpaRepository} e métodos
 * adicionais para consultas específicas de cursos.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {

    /**
     * Verifica se existe um curso com o nome exato informado.
     *
     * @param nome Nome exato do curso a ser verificado (case-sensitive)
     * @return true se existir um curso com o nome informado, false caso contrário
     * @throws IllegalArgumentException se o nome fornecido for nulo ou vazio
     */
    boolean existsByNome(String nome);

    /**
     * Busca cursos cujo nome contenha a string informada, ignorando diferenças de caixa.
     *
     * @param nome Trecho do nome do curso a ser buscado (não case-sensitive)
     * @return Lista de cursos encontrados (pode ser vazia se nenhum curso corresponder)
     * @throws IllegalArgumentException se o parâmetro nome for nulo
     */
    List<Curso> findByNomeContainingIgnoreCase(String nome);

    /**
     * Busca cursos por área acadêmica, ignorando diferenças de caixa.
     *
     * @param areaAcademica Área acadêmica dos cursos a serem buscados (não case-sensitive)
     * @return Lista de cursos encontrados (pode ser vazia se nenhum curso corresponder)
     * @throws IllegalArgumentException se o parâmetro areaAcademica for nulo
     */
    List<Curso> findByAreaAcademicaIgnoreCase(String areaAcademica);

    /**
     * Busca cursos por nome exato, ignorando diferenças de caixa.
     *
     * @param nome Nome exato do curso (não case-sensitive)
     * @return Lista de cursos com o nome exato informado
     * @throws IllegalArgumentException se o parâmetro nome for nulo
     */
    List<Curso> findByNomeIgnoreCase(String nome);
}