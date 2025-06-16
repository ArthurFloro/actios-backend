package br.com.actios.actios_backend.controllers;

import br.com.actios.actios_backend.dto.FeedbackEventoDTO;
import br.com.actios.actios_backend.model.FeedbackEvento;
import br.com.actios.actios_backend.service.FeedbackEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para operações relacionadas a feedbacks de eventos.
 * <p>
 * Mapeado para a rota base "/api/feedback-eventos" e fornece endpoints para
 * criação, consulta e análise estatística de feedbacks sobre eventos.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@RestController
@RequestMapping("/api/feedback-eventos")
public class FeedbackEventoController {

    @Autowired
    private FeedbackEventoService feedbackEventoService;

    /**
     * Cria um novo feedback para um evento.
     *
     * @param idUsuario ID do usuário que está enviando o feedback
     * @param idEvento ID do evento que está sendo avaliado
     * @param nota Nota atribuída ao evento (1-5)
     * @param comentario Comentário opcional sobre o evento
     * @return ResponseEntity contendo o FeedbackEventoDTO criado
     */
    @PostMapping("/criar")
    public ResponseEntity<FeedbackEventoDTO> criarFeedback(
            @RequestParam Integer idUsuario,
            @RequestParam Integer idEvento,
            @RequestParam Integer nota,
            @RequestParam(required = false) String comentario) {
        FeedbackEvento feedback = feedbackEventoService.criarFeedback(idUsuario, idEvento, nota, comentario);
        return ResponseEntity.ok(new FeedbackEventoDTO(feedback));
    }

    /**
     * Lista todos os feedbacks de um usuário específico.
     *
     * @param idUsuario ID do usuário
     * @return ResponseEntity contendo lista de FeedbackEventoDTO
     */
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<FeedbackEventoDTO>> listarPorUsuario(@PathVariable Integer idUsuario) {
        List<FeedbackEvento> feedbacks = feedbackEventoService.listarPorUsuario(idUsuario);
        List<FeedbackEventoDTO> dtos = feedbacks.stream()
                .map(FeedbackEventoDTO::new)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    /**
     * Lista todos os feedbacks de um evento específico.
     *
     * @param idEvento ID do evento
     * @return ResponseEntity contendo lista de FeedbackEventoDTO
     */
    @GetMapping("/evento/{idEvento}")
    public ResponseEntity<List<FeedbackEventoDTO>> listarPorEvento(@PathVariable Integer idEvento) {
        List<FeedbackEvento> feedbacks = feedbackEventoService.listarPorEvento(idEvento);
        List<FeedbackEventoDTO> dtos = feedbacks.stream()
                .map(FeedbackEventoDTO::new)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    /**
     * Obtém o feedback específico de um usuário para um evento.
     *
     * @param idUsuario ID do usuário
     * @param idEvento ID do evento
     * @return ResponseEntity contendo o FeedbackEventoDTO encontrado
     */
    @GetMapping("/usuario/{idUsuario}/evento/{idEvento}")
    public ResponseEntity<FeedbackEventoDTO> getFeedbackPorUsuarioEEvento(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idEvento) {
        FeedbackEvento feedback = feedbackEventoService.getFeedbackPorUsuarioEEvento(idUsuario, idEvento);
        return ResponseEntity.ok(new FeedbackEventoDTO(feedback));
    }

    /**
     * Conta o total de feedbacks recebidos para um evento.
     *
     * @param idEvento ID do evento
     * @return ResponseEntity contendo o número total de feedbacks
     */
    @GetMapping("/evento/{idEvento}/contagem")
    public ResponseEntity<Long> contarFeedbacksPorEvento(@PathVariable Integer idEvento) {
        long total = feedbackEventoService.contarFeedbacksPorEvento(idEvento);
        return ResponseEntity.ok(total);
    }

    /**
     * Calcula a média das notas recebidas por um evento.
     *
     * @param idEvento ID do evento
     * @return ResponseEntity contendo a média das notas
     */
    @GetMapping("/evento/{idEvento}/media-notas")
    public ResponseEntity<Double> calcularMediaNotasPorEvento(@PathVariable Integer idEvento) {
        double media = feedbackEventoService.calcularMediaNotasPorEvento(idEvento);
        return ResponseEntity.ok(media);
    }

    /**
     * Conta o total de feedbacks com uma nota específica.
     *
     * @param nota Nota a ser contada (1-5)
     * @return ResponseEntity contendo o número total de feedbacks com a nota
     */
    @GetMapping("/nota/{nota}/contagem")
    public ResponseEntity<Long> contarFeedbacksPorNota(@PathVariable Integer nota) {
        long total = feedbackEventoService.contarFeedbacksPorNota(nota);
        return ResponseEntity.ok(total);
    }
}