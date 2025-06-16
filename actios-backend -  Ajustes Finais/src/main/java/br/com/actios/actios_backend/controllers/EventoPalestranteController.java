package br.com.actios.actios_backend.controllers;

import br.com.actios.actios_backend.dto.EventoDTO;
import br.com.actios.actios_backend.service.EventoPalestranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para gestão da relação entre eventos e palestrantes.
 * <p>
 * Mapeado para a rota base "/api/evento_palestrante" e fornece endpoints para
 * associação, desassociação e consulta de palestrantes em eventos.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@RestController
@RequestMapping("/api/evento_palestrante")
public class EventoPalestranteController {

    @Autowired
    private EventoPalestranteService eventoPalestranteService;

    /**
     * Busca eventos futuros associados a um palestrante específico.
     *
     * @param nome Nome do palestrante para filtro
     * @param pageable Configuração de paginação (page, size, sort)
     * @return Page<EventoDTO> contendo os eventos paginados
     */
    @GetMapping("/eventos-futuros")
    public Page<EventoDTO> getEventosFuturosPorNomePalestrante(
            @RequestParam String nome,
            Pageable pageable) {
        return eventoPalestranteService.buscarEventosFuturosPorNomePalestrante(nome, pageable);
    }

    /**
     * Associa um palestrante a um evento.
     *
     * @param eventoId ID do evento
     * @param palestranteId ID do palestrante
     * @return String com mensagem de confirmação da operação
     */
    @PostMapping("/associar")
    public String associarPalestrante(
            @RequestParam int eventoId,
            @RequestParam int palestranteId) {
        return eventoPalestranteService.associarPalestranteAoEvento(eventoId, palestranteId);
    }

    /**
     * Remove a associação entre um palestrante e um evento.
     *
     * @param eventoId ID do evento
     * @param palestranteId ID do palestrante
     * @return String com mensagem de confirmação da operação
     */
    @DeleteMapping("/desassociar")
    public String desassociarPalestrante(
            @RequestParam int eventoId,
            @RequestParam int palestranteId) {
        return eventoPalestranteService.desassociarPalestranteDoEvento(eventoId, palestranteId);
    }
}