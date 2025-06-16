package br.com.actios.actios_backend.repositorys;

import br.com.actios.actios_backend.model.Faculdade;
import br.com.actios.actios_backend.model.Organizador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações de persistência relacionadas à entidade {@link Organizador}.
 * <p>
 * Oferece operações para gerenciamento de organizadores de eventos, incluindo buscas por
 * email, nome, faculdade associada e verificações de existência.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Repository
public interface OrganizadorRepository extends JpaRepository<Organizador, Integer> {

    /**
     * Busca um organizador pelo email (campo único).
     *
     * @param email Email do organizador (case-sensitive)
     * @return {@link Optional} contendo o organizador se encontrado, ou vazio caso contrário
     * @throws IllegalArgumentException se o email for nulo ou vazio
     */
    Optional<Organizador> findByEmail(String email);

    /**
     * Busca organizadores cujo nome contenha o texto informado (case-insensitive).
     *
     * @param nome Trecho do nome para busca
     * @return Lista de organizadores com nomes correspondentes, podendo ser vazia
     * @throws IllegalArgumentException se o nome for nulo
     */
    List<Organizador> findByNomeContainingIgnoreCase(String nome);

    /**
     * Busca todos os organizadores associados a uma faculdade específica.
     *
     * @param faculdade Faculdade para filtrar os organizadores
     * @return Lista de organizadores da faculdade, podendo ser vazia
     * @throws IllegalArgumentException se a faculdade for nula
     */
    List<Organizador> findByFaculdade(Faculdade faculdade);

    /**
     * Verifica se existe um organizador cadastrado com o email informado.
     *
     * @param email Email para verificação (case-sensitive)
     * @return true se o email já estiver em uso, false caso contrário
     * @throws IllegalArgumentException se o email for nulo ou vazio
     */
    boolean existsByEmail(String email);

    /**
     * Conta o número de organizadores associados a uma faculdade.
     *
     * @param faculdade Faculdade para contagem
     * @return Quantidade de organizadores vinculados à faculdade
     * @throws IllegalArgumentException se a faculdade for nula
     */
    long countByFaculdade(Faculdade faculdade);
}