package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.dto.EventoDTO;
import br.com.actios.actios_backend.model.Evento;
import br.com.actios.actios_backend.model.EventoPalestrante;
import br.com.actios.actios_backend.model.Palestrante;
import br.com.actios.actios_backend.model.EventoPalestranteId;
import br.com.actios.actios_backend.repositorys.EventoPalestranteRepository;
import br.com.actios.actios_backend.repositorys.EventoRepository;
import br.com.actios.actios_backend.repositorys.PalestranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Serviço responsável pela gestão da associação entre eventos e palestrantes.
 * <p>
 * Oferece operações para associar/desassociar palestrantes a eventos e consultas
 * relacionadas a essas associações.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Service
@Transactional
public class EventoPalestranteService {

    private final EventoPalestranteRepository eventoPalestranteRepository;
    private final EventoRepository eventoRepository;
    private final PalestranteRepository palestranteRepository;

    /**
     * Construtor com injeção de dependência dos repositórios necessários.
     *
     * @param eventoPalestranteRepository Repositório de associações evento-palestrante
     * @param eventoRepository Repositório de eventos
     * @param palestranteRepository Repositório de palestrantes
     */
    @Autowired
    public EventoPalestranteService(
            EventoPalestranteRepository eventoPalestranteRepository,
            EventoRepository eventoRepository,
            PalestranteRepository palestranteRepository) {
        this.eventoPalestranteRepository = eventoPalestranteRepository;
        this.eventoRepository = eventoRepository;
        this.palestranteRepository = palestranteRepository;
    }

    /**
     * Busca eventos futuros associados a palestrantes cujo nome contenha o termo informado.
     *
     * @param nome Termo parcial para busca no nome do palestrante (não pode ser nulo ou vazio)
     * @param pageable Configuração de paginação (não pode ser nulo)
     * @return Página de {@link EventoDTO} contendo os eventos encontrados
     * @throws IllegalArgumentException se nome ou pageable forem nulos
     */
    @Transactional(readOnly = true)
    public Page<EventoDTO> buscarEventosFuturosPorNomePalestrante(String nome, Pageable pageable) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do palestrante não pode ser nulo ou vazio.");
        }
        if (pageable == null) {
            throw new IllegalArgumentException("Pageable não pode ser nulo.");
        }

        LocalDate hoje = LocalDate.now();
        Page<Evento> eventos = eventoPalestranteRepository
                .findEventosFuturosByNomeParcialDoPalestrante(hoje, nome, pageable);
        return eventos.map(EventoDTO::new);
    }

    /**
     * Associa um palestrante a um evento.
     *
     * @param eventoId ID do evento (deve ser positivo)
     * @param palestranteId ID do palestrante (deve ser positivo)
     * @return Mensagem indicando o resultado da operação
     * @throws IllegalArgumentException se os IDs forem inválidos
     */
    @Transactional
    public String associarPalestranteAoEvento(int eventoId, int palestranteId) {
        if (eventoId <= 0 || palestranteId <= 0) {
            throw new IllegalArgumentException("IDs devem ser números positivos.");
        }

        Optional<Evento> eventoOpt = eventoRepository.findById(eventoId);
        Optional<Palestrante> palestranteOpt = palestranteRepository.findById(palestranteId);

        if (eventoOpt.isEmpty() || palestranteOpt.isEmpty()) {
            return "Evento ou Palestrante não encontrado.";
        }

        Evento evento = eventoOpt.get();
        Palestrante palestrante = palestranteOpt.get();

        EventoPalestranteId id = new EventoPalestranteId(evento.getIdEvento(), palestrante.getIdPalestrante());

        if (eventoPalestranteRepository.existsById(id)) {
            return "Associação já existe.";
        }

        EventoPalestrante ep = new EventoPalestrante();
        ep.setEvento(evento);
        ep.setPalestrante(palestrante);
        eventoPalestranteRepository.save(ep);

        return "Palestrante associado ao evento com sucesso.";
    }

    /**
     * Remove a associação entre um palestrante e um evento.
     *
     * @param eventoId ID do evento (deve ser positivo)
     * @param palestranteId ID do palestrante (deve ser positivo)
     * @return Mensagem indicando o resultado da operação
     * @throws IllegalArgumentException se os IDs forem inválidos
     */
    @Transactional
    public String desassociarPalestranteDoEvento(int eventoId, int palestranteId) {
        if (eventoId <= 0 || palestranteId <= 0) {
            throw new IllegalArgumentException("IDs devem ser números positivos.");
        }

        EventoPalestranteId id = new EventoPalestranteId(eventoId, palestranteId);
        if (!eventoPalestranteRepository.existsById(id)) {
            return "Associação não encontrada.";
        }

        eventoPalestranteRepository.deleteById(id);
        return "Palestrante desassociado do evento com sucesso.";
    }
}