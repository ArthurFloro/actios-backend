package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.dto.CursoDTO;
import br.com.actios.actios_backend.exceptions.RecursoNaoEncontradoException;
import br.com.actios.actios_backend.model.Curso;
import br.com.actios.actios_backend.repositorys.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    // Retorna todos os cursos como DTO
    public List<CursoDTO> listarTodos() {
        return cursoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Retorna a entidade (uso interno ou para salvar/atualizar)
    public Optional<Curso> buscarPorId(Integer id) {
        return cursoRepository.findById(id);
    }

    // Retorna um curso específico como DTO (opcional)
    public Optional<CursoDTO> buscarPorIdDTO(Integer id) {
        return cursoRepository.findById(id)
                .map(this::toDTO);
    }

    public Curso salvar(Curso curso) {
        return cursoRepository.save(curso);
    }

    public Curso atualizar(Integer id, Curso cursoAtualizado) {
        return cursoRepository.findById(id).map(curso -> {
            curso.setNome(cursoAtualizado.getNome());
            curso.setAreaAcademica(cursoAtualizado.getAreaAcademica());
            return cursoRepository.save(curso);
        }).orElseThrow(() -> new RecursoNaoEncontradoException("Curso não encontrado"));
    }

    public void deletar(Integer id) {
        cursoRepository.deleteById(id);
    }

    // Conversor de Curso -> CursoDTO
    private CursoDTO toDTO(Curso curso) {
        return new CursoDTO(
                curso.getId(),
                curso.getNome(),
                curso.getAreaAcademica()
        );
    }
}