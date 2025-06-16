package br.com.actios.actios_backend.controllers;

import br.com.actios.actios_backend.dto.ParticipacaoDTO;
import br.com.actios.actios_backend.model.Participacao;
import br.com.actios.actios_backend.service.ParticipacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para operações relacionadas a participações em eventos.
 * <p>
 * Mapeado para a rota base "/api/participacoes" e fornece endpoints para
 * gerenciamento de participações, check-ins e feedbacks de eventos.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@RestController
@RequestMapping("/api/participacoes")
public class ParticipacaoController {

    @Autowired
    private ParticipacaoService participacaoService;

    /**
     * Registra uma nova participação de usuário em um evento.
     *
     * @param idUsuario ID do usuário participante
     * @param idEvento ID do evento
     * @return ResponseEntity com ParticipacaoDTO contendo os dados da participação registrada
     */
    @PostMapping("/registrar")
    public ResponseEntity<ParticipacaoDTO> registrarParticipacao(
            @RequestParam Integer idUsuario,
            @RequestParam Integer idEvento) {

        Participacao participacao = participacaoService.registrarParticipacao(idUsuario, idEvento);

        ParticipacaoDTO dto = new ParticipacaoDTO(
                participacao.getIdParticipacao(),
                participacao.getUsuario().getIdUsuario(),
                participacao.getUsuario().getNome(),
                participacao.getEvento().getIdEvento(),
                participacao.getEvento().getTitulo(),
                participacao.getCheckin(),
                participacao.getFeedback(),
                participacao.getDataCriacao()
        );

        return ResponseEntity.ok(dto);
    }

    /**
     * Realiza o check-in de um participante em um evento.
     *
     * @param idParticipacao ID da participação
     * @return ResponseEntity vazio com status 204 (No Content)
     */
    @PostMapping("/{idParticipacao}/checkin")
    public ResponseEntity<Void> realizarCheckin(@PathVariable Integer idParticipacao) {
        participacaoService.realizarCheckin(idParticipacao);
        return ResponseEntity.noContent().build();
    }

    /**
     * Adiciona feedback a uma participação em evento.
     *
     * @param idParticipacao ID da participação
     * @param feedback Texto do feedback
     * @return ResponseEntity vazio com status 204 (No Content)
     */
    @PostMapping("/{idParticipacao}/feedback")
    public ResponseEntity<Void> adicionarFeedback(
            @PathVariable Integer idParticipacao,
            @RequestParam String feedback) {
        participacaoService.adicionarFeedback(idParticipacao, feedback);
        return ResponseEntity.noContent().build();
    }

    /**
     * Lista todas as participações de um usuário específico.
     *
     * @param idUsuario ID do usuário
     * @return ResponseEntity com lista de ParticipacaoDTO
     */
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<ParticipacaoDTO>> listarPorUsuario(@PathVariable Integer idUsuario) {
        List<Participacao> participacoes = participacaoService.listarPorUsuario(idUsuario);

        List<ParticipacaoDTO> dtos = participacoes.stream().map(p -> new ParticipacaoDTO(
                p.getIdParticipacao(),
                p.getUsuario().getIdUsuario(),
                p.getUsuario().getNome(),
                p.getEvento().getIdEvento(),
                p.getEvento().getTitulo(),
                p.getCheckin(),
                p.getFeedback(),
                p.getDataCriacao()
        )).toList();

        return ResponseEntity.ok(dtos);
    }

    /**
     * Lista todas as participações em um evento específico.
     *
     * @param idEvento ID do evento
     * @return ResponseEntity com lista de ParticipacaoDTO
     */
    @GetMapping("/evento/{idEvento}")
    public ResponseEntity<List<ParticipacaoDTO>> listarPorEvento(@PathVariable Integer idEvento) {
        List<Participacao> participacoes = participacaoService.listarPorEvento(idEvento);

        List<ParticipacaoDTO> dtos = participacoes.stream().map(p -> new ParticipacaoDTO(
                p.getIdParticipacao(),
                p.getUsuario().getIdUsuario(),
                p.getUsuario().getNome(),
                p.getEvento().getIdEvento(),
                p.getEvento().getTitulo(),
                p.getCheckin(),
                p.getFeedback(),
                p.getDataCriacao()
        )).toList();

        return ResponseEntity.ok(dtos);
    }

    /**
     * Conta o total de participantes em um evento.
     *
     * @param idEvento ID do evento
     * @return ResponseEntity com o total de participantes
     */
    @GetMapping("/evento/{idEvento}/contagem")
    public ResponseEntity<Long> contarParticipantesPorEvento(@PathVariable Integer idEvento) {
        long total = participacaoService.contarParticipantesPorEvento(idEvento);
        return ResponseEntity.ok(total);
    }

    /**
     * Conta o total de check-ins realizados em um evento.
     *
     * @param idEvento ID do evento
     * @return ResponseEntity com o total de check-ins
     */
    @GetMapping("/evento/{idEvento}/checkins")
    public ResponseEntity<Long> contarCheckinsPorEvento(@PathVariable Integer idEvento) {
        long total = participacaoService.contarCheckinsPorEvento(idEvento);
        return ResponseEntity.ok(total);
    }
}