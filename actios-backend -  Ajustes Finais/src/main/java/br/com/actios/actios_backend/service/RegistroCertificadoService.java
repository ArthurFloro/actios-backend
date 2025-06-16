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

/**
 * Serviço responsável pelo gerenciamento de registros de certificados.
 * <p>
 * Oferece operações para criação, validação e consulta de certificados emitidos
 * para usuários que completaram cursos.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Service
public class RegistroCertificadoService {

    @Autowired
    private RegistroCertificadoRepository registroCertificadoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    /**
     * Cria um novo registro de certificado para um usuário em um curso.
     *
     * @param idUsuario ID do usuário que receberá o certificado
     * @param idCurso ID do curso concluído
     * @return DTO contendo os dados do certificado criado
     * @throws RecursoNaoEncontradoException se usuário ou curso não forem encontrados
     * @throws RecursoExistenteException se já existir certificado para este usuário e curso
     */
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

    /**
     * Valida um certificado pelo seu código de validação.
     *
     * @param codigoValidacao Código único de validação do certificado
     * @return DTO contendo os dados do certificado validado
     * @throws RecursoNaoEncontradoException se o certificado não for encontrado
     */
    public RegistroCertificadoDTO validarCertificado(String codigoValidacao) {
        RegistroCertificado certificado = registroCertificadoRepository.findByCodigoValidacao(codigoValidacao);
        if (certificado == null) {
            throw new RecursoNaoEncontradoException("Certificado com código '" + codigoValidacao + "' não encontrado ou inválido");
        }
        return toDTO(certificado);
    }

    /**
     * Lista todos os certificados de um usuário específico.
     *
     * @param idUsuario ID do usuário
     * @return Lista de DTOs contendo os certificados do usuário
     * @throws RecursoNaoEncontradoException se o usuário não for encontrado
     */
    public List<RegistroCertificadoDTO> listarPorUsuario(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário com ID " + idUsuario + " não encontrado"));

        return registroCertificadoRepository.findByUsuario(usuario)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    /**
     * Lista todos os certificados emitidos para um curso específico.
     *
     * @param idCurso ID do curso
     * @return Lista de DTOs contendo os certificados do curso
     * @throws RecursoNaoEncontradoException se o curso não for encontrado
     */
    public List<RegistroCertificadoDTO> listarPorCurso(Integer idCurso) {
        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Curso com ID " + idCurso + " não encontrado"));

        return registroCertificadoRepository.findByCurso(curso)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    /**
     * Conta quantos certificados um usuário possui.
     *
     * @param idUsuario ID do usuário
     * @return Quantidade de certificados do usuário
     * @throws RecursoNaoEncontradoException se o usuário não for encontrado
     */
    public long contarPorUsuario(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário com ID " + idUsuario + " não encontrado"));

        return registroCertificadoRepository.countByUsuario(usuario);
    }

    /**
     * Conta quantos certificados foram emitidos para um curso.
     *
     * @param idCurso ID do curso
     * @return Quantidade de certificados emitidos para o curso
     * @throws RecursoNaoEncontradoException se o curso não for encontrado
     */
    public long contarPorCurso(Integer idCurso) {
        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Curso com ID " + idCurso + " não encontrado"));

        return registroCertificadoRepository.countByCurso(curso);
    }

    /**
     * Lista certificados específicos de um usuário em um curso.
     *
     * @param idUsuario ID do usuário
     * @param idCurso ID do curso
     * @return Lista de DTOs contendo os certificados encontrados
     * @throws RecursoNaoEncontradoException se usuário ou curso não forem encontrados
     */
    public List<RegistroCertificadoDTO> listarPorUsuarioECurso(Integer idUsuario, Integer idCurso) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário com ID " + idUsuario + " não encontrado"));

        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Curso com ID " + idCurso + " não encontrado"));

        return registroCertificadoRepository.findByUsuarioAndCurso(usuario, curso)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    /**
     * Converte um RegistroCertificado em seu DTO correspondente.
     *
     * @param registro Entidade RegistroCertificado
     * @return DTO com os dados do certificado
     */
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