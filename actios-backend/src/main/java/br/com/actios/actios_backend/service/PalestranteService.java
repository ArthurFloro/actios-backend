package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.exceptions.RecursoExistenteException;
import br.com.actios.actios_backend.exceptions.RecursoNaoEncontradoException;
import br.com.actios.actios_backend.model.Palestrante;
import br.com.actios.actios_backend.repositorys.PalestranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PalestranteService {

    private final PalestranteRepository palestranteRepository;

    @Autowired
    public PalestranteService(PalestranteRepository palestranteRepository) {
        this.palestranteRepository = palestranteRepository;
    }

    public Palestrante cadastrar(Palestrante palestrante) throws Exception {
        if (palestranteRepository.existsByEmail(palestrante.getEmail())) {
            throw new RecursoExistenteException("E-mail já cadastrado para outro palestrante.");
        }
        return palestranteRepository.save(palestrante);
    }

    public List<Palestrante> listarTodos() {
        return palestranteRepository.findAll();
    }

    public Palestrante buscarPorId(Integer id) {
        return palestranteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Palestrante não encontrado."));
    }


    public Palestrante atualizar(Palestrante palestrante) throws Exception {
        if (!palestranteRepository.existsById(palestrante.getIdPalestrante())) {
            throw new RecursoNaoEncontradoException("Palestrante não encontrado para atualização.");
        }
        return palestranteRepository.save(palestrante);
    }

    public void excluir(Integer id) throws Exception {
        if (!palestranteRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Palestrante não encontrado para exclusão.");
        }
        palestranteRepository.deleteById(id);
    }
}
