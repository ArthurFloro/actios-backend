package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.exceptions.CampoObrigatorioException;
import br.com.actios.actios_backend.exceptions.RecursoNaoEncontradoException;
import br.com.actios.actios_backend.model.Faculdade;
import br.com.actios.actios_backend.repositorys.FaculdadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Serviço responsável pelas operações de negócio relacionadas a {@link Faculdade}.
 * <p>
 * Oferece operações CRUD para faculdades, incluindo validações de negócio
 * e tratamento de exceções específicas.
 *
 * @author Equipe Actios
 * @version 1.1
 * @since 2023-08-30
 */
@Service
@Transactional
public class FaculdadeService {

    private final FaculdadeRepository faculdadeRepository;

    /**
     * Construtor com injeção de dependência do repositório de faculdades.
     *
     * @param faculdadeRepository Repositório de faculdades (não pode ser nulo)
     */
    @Autowired
    public FaculdadeService(FaculdadeRepository faculdadeRepository) {
        this.faculdadeRepository = faculdadeRepository;
    }

    /**
     * Cadastra uma nova faculdade no sistema.
     *
     * @param faculdade Faculdade a ser cadastrada (não pode ser nula)
     * @return Faculdade cadastrada
     * @throws CampoObrigatorioException se o nome da faculdade não for informado
     * @throws IllegalArgumentException se a faculdade for nula
     */
    @Transactional
    public Faculdade cadastrar(Faculdade faculdade) {
        if (faculdade == null) {
            throw new IllegalArgumentException("Faculdade não pode ser nula.");
        }
        if (faculdade.getNome() == null || faculdade.getNome().isBlank()) {
            throw new CampoObrigatorioException("Nome da faculdade é obrigatório.");
        }

        return faculdadeRepository.save(faculdade);
    }

    /**
     * Busca uma faculdade pelo ID.
     *
     * @param id ID da faculdade (não pode ser nulo)
     * @return Faculdade encontrada
     * @throws RecursoNaoEncontradoException se a faculdade não for encontrada
     * @throws IllegalArgumentException se o ID for nulo
     */
    @Transactional(readOnly = true)
    public Faculdade buscarPorId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID da faculdade não pode ser nulo.");
        }
        return faculdadeRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Faculdade não encontrada com ID: " + id));
    }

    /**
     * Lista todas as faculdades cadastradas.
     *
     * @return Lista de faculdades ordenadas por nome
     */
    @Transactional(readOnly = true)
    public List<Faculdade> listarTodas() {
        return faculdadeRepository.findAllByOrderByNomeAsc();
    }

    /**
     * Atualiza os dados de uma faculdade existente.
     *
     * @param faculdade Faculdade com dados atualizados (não pode ser nula)
     * @return Faculdade atualizada
     * @throws RecursoNaoEncontradoException se a faculdade não for encontrada
     * @throws CampoObrigatorioException se o nome da faculdade não for informado
     * @throws IllegalArgumentException se a faculdade for nula
     */
    @Transactional
    public Faculdade atualizar(Faculdade faculdade) {
        if (faculdade == null) {
            throw new IllegalArgumentException("Faculdade não pode ser nula.");
        }
        if (faculdade.getIdFaculdade() == null || !faculdadeRepository.existsById(faculdade.getIdFaculdade())) {
            throw new RecursoNaoEncontradoException("Faculdade não encontrada para atualização.");
        }
        if (faculdade.getNome() == null || faculdade.getNome().isBlank()) {
            throw new CampoObrigatorioException("Nome da faculdade é obrigatório.");
        }

        return faculdadeRepository.save(faculdade);
    }

    /**
     * Exclui uma faculdade do sistema.
     *
     * @param id ID da faculdade a ser excluída (não pode ser nulo)
     * @throws RecursoNaoEncontradoException se a faculdade não for encontrada
     * @throws IllegalArgumentException se o ID for nulo
     */
    @Transactional
    public void excluir(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID da faculdade não pode ser nulo.");
        }
        if (!faculdadeRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Faculdade não encontrada para exclusão.");
        }
        faculdadeRepository.deleteById(id);
    }
}