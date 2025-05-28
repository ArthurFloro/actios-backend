package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.exceptions.*;
import br.com.actios.actios_backend.model.Palestrante;
import br.com.actios.actios_backend.repositorys.PalestranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

import br.com.actios.actios_backend.exceptions.OperacaoNaoPermitidaException;

@Service
public class PalestranteService {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private final PalestranteRepository palestranteRepository;

    @Autowired
    public PalestranteService(PalestranteRepository palestranteRepository) {
        this.palestranteRepository = palestranteRepository;
    }

    public Palestrante cadastrar(Palestrante palestrante) {
        validarPalestrante(palestrante);
        validarEmail(palestrante.getEmail());

        if (palestranteRepository.existsByEmail(palestrante.getEmail())) {
            throw new RecursoExistenteException("E-mail já cadastrado para outro palestrante.");
        }

        try {
            return palestranteRepository.save(palestrante);
        } catch (DataAccessException e) {
            throw new OperacaoNaoPermitidaException("Falha ao acessar o banco de dados para cadastrar palestrante", e);
        } catch (Exception e) {
            throw new OperacaoNaoPermitidaException("Erro inesperado ao cadastrar palestrante", e);
        }
    }

    public List<Palestrante> listarTodos() {
        try {
            List<Palestrante> palestrantes = palestranteRepository.findAll();
            if (palestrantes.isEmpty()) {
                throw new RecursoNaoEncontradoException("Nenhum palestrante encontrado.");
            }
            return palestrantes;
        } catch (DataAccessException e) {
            throw new OperacaoNaoPermitidaException("Falha ao acessar o banco de dados para listar palestrantes", e);
        } catch (Exception e) {
            throw new OperacaoNaoPermitidaException("Erro inesperado ao listar palestrantes", e);
        }
    }

    public Palestrante buscarPorId(Integer id) {
        if (id == null || id <= 0) {
            throw new CampoObrigatorioException("ID do palestrante é inválido.");
        }

        try {
            return palestranteRepository.findById(id)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Palestrante com ID " + id + " não encontrado."));
        } catch (DataAccessException e) {
            throw new OperacaoNaoPermitidaException("Falha ao acessar o banco de dados para buscar palestrante", e);
        }
    }

    public Palestrante atualizar(Palestrante palestrante) {
        validarPalestrante(palestrante);

        if (palestrante.getIdPalestrante() == null) {
            throw new CampoObrigatorioException("ID do palestrante é obrigatório para atualização.");
        }

        try {
            Palestrante existente = buscarPorId(palestrante.getIdPalestrante());

            if (!existente.getEmail().equals(palestrante.getEmail())) {
                validarEmail(palestrante.getEmail());
                if (palestranteRepository.existsByEmail(palestrante.getEmail())) {
                    throw new RecursoExistenteException("Novo e-mail já está cadastrado para outro palestrante.");
                }
            }

            return palestranteRepository.save(palestrante);
        } catch (DataAccessException e) {
            throw new OperacaoNaoPermitidaException("Falha ao acessar o banco de dados para atualizar palestrante", e);
        }
    }

    public void excluir(Integer id) {
        if (id == null || id <= 0) {
            throw new CampoObrigatorioException("ID do palestrante é inválido.");
        }

        try {
            if (!palestranteRepository.existsById(id)) {
                throw new RecursoNaoEncontradoException("Palestrante com ID " + id + " não encontrado para exclusão.");
            }
            palestranteRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new OperacaoNaoPermitidaException("Falha ao acessar o banco de dados para excluir palestrante", e);
        }
    }

    private void validarPalestrante(Palestrante palestrante) {
        if (palestrante == null) {
            throw new CampoObrigatorioException("Palestrante não pode ser nulo.");
        }

        if (palestrante.getNome() == null || palestrante.getNome().trim().isEmpty()) {
            throw new CampoObrigatorioException("Nome do palestrante é obrigatório.");
        }

        if (palestrante.getEmail() == null || palestrante.getEmail().trim().isEmpty()) {
            throw new CampoObrigatorioException("E-mail do palestrante é obrigatório.");
        }

        // Adicione outras validações específicas do seu domínio aqui
    }

    private void validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new CampoObrigatorioException("E-mail do palestrante é obrigatório.");
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new CredenciaisInvalidasException("Formato de e-mail inválido.");
        }
    }
}