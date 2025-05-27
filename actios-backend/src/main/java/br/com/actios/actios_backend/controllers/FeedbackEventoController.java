package br.com.actios.actios_backend.controllers;

import br.com.actios.actios_backend.dto.FeedbackEventoDTO;
import br.com.actios.actios_backend.model.FeedbackEvento;
import br.com.actios.actios_backend.service.FeedbackEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback-eventos")
public class FeedbackEventoController {

    @Autowired
    private FeedbackEventoService feedbackEventoService;

    @PostMapping("/criar")
    public ResponseEntity<FeedbackEventoDTO> criarFeedback(
            @RequestParam Integer idUsuario,
            @RequestParam Integer idEvento,
            @RequestParam Integer nota,
            @RequestParam(required = false) String comentario) {
        FeedbackEvento feedback = feedbackEventoService.criarFeedback(idUsuario, idEvento, nota, comentario);
        return ResponseEntity.ok(new FeedbackEventoDTO(feedback));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<FeedbackEventoDTO>> listarPorUsuario(@PathVariable Integer idUsuario) {
        List<FeedbackEvento> feedbacks = feedbackEventoService.listarPorUsuario(idUsuario);
        List<FeedbackEventoDTO> dtos = feedbacks.stream()
                .map(FeedbackEventoDTO::new)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/evento/{idEvento}")
    public ResponseEntity<List<FeedbackEventoDTO>> listarPorEvento(@PathVariable Integer idEvento) {
        List<FeedbackEvento> feedbacks = feedbackEventoService.listarPorEvento(idEvento);
        List<FeedbackEventoDTO> dtos = feedbacks.stream()
                .map(FeedbackEventoDTO::new)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/usuario/{idUsuario}/evento/{idEvento}")
    public ResponseEntity<FeedbackEventoDTO> getFeedbackPorUsuarioEEvento(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idEvento) {
        FeedbackEvento feedback = feedbackEventoService.getFeedbackPorUsuarioEEvento(idUsuario, idEvento);
        return ResponseEntity.ok(new FeedbackEventoDTO(feedback));
    }

    @GetMapping("/evento/{idEvento}/contagem")
    public ResponseEntity<Long> contarFeedbacksPorEvento(@PathVariable Integer idEvento) {
        long total = feedbackEventoService.contarFeedbacksPorEvento(idEvento);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/evento/{idEvento}/media-notas")
    public ResponseEntity<Double> calcularMediaNotasPorEvento(@PathVariable Integer idEvento) {
        double media = feedbackEventoService.calcularMediaNotasPorEvento(idEvento);
        return ResponseEntity.ok(media);
    }

    @GetMapping("/nota/{nota}/contagem")
    public ResponseEntity<Long> contarFeedbacksPorNota(@PathVariable Integer nota) {
        long total = feedbackEventoService.contarFeedbacksPorNota(nota);
        return ResponseEntity.ok(total);
    }
}
