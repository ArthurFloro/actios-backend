package br.com.actios.actios_backend.controllers;

import br.com.actios.actios_backend.dto.RegistroCertificadoDTO;
import br.com.actios.actios_backend.exceptions.RecursoNaoEncontradoException;
import br.com.actios.actios_backend.service.RegistroCertificadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Controller REST para operações relacionadas a certificados de cursos.
 * <p>
 * Mapeado para a rota base "/api/certificados" e fornece endpoints para
 * criação, validação e consulta de certificados de usuários em cursos.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@RestController
@RequestMapping("/api/certificados")
public class RegistroCertificadoController {

    @Autowired
    private RegistroCertificadoService registroCertificadoService;

    /**
     * Cria um novo registro de certificado para um usuário em um curso.
     *
     * @param idUsuario ID do usuário que receberá o certificado
     * @param idCurso ID do curso relacionado ao certificado
     * @return ResponseEntity com RegistroCertificadoDTO contendo os dados do certificado criado
     */
    @PostMapping("/criar")
    public ResponseEntity<RegistroCertificadoDTO> criarRegistroCertificado(
            @RequestParam Integer idUsuario,
            @RequestParam Integer idCurso) {
        RegistroCertificadoDTO dto = registroCertificadoService.criarRegistroCertificado(idUsuario, idCurso);
        return ResponseEntity.ok(dto);
    }

    /**
     * Valida um certificado pelo código de validação.
     *
     * @param codigoValidacao Código único de validação do certificado
     * @return ResponseEntity com RegistroCertificadoDTO se válido ou mensagem de erro se inválido
     */
    @GetMapping("/validar/{codigoValidacao}")
    public ResponseEntity<?> validarCertificado(
            @PathVariable String codigoValidacao) {
        try {
            RegistroCertificadoDTO dto = registroCertificadoService.validarCertificado(codigoValidacao);
            return ResponseEntity.ok(dto);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "error", "Certificado não encontrado",
                            "message", e.getMessage(),
                            "codigo", codigoValidacao,
                            "timestamp", LocalDateTime.now()
                    ));
        }
    }

    /**
     * Lista todos os certificados de um usuário específico.
     *
     * @param idUsuario ID do usuário
     * @return ResponseEntity com lista de RegistroCertificadoDTO
     */
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<RegistroCertificadoDTO>> listarPorUsuario(@PathVariable Integer idUsuario) {
        List<RegistroCertificadoDTO> lista = registroCertificadoService.listarPorUsuario(idUsuario);
        return ResponseEntity.ok(lista);
    }

    /**
     * Lista todos os certificados emitidos para um curso específico.
     *
     * @param idCurso ID do curso
     * @return ResponseEntity com lista de RegistroCertificadoDTO
     */
    @GetMapping("/curso/{idCurso}")
    public ResponseEntity<List<RegistroCertificadoDTO>> listarPorCurso(@PathVariable Integer idCurso) {
        List<RegistroCertificadoDTO> lista = registroCertificadoService.listarPorCurso(idCurso);
        return ResponseEntity.ok(lista);
    }

    /**
     * Conta o total de certificados de um usuário.
     *
     * @param idUsuario ID do usuário
     * @return ResponseEntity com o total de certificados
     */
    @GetMapping("/usuario/{idUsuario}/contagem")
    public ResponseEntity<Long> contarPorUsuario(@PathVariable Integer idUsuario) {
        long total = registroCertificadoService.contarPorUsuario(idUsuario);
        return ResponseEntity.ok(total);
    }

    /**
     * Conta o total de certificados emitidos para um curso.
     *
     * @param idCurso ID do curso
     * @return ResponseEntity com o total de certificados
     */
    @GetMapping("/curso/{idCurso}/contagem")
    public ResponseEntity<Long> contarPorCurso(@PathVariable Integer idCurso) {
        long total = registroCertificadoService.contarPorCurso(idCurso);
        return ResponseEntity.ok(total);
    }

    /**
     * Lista certificados específicos de um usuário em um curso.
     *
     * @param idUsuario ID do usuário
     * @param idCurso ID do curso
     * @return ResponseEntity com lista de RegistroCertificadoDTO
     */
    @GetMapping("/usuario/{idUsuario}/curso/{idCurso}")
    public ResponseEntity<List<RegistroCertificadoDTO>> listarPorUsuarioECurso(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idCurso) {
        List<RegistroCertificadoDTO> lista = registroCertificadoService.listarPorUsuarioECurso(idUsuario, idCurso);
        return ResponseEntity.ok(lista);
    }
}