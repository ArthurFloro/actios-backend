package br.com.actios.actios_backend.controllers;

import br.com.actios.actios_backend.dto.CursoDTO;
import br.com.actios.actios_backend.model.Curso;
import br.com.actios.actios_backend.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.validation.Valid;

/**
 * Controlador REST para operações relacionadas a cursos.
 * <p>
 * Mapeado para a rota base "/api/cursos" e fornece endpoints para CRUD de cursos.
 * Permite acesso cross-origin através da anotação @CrossOrigin.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@RestController
@RequestMapping("/api/cursos")
@CrossOrigin(origins = "*")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    /**
     * Lista todos os cursos cadastrados no formato DTO.
     *
     * @return Lista de CursoDTO contendo id, nome e área acadêmica de cada curso.
     */
    @GetMapping("/listar")
    public List<CursoDTO> listarTodos() {
        return cursoService.listarTodos()
                .stream()
                .map(curso -> new CursoDTO(curso.getId(), curso.getNome(), curso.getAreaAcademica()))
                .toList();
    }

    /**
     * Busca um curso específico pelo ID.
     *
     * @param id ID do curso a ser buscado (como variável de caminho).
     * @return ResponseEntity contendo o CursoDTO correspondente e status HTTP 200 (OK),
     *         ou status 404 (Not Found) se o curso não existir.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CursoDTO> buscarPorId(@PathVariable Integer id) {
        return cursoService.buscarPorId(id)
                .map(curso -> ResponseEntity.ok(new CursoDTO(curso.getId(), curso.getNome(), curso.getAreaAcademica())))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cadastra um novo curso.
     *
     * @param cursoDTO DTO contendo os dados do curso a ser cadastrado (no corpo da requisição).
     * @return ResponseEntity contendo o CursoDTO do curso cadastrado e status HTTP 200 (OK).
     */
    @PostMapping(value = "/cadastrar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CursoDTO> criar(@Valid @RequestBody CursoDTO cursoDTO) {
        Curso curso = new Curso();
        curso.setNome(cursoDTO.getNome());
        curso.setAreaAcademica(cursoDTO.getAreaAcademica());

        Curso cursoSalvo = cursoService.salvar(curso);

        return ResponseEntity.ok(new CursoDTO(
                cursoSalvo.getId(),
                cursoSalvo.getNome(),
                cursoSalvo.getAreaAcademica()
        ));
    }

    /**
     * Atualiza os dados de um curso existente.
     *
     * @param id ID do curso a ser atualizado (como variável de caminho).
     * @param cursoDTO DTO contendo os novos dados do curso (no corpo da requisição).
     * @return ResponseEntity contendo o CursoDTO atualizado e status HTTP 200 (OK),
     *         ou status 404 (Not Found) se o curso não existir.
     */
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<CursoDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody CursoDTO cursoDTO) {

        Curso curso = new Curso();
        curso.setNome(cursoDTO.getNome());
        curso.setAreaAcademica(cursoDTO.getAreaAcademica());

        try {
            Curso atualizado = cursoService.atualizar(id, curso);
            return ResponseEntity.ok(new CursoDTO(
                    atualizado.getId(),
                    atualizado.getNome(),
                    atualizado.getAreaAcademica()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Exclui um curso pelo ID.
     *
     * @param id ID do curso a ser excluído (como variável de caminho).
     * @return ResponseEntity vazio com status HTTP 204 (No Content).
     */
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        cursoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}