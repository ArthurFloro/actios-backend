package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.exceptions.RecursoExistenteException;
import br.com.actios.actios_backend.exceptions.RecursoNaoEncontradoException;
import br.com.actios.actios_backend.model.Palestrante;
import br.com.actios.actios_backend.repositorys.PalestranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço responsável pelas operações relacionadas a palestrantes.
 * <p>
 * Oferece funcionalidades para cadastro, listagem, busca, atualização e exclusão de palestrantes,
 * garantindo a integridade dos dados e tratamento adequado de exceções.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Service
public class PalestranteService {

    private final PalestranteRepository palestranteRepository;

    /**
     * Construtor para injeção de dependência do repositório de palestrantes.
     *
     * @param palestranteRepository Repositório JPA para operações com palestrantes
     */
    @Autowired
    public PalestranteService(PalestranteRepository palestranteRepository) {
        this.palestranteRepository = palestranteRepository;
    }

    /**
     * Cadastra um novo palestrante no sistema.
     *
     * @param palestrante Objeto Palestrante contendo todos os dados necessários para cadastro
     * @return Palestrante cadastrado e persistido no banco de dados
     * @throws RecursoExistenteException se já existir um palestrante com o mesmo e-mail
     * @throws Exception em caso de erros inesperados durante a operação
     */
    public Palestrante cadastrar(Palestrante palestrante) throws Exception {
        if (palestranteRepository.existsByEmail(palestrante.getEmail())) {
            throw new RecursoExistenteException("E-mail já cadastrado para outro palestrante.");
        }
        return palestranteRepository.save(palestrante);
    }

    /**
     * Retorna todos os palestrantes cadastrados no sistema.
     *
     * @return Lista contendo todos os palestrantes cadastrados
     */
    public List<Palestrante> listarTodos() {
        return palestranteRepository.findAll();
    }

    /**
     * Busca um palestrante específico pelo seu ID.
     *
     * @param id ID do palestrante a ser buscado
     * @return Palestrante correspondente ao ID fornecido
     * @throws RecursoNaoEncontradoException se nenhum palestrante for encontrado com o ID informado
     */
    public Palestrante buscarPorId(Integer id) {
        return palestranteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Palestrante não encontrado."));
    }

    /**
     * Atualiza os dados de um palestrante existente.
     *
     * @param palestrante Objeto Palestrante com os dados atualizados
     * @return Palestrante atualizado e persistido no banco de dados
     * @throws RecursoNaoEncontradoException se o palestrante não for encontrado para atualização
     * @throws Exception em caso de erros inesperados durante a operação
     */
    public Palestrante atualizar(Palestrante palestrante) throws Exception {
        if (!palestranteRepository.existsById(palestrante.getIdPalestrante())) {
            throw new RecursoNaoEncontradoException("Palestrante não encontrado para atualização.");
        }
        return palestranteRepository.save(palestrante);
    }

    /**
     * Remove um palestrante do sistema.
     *
     * @param id ID do palestrante a ser excluído
     * @throws RecursoNaoEncontradoException se o palestrante não for encontrado para exclusão
     * @throws Exception em caso de erros inesperados durante a operação
     */
    public void excluir(Integer id) throws Exception {
        if (!palestranteRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Palestrante não encontrado para exclusão.");
        }
        palestranteRepository.deleteById(id);
    }
}