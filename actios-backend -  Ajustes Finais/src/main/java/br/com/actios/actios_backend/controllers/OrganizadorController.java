package br.com.actios.actios_backend.controllers;

import br.com.actios.actios_backend.dto.OrganizadorDTO;
import br.com.actios.actios_backend.model.Organizador;
import br.com.actios.actios_backend.service.OrganizadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para operações relacionadas a organizadores de eventos.
 * <p>
 * Mapeado para a rota base "/api/organizadores" e fornece endpoints para
 * gerenciamento de organizadores, incluindo criação, atualização e consultas.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@RestController
@RequestMapping("/api/organizadores")
public class OrganizadorController {

    @Autowired
    private OrganizadorService organizadorService;

    /**
     * Cria um novo organizador no sistema.
     *
     * @param dto DTO contendo os dados do organizador (nome, email e ID da faculdade)
     * @return ResponseEntity com o Organizador criado
     */
    @PostMapping("/criar")
    public ResponseEntity<Organizador> criarOrganizador(@RequestBody OrganizadorDTO dto) {
        Organizador organizador = organizadorService.criarOrganizador(
                dto.getNome(),
                dto.getEmail(),
                dto.getIdFaculdade()
        );
        return ResponseEntity.ok(organizador);
    }

    /**
     * Atualiza os dados de um organizador existente.
     *
     * @param idOrganizador ID do organizador a ser atualizado
     * @param dto DTO contendo os novos dados do organizador
     * @return ResponseEntity com o Organizador atualizado
     */
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

    /**
     * Busca um organizador pelo email.
     *
     * @param email Email do organizador a ser buscado
     * @return ResponseEntity contendo o Organizador encontrado (pode ser vazio)
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<Optional<Organizador>> buscarPorEmail(@PathVariable String email) {
        Optional<Organizador> organizador = organizadorService.buscarPorEmail(email);
        return ResponseEntity.ok(organizador);
    }

    /**
     * Busca organizadores por nome (busca parcial).
     *
     * @param nome Nome ou parte do nome para busca
     * @return ResponseEntity com lista de Organizadores encontrados
     */
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Organizador>> buscarPorNome(@PathVariable String nome) {
        List<Organizador> organizadores = organizadorService.buscarPorNome(nome);
        return ResponseEntity.ok(organizadores);
    }

    /**
     * Lista todos os organizadores de uma faculdade específica.
     *
     * @param idFaculdade ID da faculdade
     * @return ResponseEntity com lista de Organizadores
     */
    @GetMapping("/faculdade/{idFaculdade}")
    public ResponseEntity<List<Organizador>> listarPorFaculdade(@PathVariable Integer idFaculdade) {
        List<Organizador> organizadores = organizadorService.listarPorFaculdade(idFaculdade);
        return ResponseEntity.ok(organizadores);
    }

    /**
     * Conta o total de organizadores de uma faculdade.
     *
     * @param idFaculdade ID da faculdade
     * @return ResponseEntity com o total de organizadores
     */
    @GetMapping("/faculdade/{idFaculdade}/contagem")
    public ResponseEntity<Long> contarPorFaculdade(@PathVariable Integer idFaculdade) {
        long total = organizadorService.contarOrganizadoresPorFaculdade(idFaculdade);
        return ResponseEntity.ok(total);
    }
}