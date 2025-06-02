package br.com.actios.actios_backend.controllers;

import br.com.actios.actios_backend.model.Categoria;
import br.com.actios.actios_backend.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Categoria> cadastrar(@RequestBody Categoria categoria) throws Exception {
        Categoria nova = categoriaService.cadastrar(categoria);
        return ResponseEntity.ok(nova);
    }

    @GetMapping({"/listar", "/listarTodas"})
    public ResponseEntity<List<Categoria>> listar() {
        List<Categoria> categorias = categoriaService.listarTodas();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarPorId(@PathVariable Integer id) throws Exception {
        Categoria categoria = categoriaService.buscarPorId(id);
        return ResponseEntity.ok(categoria);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Categoria> atualizar(@RequestBody Categoria categoria) throws Exception {
        Categoria atualizada = categoriaService.atualizar(categoria);
        return ResponseEntity.ok(atualizada);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) throws Exception {
        categoriaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
