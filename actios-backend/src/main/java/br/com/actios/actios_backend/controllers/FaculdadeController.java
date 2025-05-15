package br.com.actios.actios_backend.controllers;

import br.com.actios.actios_backend.model.Faculdade;
import br.com.actios.actios_backend.service.FaculdadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faculdades")
public class FaculdadeController {

    private final FaculdadeService faculdadeService;

    @Autowired
    public FaculdadeController(FaculdadeService faculdadeService) {
        this.faculdadeService = faculdadeService;
    }

    // POST - Cadastrar nova faculdade
    @PostMapping("/cadastrar")
    public Faculdade criarFaculdade(@RequestBody Faculdade faculdade) throws Exception {
        return faculdadeService.cadastrar(faculdade);
    }

    // GET - Listar todas as faculdades
    @GetMapping("/listar")
    public List<Faculdade> listarFaculdades() {
        return faculdadeService.listarTodas();
    }

    // GET - Buscar por ID
    @GetMapping("/{id}")
    public Faculdade buscarPorId(@PathVariable Long id) throws Exception {
        return faculdadeService.buscarPorId(id);
    }

    // PUT - Atualizar faculdade
    @PutMapping("/atualizar")
    public Faculdade atualizarFaculdade(@RequestBody Faculdade faculdade) throws Exception {
        return faculdadeService.atualizar(faculdade);
    }

    // DELETE - Excluir faculdade
    @DeleteMapping("/excluir/{id}")
    public void excluirFaculdade(@PathVariable Long id) throws Exception {
        faculdadeService.excluir(id);
    }
}
