package br.com.actios.actios_backend.controllers;

import br.com.actios.actios_backend.dto.NotificacaoDTO;
import br.com.actios.actios_backend.model.Notificacao;
import br.com.actios.actios_backend.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/notificacoes")
public class NotificacaoController {
    
    @Autowired
    private NotificacaoService notificacaoService;

    @PostMapping("/criar")
    public ResponseEntity<Notificacao> criarNotificacao(
            @RequestParam Integer idUsuario,
            @RequestParam Integer idEvento,
            @RequestParam String mensagem) {
        Notificacao notificacao = notificacaoService.criarNotificacao(idUsuario, idEvento, mensagem);
        return ResponseEntity.ok(notificacao);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<NotificacaoDTO>> listarPorUsuario(@PathVariable Integer idUsuario) {
        List<Notificacao> notificacoes = notificacaoService.listarPorUsuario(idUsuario);
        List<NotificacaoDTO> dtos = notificacoes.stream()
                .map(NotificacaoDTO::new)
                .toList();
        return ResponseEntity.ok(dtos);
    }


    @GetMapping("/evento/{idEvento}")
    public ResponseEntity<List<NotificacaoDTO>> listarPorEvento(@PathVariable Integer idEvento) {
        List<Notificacao> notificacoes = notificacaoService.listarPorEvento(idEvento);
        List<NotificacaoDTO> dtos = notificacoes.stream()
                .map(NotificacaoDTO::new)
                .toList();
        return ResponseEntity.ok(dtos);
    }


    @GetMapping("/data")
    public ResponseEntity<List<NotificacaoDTO>> listarPorData(@RequestParam("data") LocalDateTime data) {
        List<Notificacao> notificacoes = notificacaoService.listarPorData(data);

        List<NotificacaoDTO> dtos = notificacoes.stream()
                .map(NotificacaoDTO::new)
                .toList();
        return ResponseEntity.ok(dtos);
    }


    @GetMapping("/usuario/{idUsuario}/contagem")
    public ResponseEntity<Long> contarPorUsuario(@PathVariable Integer idUsuario) {
        long total = notificacaoService.contarPorUsuario(idUsuario);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/evento/{idEvento}/contagem")
    public ResponseEntity<Long> contarPorEvento(@PathVariable Integer idEvento) {
        long total = notificacaoService.contarPorEvento(idEvento);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/usuario/{idUsuario}/evento/{idEvento}")
    public ResponseEntity<List<Notificacao>> listarPorUsuarioEEvento(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idEvento) {
        List<Notificacao> notificacoes = notificacaoService.listarPorUsuarioEEvento(idUsuario, idEvento);
        return ResponseEntity.ok(notificacoes);
    }
}
