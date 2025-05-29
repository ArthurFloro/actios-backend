package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.dto.RegistroCertificadoDTO;
import br.com.actios.actios_backend.exceptions.*;
import br.com.actios.actios_backend.model.RegistroCertificado;
import br.com.actios.actios_backend.model.Usuario;
import br.com.actios.actios_backend.model.Curso;
import br.com.actios.actios_backend.repositorys.RegistroCertificadoRepository;
import br.com.actios.actios_backend.repositorys.UsuarioRepository;
import br.com.actios.actios_backend.repositorys.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RegistroCertificadoService {

    private final RegistroCertificadoRepository registroCertificadoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;

    @Autowired
    public RegistroCertificadoService(RegistroCertificadoRepository registroCertificadoRepository,
                                      UsuarioRepository usuarioRepository,
                                      CursoRepository cursoRepository) {
        this.registroCertificadoRepository = registroCertificadoRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
    }

    @Transactional
    public RegistroCertificadoDTO criarRegistroCertificado(Integer idUsuario, Integer idCurso) {
        validarIds(idUsuario, idCurso);

        try {
            Usuario usuario = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário com ID " + idUsuario + " não encontrado"));

            Curso curso = cursoRepository.findById(idCurso)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Curso com ID " + idCurso + " não encontrado"));

            validarElegibilidadeCertificado(usuario, curso);

            if (!registroCertificadoRepository.findByUsuarioAndCurso(usuario, curso).isEmpty()) {
                throw new RecursoExistenteException("Certificado já existe para este usuário e curso");
            }

            RegistroCertificado registro = new RegistroCertificado();
            registro.setUsuario(usuario);
            registro.setCurso(curso);
            registro.setCodigoValidacao(gerarCodigoUnico());
            registro.setDataEmissao(LocalDate.now());

            RegistroCertificado salvo = registroCertificadoRepository.save(registro);
            return toDTO(salvo);
        } catch (DataAccessException e) {
            throw new OperacaoNaoPermitidaException("Falha ao criar registro de certificado", e);
        }
    }

    public RegistroCertificadoDTO validarCertificado(String codigoValidacao) {
        if (codigoValidacao == null || codigoValidacao.trim().isEmpty()) {
            throw new CampoObrigatorioException("Código de validação é obrigatório");
        }

        try {
            RegistroCertificado certificado = registroCertificadoRepository.findByCodigoValidacao(codigoValidacao);
            if (certificado == null) {
                throw new RecursoNaoEncontradoException("Certificado com código '" + codigoValidacao + "' não encontrado");
            }

            validarDataCertificado(certificado);
            return toDTO(certificado);
        } catch (DataAccessException e) {
            throw new OperacaoNaoPermitidaException("Falha ao validar certificado", e);
        }
    }

    public List<RegistroCertificadoDTO> listarPorUsuario(Integer idUsuario) {
        if (idUsuario == null || idUsuario <= 0) {
            throw new CampoObrigatorioException("ID do usuário é inválido");
        }

        try {
            Usuario usuario = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário com ID " + idUsuario + " não encontrado"));

            List<RegistroCertificado> registros = registroCertificadoRepository.findByUsuario(usuario);

            if (registros.isEmpty()) {
                throw new RecursoNaoEncontradoException("Nenhum certificado encontrado para este usuário");
            }

            return registros.stream().map(this::toDTO).collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new OperacaoNaoPermitidaException("Falha ao listar certificados por usuário", e);
        }
    }

    public List<RegistroCertificadoDTO> listarPorCurso(Integer idCurso) {
        if (idCurso == null || idCurso <= 0) {
            throw new CampoObrigatorioException("ID do curso é inválido");
        }

        try {
            Curso curso = cursoRepository.findById(idCurso)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Curso com ID " + idCurso + " não encontrado"));

            List<RegistroCertificado> registros = registroCertificadoRepository.findByCurso(curso);

            if (registros.isEmpty()) {
                throw new RecursoNaoEncontradoException("Nenhum certificado encontrado para este curso");
            }

            return registros.stream().map(this::toDTO).collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new OperacaoNaoPermitidaException("Falha ao listar certificados por curso", e);
        }
    }

    public long contarPorUsuario(Integer idUsuario) {
        if (idUsuario == null || idUsuario <= 0) {
            throw new CampoObrigatorioException("ID do usuário é inválido");
        }

        try {
            Usuario usuario = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário com ID " + idUsuario + " não encontrado"));

            return registroCertificadoRepository.countByUsuario(usuario);
        } catch (DataAccessException e) {
            throw new OperacaoNaoPermitidaException("Falha ao contar certificados por usuário", e);
        }
    }

    public long contarPorCurso(Integer idCurso) {
        if (idCurso == null || idCurso <= 0) {
            throw new CampoObrigatorioException("ID do curso é inválido");
        }

        try {
            Curso curso = cursoRepository.findById(idCurso)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Curso com ID " + idCurso + " não encontrado"));

            return registroCertificadoRepository.countByCurso(curso);
        } catch (DataAccessException e) {
            throw new OperacaoNaoPermitidaException("Falha ao contar certificados por curso", e);
        }
    }

    public List<RegistroCertificadoDTO> listarPorUsuarioECurso(Integer idUsuario, Integer idCurso) {
        validarIds(idUsuario, idCurso);

        try {
            Usuario usuario = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário com ID " + idUsuario + " não encontrado"));

            Curso curso = cursoRepository.findById(idCurso)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Curso com ID " + idCurso + " não encontrado"));

            List<RegistroCertificado> registros = registroCertificadoRepository.findByUsuarioAndCurso(usuario, curso);

            if (registros.isEmpty()) {
                throw new RecursoNaoEncontradoException("Nenhum certificado encontrado para este usuário e curso");
            }

            return registros.stream().map(this::toDTO).collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new OperacaoNaoPermitidaException("Falha ao listar certificados por usuário e curso", e);
        }
    }

    private void validarIds(Integer idUsuario, Integer idCurso) {
        if (idUsuario == null || idUsuario <= 0) {
            throw new CampoObrigatorioException("ID do usuário é inválido");
        }
        if (idCurso == null || idCurso <= 0) {
            throw new CampoObrigatorioException("ID do curso é inválido");
        }
    }

    private void validarElegibilidadeCertificado(Usuario usuario, Curso curso) {
        // Verifica se o usuário completou o curso
        if (!usuario.getCursosCompletos().contains(curso)) {
            throw new OperacaoNaoPermitidaException("Usuário não completou o curso necessário para o certificado");
        }

        // Verifica tipo de usuário se necessário
        if ("INSTRUTOR".equals(usuario.getTipo())) {
            throw new TipoUsuarioInvalidoException("Instrutores não podem receber certificados como participantes");
        }
    }

    private void validarDataCertificado(RegistroCertificado certificado) {
        // Validade de 5 anos para o certificado
        if (certificado.getDataEmissao().plusYears(5).isBefore(LocalDate.now())) {
            throw new DataInvalidaException("Certificado expirado (validade de 5 anos)");
        }
    }

    private String gerarCodigoUnico() {
        String codigo;
        do {
            codigo = UUID.randomUUID().toString();
        } while (registroCertificadoRepository.existsByCodigoValidacao(codigo));

        return codigo;
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