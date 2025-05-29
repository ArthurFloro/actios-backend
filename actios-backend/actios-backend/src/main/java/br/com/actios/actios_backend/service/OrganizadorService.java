package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.exceptions.*;
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
import java.util.regex.Pattern;

@Service
public class OrganizadorService {

    private final OrganizadorRepository organizadorRepository;
    private final FaculdadeRepository faculdadeRepository;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    @Autowired
    public OrganizadorService(OrganizadorRepository organizadorRepository,
                              FaculdadeRepository faculdadeRepository) {
        this.organizadorRepository = organizadorRepository;
        this.faculdadeRepository = faculdadeRepository;
    }

    @Transactional
    public Organizador criarOrganizador(String nome, String email, Integer idFaculdade) {
        // Validações básicas
        if (nome == null || nome.isBlank()) {
            throw new CampoObrigatorioException("Nome do organizador é obrigatório");
        }
        if (email == null || email.isBlank()) {
            throw new CampoObrigatorioException("Email do organizador é obrigatório");
        }
        if (idFaculdade == null) {
            throw new CampoObrigatorioException("ID da faculdade é obrigatório");
        }

        // Validação de formato de email
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new CredenciaisInvalidasException("Formato de email inválido");
        }

        // Verificar unicidade do email
        if (organizadorRepository.existsByEmail(email)) {
            throw new RecursoExistenteException("Email já cadastrado para outro organizador");
        }

        Faculdade faculdade = faculdadeRepository.findById(idFaculdade)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Faculdade não encontrada com ID: " + idFaculdade));

        Organizador organizador = new Organizador();
        organizador.setNome(nome.trim());
        organizador.setEmail(email.toLowerCase().trim());
        organizador.setFaculdade(faculdade);
        organizador.setDataCadastro(LocalDateTime.now());
        organizador.setAtivo(true);

        return organizadorRepository.save(organizador);
    }

    @Transactional
    public Organizador atualizarOrganizador(Integer idOrganizador, String nome, String email, Integer idFaculdade) {
        // Validações básicas
        if (idOrganizador == null) {
            throw new CampoObrigatorioException("ID do organizador é obrigatório");
        }

        Organizador organizador = organizadorRepository.findById(idOrganizador)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Organizador não encontrado com ID: " + idOrganizador));

        // Validação de email
        if (email != null) {
            if (email.isBlank()) {
                throw new CampoObrigatorioException("Email não pode ser vazio");
            }
            if (!EMAIL_PATTERN.matcher(email).matches()) {
                throw new CredenciaisInvalidasException("Formato de email inválido");
            }
            if (!email.equalsIgnoreCase(organizador.getEmail()) &&
                    organizadorRepository.existsByEmail(email)) {
                throw new RecursoExistenteException("Email já cadastrado para outro organizador");
            }
            organizador.setEmail(email.toLowerCase().trim());
        }

        // Validação de nome
        if (nome != null) {
            if (nome.isBlank()) {
                throw new CampoObrigatorioException("Nome não pode ser vazio");
            }
            organizador.setNome(nome.trim());
        }

        // Validação de faculdade
        if (idFaculdade != null) {
            Faculdade faculdade = faculdadeRepository.findById(idFaculdade)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Faculdade não encontrada com ID: " + idFaculdade));
            organizador.setFaculdade(faculdade);
        }

        organizador.setDataAtualizacao(LocalDateTime.now());
        return organizadorRepository.save(organizador);
    }

    public Optional<Organizador> buscarPorEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new CampoObrigatorioException("Email é obrigatório");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new CredenciaisInvalidasException("Formato de email inválido");
        }

        return organizadorRepository.findByEmail(email.toLowerCase().trim());
    }

    public List<Organizador> buscarPorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new CampoObrigatorioException("Nome é obrigatório para busca");
        }

        List<Organizador> organizadores = organizadorRepository.findByNomeContainingIgnoreCase(nome.trim());

        if (organizadores.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhum organizador encontrado com o nome: " + nome);
        }

        return organizadores;
    }

    public List<Organizador> listarPorFaculdade(Integer idFaculdade) {
        if (idFaculdade == null) {
            throw new CampoObrigatorioException("ID da faculdade é obrigatório");
        }

        Faculdade faculdade = faculdadeRepository.findById(idFaculdade)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Faculdade não encontrada com ID: " + idFaculdade));

        List<Organizador> organizadores = organizadorRepository.findByFaculdade(faculdade);

        if (organizadores.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhum organizador encontrado para esta faculdade");
        }

        return organizadores;
    }

    public long contarOrganizadoresPorFaculdade(Integer idFaculdade) {
        if (idFaculdade == null) {
            throw new CampoObrigatorioException("ID da faculdade é obrigatório");
        }

        Faculdade faculdade = faculdadeRepository.findById(idFaculdade)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Faculdade não encontrada com ID: " + idFaculdade));

        return organizadorRepository.countByFaculdade(faculdade);
    }

    @Transactional
    public void desativarOrganizador(Integer idOrganizador) {
        if (idOrganizador == null) {
            throw new CampoObrigatorioException("ID do organizador é obrigatório");
        }

        Organizador organizador = organizadorRepository.findById(idOrganizador)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Organizador não encontrado com ID: " + idOrganizador));

        if (!organizador.isAtivo()) {
            throw new OperacaoNaoPermitidaException("Organizador já está desativado");
        }

        organizador.setAtivo(false);
        organizador.setDataAtualizacao(LocalDateTime.now());
        organizadorRepository.save(organizador);
    }
}