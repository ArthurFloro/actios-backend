package br.com.actios.actios_backend.repositorys;

import br.com.actios.actios_backend.model.Palestrante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório para operações de persistência relacionadas à entidade {@link Palestrante}.
 * <p>
 * Oferece operações para verificação e busca de palestrantes através de seus emails.
 * Garante a integridade dos dados mantendo o email como campo único.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Repository
public interface PalestranteRepository extends JpaRepository<Palestrante, Integer> {

    /**
     * Verifica se existe um palestrante cadastrado com o email informado.
     * <p>
     * A verificação é case-sensitive e considera o email como campo único.
     *
     * @param email Email a ser verificado (não pode ser nulo ou vazio)
     * @return true se o email já estiver em uso por algum palestrante, false caso contrário
     * @throws IllegalArgumentException se o email fornecido for nulo ou vazio
     */
    boolean existsByEmail(String email);

    /**
     * Busca um palestrante pelo seu email.
     * <p>
     * A busca é case-sensitive e utiliza o email como identificador único.
     *
     * @param email Email do palestrante a ser buscado (não pode ser nulo ou vazio)
     * @return {@link Optional} contendo o palestrante se encontrado, ou vazio caso contrário
     * @throws IllegalArgumentException se o email fornecido for nulo ou vazio
     */
    Optional<Palestrante> findByEmail(String email);
}