package br.com.actios.actios_backend.repositorys;

import br.com.actios.actios_backend.model.Curso;
import br.com.actios.actios_backend.model.RegistroCertificado;
import br.com.actios.actios_backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações de persistência relacionadas à entidade {@link RegistroCertificado}.
 * <p>
 * Oferece operações para gerenciamento de certificados emitidos, incluindo buscas por
 * usuário, curso, código de validação e combinações desses critérios, além de contagens
 * estatísticas.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Repository
public interface RegistroCertificadoRepository extends JpaRepository<RegistroCertificado, Integer> {

    /**
     * Busca todos os certificados emitidos para um usuário específico.
     *
     * @param usuario Usuário beneficiário dos certificados
     * @return Lista de certificados do usuário, ordenados por data de emissão (decrescente)
     * @throws IllegalArgumentException se o usuário for nulo
     */
    List<RegistroCertificado> findByUsuario(Usuario usuario);

    /**
     * Busca todos os certificados emitidos para um curso específico.
     *
     * @param curso Curso relacionado aos certificados
     * @return Lista de certificados do curso, ordenados por data de emissão (decrescente)
     * @throws IllegalArgumentException se o curso for nulo
     */
    List<RegistroCertificado> findByCurso(Curso curso);

    /**
     * Busca um certificado específico pelo seu código de validação único.
     *
     * @param codigoValidacao Código alfanumérico de validação do certificado
     * @return O certificado correspondente ao código, ou null se não encontrado
     * @throws IllegalArgumentException se o código for nulo ou vazio
     */
    RegistroCertificado findByCodigoValidacao(String codigoValidacao);

    /**
     * Conta o número total de certificados emitidos para um usuário.
     *
     * @param usuario Usuário beneficiário dos certificados
     * @return Quantidade de certificados do usuário
     * @throws IllegalArgumentException se o usuário for nulo
     */
    long countByUsuario(Usuario usuario);

    /**
     * Conta o número total de certificados emitidos para um curso.
     *
     * @param curso Curso relacionado aos certificados
     * @return Quantidade de certificados emitidos para o curso
     * @throws IllegalArgumentException se o curso for nulo
     */
    long countByCurso(Curso curso);

    /**
     * Busca certificados específicos combinando usuário e curso.
     *
     * @param usuario Usuário beneficiário dos certificados
     * @param curso Curso relacionado aos certificados
     * @return Lista de certificados correspondentes aos critérios, ordenados por data de emissão (decrescente)
     * @throws IllegalArgumentException se usuário ou curso forem nulos
     */
    List<RegistroCertificado> findByUsuarioAndCurso(Usuario usuario, Curso curso);
}