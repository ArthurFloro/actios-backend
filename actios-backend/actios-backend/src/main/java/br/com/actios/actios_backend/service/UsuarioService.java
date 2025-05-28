package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.exceptions.*;
import br.com.actios.actios_backend.model.Faculdade;
import br.com.actios.actios_backend.model.Usuario;
import br.com.actios.actios_backend.repositorys.FaculdadeRepository;
import br.com.actios.actios_backend.repositorys.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UsuarioService {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern SENHA_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

    private final UsuarioRepository usuarioRepository;
    private final FaculdadeRepository faculdadeRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository,
                          FaculdadeRepository faculdadeRepository) {
        this.usuarioRepository = usuarioRepository;
        this.faculdadeRepository = faculdadeRepository;
    }

    @Transactional
    public Usuario cadastrar(Usuario usuario) {
        validarUsuario(usuario);

        try {
            if (usuarioRepository.existsByEmail(usuario.getEmail())) {
                throw new RecursoExistenteException("E-mail já cadastrado.");
            }

            // Validação de faculdade
            if (usuario.getFaculdade() != null && usuario.getFaculdade().getIdFaculdade() != null) {
                Faculdade faculdade = faculdadeRepository.findById(usuario.getFaculdade().getIdFaculdade())
                        .orElseThrow(() -> new RecursoNaoEncontradoException("Faculdade não encontrada."));
                usuario.setFaculdade(faculdade);
            }

            usuario.setIdUsuario(null);
            // Senha armazenada em texto puro (sem criptografia)
            usuario.setDataCadastro(LocalDateTime.now());
            usuario.setAtivo(true);

            return usuarioRepository.save(usuario);
        } catch (DataAccessException e) {
            throw new OperacaoNaoPermitidaException("Falha ao cadastrar usuário no banco de dados", e);
        }
    }

    public Usuario autenticar(String email, String senha) {
        if (email == null || email.trim().isEmpty()) {
            throw new CampoObrigatorioException("E-mail é obrigatório.");
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new CampoObrigatorioException("Senha é obrigatória.");
        }

        try {
            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new CredenciaisInvalidasException("Credenciais inválidas."));

            // Comparação direta da senha em texto puro
            if (!usuario.getSenha().equals(senha)) {
                throw new CredenciaisInvalidasException("Credenciais inválidas.");
            }

            if (!usuario.isAtivo()) {
                throw new OperacaoNaoPermitidaException("Usuário inativo.");
            }

            return usuario;
        } catch (DataAccessException e) {
            throw new OperacaoNaoPermitidaException("Falha ao autenticar usuário", e);
        }
    }

    public List<Usuario> listarTodos() {
        try {
            List<Usuario> usuarios = usuarioRepository.findAll();
            if (usuarios.isEmpty()) {
                throw new RecursoNaoEncontradoException("Nenhum usuário encontrado.");
            }
            return usuarios;
        } catch (DataAccessException e) {
            throw new OperacaoNaoPermitidaException("Falha ao listar usuários", e);
        }
    }

    public Usuario buscarPorId(Integer id) {
        if (id == null || id <= 0) {
            throw new CampoObrigatorioException("ID do usuário é inválido.");
        }

        try {
            return usuarioRepository.findById(id)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));
        } catch (DataAccessException e) {
            throw new OperacaoNaoPermitidaException("Falha ao buscar usuário por ID", e);
        }
    }

    @Transactional
    public Usuario atualizar(Usuario usuario) {
        validarUsuario(usuario);

        try {
            if (!usuarioRepository.existsById(usuario.getIdUsuario())) {
                throw new RecursoNaoEncontradoException("Usuário não encontrado.");
            }

            // Verifica se o email foi alterado
            Usuario usuarioExistente = buscarPorId(usuario.getIdUsuario());
            if (!usuarioExistente.getEmail().equals(usuario.getEmail()) &&
                    usuarioRepository.existsByEmail(usuario.getEmail())) {
                throw new RecursoExistenteException("Novo e-mail já está em uso.");
            }

            // Atualizar faculdade, se fornecida
            if (usuario.getFaculdade() != null && usuario.getFaculdade().getIdFaculdade() != null) {
                Faculdade faculdade = faculdadeRepository.findById(usuario.getFaculdade().getIdFaculdade())
                        .orElseThrow(() -> new RecursoNaoEncontradoException("Faculdade não encontrada."));
                usuario.setFaculdade(faculdade);
            }

            // Mantém a senha atual se não for fornecida uma nova (sem criptografia)
            if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
                usuario.setSenha(usuarioExistente.getSenha());
            }

            return usuarioRepository.save(usuario);
        } catch (DataAccessException e) {
            throw new OperacaoNaoPermitidaException("Falha ao atualizar usuário", e);
        }
    }

    @Transactional
    public void excluir(Integer id) {
        if (id == null || id <= 0) {
            throw new CampoObrigatorioException("ID do usuário é inválido.");
        }

        try {
            Usuario usuario = buscarPorId(id);
            usuario.setAtivo(false);
            usuarioRepository.save(usuario);
        } catch (DataAccessException e) {
            throw new OperacaoNaoPermitidaException("Falha ao excluir usuário", e);
        }
    }

    private void validarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new CampoObrigatorioException("Usuário não pode ser nulo.");
        }

        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new CampoObrigatorioException("Nome é obrigatório.");
        }

        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new CampoObrigatorioException("E-mail é obrigatório.");
        }

        if (!EMAIL_PATTERN.matcher(usuario.getEmail()).matches()) {
            throw new CredenciaisInvalidasException("Formato de e-mail inválido.");
        }

        // Validação de senha apenas para novos usuários ou quando for alterada
        if (usuario.getIdUsuario() == null ||
                (usuario.getSenha() != null && !usuario.getSenha().isEmpty())) {
            if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
                throw new CampoObrigatorioException("Senha é obrigatória.");
            }

            if (!SENHA_PATTERN.matcher(usuario.getSenha()).matches()) {
                throw new CredenciaisInvalidasException("Senha deve conter pelo menos 8 caracteres, incluindo maiúsculas, minúsculas, números e caracteres especiais.");
            }
        }

        // Validação de tipo de usuário
        if (usuario.getTipo() == null || !List.of("ALUNO", "PROFESSOR", "ADMIN").contains(usuario.getTipo())) {
            throw new TipoUsuarioInvalidoException("Tipo de usuário inválido.");
        }
    }
}