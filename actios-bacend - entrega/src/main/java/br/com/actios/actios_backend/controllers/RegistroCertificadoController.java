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

@RestController
@RequestMapping("/api/certificados")
public class RegistroCertificadoController {

    @Autowired
    private RegistroCertificadoService registroCertificadoService;

    @PostMapping("/criar")
    public ResponseEntity<RegistroCertificadoDTO> criarRegistroCertificado(
            @RequestParam Integer idUsuario,
            @RequestParam Integer idCurso) {
        RegistroCertificadoDTO dto = registroCertificadoService.criarRegistroCertificado(idUsuario, idCurso);
        return ResponseEntity.ok(dto);
    }

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

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<RegistroCertificadoDTO>> listarPorUsuario(@PathVariable Integer idUsuario) {
        List<RegistroCertificadoDTO> lista = registroCertificadoService.listarPorUsuario(idUsuario);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/curso/{idCurso}")
    public ResponseEntity<List<RegistroCertificadoDTO>> listarPorCurso(@PathVariable Integer idCurso) {
        List<RegistroCertificadoDTO> lista = registroCertificadoService.listarPorCurso(idCurso);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/usuario/{idUsuario}/contagem")
    public ResponseEntity<Long> contarPorUsuario(@PathVariable Integer idUsuario) {
        long total = registroCertificadoService.contarPorUsuario(idUsuario);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/curso/{idCurso}/contagem")
    public ResponseEntity<Long> contarPorCurso(@PathVariable Integer idCurso) {
        long total = registroCertificadoService.contarPorCurso(idCurso);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/usuario/{idUsuario}/curso/{idCurso}")
    public ResponseEntity<List<RegistroCertificadoDTO>> listarPorUsuarioECurso(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idCurso) {
        List<RegistroCertificadoDTO> lista = registroCertificadoService.listarPorUsuarioECurso(idUsuario, idCurso);
        return ResponseEntity.ok(lista);
    }
}
