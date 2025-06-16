package br.com.actios.actios_backend.repositorys;

import br.com.actios.actios_backend.model.Faculdade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações de persistência relacionadas à entidade {@link Faculdade}.
 * <p>
 * Oferece operações CRUD básicas e consultas específicas para faculdades, incluindo
 * buscas por nome, localização e relacionamentos com organizadores.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Repository
public interface FaculdadeRepository extends JpaRepository<Faculdade, Integer> {

    /**
     * Busca faculdades por nome (contendo o texto informado, case-insensitive).
     *
     * @param nome Trecho do nome da faculdade
     * @return Lista de faculdades com nomes correspondentes
     * @throws IllegalArgumentException se o nome for nulo
     */
    List<Faculdade> findByNomeContainingIgnoreCase(String nome);

    /**
     * Busca uma faculdade pelo nome exato (case-insensitive).
     *
     * @param nome Nome exato da faculdade
     * @return Optional contendo a faculdade se encontrada
     * @throws IllegalArgumentException se o nome for nulo
     */
    Optional<Faculdade> findByNomeIgnoreCase(String nome);

    /**
     * Busca faculdades por localização (contendo o texto informado, case-insensitive).
     *
     * @param localizacao Trecho da localização
     * @return Lista de faculdades na localização informada
     * @throws IllegalArgumentException se a localização for nula
     */
    List<Faculdade> findByLocalizacaoContainingIgnoreCase(String localizacao);

    /**
     * Busca faculdades com paginação e ordenação.
     *
     * @param pageable Configuração de paginação e ordenação
     * @return Página de faculdades
     */
    Page<Faculdade> findAll(Pageable pageable);

    /**
     * Busca todas as faculdades ordenadas por nome em ordem ascendente.
     *
     * @return Lista de faculdades ordenadas por nome
     */
    List<Faculdade> findAllByOrderByNomeAsc();

    /**
     * Verifica se existe uma faculdade com o nome informado.
     *
     * @param nome Nome exato a ser verificado (case-sensitive)
     * @return true se existir, false caso contrário
     * @throws IllegalArgumentException se o nome for nulo
     */
    boolean existsByNome(String nome);

    /**
     * Conta o número de organizadores associados a uma faculdade.
     *
     * @param faculdadeId ID da faculdade
     * @return Quantidade de organizadores
     * @throws IllegalArgumentException se o ID for nulo
     */
    @Query("SELECT COUNT(o) FROM Organizador o WHERE o.faculdade.id = :faculdadeId")
    long countOrganizadoresByFaculdadeId(Integer faculdadeId);

    /**
     * Busca faculdades que possuem pelo menos um organizador cadastrado.
     *
     * @return Lista de faculdades com organizadores
     */
    @Query("SELECT DISTINCT f FROM Faculdade f JOIN f.organizadores o WHERE SIZE(f.organizadores) > 0")
    List<Faculdade> findFaculdadesComOrganizadores();

    /**
     * Busca faculdades sem nenhum organizador cadastrado.
     *
     * @return Lista de faculdades sem organizadores
     */
    @Query("SELECT f FROM Faculdade f WHERE SIZE(f.organizadores) = 0")
    List<Faculdade> findFaculdadesSemOrganizadores();
}