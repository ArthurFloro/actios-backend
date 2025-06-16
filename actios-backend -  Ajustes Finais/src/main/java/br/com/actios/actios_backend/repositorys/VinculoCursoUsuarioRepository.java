package br.com.actios.actios_backend.repositorys;

import br.com.actios.actios_backend.model.Curso;
import br.com.actios.actios_backend.model.Usuario;
import br.com.actios.actios_backend.model.VinculoCursoUsuario;
import br.com.actios.actios_backend.model.VinculoCursoUsuarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório para operações de persistência relacionadas à entidade {@link VinculoCursoUsuario}.
 * <p>
 * Gerencia os vínculos entre usuários e cursos, permitindo consultas bidirecionais
 * (cursos por usuário e usuários por curso) e verificação de existência de vínculos.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Repository
public interface VinculoCursoUsuarioRepository extends JpaRepository<VinculoCursoUsuario, VinculoCursoUsuarioId> {

    /**
     * Busca todos os vínculos de cursos associados a um usuário específico.
     *
     * @param usuario Usuário para consulta (não pode ser nulo)
     * @return Lista de vínculos contendo os cursos do usuário, ordenados por data de vinculação
     * @throws IllegalArgumentException se o usuário for nulo
     */
    List<VinculoCursoUsuario> findByUsuario(Usuario usuario);

    /**
     * Busca todos os vínculos de usuários associados a um curso específico.
     *
     * @param curso Curso para consulta (não pode ser nulo)
     * @return Lista de vínculos contendo os usuários do curso, ordenados por data de vinculação
     * @throws IllegalArgumentException se o curso for nulo
     */
    List<VinculoCursoUsuario> findByCurso(Curso curso);

    /**
     * Verifica se existe um vínculo específico entre usuário e curso.
     *
     * @param id ID composto contendo a chave do usuário e do curso (não pode ser nulo)
     * @return true se o vínculo existir, false caso contrário
     * @throws IllegalArgumentException se o ID for nulo
     */
    boolean existsById(VinculoCursoUsuarioId id);
}