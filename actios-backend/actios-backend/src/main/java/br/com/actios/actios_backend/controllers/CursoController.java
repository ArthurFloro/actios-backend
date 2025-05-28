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
    public ResponseEntity<List<CursoDTO>> listarTodos() {
        List<CursoDTO> cursos = cursoService.listarTodos();
        return ResponseEntity.ok(cursos);
    }

    // GET - Buscar curso por ID
    @GetMapping("/{id}")
    public ResponseEntity<CursoDTO> buscarPorId(@PathVariable Integer id) {
        CursoDTO curso = cursoService.buscarPorId(id);
        return ResponseEntity.ok(curso);
    }

    // POST - Cadastrar novo curso
    @PostMapping("/cadastrar")
    public ResponseEntity<CursoDTO> criar(@RequestBody Curso curso) {
        CursoDTO cursoSalvo = cursoService.salvar(curso);
        return ResponseEntity.ok(cursoSalvo);
    }

    // PUT - Atualizar curso
    @PutMapping("/{id}")
    public ResponseEntity<CursoDTO> atualizar(@PathVariable Integer id, @RequestBody Curso curso) {
        CursoDTO cursoAtualizado = cursoService.atualizar(id, curso);
        return ResponseEntity.ok(cursoAtualizado);
    }

    // DELETE - Excluir curso
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        cursoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}