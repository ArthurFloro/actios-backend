package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.dto.CursoDTO;
import br.com.actios.actios_backend.exceptions.RecursoExistenteException;
import br.com.actios.actios_backend.exceptions.RecursoNaoEncontradoException;
import br.com.actios.actios_backend.model.Curso;
import br.com.actios.actios_backend.repositorys.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Serviço responsável pelas operações de negócio relacionadas a {@link Curso}.
 * <p>
 * Oferece operações CRUD para cursos, com conversão entre entidades e DTOs,
 * validações de negócio e tratamento de exceções específicas.
 *
 * @author Equipe Actios
 * @version 1.1
 * @since 2023-08-30
 */
@Service
@Transactional
public class CursoService {

    private final CursoRepository cursoRepository;

    /**
     * Construtor com injeção de dependência do repositório de cursos.
     *
     * @param cursoRepository Repositório de cursos (não pode ser nulo)
     */
    @Autowired
    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    /**
     * Lista todos os cursos convertidos para DTO.
     *
     * @return Lista de {@link CursoDTO} ordenados por nome
     */
    @Transactional(readOnly = true)
    public List<CursoDTO> listarTodos() {
        return cursoRepository.findAll(Sort.by(Sort.Direction.ASC, "nome")).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um curso pelo ID (retorna a entidade completa).
     *
     * @param id ID do curso (não pode ser nulo)
     * @return {@link Optional} contendo o curso, ou vazio se não encontrado
     * @throws IllegalArgumentException se o ID for nulo
     */
    @Transactional(readOnly = true)
    public Optional<Curso> buscarPorId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do curso não pode ser nulo.");
        }
        return cursoRepository.findById(id);
    }

    /**
     * Busca um curso pelo ID convertido para DTO.
     *
     * @param id ID do curso (não pode ser nulo)
     * @return {@link Optional} contendo o DTO do curso, ou vazio se não encontrado
     * @throws IllegalArgumentException se o ID for nulo
     */
    @Transactional(readOnly = true)
    public Optional<CursoDTO> buscarPorIdDTO(Integer id) {
        return buscarPorId(id).map(this::toDTO);
    }

    /**
     * Salva um novo curso no sistema.
     * <p>
     * Valida se já existe um curso com o mesmo nome antes de salvar.
     *
     * @param curso Curso a ser salvo (não pode ser nulo)
     * @return Curso salvo
     * @throws RecursoExistenteException se já existir um curso com o mesmo nome
     * @throws IllegalArgumentException se o curso for nulo
     */
    public Curso salvar(Curso curso) {
        if (curso == null) {
            throw new IllegalArgumentException("Curso não pode ser nulo.");
        }
        if (cursoRepository.existsByNome(curso.getNome())) {
            throw new RecursoExistenteException("Já existe um curso com este nome.");
        }
        return cursoRepository.save(curso);
    }

    /**
     * Atualiza um curso existente.
     *
     * @param id ID do curso a ser atualizado (não pode ser nulo)
     * @param cursoAtualizado Dados atualizados do curso (não pode ser nulo)
     * @return Curso atualizado
     * @throws RecursoNaoEncontradoException se o curso não for encontrado
     * @throws IllegalArgumentException se ID ou curso forem nulos
     */
    public Curso atualizar(Integer id, Curso cursoAtualizado) {
        if (id == null || cursoAtualizado == null) {
            throw new IllegalArgumentException("ID e curso não podem ser nulos.");
        }

        return cursoRepository.findById(id)
                .map(curso -> {
                    curso.setNome(cursoAtualizado.getNome());
                    curso.setAreaAcademica(cursoAtualizado.getAreaAcademica());
                    return cursoRepository.save(curso);
                })
                .orElseThrow(() -> new RecursoNaoEncontradoException("Curso não encontrado"));
    }

    /**
     * Remove um curso do sistema.
     *
     * @param id ID do curso a ser removido (não pode ser nulo)
     * @throws RecursoNaoEncontradoException se o curso não for encontrado
     * @throws IllegalArgumentException se o ID for nulo
     */
    public void deletar(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do curso não pode ser nulo.");
        }
        if (!cursoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Curso não encontrado para exclusão.");
        }
        cursoRepository.deleteById(id);
    }

    /**
     * Converte uma entidade Curso para CursoDTO.
     *
     * @param curso Entidade Curso a ser convertida (não pode ser nula)
     * @return DTO correspondente ao curso
     * @throws IllegalArgumentException se o curso for nulo
     */
    private CursoDTO toDTO(Curso curso) {
        if (curso == null) {
            throw new IllegalArgumentException("Curso não pode ser nulo para conversão.");
        }
        return new CursoDTO(
                curso.getId(),
                curso.getNome(),
                curso.getAreaAcademica()
        );
    }
}