package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.exceptions.*;
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

@Service
public class VinculoCursoUsuarioService {

    @Autowired
    private VinculoCursoUsuarioRepository vinculoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Transactional
    public VinculoCursoUsuario vincular(Integer idUsuario, Integer idCurso) {
        if (idUsuario == null) {
            throw new CampoObrigatorioException("ID do usuário é obrigatório");
        }

        if (idCurso == null) {
            throw new CampoObrigatorioException("ID do curso é obrigatório");
        }

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com ID: " + idUsuario));

        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Curso não encontrado com ID: " + idCurso));

        VinculoCursoUsuarioId id = new VinculoCursoUsuarioId(idUsuario, idCurso);

        if (vinculoRepository.existsById(id)) {
            throw new RecursoExistenteException(String.format(
                    "Vínculo já existe entre usuário %s e curso %s", idUsuario, idCurso));
        }

        try {
            VinculoCursoUsuario vinculo = new VinculoCursoUsuario(usuario, curso);
            return vinculoRepository.save(vinculo);
        } catch (Exception e) {
            throw new OperacaoNaoPermitidaException("Falha ao vincular usuário ao curso", e);
        }
    }

    @Transactional
    public void desvincular(Integer idUsuario, Integer idCurso) {
        if (idUsuario == null) {
            throw new CampoObrigatorioException("ID do usuário é obrigatório");
        }

        if (idCurso == null) {
            throw new CampoObrigatorioException("ID do curso é obrigatório");
        }

        VinculoCursoUsuarioId id = new VinculoCursoUsuarioId(idUsuario, idCurso);

        if (!vinculoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException(String.format(
                    "Vínculo não encontrado entre usuário %s e curso %s", idUsuario, idCurso));
        }

        try {
            vinculoRepository.deleteById(id);
        } catch (Exception e) {
            throw new OperacaoNaoPermitidaException("Falha ao desvincular usuário do curso", e);
        }
    }

    public List<Curso> getCursosDoUsuario(Integer idUsuario) {
        if (idUsuario == null) {
            throw new CampoObrigatorioException("ID do usuário é obrigatório");
        }

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com ID: " + idUsuario));

        try {
            List<VinculoCursoUsuario> vinculos = vinculoRepository.findByUsuario(usuario);
            return vinculos.stream()
                    .map(VinculoCursoUsuario::getCurso)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new OperacaoNaoPermitidaException("Falha ao recuperar cursos do usuário", e);
        }
    }


    public List<Usuario> getUsuariosDoCurso(Integer idCurso) {
        if (idCurso == null) {
            throw new CampoObrigatorioException("ID do curso é obrigatório");
        }

        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Curso não encontrado com ID: " + idCurso));

        try {
            List<VinculoCursoUsuario> vinculos = vinculoRepository.findByCurso(curso);
            return vinculos.stream()
                    .map(VinculoCursoUsuario::getUsuario)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new OperacaoNaoPermitidaException("Falha ao recuperar usuários do curso", e);
        }
    }

}