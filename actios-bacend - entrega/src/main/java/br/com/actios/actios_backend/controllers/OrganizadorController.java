package br.com.actios.actios_backend.controllers;

import br.com.actios.actios_backend.dto.OrganizadorDTO;
import br.com.actios.actios_backend.model.Organizador;
import br.com.actios.actios_backend.service.OrganizadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/organizadores")
public class OrganizadorController {
    
    @Autowired
    private OrganizadorService organizadorService;

    @PostMapping("/criar")
    public ResponseEntity<Organizador> criarOrganizador(@RequestBody OrganizadorDTO dto) {
        Organizador organizador = organizadorService.criarOrganizador(
                dto.getNome(),
                dto.getEmail(),
                dto.getIdFaculdade()
        );
        return ResponseEntity.ok(organizador);
    }


    @PutMapping("/{idOrganizador}")
    public ResponseEntity<Organizador> atualizarOrganizador(
            @PathVariable Integer idOrganizador,
            @RequestBody OrganizadorDTO dto) {

        Organizador organizador = organizadorService.atualizarOrganizador(
                idOrganizador,
                dto.getNome(),
                dto.getEmail(),
                dto.getIdFaculdade()
        );
        return ResponseEntity.ok(organizador);
    }


    @GetMapping("/email/{email}")
    public ResponseEntity<Optional<Organizador>> buscarPorEmail(@PathVariable String email) {
        Optional<Organizador> organizador = organizadorService.buscarPorEmail(email);
        return ResponseEntity.ok(organizador);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Organizador>> buscarPorNome(@PathVariable String nome) {
        List<Organizador> organizadores = organizadorService.buscarPorNome(nome);
        return ResponseEntity.ok(organizadores);
    }

    @GetMapping("/faculdade/{idFaculdade}")
    public ResponseEntity<List<Organizador>> listarPorFaculdade(@PathVariable Integer idFaculdade) {
        List<Organizador> organizadores = organizadorService.listarPorFaculdade(idFaculdade);
        return ResponseEntity.ok(organizadores);
    }

    @GetMapping("/faculdade/{idFaculdade}/contagem")
    public ResponseEntity<Long> contarPorFaculdade(@PathVariable Integer idFaculdade) {
        long total = organizadorService.contarOrganizadoresPorFaculdade(idFaculdade);
        return ResponseEntity.ok(total);
    }
}
