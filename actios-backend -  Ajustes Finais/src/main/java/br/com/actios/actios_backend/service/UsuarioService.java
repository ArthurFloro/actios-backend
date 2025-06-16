package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.exceptions.CredenciaisInvalidasException;
import br.com.actios.actios_backend.exceptions.RecursoExistenteException;
import br.com.actios.actios_backend.exceptions.RecursoNaoEncontradoException;
import br.com.actios.actios_backend.model.Faculdade;
import br.com.actios.actios_backend.model.Usuario;
import br.com.actios.actios_backend.repositorys.FaculdadeRepository;
import br.com.actios.actios_backend.repositorys.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Serviço para operações relacionadas a usuários.
 *
 * <p>Esta classe fornece métodos para cadastrar, autenticar, listar, buscar, atualizar e excluir usuários.</p>
 */
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final FaculdadeRepository faculdadeRepository;

    /**
     * Construtor para injeção de dependências.
     *
     * @param usuarioRepository Repositório de usuários
     * @param faculdadeRepository Repositório de faculdades
     */
    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, FaculdadeRepository faculdadeRepository) {
        this.usuarioRepository = usuarioRepository;
        this.faculdadeRepository = faculdadeRepository;
    }

    /**
     * Cadastra um novo usuário no sistema.
     *
     * @param usuario O usuário a ser cadastrado
     * @return O usuário cadastrado
     * @throws RecursoExistenteException Se o e-mail já estiver cadastrado
     * @throws RecursoNaoEncontradoException Se a faculdade associada não for encontrada
     */
    public Usuario cadastrar(Usuario usuario) throws Exception {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RecursoExistenteException("E-mail já cadastrado.");
        }

        // Se vier ID de faculdade, buscar e associar
        if (usuario.getFaculdade() != null && usuario.getFaculdade().getIdFaculdade() != null) {
            Faculdade faculdade = faculdadeRepository.findById(usuario.getFaculdade().getIdFaculdade())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Faculdade não encontrada."));
            usuario.setFaculdade(faculdade);
        }

        usuario.setIdUsuario(null);  // << Aqui o ajuste para garantir que o ID será gerado pelo banco

        usuario.setDataCadastro(LocalDateTime.now());

        return usuarioRepository.save(usuario);
    }

    /**
     * Autentica um usuário com base no e-mail e senha.
     *
     * @param email O e-mail do usuário
     * @param senha A senha do usuário
     * @return O usuário autenticado
     * @throws CredenciaisInvalidasException Se as credenciais forem inválidas
     */
    public Usuario autenticar(String email, String senha) {
        // Verifica primeiro se o email existe
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isEmpty()) {
            throw new CredenciaisInvalidasException("Email não cadastrado no sistema.");
        }

        // Se o email existe, verifica a senha
        Usuario usuario = usuarioOpt.get();
        if (!usuario.getSenha().equals(senha)) {
            throw new CredenciaisInvalidasException("Senha incorreta.");
        }

        return usuario;
    }

    /**
     * Lista todos os usuários cadastrados no sistema.
     *
     * @return Uma lista de todos os usuários
     */
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    /**
     * Busca um usuário pelo seu ID.
     *
     * @param id O ID do usuário
     * @return O usuário encontrado
     * @throws RecursoNaoEncontradoException Se o usuário não for encontrado
     */
    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));
    }

    /**
     * Atualiza as informações de um usuário existente.
     *
     * @param usuario O usuário com as informações atualizadas
     * @return O usuário atualizado
     * @throws RecursoNaoEncontradoException Se o usuário ou faculdade não forem encontrados
     */
    public Usuario atualizar(Usuario usuario) throws Exception {
        if (!usuarioRepository.existsById(usuario.getIdUsuario())) {
            throw new RecursoNaoEncontradoException("Usuário não encontrado.");
        }

        // Atualizar faculdade, se fornecida
        if (usuario.getFaculdade() != null && usuario.getFaculdade().getIdFaculdade() != null) {
            Faculdade faculdade = faculdadeRepository.findById(usuario.getFaculdade().getIdFaculdade())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Faculdade não encontrada."));
            usuario.setFaculdade(faculdade);
        }

        return usuarioRepository.save(usuario);
    }

    /**
     * Exclui um usuário do sistema.
     *
     * @param id O ID do usuário a ser excluído
     * @throws RecursoNaoEncontradoException Se o usuário não for encontrado
     */
    public void excluir(Integer id) throws Exception {
        if (!usuarioRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Usuário não encontrado.");
        }
        usuarioRepository.deleteById(id);
    }
}