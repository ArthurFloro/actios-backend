package br.com.actios.actios_backend.controllers;

import br.com.actios.actios_backend.dto.InscricaoDTO;
import br.com.actios.actios_backend.model.Inscricao;
import br.com.actios.actios_backend.service.InscricaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para operações relacionadas a inscrições em eventos.
 * <p>
 * Mapeado para a rota base "/api/inscricoes" e fornece endpoints para
 * gerenciamento de inscrições de usuários em eventos.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@RestController
@RequestMapping("/api/inscricoes")
public class InscricaoController {

    @Autowired
    private InscricaoService inscricaoService;

    /**
     * Realiza a inscrição de um usuário em um evento.
     *
     * @param idUsuario ID do usuário que está se inscrevendo
     * @param idEvento ID do evento no qual o usuário deseja se inscrever
     * @return ResponseEntity contendo o InscricaoDTO com os dados da inscrição realizada
     * @throws Exception Se ocorrer erro durante o processo de inscrição
     */
    @PostMapping("/inscrever")
    public ResponseEntity<InscricaoDTO> inscrever(
            @RequestParam Integer idUsuario,
            @RequestParam Integer idEvento) throws Exception {
        Inscricao inscricao = inscricaoService.inscrever(idUsuario, idEvento);
        return ResponseEntity.ok(new InscricaoDTO(inscricao));
    }

    /**
     * Lista todas as inscrições cadastradas no sistema.
     *
     * @return ResponseEntity contendo lista de InscricaoDTO com todas as inscrições
     */
    @GetMapping("/listar")
    public ResponseEntity<List<InscricaoDTO>> listarTodas() {
        List<Inscricao> inscricoes = inscricaoService.listarTodas();
        List<InscricaoDTO> dtos = inscricoes.stream()
                .map(InscricaoDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Cancela uma inscrição existente.
     *
     * @param id ID da inscrição a ser cancelada
     * @return ResponseEntity vazio com status 204 (No Content)
     * @throws Exception Se a inscrição não for encontrada ou ocorrer erro no cancelamento
     */
    @DeleteMapping("/cancelar/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Integer id) throws Exception {
        inscricaoService.cancelarInscricao(id);
        return ResponseEntity.noContent().build();
    }
}