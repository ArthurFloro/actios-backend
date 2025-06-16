package br.com.actios.actios_backend.controllers;

import br.com.actios.actios_backend.model.Categoria;
import br.com.actios.actios_backend.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para operações relacionadas a categorias.
 * <p>
 * Mapeado para a rota base "/api/categorias" e fornece endpoints para CRUD de categorias.
 *
 * @author Equipe Actios/Autor
 * @version 1.0
 * @since 2023-08-30
 */
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    /**
     * Construtor com injeção de dependência do serviço de categorias.
     *
     * @param categoriaService Serviço de lógica de negócios para categorias.
     */
    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    /**
     * Cadastra uma nova categoria.
     *
     * @param categoria Objeto Categoria contendo os dados para cadastro (no corpo da requisição).
     * @return ResponseEntity contendo a categoria cadastrada e status HTTP 200 (OK).
     * @throws Exception Se ocorrer um erro durante o cadastro.
     */
    @PostMapping("/cadastrar")
    public ResponseEntity<Categoria> cadastrar(@RequestBody Categoria categoria) throws Exception {
        Categoria nova = categoriaService.cadastrar(categoria);
        return ResponseEntity.ok(nova);
    }

    /**
     * Lista todas as categorias cadastradas.
     * <p>
     * Acessível pelos endpoints "/listar" e "/listarTodas".
     *
     * @return ResponseEntity contendo a lista de categorias e status HTTP 200 (OK).
     */
    @GetMapping({"/listar", "/listarTodas"})
    public ResponseEntity<List<Categoria>> listar() {
        List<Categoria> categorias = categoriaService.listarTodas();
        return ResponseEntity.ok(categorias);
    }

    /**
     * Busca uma categoria pelo ID.
     *
     * @param id ID da categoria (como variável de caminho).
     * @return ResponseEntity contendo a categoria encontrada e status HTTP 200 (OK).
     * @throws Exception Se a categoria não for encontrada ou ocorrer outro erro.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarPorId(@PathVariable Integer id) throws Exception {
        Categoria categoria = categoriaService.buscarPorId(id);
        return ResponseEntity.ok(categoria);
    }

    /**
     * Atualiza os dados de uma categoria existente.
     *
     * @param categoria Objeto Categoria com os dados atualizados (no corpo da requisição).
     * @return ResponseEntity contendo a categoria atualizada e status HTTP 200 (OK).
     * @throws Exception Se a categoria não existir ou ocorrer outro erro.
     */
    @PutMapping("/atualizar")
    public ResponseEntity<Categoria> atualizar(@RequestBody Categoria categoria) throws Exception {
        Categoria atualizada = categoriaService.atualizar(categoria);
        return ResponseEntity.ok(atualizada);
    }

    /**
     * Exclui uma categoria pelo ID.
     *
     * @param id ID da categoria a ser excluída (como variável de caminho).
     * @return ResponseEntity vazio com status HTTP 204 (No Content).
     * @throws Exception Se a categoria não existir ou ocorrer outro erro.
     */
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) throws Exception {
        categoriaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}