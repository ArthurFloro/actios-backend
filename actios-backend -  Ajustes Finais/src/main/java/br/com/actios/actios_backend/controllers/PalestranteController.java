package br.com.actios.actios_backend.controllers;

import br.com.actios.actios_backend.model.Palestrante;
import br.com.actios.actios_backend.service.PalestranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para operações relacionadas a palestrantes.
 * <p>
 * Mapeado para a rota base "/api/palestrantes" e fornece endpoints para
 * gerenciamento completo de palestrantes, incluindo cadastro, listagem,
 * busca, atualização e exclusão.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@RestController
@RequestMapping("/api/palestrantes")
public class PalestranteController {

    private final PalestranteService palestranteService;

    /**
     * Construtor com injeção de dependência do serviço de palestrantes.
     *
     * @param palestranteService Serviço contendo a lógica de negócios para palestrantes
     */
    @Autowired
    public PalestranteController(PalestranteService palestranteService) {
        this.palestranteService = palestranteService;
    }

    /**
     * Cadastra um novo palestrante no sistema.
     *
     * @param palestrante Objeto Palestrante contendo os dados para cadastro
     * @return ResponseEntity com o Palestrante cadastrado
     * @throws Exception Se ocorrer erro durante o cadastro
     */
    @PostMapping("/cadastrar")
    public ResponseEntity<Palestrante> cadastrar(@RequestBody Palestrante palestrante) throws Exception {
        Palestrante novo = palestranteService.cadastrar(palestrante);
        return ResponseEntity.ok(novo);
    }

    /**
     * Lista todos os palestrantes cadastrados.
     *
     * @return ResponseEntity contendo lista de Palestrantes
     */
    @GetMapping("/listar")
    public ResponseEntity<List<Palestrante>> listar() {
        List<Palestrante> palestrantes = palestranteService.listarTodos();
        return ResponseEntity.ok(palestrantes);
    }

    /**
     * Busca um palestrante específico pelo ID.
     *
     * @param id ID do palestrante a ser buscado
     * @return ResponseEntity com o Palestrante encontrado
     * @throws Exception Se o palestrante não for encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<Palestrante> buscarPorId(@PathVariable Integer id) throws Exception {
        Palestrante palestrante = palestranteService.buscarPorId(id);
        return ResponseEntity.ok(palestrante);
    }

    /**
     * Atualiza os dados de um palestrante existente.
     *
     * @param palestrante Objeto Palestrante com os dados atualizados
     * @return ResponseEntity com o Palestrante atualizado
     * @throws Exception Se o palestrante não existir ou ocorrer erro na atualização
     */
    @PutMapping("/atualizar")
    public ResponseEntity<Palestrante> atualizar(@RequestBody Palestrante palestrante) throws Exception {
        Palestrante atualizado = palestranteService.atualizar(palestrante);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * Exclui um palestrante pelo ID.
     *
     * @param id ID do palestrante a ser excluído
     * @return ResponseEntity vazio com status 204 (No Content)
     * @throws Exception Se o palestrante não existir ou ocorrer erro na exclusão
     */
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) throws Exception {
        palestranteService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}