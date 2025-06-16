package br.com.actios.actios_backend.repositorys;

import br.com.actios.actios_backend.enums.FormatoEvento;
import br.com.actios.actios_backend.model.EventoDetalhe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Repositório para operações de persistência relacionadas à entidade {@link EventoDetalhe}.
 * <p>
 * Oferece operações CRUD básicas e consultas específicas para detalhes de eventos,
 * incluindo filtros por formato, certificado, valor e datas.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Repository
public interface EventoDetalheRepository extends JpaRepository<EventoDetalhe, Integer> {

    /**
     * Busca detalhes de eventos por formato específico.
     *
     * @param formato Formato do evento a ser filtrado (PRESENCIAL, ONLINE ou HIBRIDO)
     * @return Lista de detalhes de eventos com o formato especificado (pode ser vazia)
     * @throws IllegalArgumentException se o formato for nulo
     */
    List<EventoDetalhe> findByFormato(FormatoEvento formato);

    /**
     * Busca detalhes de eventos por disponibilidade de certificado.
     *
     * @param certificado true para eventos com certificado, false para sem certificado
     * @return Lista de detalhes de eventos que atendem ao critério (pode ser vazia)
     */
    List<EventoDetalhe> findByCertificado(Boolean certificado);

    /**
     * Busca detalhes de eventos com valor menor ou igual ao especificado.
     *
     * @param valorMax Valor máximo a ser considerado (inclusive)
     * @return Lista de detalhes de eventos dentro do valor máximo (pode ser vazia)
     * @throws IllegalArgumentException se o valor for negativo
     */
    List<EventoDetalhe> findByValorLessThanEqual(BigDecimal valorMax);

    /**
     * Busca detalhes de eventos com data de fim maior ou igual à especificada.
     *
     * @param data Data mínima a ser considerada (inclusive)
     * @return Lista de detalhes de eventos após a data informada (pode ser vazia)
     * @throws IllegalArgumentException se a data for nula
     */
    List<EventoDetalhe> findByDataFimGreaterThanEqual(LocalDate data);

    /**
     * Busca detalhes de eventos dentro de um intervalo de valores.
     *
     * @param valorMin Valor mínimo do evento (inclusive)
     * @param valorMax Valor máximo do evento (inclusive)
     * @return Lista de detalhes de eventos no intervalo de valores (pode ser vazia)
     * @throws IllegalArgumentException se algum valor for negativo ou min > max
     */
    List<EventoDetalhe> findByValorBetween(BigDecimal valorMin, BigDecimal valorMax);

    /**
     * Busca detalhes de eventos dentro de um intervalo de datas de fim.
     *
     * @param dataInicio Data mínima de fim (inclusive)
     * @param dataFim Data máxima de fim (inclusive)
     * @return Lista de detalhes de eventos no intervalo de datas (pode ser vazia)
     * @throws IllegalArgumentException se datas forem inválidas ou inicio > fim
     */
    List<EventoDetalhe> findByDataFimBetween(LocalDate dataInicio, LocalDate dataFim);

    /**
     * Busca detalhes de eventos gratuitos (valor zero ou nulo).
     *
     * @return Lista de detalhes de eventos gratuitos (pode ser vazia)
     */
    @Query("SELECT ed FROM EventoDetalhe ed WHERE ed.valor IS NULL OR ed.valor = 0")
    List<EventoDetalhe> findEventosGratuitos();
}