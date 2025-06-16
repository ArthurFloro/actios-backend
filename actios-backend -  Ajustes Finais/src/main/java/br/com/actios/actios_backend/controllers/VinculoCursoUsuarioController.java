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

/**
 * Controlador REST para operações relacionadas a vínculos entre usuários e cursos.
 * <p>
 * Mapeado para a rota base "/api/vinculos-curso-usuario" e fornece endpoints para
 * gerenciamento de associações entre usuários e cursos.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@RestController
@RequestMapping("/api/vinculos-curso-usuario")
public class VinculoCursoUsuarioController {

    @Autowired
    private VinculoCursoUsuarioService vinculoService;

    /**
     * Cria um vínculo entre um usuário e um curso.
     *
     * @param idUsuario ID do usuário a ser vinculado
     * @param idCurso ID do curso a ser vinculado
     * @return ResponseEntity vazio com status 200 (OK)
     */
    @PostMapping("/vincular")
    public ResponseEntity<Void> vincular(
            @RequestParam Integer idUsuario,
            @RequestParam Integer idCurso) {
        vinculoService.vincular(idUsuario, idCurso);
        return ResponseEntity.ok().build();
    }

    /**
     * Remove um vínculo existente entre um usuário e um curso.
     *
     * @param idUsuario ID do usuário a ser desvinculado
     * @param idCurso ID do curso a ser desvinculado
     * @return ResponseEntity vazio com status 200 (OK)
     */
    @DeleteMapping("/desvincular")
    public ResponseEntity<Void> desvincular(
            @RequestParam Integer idUsuario,
            @RequestParam Integer idCurso) {
        vinculoService.desvincular(idUsuario, idCurso);
        return ResponseEntity.ok().build();
    }

    /**
     * Lista todos os cursos vinculados a um usuário específico.
     *
     * @param idUsuario ID do usuário
     * @return ResponseEntity contendo lista de CursoDTO
     */
    @GetMapping("/cursos/{idUsuario}")
    public ResponseEntity<List<CursoDTO>> getCursosDoUsuario(@PathVariable Integer idUsuario) {
        List<Curso> cursos = vinculoService.getCursosDoUsuario(idUsuario);
        List<CursoDTO> cursoDTOs = cursos.stream()
                .map(c -> new CursoDTO(c.getId(), c.getNome(), c.getAreaAcademica()))
                .toList();
        return ResponseEntity.ok(cursoDTOs);
    }

    /**
     * Lista todos os usuários vinculados a um curso específico.
     *
     * @param idCurso ID do curso
     * @return ResponseEntity contendo lista de UsuarioDTO
     */
    @GetMapping("/usuarios/{idCurso}")
    public ResponseEntity<List<UsuarioDTO>> getUsuariosDoCurso(@PathVariable Integer idCurso) {
        List<Usuario> usuarios = vinculoService.getUsuariosDoCurso(idCurso);
        List<UsuarioDTO> dtos = usuarios.stream()
                .map(UsuarioDTO::fromUsuario)
                .toList();
        return ResponseEntity.ok(dtos);
    }
}