package br.com.actios.actios_backend.repositorys;

import br.com.actios.actios_backend.model.Evento;
import br.com.actios.actios_backend.model.EventoPalestrante;
import br.com.actios.actios_backend.model.EventoPalestranteId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositório para operações de persistência relacionadas à entidade {@link EventoPalestrante}.
 * <p>
 * Oferece operações CRUD básicas e consultas específicas para a relação entre eventos e palestrantes.
 * Utiliza {@link EventoPalestranteId} como chave primária composta.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Repository
public interface EventoPalestranteRepository extends JpaRepository<EventoPalestrante, EventoPalestranteId> {

    /**
     * Busca eventos futuros que possuam palestrantes com nome contendo o texto informado.
     *
     * @param agora Data de referência para eventos futuros (normalmente a data atual)
     * @param nome Trecho do nome do palestrante (não case-sensitive)
     * @param pageable Configuração de paginação e ordenação
     * @return Página de eventos que atendem aos critérios, ordenados por data ascendente
     * @throws IllegalArgumentException se algum parâmetro for nulo
     */
    @Query("SELECT ep.evento FROM EventoPalestrante ep " +
            "WHERE ep.evento.data >= :agora " +
            "AND LOWER(ep.palestrante.nome) LIKE LOWER(CONCAT('%', :nome, '%')) " +
            "ORDER BY ep.evento.data ASC")
    Page<Evento> findEventosFuturosByNomeParcialDoPalestrante(LocalDate agora, String nome, Pageable pageable);

    /**
     * Busca todos os eventos de um palestrante específico.
     *
     * @param palestranteId ID do palestrante
     * @return Lista de relações Evento-Palestrante para o palestrante informado
     * @throws IllegalArgumentException se o ID for nulo
     */
    List<EventoPalestrante> findByPalestrante_IdPalestrante(Integer palestranteId);


    /**
     * Busca todos os palestrantes de um evento específico.
     *
     * @param eventoId ID do evento
     * @return Lista de relações Evento-Palestrante para o evento informado
     * @throws IllegalArgumentException se o ID for nulo
     */
    List<EventoPalestrante> findByEvento_IdEvento(Integer eventoId);

    /**
     * Verifica se existe relação entre um evento e um palestrante específicos.
     *
     * @param eventoId ID do evento
     * @param palestranteId ID do palestrante
     * @return true se a relação existir, false caso contrário
     * @throws IllegalArgumentException se algum ID for nulo
     */
    boolean existsByEvento_IdEventoAndPalestrante_IdPalestrante(Integer eventoId, Integer palestranteId);

    /**
     * Busca eventos futuros de um palestrante específico.
     *
     * @param palestranteId ID do palestrante
     * @param dataReferencia Data de referência para eventos futuros
     * @param pageable Configuração de paginação e ordenação
     * @return Página de eventos futuros do palestrante
     * @throws IllegalArgumentException se algum parâmetro for nulo
     */
    @Query("SELECT ep.evento FROM EventoPalestrante ep " +
            "WHERE ep.palestrante.idPalestrante = :palestranteId " +
            "AND ep.evento.data >= :dataReferencia " +
            "ORDER BY ep.evento.data ASC")
    Page<Evento> findEventosFuturosByPalestrante(Integer palestranteId, LocalDate dataReferencia, Pageable pageable);

}