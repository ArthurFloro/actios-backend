package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.enums.FormatoEvento;
import br.com.actios.actios_backend.exceptions.CampoObrigatorioException;
import br.com.actios.actios_backend.exceptions.RecursoNaoEncontradoException;
import br.com.actios.actios_backend.model.Evento;
import br.com.actios.actios_backend.model.EventoDetalhe;
import br.com.actios.actios_backend.repositorys.EventoDetalheRepository;
import br.com.actios.actios_backend.repositorys.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Serviço responsável pelas operações de negócio relacionadas a {@link EventoDetalhe}.
 * <p>
 * Gerencia os detalhes complementares de eventos, incluindo informações como formato,
 * certificação, valores e datas adicionais.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Service
@Transactional
public class EventoDetalheService {

    private final EventoDetalheRepository detalheRepository;
    private final EventoRepository eventoRepository;

    /**
     * Construtor com injeção de dependência dos repositórios necessários.
     *
     * @param detalheRepository Repositório de detalhes de evento (não pode ser nulo)
     * @param eventoRepository Repositório de eventos (não pode ser nulo)
     */
    @Autowired
    public EventoDetalheService(EventoDetalheRepository detalheRepository, EventoRepository eventoRepository) {
        this.detalheRepository = detalheRepository;
        this.eventoRepository = eventoRepository;
    }

    /**
     * Salva os detalhes de um evento, validando a existência do evento associado.
     *
     * @param detalhe Detalhes do evento a serem salvos (não pode ser nulo)
     * @return Detalhes do evento salvos
     * @throws CampoObrigatorioException se o evento associado não for informado
     * @throws RecursoNaoEncontradoException se o evento associado não for encontrado
     * @throws IllegalArgumentException se os detalhes forem nulos
     */
    @Transactional
    public EventoDetalhe salvar(EventoDetalhe detalhe) {
        if (detalhe == null) {
            throw new IllegalArgumentException("Detalhes do evento não podem ser nulos.");
        }
        if (detalhe.getEvento() == null || detalhe.getEvento().getIdEvento() == null) {
            throw new CampoObrigatorioException("Evento é obrigatório");
        }

        Evento evento = eventoRepository.findById(detalhe.getEvento().getIdEvento())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));

        detalhe.setEvento(evento);
        return detalheRepository.save(detalhe);
    }

    /**
     * Busca os detalhes de um evento específico.
     *
     * @param idEvento ID do evento (não pode ser nulo)
     * @return Detalhes do evento encontrado
     * @throws RecursoNaoEncontradoException se os detalhes não forem encontrados
     * @throws IllegalArgumentException se o ID for nulo
     */
    public EventoDetalhe buscarPorEvento(Integer idEvento) {
        if (idEvento == null) {
            throw new IllegalArgumentException("ID do evento não pode ser nulo.");
        }
        return detalheRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Detalhes do evento não encontrados"));
    }

    /**
     * Lista todos os detalhes de eventos cadastrados.
     *
     * @return Lista de todos os detalhes de eventos
     */
    @Transactional(readOnly = true)
    public List<EventoDetalhe> listarTodos() {
        return detalheRepository.findAll();
    }

    /**
     * Lista detalhes de eventos por formato específico.
     *
     * @param formato Formato do evento a ser filtrado (não pode ser nulo)
     * @return Lista de detalhes de eventos com o formato especificado
     * @throws IllegalArgumentException se o formato for nulo
     */
    @Transactional(readOnly = true)
    public List<EventoDetalhe> listarPorFormato(FormatoEvento formato) {
        if (formato == null) {
            throw new IllegalArgumentException("Formato do evento não pode ser nulo.");
        }
        return detalheRepository.findByFormato(formato);
    }

    /**
     * Lista detalhes de eventos por disponibilidade de certificado.
     *
     * @param certificado Indica se deve filtrar por eventos com certificado (true) ou sem (false)
     * @return Lista de detalhes de eventos conforme o filtro de certificado
     * @throws IllegalArgumentException se o parâmetro for nulo
     */
    @Transactional(readOnly = true)
    public List<EventoDetalhe> listarPorCertificado(Boolean certificado) {
        if (certificado == null) {
            throw new IllegalArgumentException("Parâmetro certificado não pode ser nulo.");
        }
        return detalheRepository.findByCertificado(certificado);
    }

    /**
     * Lista detalhes de eventos com valor menor ou igual ao especificado.
     *
     * @param valorMax Valor máximo para filtro (não pode ser nulo)
     * @return Lista de detalhes de eventos dentro do valor máximo
     * @throws IllegalArgumentException se o valor for nulo
     */
    @Transactional(readOnly = true)
    public List<EventoDetalhe> listarPorValorMaximo(BigDecimal valorMax) {
        if (valorMax == null) {
            throw new IllegalArgumentException("Valor máximo não pode ser nulo.");
        }
        return detalheRepository.findByValorLessThanEqual(valorMax);
    }

    /**
     * Lista detalhes de eventos com data de término após a data especificada.
     *
     * @param data Data mínima para filtro (não pode ser nulo)
     * @return Lista de detalhes de eventos com data de término após a data informada
     * @throws IllegalArgumentException se a data for nulo
     */
    @Transactional(readOnly = true)
    public List<EventoDetalhe> listarPorDataFimApos(LocalDate data) {
        if (data == null) {
            throw new IllegalArgumentException("Data não pode ser nula.");
        }
        return detalheRepository.findByDataFimGreaterThanEqual(data);
    }

    /**
     * Atualiza os detalhes de um evento existente.
     * <p>
     * Apenas atualiza os campos que foram explicitamente informados no objeto de atualização.
     *
     * @param idEvento ID do evento a ser atualizado (não pode ser nulo)
     * @param detalheAtualizado Objeto contendo os campos a serem atualizados (não pode ser nulo)
     * @throws RecursoNaoEncontradoException se os detalhes do evento não forem encontrados
     * @throws IllegalArgumentException se ID ou detalhes forem nulos
     */
    @Transactional
    public void atualizar(Integer idEvento, EventoDetalhe detalheAtualizado) {
        if (idEvento == null) {
            throw new IllegalArgumentException("ID do evento não pode ser nulo.");
        }
        if (detalheAtualizado == null) {
            throw new IllegalArgumentException("Detalhes atualizados não podem ser nulos.");
        }

        EventoDetalhe detalhe = detalheRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Detalhes do evento com ID " + idEvento + " não encontrados"));

        if (detalheAtualizado.getDataFim() != null) {
            detalhe.setDataFim(detalheAtualizado.getDataFim());
        }

        if (detalheAtualizado.getFormato() != null) {
            detalhe.setFormato(detalheAtualizado.getFormato());
        }

        if (detalheAtualizado.getCertificado() != null) {
            detalhe.setCertificado(detalheAtualizado.getCertificado());
        }

        if (detalheAtualizado.getValor() != null) {
            detalhe.setValor(detalheAtualizado.getValor());
        }

        detalheRepository.save(detalhe);
    }
}