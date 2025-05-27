package br.com.actios.actios_backend.controllers;

import br.com.actios.actios_backend.dto.EventoDTO;
import br.com.actios.actios_backend.model.Evento;
import br.com.actios.actios_backend.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    private final EventoService eventoService;

    @Autowired
    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

        @PostMapping("/cadastrar")
        public ResponseEntity<Evento> cadastrar(@RequestBody Evento evento) throws Exception {
            Evento novo = eventoService.cadastrar(evento);
            return ResponseEntity.ok(novo);
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
    public ResponseEntity<Evento> buscarPorId(@PathVariable Integer id) throws Exception {
        Evento evento = eventoService.buscarPorId(id);
        return ResponseEntity.ok(evento);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Evento> atualizar(@RequestBody Evento evento) throws Exception {
        Evento atualizado = eventoService.atualizar(evento);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) throws Exception {
        eventoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/futuros-por-nome-palestrante")
    public ResponseEntity<Page<Evento>> listarEventosFuturosPorNomePalestrante(
            @RequestParam String nome,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sort
    ) {
        Sort.Direction direction = sort.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "data"));
        Page<Evento> eventos = eventoService.listarEventosFuturosPorNomePalestrante(nome, pageable);
        return ResponseEntity.ok(eventos);
    }
}
