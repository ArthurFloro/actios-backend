package br.com.actios.actios_backend.controllers;

import br.com.actios.actios_backend.dto.ParticipacaoDTO;
import br.com.actios.actios_backend.model.Participacao;
import br.com.actios.actios_backend.service.ParticipacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participacoes")
public class ParticipacaoController {
    
    @Autowired
    private ParticipacaoService participacaoService;

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


    @PostMapping("/{idParticipacao}/checkin")
    public ResponseEntity<Void> realizarCheckin(@PathVariable Integer idParticipacao) {
        participacaoService.realizarCheckin(idParticipacao);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idParticipacao}/feedback")
    public ResponseEntity<Void> adicionarFeedback(
            @PathVariable Integer idParticipacao,
            @RequestParam String feedback) {
        participacaoService.adicionarFeedback(idParticipacao, feedback);
        return ResponseEntity.noContent().build();
    }

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


    @GetMapping("/evento/{idEvento}/contagem")
    public ResponseEntity<Long> contarParticipantesPorEvento(@PathVariable Integer idEvento) {
        long total = participacaoService.contarParticipantesPorEvento(idEvento);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/evento/{idEvento}/checkins")
    public ResponseEntity<Long> contarCheckinsPorEvento(@PathVariable Integer idEvento) {
        long total = participacaoService.contarCheckinsPorEvento(idEvento);
        return ResponseEntity.ok(total);
    }
}
