package br.com.actios.actios_backend.repositorys;

import br.com.actios.actios_backend.model.Evento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositório para operações de persistência relacionadas à entidade {@link Evento}.
 * <p>
 * Oferece operações CRUD básicas e consultas específicas para eventos, incluindo
 * filtros por data, categoria, faculdade e status.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {

    /**
     * Busca eventos futuros a partir de uma data de referência.
     *
     * @param dataReferencia Data mínima para os eventos (inclusive)
     * @return Lista de eventos com data igual ou posterior à referência
     */
    List<Evento> findByDataGreaterThanEqual(LocalDate dataReferencia);

    /**
     * Busca eventos passados até uma data de referência.
     *
     * @param dataReferencia Data máxima para os eventos (exclusive)
     * @return Lista de eventos com data anterior à referência
     */
    List<Evento> findByDataLessThan(LocalDate dataReferencia);

    /**
     * Busca eventos por categoria com paginação.
     *
     * @param categoriaId ID da categoria
     * @param pageable Configuração de paginação
     * @return Página de eventos da categoria especificada
     */
    Page<Evento> findByCategoriaIdCategoria(Integer categoriaId, Pageable pageable);

    /**
     * Busca eventos por faculdade organizadora.
     *
     * @param faculdadeId ID da faculdade
     * @return Lista de eventos organizados pela faculdade
     */
    List<Evento> findByFaculdadeIdFaculdade(Integer faculdadeId);

    /**
     * Busca eventos por título contendo o texto informado (case-insensitive).
     *
     * @param titulo Trecho do título a ser buscado
     * @return Lista de eventos com títulos correspondentes
     */
    List<Evento> findByTituloContainingIgnoreCase(String titulo);

    /**
     * Busca eventos futuros por faculdade e categoria.
     *
     * @param faculdadeId ID da faculdade
     * @param categoriaId ID da categoria
     * @param dataAtual Data atual para filtrar eventos futuros
     * @return Lista de eventos que atendem aos critérios
     */
    @Query("SELECT e FROM Evento e WHERE " +
            "e.faculdade.idFaculdade = :faculdadeId AND " +
            "e.categoria.idCategoria = :categoriaId AND " +
            "e.data >= :dataAtual " +
            "ORDER BY e.data ASC")
    List<Evento> findEventosFuturosPorFaculdadeECategoria(
            Integer faculdadeId,
            Integer categoriaId,
            LocalDate dataAtual
    );

    /**
     * Verifica se existe algum evento com o título exato informado.
     *
     * @param titulo Título exato a ser verificado
     * @return true se existir um evento com o título, false caso contrário
     */
    boolean existsByTitulo(String titulo);

    /**
     * Conta o número de eventos por categoria usando o atributo 'idCategoria' da entidade Categoria.
     *
     * @param categoriaId ID da categoria
     * @return Quantidade de eventos na categoria especificada
     */
    long countByCategoriaIdCategoria(Integer categoriaId);
}
