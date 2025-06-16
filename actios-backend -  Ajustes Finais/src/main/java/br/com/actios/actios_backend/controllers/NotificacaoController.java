package br.com.actios.actios_backend.controllers;

import br.com.actios.actios_backend.dto.NotificacaoDTO;
import br.com.actios.actios_backend.model.Notificacao;
import br.com.actios.actios_backend.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador REST para operações relacionadas a notificações.
 * <p>
 * Mapeado para a rota base "/api/notificacoes" e fornece endpoints para
 * criação, consulta e contagem de notificações para usuários e eventos.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@RestController
@RequestMapping("/api/notificacoes")
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    /**
     * Cria uma nova notificação para um usuário e evento.
     *
     * @param idUsuario ID do usuário que receberá a notificação
     * @param idEvento ID do evento relacionado à notificação
     * @param mensagem Conteúdo da notificação
     * @return ResponseEntity com a Notificacao criada
     */
    @PostMapping("/criar")
    public ResponseEntity<Notificacao> criarNotificacao(
            @RequestParam Integer idUsuario,
            @RequestParam Integer idEvento,
            @RequestParam String mensagem) {
        Notificacao notificacao = notificacaoService.criarNotificacao(idUsuario, idEvento, mensagem);
        return ResponseEntity.ok(notificacao);
    }

    /**
     * Lista todas as notificações de um usuário específico.
     *
     * @param idUsuario ID do usuário
     * @return ResponseEntity com lista de NotificacaoDTO
     */
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<NotificacaoDTO>> listarPorUsuario(@PathVariable Integer idUsuario) {
        List<Notificacao> notificacoes = notificacaoService.listarPorUsuario(idUsuario);
        List<NotificacaoDTO> dtos = notificacoes.stream()
                .map(NotificacaoDTO::new)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    /**
     * Lista todas as notificações relacionadas a um evento específico.
     *
     * @param idEvento ID do evento
     * @return ResponseEntity com lista de NotificacaoDTO
     */
    @GetMapping("/evento/{idEvento}")
    public ResponseEntity<List<NotificacaoDTO>> listarPorEvento(@PathVariable Integer idEvento) {
        List<Notificacao> notificacoes = notificacaoService.listarPorEvento(idEvento);
        List<NotificacaoDTO> dtos = notificacoes.stream()
                .map(NotificacaoDTO::new)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    /**
     * Lista notificações criadas em uma data específica.
     *
     * @param data Data de criação das notificações (formato ISO)
     * @return ResponseEntity com lista de NotificacaoDTO
     */
    @GetMapping("/data")
    public ResponseEntity<List<NotificacaoDTO>> listarPorData(@RequestParam("data") LocalDateTime data) {
        List<Notificacao> notificacoes = notificacaoService.listarPorData(data);
        List<NotificacaoDTO> dtos = notificacoes.stream()
                .map(NotificacaoDTO::new)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    /**
     * Conta o total de notificações de um usuário.
     *
     * @param idUsuario ID do usuário
     * @return ResponseEntity com o total de notificações
     */
    @GetMapping("/usuario/{idUsuario}/contagem")
    public ResponseEntity<Long> contarPorUsuario(@PathVariable Integer idUsuario) {
        long total = notificacaoService.contarPorUsuario(idUsuario);
        return ResponseEntity.ok(total);
    }

    /**
     * Conta o total de notificações relacionadas a um evento.
     *
     * @param idEvento ID do evento
     * @return ResponseEntity com o total de notificações
     */
    @GetMapping("/evento/{idEvento}/contagem")
    public ResponseEntity<Long> contarPorEvento(@PathVariable Integer idEvento) {
        long total = notificacaoService.contarPorEvento(idEvento);
        return ResponseEntity.ok(total);
    }

    /**
     * Lista notificações específicas de um usuário para um evento.
     *
     * @param idUsuario ID do usuário
     * @param idEvento ID do evento
     * @return ResponseEntity com lista de Notificacao
     */
    @GetMapping("/usuario/{idUsuario}/evento/{idEvento}")
    public ResponseEntity<List<Notificacao>> listarPorUsuarioEEvento(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idEvento) {
        List<Notificacao> notificacoes = notificacaoService.listarPorUsuarioEEvento(idUsuario, idEvento);
        return ResponseEntity.ok(notificacoes);
    }
}