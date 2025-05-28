package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.dto.CursoDTO;
import br.com.actios.actios_backend.exceptions.*;
import br.com.actios.actios_backend.model.Curso;
import br.com.actios.actios_backend.repositorys.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public List<CursoDTO> listarTodos() {
        List<Curso> cursos = cursoRepository.findAll();

        if (cursos.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhum curso encontrado");
        }

        return cursos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CursoDTO buscarPorId(Integer id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Curso não encontrado com ID: " + id));
        return toDTO(curso);
    }

    public CursoDTO salvar(Curso curso) {
        validarCurso(curso);

        if (cursoRepository.existsByNome(curso.getNome())) {
            throw new RecursoExistenteException("Já existe um curso com este nome");
        }

        try {
            Curso cursoSalvo = cursoRepository.save(curso);
            return toDTO(cursoSalvo);
        } catch (Exception e) {
            throw new OperacaoNaoPermitidaException("Falha ao salvar curso: " + e.getMessage());
        }
    }

    public CursoDTO atualizar(Integer id, Curso cursoAtualizado) {
        validarCurso(cursoAtualizado);

        return cursoRepository.findById(id)
                .map(curso -> {
                    if (!curso.getNome().equals(cursoAtualizado.getNome())) {
                        if (cursoRepository.existsByNome(cursoAtualizado.getNome())) {
                            throw new RecursoExistenteException("Já existe um curso com este nome");
                        }
                    }
                    curso.setNome(cursoAtualizado.getNome());
                    curso.setAreaAcademica(cursoAtualizado.getAreaAcademica());
                    return toDTO(cursoRepository.save(curso));
                })
                .orElseThrow(() -> new RecursoNaoEncontradoException("Curso não encontrado com ID: " + id));
    }

    public void deletar(Integer id) {
        if (!cursoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Curso não encontrado com ID: " + id);
        }

        try {
            cursoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new OperacaoNaoPermitidaException(
                    "Não é possível excluir o curso pois está associado a outras entidades");
        }
    }

    private void validarCurso(Curso curso) {
        if (curso.getNome() == null || curso.getNome().trim().isEmpty()) {
            throw new CampoObrigatorioException("Nome do curso é obrigatório");
        }
        if (curso.getAreaAcademica() == null || curso.getAreaAcademica().trim().isEmpty()) {
            throw new CampoObrigatorioException("Área acadêmica é obrigatória");
        }
    }

    private CursoDTO toDTO(Curso curso) {
        return new CursoDTO(
                curso.getId(),
                curso.getNome(),
                curso.getAreaAcademica()
        );
    }
}