package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.exceptions.CampoObrigatorioException;
import br.com.actios.actios_backend.exceptions.RecursoNaoEncontradoException;
import br.com.actios.actios_backend.model.Faculdade;
import br.com.actios.actios_backend.repositorys.FaculdadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FaculdadeService {

    private final FaculdadeRepository faculdadeRepository;

    @Autowired
    public FaculdadeService(FaculdadeRepository faculdadeRepository) {
        this.faculdadeRepository = faculdadeRepository;
    }

    public Faculdade cadastrar(Faculdade faculdade) throws Exception {
        if (faculdade.getNome() == null || faculdade.getNome().isBlank()) {
            throw new CampoObrigatorioException("Nome da faculdade é obrigatório.");
        }

        return faculdadeRepository.save(faculdade);
    }

    public Faculdade buscarPorId(Integer id) {
        return faculdadeRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Faculdade não encontrada."));
    }

    public List<Faculdade> listarTodas() {
        return faculdadeRepository.findAll();
    }

    public Faculdade atualizar(Faculdade faculdade) throws Exception {
        if (faculdade.getIdFaculdade() == null || !faculdadeRepository.existsById(faculdade.getIdFaculdade())) {
            throw new RecursoNaoEncontradoException("Faculdade não encontrada para atualização.");
        }

        return faculdadeRepository.save(faculdade);
    }

    public void excluir(Integer id) throws Exception {
        if (!faculdadeRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Faculdade não encontrada para exclusão.");
        }
        faculdadeRepository.deleteById(id);
    }
}
