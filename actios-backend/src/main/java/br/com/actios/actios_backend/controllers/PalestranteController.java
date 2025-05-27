package br.com.actios.actios_backend.controllers;

import br.com.actios.actios_backend.model.Palestrante;
import br.com.actios.actios_backend.service.PalestranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/palestrantes")
public class PalestranteController {

    private final PalestranteService palestranteService;

    @Autowired
    public PalestranteController(PalestranteService palestranteService) {
        this.palestranteService = palestranteService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Palestrante> cadastrar(@RequestBody Palestrante palestrante) throws Exception {
        Palestrante novo = palestranteService.cadastrar(palestrante);
        return ResponseEntity.ok(novo);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Palestrante>> listar() {
        List<Palestrante> palestrantes = palestranteService.listarTodos();
        return ResponseEntity.ok(palestrantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Palestrante> buscarPorId(@PathVariable Integer id) throws Exception {
        Palestrante palestrante = palestranteService.buscarPorId(id);
        return ResponseEntity.ok(palestrante);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Palestrante> atualizar(@RequestBody Palestrante palestrante) throws Exception {
        Palestrante atualizado = palestranteService.atualizar(palestrante);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) throws Exception {
        palestranteService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
