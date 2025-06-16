package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.exceptions.RecursoExistenteException;
import br.com.actios.actios_backend.exceptions.RecursoNaoEncontradoException;
import br.com.actios.actios_backend.model.Categoria;
import br.com.actios.actios_backend.repositorys.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço responsável pelas operações de negócio relacionadas a {@link Categoria}.
 * <p>
 * Oferece operações para cadastro, listagem, busca, atualização e exclusão de categorias,
 * com validações de negócio e tratamento de exceções específicas.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    /**
     * Construtor com injeção de dependência do repositório de categorias.
     *
     * @param categoriaRepository Repositório de categorias (não pode ser nulo)
     */
    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    /**
     * Cadastra uma nova categoria no sistema.
     * <p>
     * Valida se já existe uma categoria com o mesmo nome antes de realizar o cadastro.
     *
     * @param categoria Categoria a ser cadastrada (não pode ser nula)
     * @return Categoria cadastrada
     * @throws RecursoExistenteException se já existir uma categoria com o mesmo nome
     * @throws IllegalArgumentException se a categoria for nula
     */
    public Categoria cadastrar(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("Categoria não pode ser nula.");
        }
        if (categoriaRepository.existsByNome(categoria.getNome())) {
            throw new RecursoExistenteException("Categoria já cadastrada com esse nome.");
        }
        return categoriaRepository.save(categoria);
    }

    /**
     * Lista todas as categorias cadastradas no sistema.
     *
     * @return Lista de categorias ordenadas alfabeticamente
     */
    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
    }

    /**
     * Busca uma categoria específica pelo seu ID.
     *
     * @param id ID da categoria a ser buscada (não pode ser nulo)
     * @return Categoria encontrada
     * @throws RecursoNaoEncontradoException se nenhuma categoria for encontrada com o ID informado
     * @throws IllegalArgumentException se o ID for nulo
     */
    public Categoria buscarPorId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID da categoria não pode ser nulo.");
        }
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada."));
    }

    /**
     * Atualiza os dados de uma categoria existente.
     * <p>
     * Verifica a existência da categoria antes de realizar a atualização.
     *
     * @param categoria Categoria com os dados atualizados (não pode ser nula)
     * @return Categoria atualizada
     * @throws RecursoNaoEncontradoException se a categoria não existir
     * @throws IllegalArgumentException se a categoria for nula
     */
    public Categoria atualizar(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("Categoria não pode ser nula.");
        }
        if (!categoriaRepository.existsById(categoria.getIdCategoria())) {
            throw new RecursoNaoEncontradoException("Categoria não encontrada para atualização.");
        }
        return categoriaRepository.save(categoria);
    }

    /**
     * Exclui uma categoria do sistema.
     * <p>
     * Verifica a existência da categoria antes de realizar a exclusão.
     *
     * @param id ID da categoria a ser excluída (não pode ser nulo)
     * @throws RecursoNaoEncontradoException se a categoria não existir
     * @throws IllegalArgumentException se o ID for nulo
     */
    public void excluir(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID da categoria não pode ser nulo.");
        }
        if (!categoriaRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Categoria não encontrada para exclusão.");
        }
        categoriaRepository.deleteById(id);
    }
}