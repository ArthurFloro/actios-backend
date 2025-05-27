package br.com.actios.actios_backend.controllers;

import br.com.actios.actios_backend.dto.CursoDTO;
import br.com.actios.actios_backend.model.Curso;
import br.com.actios.actios_backend.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@CrossOrigin(origins = "*")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    // GET - Listar todos os cursos
    @GetMapping("/listar")
    public List<CursoDTO> listarTodos() {
        return cursoService.listarTodos()
                .stream()
                .map(curso -> new CursoDTO(curso.getId(), curso.getNome(), curso.getAreaAcademica()))
                .toList();
    }


    // GET - Buscar curso por ID
    @GetMapping("/{id}")
    public ResponseEntity<CursoDTO> buscarPorId(@PathVariable Integer id) {
        return cursoService.buscarPorId(id)
                .map(curso -> ResponseEntity.ok(new CursoDTO(curso.getId(), curso.getNome(), curso.getAreaAcademica())))
                .orElse(ResponseEntity.notFound().build());
    }

    // POST - Cadastrar novo curso
    @PostMapping("/cadastrar")
    public Curso criar(@RequestBody Curso curso) {

        return cursoService.salvar(curso);
    }

    // PUT - Atualizar curso
    @PutMapping("/{id}")
    public ResponseEntity<CursoDTO> atualizar(@PathVariable Integer id, @RequestBody Curso curso) {
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


    // DELETE - Excluir curso
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        cursoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
