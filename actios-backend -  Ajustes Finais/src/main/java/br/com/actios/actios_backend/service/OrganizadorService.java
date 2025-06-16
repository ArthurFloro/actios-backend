package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.exceptions.RecursoExistenteException;
import br.com.actios.actios_backend.exceptions.RecursoNaoEncontradoException;
import br.com.actios.actios_backend.model.Organizador;
import br.com.actios.actios_backend.model.Faculdade;
import br.com.actios.actios_backend.repositorys.OrganizadorRepository;
import br.com.actios.actios_backend.repositorys.FaculdadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pelas operações relacionadas a organizadores.
 * <p>
 * Gerencia a criação, atualização e consulta de organizadores associados a faculdades.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Service
@Transactional
public class OrganizadorService {

    @Autowired
    private OrganizadorRepository organizadorRepository;

    @Autowired
    private FaculdadeRepository faculdadeRepository;

    /**
     * Cria um novo organizador no sistema.
     *
     * @param nome Nome completo do organizador (não pode ser nulo ou vazio)
     * @param email Email do organizador (deve ser único no sistema)
     * @param idFaculdade ID da faculdade à qual o organizador está vinculado
     * @return Organizador criado e persistido
     * @throws RecursoExistenteException se o email já estiver cadastrado
     * @throws RecursoNaoEncontradoException se a faculdade não for encontrada
     * @throws IllegalArgumentException se algum parâmetro obrigatório for inválido
     */
    @Transactional
    public Organizador criarOrganizador(String nome, String email, Integer idFaculdade) {
        if (organizadorRepository.existsByEmail(email)) {
            throw new RecursoExistenteException("Email já cadastrado para outro organizador");
        }

        Faculdade faculdade = faculdadeRepository.findById(idFaculdade)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Faculdade não encontrada"));

        Organizador organizador = new Organizador();
        organizador.setNome(nome);
        organizador.setEmail(email);
        organizador.setFaculdade(faculdade);

        return organizadorRepository.save(organizador);
    }

    /**
     * Atualiza os dados de um organizador existente.
     *
     * @param idOrganizador ID do organizador a ser atualizado
     * @param nome Novo nome do organizador (opcional)
     * @param email Novo email do organizador (deve ser único, opcional)
     * @param idFaculdade Nova faculdade do organizador (opcional)
     * @return Organizador atualizado
     * @throws RecursoNaoEncontradoException se organizador ou faculdade não forem encontrados
     * @throws RecursoExistenteException se o novo email já estiver em uso
     */
    @Transactional
    public Organizador atualizarOrganizador(Integer idOrganizador, String nome, String email, Integer idFaculdade) {
        Organizador organizador = organizadorRepository.findById(idOrganizador)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Organizador não encontrado"));

        if (email != null && !email.equals(organizador.getEmail()) &&
                organizadorRepository.existsByEmail(email)) {
            throw new RecursoExistenteException("Email já cadastrado para outro organizador");
        }

        if (nome != null) {
            organizador.setNome(nome);
        }

        if (email != null) {
            organizador.setEmail(email);
        }

        if (idFaculdade != null) {
            Faculdade faculdade = faculdadeRepository.findById(idFaculdade)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Faculdade não encontrada"));
            organizador.setFaculdade(faculdade);
        }

        return organizadorRepository.save(organizador);
    }

    /**
     * Busca um organizador pelo seu email.
     *
     * @param email Email do organizador a ser buscado
     * @return Optional contendo o organizador, se encontrado
     */
    public Optional<Organizador> buscarPorEmail(String email) {
        return organizadorRepository.findByEmail(email);
    }

    /**
     * Busca organizadores cujo nome contenha o termo informado (case insensitive).
     *
     * @param nome Termo de busca para o nome do organizador
     * @return Lista de organizadores encontrados (pode ser vazia)
     */
    public List<Organizador> buscarPorNome(String nome) {
        return organizadorRepository.findByNomeContainingIgnoreCase(nome);
    }

    /**
     * Lista todos os organizadores de uma faculdade específica.
     *
     * @param idFaculdade ID da faculdade
     * @return Lista de organizadores vinculados à faculdade
     * @throws RecursoNaoEncontradoException se a faculdade não for encontrada
     */
    public List<Organizador> listarPorFaculdade(Integer idFaculdade) {
        Faculdade faculdade = faculdadeRepository.findById(idFaculdade)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Faculdade não encontrada"));

        return organizadorRepository.findByFaculdade(faculdade);
    }

    /**
     * Conta quantos organizadores estão vinculados a uma faculdade.
     *
     * @param idFaculdade ID da faculdade
     * @return Quantidade de organizadores vinculados
     * @throws RecursoNaoEncontradoException se a faculdade não for encontrada
     */
    public long contarOrganizadoresPorFaculdade(Integer idFaculdade) {
        Faculdade faculdade = faculdadeRepository.findById(idFaculdade)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Faculdade não encontrada"));

        return organizadorRepository.countByFaculdade(faculdade);
    }
}