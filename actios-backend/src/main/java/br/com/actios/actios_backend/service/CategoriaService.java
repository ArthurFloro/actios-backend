package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.exceptions.RecursoExistenteException;
import br.com.actios.actios_backend.exceptions.RecursoNaoEncontradoException;
import br.com.actios.actios_backend.model.Categoria;
import br.com.actios.actios_backend.repositorys.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria cadastrar(Categoria categoria) {
        if (categoriaRepository.existsByNome(categoria.getNome())) {
            throw new RecursoExistenteException("Categoria já cadastrada com esse nome.");
        }
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(Integer id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada."));
    }

    public Categoria atualizar(Categoria categoria) {
        if (!categoriaRepository.existsById(categoria.getIdCategoria())) {
            throw new RecursoNaoEncontradoException("Categoria não encontrada para atualização.");
        }
        return categoriaRepository.save(categoria);
    }

    public void excluir(Integer id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Categoria não encontrada para exclusão.");
        }
        categoriaRepository.deleteById(id);
    }
}