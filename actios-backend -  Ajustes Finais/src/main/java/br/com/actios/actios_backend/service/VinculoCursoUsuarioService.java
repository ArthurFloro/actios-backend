package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.exceptions.RecursoExistenteException;
import br.com.actios.actios_backend.exceptions.RecursoNaoEncontradoException;
import br.com.actios.actios_backend.model.VinculoCursoUsuario;
import br.com.actios.actios_backend.model.VinculoCursoUsuarioId;
import br.com.actios.actios_backend.model.Usuario;
import br.com.actios.actios_backend.model.Curso;
import br.com.actios.actios_backend.repositorys.VinculoCursoUsuarioRepository;
import br.com.actios.actios_backend.repositorys.UsuarioRepository;
import br.com.actios.actios_backend.repositorys.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;

/**
 * Serviço para gerenciamento de vínculos entre usuários e cursos.
 *
 * <p>Esta classe fornece operações para vincular, desvincular e consultar relações
 * entre usuários e cursos no sistema.</p>
 */
@Service
public class VinculoCursoUsuarioService {

    @Autowired
    private VinculoCursoUsuarioRepository vinculoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    /**
     * Cria um vínculo entre um usuário e um curso.
     *
     * @param idUsuario ID do usuário a ser vinculado
     * @param idCurso ID do curso a ser vinculado
     * @return O vínculo criado
     * @throws RecursoNaoEncontradoException Se o usuário ou curso não forem encontrados
     * @throws RecursoExistenteException Se o vínculo já existir
     */
    @Transactional
    public VinculoCursoUsuario vincular(Integer idUsuario, Integer idCurso) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Curso não encontrado"));

        VinculoCursoUsuarioId id = new VinculoCursoUsuarioId(idUsuario, idCurso);

        if (vinculoRepository.existsById(id)) {
            throw new RecursoExistenteException("Vínculo já existe");
        }

        VinculoCursoUsuario vinculo = new VinculoCursoUsuario(usuario, curso);
        return vinculoRepository.save(vinculo);
    }

    /**
     * Remove um vínculo existente entre um usuário e um curso.
     *
     * @param idUsuario ID do usuário vinculado
     * @param idCurso ID do curso vinculado
     * @throws RecursoNaoEncontradoException Se o vínculo não for encontrado
     */
    @Transactional
    public void desvincular(Integer idUsuario, Integer idCurso) {
        VinculoCursoUsuarioId id = new VinculoCursoUsuarioId(idUsuario, idCurso);

        if (!vinculoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Vínculo não encontrado");
        }

        vinculoRepository.deleteById(id);
    }

    /**
     * Obtém todos os cursos vinculados a um usuário específico.
     *
     * @param idUsuario ID do usuário
     * @return Lista de cursos vinculados ao usuário
     * @throws RecursoNaoEncontradoException Se o usuário não for encontrado
     */
    public List<Curso> getCursosDoUsuario(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        List<VinculoCursoUsuario> vinculos = vinculoRepository.findByUsuario(usuario);
        return vinculos.stream()
                .map(vinculo -> vinculo.getCurso())
                .collect(Collectors.toList());
    }

    /**
     * Obtém todos os usuários vinculados a um curso específico.
     *
     * @param idCurso ID do curso
     * @return Lista de usuários vinculados ao curso
     * @throws RecursoNaoEncontradoException Se o curso não for encontrado
     */
    public List<Usuario> getUsuariosDoCurso(Integer idCurso) {
        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Curso não encontrado"));

        List<VinculoCursoUsuario> vinculos = vinculoRepository.findByCurso(curso);
        return vinculos.stream()
                .map(vinculo -> vinculo.getUsuario())
                .collect(Collectors.toList());
    }
}