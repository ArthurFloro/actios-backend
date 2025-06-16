package br.com.actios.actios_backend.repositorys;

import br.com.actios.actios_backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório para operações de persistência relacionadas à entidade {@link Usuario}.
 * <p>
 * Oferece operações básicas de CRUD e consultas específicas para usuários do sistema,
 * incluindo autenticação por email e senha, e verificação de existência de email.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    /**
     * Verifica se já existe um usuário cadastrado com o email informado.
     * <p>
     * A verificação é case-sensitive e considera o email como campo único.
     *
     * @param email Email a ser verificado (não pode ser nulo ou vazio)
     * @return true se o email já estiver em uso, false caso contrário
     * @throws IllegalArgumentException se o email for nulo ou vazio
     */
    boolean existsByEmail(String email);

    /**
     * Busca um usuário pelo email e senha para fins de autenticação.
     * <p>
     * A comparação é case-sensitive para o email. A implementação da comparação
     * da senha depende da estratégia de armazenamento de senhas.
     *
     * @param email Email do usuário (não pode ser nulo ou vazio)
     * @param senha Senha do usuário (não pode ser nula ou vazia)
     * @return {@link Optional} contendo o usuário se as credenciais forem válidas,
     *         ou vazio caso contrário
     * @throws IllegalArgumentException se email ou senha forem nulos ou vazios
     */
    Optional<Usuario> findByEmailAndSenha(String email, String senha);

    /**
     * Busca um usuário pelo seu email.
     * <p>
     * A busca é case-sensitive e utiliza o email como identificador único.
     *
     * @param email Email do usuário (não pode ser nulo ou vazio)
     * @return {@link Optional} contendo o usuário se encontrado, ou vazio caso contrário
     * @throws IllegalArgumentException se o email for nulo ou vazio
     */
    Optional<Usuario> findByEmail(String email);
}