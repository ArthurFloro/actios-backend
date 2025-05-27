package br.com.actios.actios_backend.controllers;

import br.com.actios.actios_backend.dto.InscricaoDTO;
import br.com.actios.actios_backend.model.Inscricao;
import br.com.actios.actios_backend.service.InscricaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inscricoes")
public class InscricaoController {

    @Autowired
    private InscricaoService inscricaoService;

    @PostMapping("/api/inscrever")
    public ResponseEntity<InscricaoDTO> inscrever(
            @RequestParam Integer idUsuario,
            @RequestParam Integer idEvento) throws Exception {
        Inscricao inscricao = inscricaoService.inscrever(idUsuario, idEvento);
        return ResponseEntity.ok(new InscricaoDTO(inscricao));
    }

    @GetMapping("/api/listar")
    public ResponseEntity<List<InscricaoDTO>> listarTodas() {
        List<Inscricao> inscricoes = inscricaoService.listarTodas();
        // Converte para DTO para não expor a entidade inteira e incluir palestrantes
        List<InscricaoDTO> dtos = inscricoes.stream()
                .map(InscricaoDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/api/cancelar/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Integer id) throws Exception {
        inscricaoService.cancelarInscricao(id);
        return ResponseEntity.noContent().build();
    }
}

