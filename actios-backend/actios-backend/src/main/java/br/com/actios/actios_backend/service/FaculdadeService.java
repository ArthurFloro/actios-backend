package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.exceptions.CampoObrigatorioException;
import br.com.actios.actios_backend.exceptions.RecursoExistenteException;
import br.com.actios.actios_backend.exceptions.RecursoNaoEncontradoException;
import br.com.actios.actios_backend.exceptions.OperacaoNaoPermitidaException;
import br.com.actios.actios_backend.model.Faculdade;
import br.com.actios.actios_backend.repositorys.FaculdadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FaculdadeService {

    private final FaculdadeRepository faculdadeRepository;

    @Autowired
    public FaculdadeService(FaculdadeRepository faculdadeRepository) {
        this.faculdadeRepository = faculdadeRepository;
    }

    public Faculdade cadastrar(Faculdade faculdade) {
        validarFaculdadeParaCadastro(faculdade);

        if (faculdadeRepository.existsByNome(faculdade.getNome())) {
            throw new RecursoExistenteException("Já existe uma faculdade com este nome.");
        }

        return faculdadeRepository.save(faculdade);
    }

    public Faculdade buscarPorId(Integer id) {
        if (id == null) {
            throw new CampoObrigatorioException("ID da faculdade é obrigatório.");
        }

        return faculdadeRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Faculdade não encontrada com o ID: " + id));
    }

    public List<Faculdade> listarTodas() {
        List<Faculdade> faculdades = faculdadeRepository.findAll();

        if (faculdades.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhuma faculdade cadastrada.");
        }

        return faculdades;
    }

    public Faculdade atualizar(Faculdade faculdade) {
        validarFaculdadeParaAtualizacao(faculdade);

        if (!faculdadeRepository.existsById(faculdade.getIdFaculdade())) {
            throw new RecursoNaoEncontradoException("Faculdade não encontrada para atualização.");
        }

        Optional<Faculdade> faculdadeComMesmoNome = faculdadeRepository
                .findByNomeAndIdFaculdadeNot(faculdade.getNome(), faculdade.getIdFaculdade());

        if (faculdadeComMesmoNome.isPresent()) {
            throw new RecursoExistenteException("Já existe outra faculdade com este nome.");
        }

        return faculdadeRepository.save(faculdade);
    }

    public void excluir(Integer id) {
        if (id == null) {
            throw new CampoObrigatorioException("ID da faculdade é obrigatório.");
        }

        Faculdade faculdade = faculdadeRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Faculdade não encontrada para exclusão."));

        // Verificação se a faculdade tem cursos associados
        if (faculdade.getCursos() != null && !faculdade.getCursos().isEmpty()) {
            throw new OperacaoNaoPermitidaException("Não é possível excluir a faculdade pois ela possui cursos vinculados.");
        }

        faculdadeRepository.deleteById(id);
    }

    private void validarFaculdadeParaCadastro(Faculdade faculdade) {
        if (faculdade == null) {
            throw new CampoObrigatorioException("Dados da faculdade são obrigatórios.");
        }

        if (faculdade.getNome() == null || faculdade.getNome().isBlank()) {
            throw new CampoObrigatorioException("Nome da faculdade é obrigatório.");
        }

        // Outras validações opcionais
    }

    private void validarFaculdadeParaAtualizacao(Faculdade faculdade) {
        validarFaculdadeParaCadastro(faculdade);

        if (faculdade.getIdFaculdade() == null) {
            throw new CampoObrigatorioException("ID da faculdade é obrigatório para atualização.");
        }
    }
}
