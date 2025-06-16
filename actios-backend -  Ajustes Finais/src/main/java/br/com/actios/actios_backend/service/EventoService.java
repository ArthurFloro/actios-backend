package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.dto.EventoDTO;
import br.com.actios.actios_backend.enums.FormatoEvento;
import br.com.actios.actios_backend.exceptions.CampoObrigatorioException;
import br.com.actios.actios_backend.exceptions.DataInvalidaException;
import br.com.actios.actios_backend.exceptions.RecursoNaoEncontradoException;
import br.com.actios.actios_backend.model.*;
import br.com.actios.actios_backend.repositorys.EventoRepository;
import br.com.actios.actios_backend.repositorys.EventoPalestranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Serviço responsável pela gestão de eventos.
 * <p>
 * Oferece operações CRUD para eventos, incluindo conversão de DTOs, validações
 * e associações com palestrantes, faculdades e categorias.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Service
@Transactional
public class EventoService {

    private final EventoRepository eventoRepository;
    private final EventoPalestranteRepository eventoPalestranteRepository;
    private final FaculdadeService faculdadeService;
    private final CategoriaService categoriaService;
    private final PalestranteService palestranteService;

    /**
     * Construtor com injeção de dependência dos serviços e repositórios necessários.
     *
     * @param eventoRepository Repositório de eventos
     * @param eventoPalestranteRepository Repositório de associações evento-palestrante
     * @param faculdadeService Serviço de faculdades
     * @param categoriaService Serviço de categorias
     * @param palestranteService Serviço de palestrantes
     */
    @Autowired
    public EventoService(
            EventoRepository eventoRepository,
            EventoPalestranteRepository eventoPalestranteRepository,
            FaculdadeService faculdadeService,
            CategoriaService categoriaService,
            PalestranteService palestranteService) {
        this.eventoRepository = eventoRepository;
        this.eventoPalestranteRepository = eventoPalestranteRepository;
        this.faculdadeService = faculdadeService;
        this.categoriaService = categoriaService;
        this.palestranteService = palestranteService;
    }

    /**
     * Converte um EventoDTO para a entidade Evento, incluindo todas as associações.
     *
     * @param dto DTO contendo os dados do evento (não pode ser nulo)
     * @return Entidade Evento populada
     * @throws RecursoNaoEncontradoException se faculdade, categoria ou palestrante não forem encontrados
     * @throws CampoObrigatorioException se formato for nulo ou inválido
     * @throws IllegalArgumentException se o DTO for nulo
     */
    @Transactional(readOnly = true)
    public Evento converterDTOParaEvento(EventoDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("DTO do evento não pode ser nulo.");
        }

        Evento evento = new Evento();
        evento.setTitulo(dto.getTitulo());
        evento.setDescricao(dto.getDescricao());
        evento.setData(dto.getData());
        evento.setHorario(dto.getHorario());
        evento.setLocal(dto.getLocal());

        // Busca e associa Faculdade pelo ID
        Faculdade faculdade = faculdadeService.buscarPorId(dto.getFaculdadeId());
        evento.setFaculdade(faculdade);

        // Busca e associa Categoria pelo ID
        Categoria categoria = categoriaService.buscarPorId(dto.getCategoriaId());
        evento.setCategoria(categoria);

        // Busca e associa Palestrantes por ID
        Set<EventoPalestrante> palestrantes = dto.getPalestrantesIds().stream()
                .map(id -> {
                    Palestrante palestrante = palestranteService.buscarPorId(id);
                    EventoPalestrante ep = new EventoPalestrante();
                    ep.setEvento(evento);
                    ep.setPalestrante(palestrante);
                    return ep;
                })
                .collect(Collectors.toSet());
        evento.setEventoPalestrantes(palestrantes);

        // Configura Detalhes do Evento
        EventoDetalhe detalhe = new EventoDetalhe();
        detalhe.setCertificado(dto.getCertificado());
        detalhe.setValor(dto.getValor());

        // Valida e configura Formato
        if (dto.getFormato() == null) {
            throw new CampoObrigatorioException("Formato do evento é obrigatório.");
        }
        try {
            detalhe.setFormato(FormatoEvento.fromValue(dto.getFormato()));
        } catch (IllegalArgumentException e) {
            throw new CampoObrigatorioException(
                    "Formato de evento inválido. Valores permitidos: " +
                            String.join(", ", FormatoEvento.getValidValues()));
        }

        detalhe.setEvento(evento);
        evento.setDetalhes(detalhe);

