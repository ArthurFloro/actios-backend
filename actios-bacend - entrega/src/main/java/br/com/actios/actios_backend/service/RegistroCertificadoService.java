package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.dto.RegistroCertificadoDTO;
import br.com.actios.actios_backend.exceptions.RecursoExistenteException;
import br.com.actios.actios_backend.exceptions.RecursoNaoEncontradoException;
import br.com.actios.actios_backend.model.RegistroCertificado;
import br.com.actios.actios_backend.model.Usuario;
import br.com.actios.actios_backend.model.Curso;
import br.com.actios.actios_backend.repositorys.RegistroCertificadoRepository;
import br.com.actios.actios_backend.repositorys.UsuarioRepository;
import br.com.actios.actios_backend.repositorys.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RegistroCertificadoService {

    @Autowired
    private RegistroCertificadoRepository registroCertificadoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Transactional
    public RegistroCertificadoDTO criarRegistroCertificado(Integer idUsuario, Integer idCurso) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário com ID " + idUsuario + " não encontrado"));

        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Curso com ID " + idCurso + " não encontrado"));

        if (!registroCertificadoRepository.findByUsuarioAndCurso(usuario, curso).isEmpty()) {
            throw new RecursoExistenteException("Certificado já existe para este usuário e curso");
        }

        RegistroCertificado registro = new RegistroCertificado();
        registro.setUsuario(usuario);
        registro.setCurso(curso);
        registro.setCodigoValidacao(UUID.randomUUID().toString());
        registro.setDataEmissao(LocalDate.now());

        RegistroCertificado salvo = registroCertificadoRepository.save(registro);
        return toDTO(salvo);
    }

    public RegistroCertificadoDTO validarCertificado(String codigoValidacao) {
        RegistroCertificado certificado = registroCertificadoRepository.findByCodigoValidacao(codigoValidacao);
        if (certificado == null) {
            throw new RecursoNaoEncontradoException("Certificado com código '" + codigoValidacao + "' não encontrado ou inválido");
        }
        return toDTO(certificado);
    }

    public List<RegistroCertificadoDTO> listarPorUsuario(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário com ID " + idUsuario + " não encontrado"));

        return registroCertificadoRepository.findByUsuario(usuario)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<RegistroCertificadoDTO> listarPorCurso(Integer idCurso) {
        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Curso com ID " + idCurso + " não encontrado"));

        return registroCertificadoRepository.findByCurso(curso)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public long contarPorUsuario(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário com ID " + idUsuario + " não encontrado"));

        return registroCertificadoRepository.countByUsuario(usuario);
    }

    public long contarPorCurso(Integer idCurso) {
        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Curso com ID " + idCurso + " não encontrado"));

        return registroCertificadoRepository.countByCurso(curso);
    }

    public List<RegistroCertificadoDTO> listarPorUsuarioECurso(Integer idUsuario, Integer idCurso) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário com ID " + idUsuario + " não encontrado"));

        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Curso com ID " + idCurso + " não encontrado"));

        return registroCertificadoRepository.findByUsuarioAndCurso(usuario, curso)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    private RegistroCertificadoDTO toDTO(RegistroCertificado registro) {
        RegistroCertificadoDTO dto = new RegistroCertificadoDTO();
        dto.setIdCertificado(registro.getIdCertificado());
        dto.setNomeUsuario(registro.getUsuario().getNome());
        dto.setEmailUsuario(registro.getUsuario().getEmail());
        dto.setNomeCurso(registro.getCurso().getNome());
        dto.setCodigoValidacao(registro.getCodigoValidacao());
        dto.setDataEmissao(registro.getDataEmissao());
        return dto;
    }
}
