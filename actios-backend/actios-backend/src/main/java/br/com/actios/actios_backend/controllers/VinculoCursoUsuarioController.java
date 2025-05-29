package br.com.actios.actios_backend.controllers;

import br.com.actios.actios_backend.dto.CursoDTO;
import br.com.actios.actios_backend.dto.UsuarioDTO;
import br.com.actios.actios_backend.model.Curso;
import br.com.actios.actios_backend.model.Usuario;
import br.com.actios.actios_backend.service.VinculoCursoUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Collections;

@RestController
@RequestMapping("/api/vinculos-curso-usuario")
public class VinculoCursoUsuarioController {
    
    @Autowired
    private VinculoCursoUsuarioService vinculoService;

    @PostMapping("/vincular")
    public ResponseEntity<Void> vincular(
            @RequestParam Integer idUsuario,
            @RequestParam Integer idCurso) {
        vinculoService.vincular(idUsuario, idCurso);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/desvincular")
    public ResponseEntity<Void> desvincular(
            @RequestParam Integer idUsuario,
            @RequestParam Integer idCurso) {
        vinculoService.desvincular(idUsuario, idCurso);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/cursos/{idUsuario}")
    public ResponseEntity<List<CursoDTO>> getCursosDoUsuario(@PathVariable Integer idUsuario) {
        List<Curso> cursos = vinculoService.getCursosDoUsuario(idUsuario);

        // Converte para DTOs
        List<CursoDTO> cursoDTOs = cursos.stream()
                .map(c -> new CursoDTO(c.getId(), c.getNome(), c.getAreaAcademica()))
                .toList();

        return ResponseEntity.ok(cursoDTOs);
    }


    @GetMapping("/usuarios/{idCurso}")
    public ResponseEntity<List<UsuarioDTO>> getUsuariosDoCurso(@PathVariable Integer idCurso) {
        List<Usuario> usuarios = vinculoService.getUsuariosDoCurso(idCurso);
        List<UsuarioDTO> dtos = usuarios.stream()
                .map(UsuarioDTO::fromUsuario)
                .toList();
        return ResponseEntity.ok(dtos);
    }
}
