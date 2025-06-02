package br.com.actios.actios_backend.controllers;

import br.com.actios.actios_backend.dto.EventoDTO;
import br.com.actios.actios_backend.model.Evento;
import br.com.actios.actios_backend.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    private final EventoService eventoService;

    @Autowired
    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<EventoDTO> cadastrar(@RequestBody EventoDTO eventoDTO) {
        Evento evento = eventoService.converterDTOParaEvento(eventoDTO);
        Evento eventoSalvo = eventoService.cadastrar(evento);
        EventoDTO responseDTO = new EventoDTO(eventoSalvo);
        return ResponseEntity.ok(responseDTO);
    }

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

    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> buscarPorId(@PathVariable Integer id) {
        Evento evento = eventoService.buscarPorId(id);
        EventoDTO dto = new EventoDTO(evento);
        return ResponseEntity.ok(dto);
    }

    @PutMapping(value = "/atualizar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventoDTO> atualizar(@RequestBody EventoDTO eventoDTO) {
        Evento eventoAtualizado = eventoService.atualizar(eventoDTO);
        EventoDTO responseDTO = new EventoDTO(eventoAtualizado);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        eventoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

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