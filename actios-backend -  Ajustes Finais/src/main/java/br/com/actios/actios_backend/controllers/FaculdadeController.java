package br.com.actios.actios_backend.controllers;

import br.com.actios.actios_backend.model.Faculdade;
import br.com.actios.actios_backend.service.FaculdadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para operações relacionadas a faculdades.
 * <p>
 * Mapeado para a rota base "/api/faculdades" e fornece endpoints para CRUD completo
 * de faculdades, incluindo cadastro, listagem, busca, atualização e exclusão.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@RestController
@RequestMapping("/api/faculdades")
public class FaculdadeController {

    private final FaculdadeService faculdadeService;

    /**
     * Construtor com injeção de dependência do serviço de faculdades.
     *
     * @param faculdadeService Serviço contendo a lógica de negócios para faculdades
     */
    @Autowired
    public FaculdadeController(FaculdadeService faculdadeService) {
        this.faculdadeService = faculdadeService;
    }

    /**
     * Cadastra uma nova faculdade no sistema.
     *
     * @param faculdade Objeto Faculdade contendo os dados para cadastro
     * @return Faculdade cadastrada com ID gerado
     * @throws Exception Se ocorrer erro durante o cadastro
     */
    @PostMapping("/cadastrar")
    public Faculdade criarFaculdade(@RequestBody Faculdade faculdade) throws Exception {
        return faculdadeService.cadastrar(faculdade);
    }

    /**
     * Lista todas as faculdades cadastradas.
     *
     * @return Lista de Faculdade contendo todas as instituições cadastradas
     */
    @GetMapping("/listar")
    public List<Faculdade> listarFaculdades() {
        return faculdadeService.listarTodas();
    }

    /**
     * Busca uma faculdade específica pelo ID.
     *
     * @param id ID da faculdade a ser buscada
     * @return Faculdade correspondente ao ID fornecido
     * @throws Exception Se a faculdade não for encontrada
     */
    @GetMapping("/{id}")
    public Faculdade buscarPorId(@PathVariable Integer id) throws Exception {
        return faculdadeService.buscarPorId(id);
    }

    /**
     * Atualiza os dados de uma faculdade existente.
     *
     * @param faculdade Objeto Faculdade com os dados atualizados
     * @return Faculdade com os dados atualizados
     * @throws Exception Se a faculdade não existir ou ocorrer erro na atualização
     */
    @PutMapping("/atualizar")
    public Faculdade atualizarFaculdade(@RequestBody Faculdade faculdade) throws Exception {
        return faculdadeService.atualizar(faculdade);
    }

    /**
     * Exclui uma faculdade pelo ID.
     *
     * @param id ID da faculdade a ser excluída
     * @throws Exception Se a faculdade não existir ou ocorrer erro na exclusão
     */
    @DeleteMapping("/excluir/{id}")
    public void excluirFaculdade(@PathVariable Integer id) throws Exception {
        faculdadeService.excluir(id);
    }
}