        return evento;
    }

    /**
     * Valida os dados de um evento.
     *
     * @param evento Evento a ser validado (não pode ser nulo)
     * @param isAtualizacao Indica se é uma operação de atualização
     * @throws RecursoNaoEncontradoException para atualizações de eventos inexistentes
     * @throws CampoObrigatorioException para campos obrigatórios não preenchidos
     * @throws DataInvalidaException para datas no passado
     * @throws IllegalArgumentException se o evento for nulo
     */
    private void validarEvento(Evento evento, boolean isAtualizacao) {
        if (evento == null) {
            throw new IllegalArgumentException("Evento não pode ser nulo.");
        }
        if (isAtualizacao && (evento.getIdEvento() == null || !eventoRepository.existsById(evento.getIdEvento()))) {
            throw new RecursoNaoEncontradoException("Evento não encontrado para atualização.");
        }
        if (evento.getData() == null) {
            throw new CampoObrigatorioException("Data do evento é obrigatória.");
        }
        if (evento.getData().isBefore(LocalDate.now())) {
            throw new DataInvalidaException("Data do evento não pode ser no passado.");
        }
        if (evento.getTitulo() == null || evento.getTitulo().isBlank()) {
            throw new CampoObrigatorioException("Título do evento é obrigatório.");
        }
    }

    /**
     * Cadastra um novo evento.
     *
     * @param evento Evento a ser cadastrado (não pode ser nulo)
     * @return Evento cadastrado
     * @throws CampoObrigatorioException se dados obrigatórios não forem informados
     * @throws DataInvalidaException se a data for inválida
     * @throws IllegalArgumentException se o evento for nulo
     */
    @Transactional
    public Evento cadastrar(Evento evento) {
        validarEvento(evento, false);
        return eventoRepository.save(evento);
    }

    /**
     * Adiciona um palestrante a um evento existente.
     *
     * @param idEvento ID do evento
     * @param idPalestrante ID do palestrante
     * @throws RecursoNaoEncontradoException se evento ou palestrante não forem encontrados
     */
    @Transactional
    public void adicionarPalestrante(Integer idEvento, Integer idPalestrante) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));

        Palestrante palestrante = palestranteService.buscarPorId(idPalestrante);

        EventoPalestrante eventoPalestrante = new EventoPalestrante();
        eventoPalestrante.setEvento(evento);
        eventoPalestrante.setPalestrante(palestrante);

        eventoPalestranteRepository.save(eventoPalestrante);
    }

    /**
     * Atualiza um evento existente.
     *
     * @param dto DTO com os dados atualizados (não pode ser nulo)
     * @return Evento atualizado
     * @throws RecursoNaoEncontradoException se o evento não for encontrado
     * @throws CampoObrigatorioException se dados obrigatórios não forem informados
     * @throws DataInvalidaException se a data for inválida
     * @throws IllegalArgumentException se o DTO for nulo
     */
    @Transactional
    public Evento atualizar(EventoDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("DTO do evento não pode ser nulo.");
        }

        Evento eventoExistente = eventoRepository.findById(dto.getIdEvento())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));

        // Atualiza apenas os campos permitidos
        eventoExistente.setTitulo(dto.getTitulo());
        eventoExistente.setDescricao(dto.getDescricao());

        validarEvento(eventoExistente, true);
        return eventoRepository.save(eventoExistente);
    }

    /**
     * Exclui um evento.
     *
     * @param id ID do evento a ser excluído (não pode ser nulo)
     * @throws RecursoNaoEncontradoException se o evento não for encontrado
     * @throws IllegalArgumentException se o ID for nulo
     */
    @Transactional
    public void excluir(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do evento não pode ser nulo.");
        }
        if (!eventoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Evento não encontrado para exclusão.");
        }
        eventoRepository.deleteById(id);
    }

    /**
     * Busca um evento por ID.
     *
     * @param id ID do evento (não pode ser nulo)
     * @return Evento encontrado
     * @throws RecursoNaoEncontradoException se o evento não for encontrado
     * @throws IllegalArgumentException se o ID for nulo
     */
    @Transactional(readOnly = true)
    public Evento buscarPorId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do evento não pode ser nulo.");
        }
        return eventoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado."));
    }

    /**
     * Lista todos os eventos com paginação.
     *
     * @param pageable Configuração de paginação (não pode ser nulo)
     * @return Página de eventos
     * @throws IllegalArgumentException se pageable for nulo
     */
    @Transactional(readOnly = true)
    public Page<Evento> listarTodos(Pageable pageable) {
        if (pageable == null) {
            throw new IllegalArgumentException("Pageable não pode ser nulo.");
        }
        return eventoRepository.findAll(pageable);
    }

    /**
     * Lista eventos futuros filtrando por nome parcial do palestrante.
     *
     * @param nome Nome ou parte do nome do palestrante (não pode ser nulo ou vazio)
     * @param pageable Configuração de paginação (não pode ser nulo)
     * @return Página de eventos futuros
     * @throws IllegalArgumentException se nome ou pageable forem inválidos
     */
    @Transactional(readOnly = true)
    public Page<Evento> listarEventosFuturosPorNomePalestrante(String nome, Pageable pageable) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do palestrante não pode ser nulo ou vazio.");
        }
        if (pageable == null) {
            throw new IllegalArgumentException("Pageable não pode ser nulo.");
        }

        LocalDate hoje = LocalDate.now();
        return eventoPalestranteRepository.findEventosFuturosByNomeParcialDoPalestrante(hoje, nome, pageable);
    }
}