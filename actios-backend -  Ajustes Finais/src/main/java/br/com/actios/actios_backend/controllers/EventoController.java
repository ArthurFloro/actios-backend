package br.com.actios.actios_backend.controllers;

import br.com.actios.actios_backend.dto.EventoDTO;
import br.com.actios.actios_backend.model.Evento;
import br.com.actios.actios_backend.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para operações relacionadas a eventos.
 * <p>
 * Mapeado para a rota base "/api/eventos" e fornece endpoints para CRUD de eventos,
 * incluindo paginação e filtros especiais.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    private final EventoService eventoService;

    /**
     * Construtor com injeção de dependência do serviço de eventos.
     *
     * @param eventoService Serviço de lógica de negócios para eventos
     */
    @Autowired
    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    /**
     * Cadastra um novo evento.
     *
     * @param eventoDTO DTO contendo os dados do evento a ser cadastrado
     * @return ResponseEntity contendo o EventoDTO do evento cadastrado com status HTTP 200 (OK)
     */
    @PostMapping("/cadastrar")
    public ResponseEntity<EventoDTO> cadastrar(@RequestBody EventoDTO eventoDTO) {
        Evento evento = eventoService.converterDTOParaEvento(eventoDTO);
        Evento eventoSalvo = eventoService.cadastrar(evento);
        EventoDTO responseDTO = new EventoDTO(eventoSalvo);
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Adiciona um palestrante a um evento existente.
     *
     * @param idEvento ID do evento
     * @param idPalestrante ID do palestrante
     * @return ResponseEntity com status HTTP 200 (OK) ou erro apropriado
     */
    @PostMapping("/{idEvento}/adicionar-palestrante/{idPalestrante}")
    public ResponseEntity<Void> adicionarPalestrante(
            @PathVariable Integer idEvento,
            @PathVariable Integer idPalestrante) {
        eventoService.adicionarPalestrante(idEvento, idPalestrante);
        return ResponseEntity.ok().build();
    }

    /**
     * Lista todos os eventos com paginação e ordenação.
     *
     * @param page Número da página (default = 0)
     * @param size Tamanho da página (default = 10)
     * @param sort Direção da ordenação ("asc" ou "desc", default = "asc")
     * @return ResponseEntity contendo Page<EventoDTO> com os eventos paginados
     */
    @GetMapping("/listar")
    public ResponseEntity<Page<EventoDTO>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sort) {

        Sort.Direction direction = sort.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "data"));

        Page<Evento> eventosPage = eventoService.listarTodos(pageable);
        Page<EventoDTO> eventosDTOPage = eventosPage.map(EventoDTO::new);

        return ResponseEntity.ok(eventosDTOPage);
    }

    /**
     * Busca um evento específico pelo ID.
     *
     * @param id ID do evento a ser buscado
     * @return ResponseEntity contendo EventoDTO do evento encontrado com status HTTP 200 (OK)
     */
    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> buscarPorId(@PathVariable Integer id) {
        Evento evento = eventoService.buscarPorId(id);
        EventoDTO dto = new EventoDTO(evento);
        return ResponseEntity.ok(dto);
    }

    /**
     * Atualiza os dados de um evento existente.
     *
     * @param eventoDTO DTO contendo os dados atualizados do evento
     * @return ResponseEntity contendo EventoDTO do evento atualizado com status HTTP 200 (OK)
     */
    @PutMapping(value = "/atualizar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventoDTO> atualizar(@RequestBody EventoDTO eventoDTO) {
        Evento eventoAtualizado = eventoService.atualizar(eventoDTO);
        EventoDTO responseDTO = new EventoDTO(eventoAtualizado);
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Exclui um evento pelo ID.
     *
     * @param id ID do evento a ser excluído
     * @return ResponseEntity vazio com status HTTP 204 (No Content)
     */
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        eventoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Lista eventos futuros filtrados por nome do palestrante com paginação.
     *
     * @param nome Nome do palestrante para filtro
     * @param page Número da página (default = 0)
     * @param size Tamanho da página (default = 10)
     * @param sort Direção da ordenação ("asc" ou "desc", default = "asc")
     * @return ResponseEntity contendo Page<EventoDTO> com os eventos filtrados e paginados
     */
    @GetMapping("/futuros-por-nome-palestrante")
    public ResponseEntity<Page<EventoDTO>> listarEventosFuturosPorNomePalestrante(
            @RequestParam String nome,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sort) {

        Sort.Direction direction = sort.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size);

        Page<Evento> eventos = eventoService.listarEventosFuturosPorNomePalestrante(nome, pageable);
        Page<EventoDTO> eventosDTO = eventos.map(EventoDTO::new);

        return ResponseEntity.ok(eventosDTO);
    }
}