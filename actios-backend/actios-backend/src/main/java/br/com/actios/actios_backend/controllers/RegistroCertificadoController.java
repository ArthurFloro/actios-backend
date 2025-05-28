package br.com.actios.actios_backend.controllers;

import br.com.actios.actios_backend.dto.RegistroCertificadoDTO;
import br.com.actios.actios_backend.service.RegistroCertificadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/validar")
    public ResponseEntity<RegistroCertificadoDTO> validarCertificado(
            @RequestParam String codigoValidacao) {
        RegistroCertificadoDTO dto = registroCertificadoService.validarCertificado(codigoValidacao);
        return ResponseEntity.ok(dto);
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
